
package frontEnd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sharedObjects.Assignment;
import javax.swing.JTextField;
import sharedObjects.Command;
import sharedObjects.Course;
import sharedObjects.Email;
import sharedObjects.EmailLoginInfo;
import sharedObjects.Professor;
import sharedObjects.Submission;
import sharedObjects.User;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the main controller for the professor GUI responsible for
 * handling all events and communication between views 
 */
public class ProfController extends Controller {
	
	ProfHomeView homeView;
	ProfCourseView courseView;
	ProfAssignmentView assignmentView;
	ProfDropBoxView dropboxView;
	
	Course course;
	
	/**
	 * Constructs a new controller with the professor and client it receives
	 * as arguments
	 * @param prof the user of the program
	 * @param client the client object
	 */
	public ProfController(Professor prof, Client client) {
		super(prof, client);

		homeView = new ProfHomeView();
		homeView.addHomeListener(new SearchCourseListener(), new AddCourseListener());
		courseView = new ProfCourseView();
		courseView.addCourseListener(new ActivateCourseListener(), new UnenrollStudentListener(), new EnrollStudentListener());
		assignmentView = new ProfAssignmentView();
		dropboxView = new ProfDropBoxView();
		
		courseView.addClosingListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				courseView.setVisible(false);
				homeView.setVisible(true);
			}
		});
		
		assignmentView.addCloseWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				assignmentView.setVisible(false);
				courseView.setVisible(true);
			}
		});
		
		initializeHomeView();
	}
	
	/**
	 * Initializes the home view
	 */
	public void initializeHomeView() {
		homeView.setVisible(true);
		homeView.setCourses(retrieveCourses());
	}
	
	/**
	 * Initializes the course view
	 */
	public void initializeCourseView() {
		course = homeView.getSelectedCourse();
		courseView.setCourseName(course.getName());
		client.sendToServer(course);
		if (course.getActive() == true) {
			courseView.getActiveCheckBox().setSelected(true);
		}
		else {
			courseView.getActiveCheckBox().setSelected(false);
		}
		courseView.setVisible(true);
		courseView.addNewAssignmentListener(new uploadAssignmentButtonListener());
		courseView.addBrowseAssignmentListener(new BrowseAssignmentsListener());
		courseView.addSearchListener(new StudentSearchListener());
		courseView.addEmailButtonListener(new EmailButtonListener());
		ArrayList <User> studentList = (ArrayList<User>) client.getObjectFromServer();
		courseView.setStudents(studentList);
		courseView.setCourse(course);
	}
	
	/**
	 * Initializes the drop-box view
	 */
	public void initializeDropBoxView() {
		Assignment assignment = assignmentView.getSelectedAssignment();
		dropboxView.setAssignmentName(assignment);
		dropboxView.addSubmissionActionListener(new SubmissionActionListener());
		dropboxView.addEnterGradeListener(new AddGradeListener());
		dropboxView.setVisible(true);
		dropboxView.addClosingListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				assignmentView.setVisible(true);
				dropboxView.setVisible(false);
				
			}
		});
	}
	
	/**
	 * Requests the courses belonging to the professor from the server
	 * and returns the result
	 */
	public ArrayList<Course> retrieveCourses(){
		Command c = new Command("getProfCourses");
		client.sendToServer(c);
		client.sendToServer(user);
		ArrayList<Course> courseList = (ArrayList<Course>) client.getObjectFromServer();
		return courseList;
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This class is the listener for the upload assignment button which allows
	 * the professor to upload a file from their device to the server
	 */
	public class uploadAssignmentButtonListener implements ActionListener {
		
		/**
		 * Opens the file manager and sends the selected file to the server
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileBrowser = new JFileChooser();
			File selectedFile;
			if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileBrowser.getSelectedFile();
				byte[] content = getFileContent(selectedFile);
				String title = JOptionPane.showInputDialog("Please enter the assignment title.");
				String dueDate = JOptionPane.showInputDialog("Please enter the due date of the assignment.");
				Assignment a = new Assignment(0, courseView.getCourse().getID(), title, null, false, dueDate);
				client.sendToServer(new Command("uploadAssignment"));
				client.sendToServer(content);
				client.sendToServer(getFileExtension(selectedFile.getPath()));
				client.sendToServer(a);
				assignmentView.setAssignments(retrieveAssignments(courseView.getCourse()));
			}
		}
		
		/**
		 * Reads the contents of a file into an array of bytes
		 * @param selectedFile the file to be read
		 * @return the content of the file
		 */
		private byte[] getFileContent(File selectedFile) {
			long length = selectedFile.length();
			byte[] content = new byte[(int)length];
			try {
				FileInputStream fis = new FileInputStream(selectedFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				bis.read(content, 0, (int)length);
				bis.close();
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
			return content;
		}
		
		/**
		 * Returns the extension of a file
		 * @param path the path of the file
		 * @return the extension of the file
		 */
		private String getFileExtension(String path) {
			return path.substring(path.lastIndexOf('.'));
		}
		
		/**
		 * Returns the set of assignments belonging to the course
		 * @param c the course
		 * @return the assignments for the course
		 */
		private ArrayList<Assignment> retrieveAssignments(Course c){
			client.sendToServer(new Command("getAssignmentsForCourse"));
			client.sendToServer(c);
			ArrayList<Assignment> list = (ArrayList<Assignment>)client.getObjectFromServer();
			return list;
		}
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the submissions select button
	 */
	public class SubmissionActionListener implements ActionListener{

		/**
		 * If user has selected to download the submission, download submission, else grade the submission
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(dropboxView.getSelectedAction() == 1) {
				downloadSubmission();
			}
			else if(dropboxView.getSelectedAction() == 2) {
				gradeSubmission();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select an action from the drop-down menu.");
			}
		}
		
		/**
		 * Makes the grade panel visible
		 */
		private void gradeSubmission() {
			dropboxView.getGradePanel().setVisible(true);
		}
		
		/**
		 * Allows user to download the selected submission onto their device
		 */
		private void downloadSubmission() {
			Submission submission = dropboxView.getSelectedSubmission();
			client.sendToServer(new Command("downloadSubmission"));
			client.sendToServer(submission);
			byte[] content = (byte[])client.getObjectFromServer();
			String extension = submission.getPath().substring(submission.getPath().lastIndexOf('.'));
			String directory = getFileDirectory();
			if(directory != null) {
				String path = directory + "/" + submission.getTitle() + extension;
				writeContent(content, path);
			}
		}
		
		/**
		 * Get the directory to download a submission into
		 * @return the directory path
		 */
		private String getFileDirectory() {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select download destination");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				return chooser.getSelectedFile().getPath();
			}
			else
				return null;
		}
		
		/**
		 * Write the content of a file onto the client's machine
		 * @param content the content of the file
		 * @param path the path of the file
		 */
		private void writeContent(byte[] content, String path) {
			File newFile = new File(path);
			try {
				if(! newFile.exists()) 
					newFile.createNewFile();
				FileOutputStream writer = new FileOutputStream(newFile);
				BufferedOutputStream bos = new BufferedOutputStream(writer);
				bos.write(content);
				bos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	
		/**
		 * Return the content of a selected file as an array of bytes
		 * @param selectedFile the file to be read
		 * @return the file content
		 */
		private byte[] getFileContent(File selectedFile) {
			long length = selectedFile.length();
			byte[] content = new byte[(int)length];
			try {
				FileInputStream fis = new FileInputStream(selectedFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				bis.read(content, 0, (int)length);
				bis.close();
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
			return content;
		}
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the grade submission button
	 */
	public class AddGradeListener implements ActionListener {
		
		/**
		 * Allows user to input a grade for the submission and sends it to the server
		 * to be updated in the database
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String grade = dropboxView.getGrade();
			if (!grade.equals("") && Integer.parseInt(grade) >= 0 && Integer.parseInt(grade) <= 100) {
				Command c = new Command ("updateSubmissionGrade");
				dropboxView.getSelectedSubmission().setGrade(Integer.parseInt(grade));
				client.sendToServer(c);
				client.sendToServer(dropboxView.getSelectedSubmission());
				client.sendToServer(Integer.parseInt(grade));
				if(client.getObjectFromServer().equals("updated grades")) {
					JOptionPane.showMessageDialog(null, "Grade updated successfully.");
				}
				dropboxView.clearGrade();
				dropboxView.getGradePanel().setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "Please enter a valid grade from 0-100.");
			}
		}
		
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the browse dropbox button
	 * which allows the user to see all submissions
	 */
	public class BrowseDropboxListener implements ActionListener {

		/**
		 * Displays dropbox view for the particular assignment
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Assignment assignment = assignmentView.getSelectedAssignment();
			if (assignment == null) {
				JOptionPane.showMessageDialog(null, "Please select an assignment.");
			}
			else {
				assignmentView.setVisible(false);
				initializeDropBoxView();
				dropboxView.setSubmissions(retrieveSubmissions(assignment));
			}
		}
		
		/**
		 * Retrieves a list of submissions for a specific assignment
		 * @param a the assignment selected
		 * @return the list of submissions
		 */
		private ArrayList <Submission> retrieveSubmissions(Assignment a) {
			client.sendToServer(new Command ("getSubmissionsForAssignment"));
			client.sendToServer(a);
			return (ArrayList <Submission>)client.getObjectFromServer();
		}
		
	}

	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the select course button
	 */
	public class SearchCourseListener implements ActionListener {

		/**
		 * Displays the course view for the selected course
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Command c = new Command(e.getActionCommand());
			client.sendToServer(c);
			homeView.setVisible(false);
			initializeCourseView();
		}
		
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener for the add course button
	 */
	public class AddCourseListener implements ActionListener {

		/**
		 * Retrieves name of the course and active status and sends information to server
		 * to be added as a new course
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!homeView.getNewCourseField().equals("")) {
				Command c = new Command (e.getActionCommand());
				client.sendToServer(c);
				Course newCourse = new Course (1, user.getID(), homeView.getNewCourseField(), homeView.isActive());
				client.sendToServer(newCourse);
				homeView.addToListModel(newCourse.getName());
				homeView.addToUserCourses(newCourse);
				homeView.clearNewCourse();
			}
		}
		
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 *  This is the listener class for the course active status check-box
	 */
	public class ActivateCourseListener implements ActionListener {

		/**
		 * Updates the active status of a course
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Command c = new Command(e.getActionCommand());
			client.sendToServer(c);
			if (courseView.getActiveCheckBox().isSelected()) {
				course.setActive(true);
				client.sendToServer(true);
			}
			else {
				course.setActive(false);
				client.sendToServer(false);
			}
			
			client.sendToServer(course);
		}	
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the browse assignments button inside the course view
	 */
	public class BrowseAssignmentsListener implements ActionListener{

		/**
		 * Opens up the assignment view and displays the assignments of the course
		 */
		public void actionPerformed(ActionEvent e) {
			assignmentView.setVisible(true);
			courseView.setVisible(false);
			assignmentView.addActiveListener(new AssignmentActivateListener());
			assignmentView.addNewAssignmentListener(new uploadAssignmentButtonListener());
			assignmentView.setAssignments(retrieveAssignments(courseView.getCourse()));
			assignmentView.viewDropboxListener(new BrowseDropboxListener());
		}
		
		/**
		 * Retrieves the assignments belonging to selected course
		 * @param c the course 
		 * @return the list of assignments
		 */
		private ArrayList<Assignment> retrieveAssignments(Course c){
			client.sendToServer(new Command("getAssignmentsForCourse"));
			client.sendToServer(c);
			ArrayList<Assignment> list = (ArrayList<Assignment>)client.getObjectFromServer();
			return list;
		}
		
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the activate assignment check-box
	 */
	public class AssignmentActivateListener implements ActionListener {

		/**
		 * Updates the active status of the assignment
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Assignment selected = assignmentView.getSelectedAssignment();
			selected.setActive(assignmentView.getActivatedStatus());
			client.sendToServer(new Command("updateAssignment"));
			client.sendToServer(selected);
			client.sendToServer(selected.getActive());
		}
		
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the student search button
	 */
	public class StudentSearchListener implements ActionListener {

		/**
		 * Searches the database for the list of students
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String parameter = courseView.getSearchField();
			int searchType = courseView.getSearchType();
			if(searchType == 0) {
				client.sendToServer(new Command("searchStudentsByID"));
			}
			else if(searchType == 1) {
				client.sendToServer(new Command("searchStudentsByLastname"));
			}
			client.sendToServer(parameter);
			ArrayList<User> results = (ArrayList<User>)client.getObjectFromServer();
			courseView.setStudents(results);
		}
		
	}

	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class responsible for unenrolling a student from a course
	 */
	public class UnenrollStudentListener implements ActionListener {

		/**
		 * If user decides to unenroll student, sends the command to the server
		 * and updates the database
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField selectStudentField = courseView.getStudentField();
			if (!selectStudentField.getText().equals("")) {
				ProfStudentSearchPanel panel = new ProfStudentSearchPanel();
				User student = courseView.getSelectedStudent();
				panel.getFirstName().setText(student.getFirstName());
				panel.getLastName().setText(student.getLastName());
				panel.getID().setText(String.valueOf(student.getID()));
			
				int result = JOptionPane.showConfirmDialog(null, panel, "", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION && panel.unenroll()) {
					Command c = new Command ("unenrollStudent");
					client.sendToServer(c);
					client.sendToServer(student);
					client.sendToServer(course);
					ArrayList <User> studentList = courseView.getStudentList();
					studentList.remove(student);
					courseView.setStudents(studentList);
					courseView.getStudentField().setText("");
				}
			}	
		
		}
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class responsible for enrolling a student into a course
	 */
	public class EnrollStudentListener implements ActionListener {

		/**
		 * Sends information of requested student to server to be added into
		 * the course enrollment table
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ProfEnrollPanel panel = new ProfEnrollPanel();
			Command c = null;
			int result = JOptionPane.showConfirmDialog(null, panel, "", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION && courseView.IDSelected(panel)) {
				c = new Command ("enrollStudentByID");
				int id = Integer.parseInt(panel.getStudentField());
				client.sendToServer(c);
				client.sendToServer(id);
				client.sendToServer(course);
				courseView.setStudents((ArrayList<User>)client.getObjectFromServer());
			}
			else if (result == JOptionPane.OK_OPTION && !courseView.IDSelected(panel)) {
				c = new Command ("enrollStudentByLastname");
				client.sendToServer(c);
				String lastName = panel.getStudentField();
				client.sendToServer(lastName);
				client.sendToServer(course);
				courseView.setStudents((ArrayList<User>)client.getObjectFromServer());
			}
			
		}
		
	}
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class responsible for handling user request
	 * to send an email to all students
	 */
	public class EmailButtonListener implements ActionListener{
		
		/**
		 * Sends all required info for the email to the server to 
		 * handle sending the email to students enrolled in the course
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<User> studentList = courseView.getStudentList();
			ArrayList<String> recipients = new ArrayList<String>();
			for(User u : studentList)
				recipients.add(u.getEmail());
			SendEmailPanel panel = new SendEmailPanel();
			int result = JOptionPane.showConfirmDialog(null, panel, "", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION) {
				Email email = new Email(panel.getSender(), recipients, panel.getSubject(), panel.getContent());
				EmailLoginInfo loginInfo = new EmailLoginInfo(panel.getSender(), panel.getPassword());
				client.sendToServer(new Command("sendEmail"));
				client.sendToServer(loginInfo);
				client.sendToServer(email);
				String outcome = (String)client.getObjectFromServer();
				JOptionPane.showMessageDialog(null, outcome);
			}
		}
	}
}
