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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sharedObjects.Assignment;
import sharedObjects.Command;
import sharedObjects.Course;
import sharedObjects.Email;
import sharedObjects.EmailLoginInfo;
import sharedObjects.Professor;
import sharedObjects.Student;
import sharedObjects.Submission;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the main controller for the student GUI responsible for
 * handling all events and communication between views 
 */

public class StudentController extends Controller {
	
	StudentHomeView homeView;
	StudentCourseView courseView;
	StudentGradeView gradeView;
	Course course;
	
	/**
	 * Constructs a new controller with the student and client it receives
	 * as arguments
	 * @param student the user of the program
	 * @param client the client object
	 */
	public StudentController(Student student, Client client) {
		super(student, client);
		
		homeView = new StudentHomeView();
		initializeHomeView();
		
		courseView = new StudentCourseView();
		gradeView = new StudentGradeView();
	}

	public void initializeHomeView() {
		homeView.setVisible(true);
		homeView.addHomeListener(new SearchCourseListener());
		homeView.setCourses(retrieveCourses());
	}
	
	/**
	 * initlialized the home view and course view when needed
	 */
	public void initializeCourseView() {
		
		courseView.addClosingListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				courseView.setVisible(false);
				homeView.setVisible(true);
			}
		});
		
		course = homeView.getSelectedCourse();
		if (course == null) {
			JOptionPane.showMessageDialog(null, "Please select a course.");
		}
		else {
			homeView.setVisible(false);
			courseView.setProfName(getProf());
			courseView.setCourseName(course);
			courseView.setAssignments(retrieveAssignments());
			courseView.addAssignmentActionListener(new AssignmentActionButtonListener());
			courseView.addEmailListener(new sendEmailListener());
			courseView.addGradeListener(new ViewGradeListener());
			courseView.setVisible(true);
		}
	}
	
	/**
	 * initializes the grade view
	 */
	public void initializeGradeView() {
		gradeView.setCourseName(course);
		gradeView.setVisible(true);
		gradeView.setGrades();
		
		gradeView.addClosingListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				gradeView.setVisible(false);
				courseView.setVisible(true);
			}
		});
	}
	
	/**
	 * returns the professor of the selected course
	 */
	public Professor getProf() {
		Command c = new Command("getCourseProfessor");
		client.sendToServer(c);
		client.sendToServer(course);
		return (Professor)client.getObjectFromServer();
	}
	
	/**
	 * returns the courses of the student
	 */
	public ArrayList<Course> retrieveCourses() {
		Command c = new Command("getStudentCourses");
		client.sendToServer(c);
		client.sendToServer(user);
		ArrayList<Course> courseList = (ArrayList<Course>) client.getObjectFromServer();
		return courseList;
	}
	
	/**
	 * returns the assignment of the selected course
	 * @return
	 */
	public ArrayList<Assignment> retrieveAssignments(){
		Command c = new Command("getCourseAssignments");
		client.sendToServer(c);
		client.sendToServer(course);
		ArrayList<Assignment> assignmentList = (ArrayList<Assignment>)client.getObjectFromServer();
		return assignmentList;
	}
	
	public class SearchCourseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Command c = new Command(e.getActionCommand());
			client.sendToServer(c);
			initializeCourseView();
		}
	}
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class for the select assignment button
	 */
	public class AssignmentActionButtonListener implements ActionListener{
		
		/**
		 * if an assignment is selected, do the appropriate action; else displays a dialog box
		 * relaying the message that an assignment must be selected
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (courseView.getSelectedAssignment() == null) {
				JOptionPane.showMessageDialog(null, "Please select an assignment.");
			}
			else {
				assignmentAction();
			}
		}
		
		/**
		 * if the user selects to download the assignment, do so; if the user selects to upload a submission.
		 * do so; else, display an error message using a dialog box
		 */
		private void assignmentAction() {
			if(courseView.getSelectedAction() == 1) {
				downloadAssignment();
			}
			else if(courseView.getSelectedAction() == 2) {
				uploadSubmission();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select an action from the drop-down menu.");
			}
		}
		
		/**
		 * downloads the selected assignment 
		 */
		private void downloadAssignment() {
			Assignment assignment = courseView.getSelectedAssignment();
			client.sendToServer(new Command("downloadAssignment"));
			client.sendToServer(assignment);
			byte[] content = (byte[])client.getObjectFromServer();
			String extension = assignment.getPath().substring(assignment.getPath().lastIndexOf('.'));
			String directory = getFileDirectory();
			if(directory != null) {
				String path = directory + "/" + assignment.getTitle() + extension;
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
		 * Upload a submission for an assignment from the client's machine
		 */
		private void uploadSubmission() {
			JFileChooser fileBrowser = new JFileChooser();
			File selectedFile;
			if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileBrowser.getSelectedFile();
				byte[] content = getFileContent(selectedFile);
				String title = JOptionPane.showInputDialog("Please enter the submission title:");
				String comments = JOptionPane.showInputDialog("If you would like, please enter your comments for the submission:");
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				Submission submission = new Submission(0, courseView.getSelectedAssignment().getID(), user.getID(), null, -1, 
						comments, title, sdf.format(timestamp));
				String extension = selectedFile.getPath().substring(selectedFile.getPath().lastIndexOf('.'));
				client.sendToServer(new Command("uploadSubmission"));
				client.sendToServer(content);
				client.sendToServer(extension);
				client.sendToServer(submission);
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
	 * This is the listener class responsible for handling user request
	 * to send an email to the professor of the course
	 */
	public class sendEmailListener implements ActionListener{
		
		/**
		 * Sends all required info for the email to the server to 
		 * handle sending the email to the professor of the course
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<String> recipients = new ArrayList<String>();
			recipients.add(getProf().getEmail());
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
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * This is the listener class responsible for the view grade page button
	 */
	public class ViewGradeListener implements ActionListener {
		ArrayList <Submission> gradesList = new ArrayList<Submission>();
		
		/**
		 * displays the grades of the student for all submissions for that course 
		 * in the grade view
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Command c = new Command("viewStudentGrades");
			ArrayList<Assignment> assignmentList = courseView.getAssignmentList();
			client.sendToServer(c);
			client.sendToServer(assignmentList);
			client.sendToServer(user);
			gradesList = (ArrayList <Submission>)client.getObjectFromServer();
			gradeView.setGradesList(gradesList);
			gradeView.setOverallGrade();
			courseView.setVisible(false);
			initializeGradeView();
		}
		
	}

}
