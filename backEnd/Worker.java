package backEnd;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javax.mail.AuthenticationFailedException;

import dataBase.DBHelper;
import sharedObjects.*;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the primary worker class that handles all communications with the client
 * and carries out client requests
 */
public class Worker implements Runnable{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private DBHelper dbHelper;
	private FileHelper fileHelper;
	private EmailHelper emailHelper;
	
	/**
	 * Constructs new worker and sets up input & output streams, as well as 
	 * assign all helpers
	 * @param s the socket connected to the client
	 * @param db the database helper
	 * @param fh the file helper
	 * @param eh the email helper
	 */
	public Worker(Socket s, DBHelper db, FileHelper fh, EmailHelper eh) {
		socket = s;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbHelper = db;
		fileHelper = fh;
		emailHelper = eh;
	}
	
	/**
	 * Authenticates the user based on the login info received 
	 * and returns result to client
	 */
	private void authenticateUser() {
		LoginInfo loginInfo = null;
		User user = null;
		try {
			loginInfo = (LoginInfo)in.readObject();
			user = dbHelper.getUserHelper().authenticateUser(loginInfo);
			out.writeObject(user);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 *  Returns the course list of a professor after receiving
	 *  the professor object from client
	 */
	private void getProfCourses() {
		ArrayList<Course> courseList;
		try {
			Professor prof = (Professor)in.readObject();
			courseList = dbHelper.getCourseHelper().browseProfCourses(prof);
			out.writeObject(courseList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the course list of a student after receiving
	 * the student object from client
	 */
	private void getStudentCourses() {
		ArrayList<Course> courseList;
		Student student;
		try {
			student = (Student)in.readObject();
			ArrayList<Integer> courseIDList = dbHelper.getEnrollmentHelper().browseStudentCourses(student);
			courseList = dbHelper.getCourseHelper().extractStudentCourses(courseIDList);
			out.writeObject(courseList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives assignment info from client and utilizes file helper
	 * to save assignment on server and save info to database
	 */
	private void uploadAssignment() {
		byte[] content;
		String extension;
		Assignment assignment;
		try {
			content = (byte[])in.readObject();
			extension = (String)in.readObject();
			assignment = (Assignment)in.readObject();
			assignment.setPath(fileHelper.getAssignmentDirectory() + assignment.getTitle() + extension);
			fileHelper.writeFileContent(assignment, content);
			dbHelper.getAssignmentHelper().addAssignment(assignment);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the students enrolled in a course after receiving
	 * the course object from client
	 */
	private void getCourseStudents() {
		ArrayList<Student> studentList;
		try {
			Course course = (Course)in.readObject();
			ArrayList <Integer> studentIDList = dbHelper.getEnrollmentHelper().browseEnrollment(course);
			studentList = dbHelper.getUserHelper().extractUsersName(studentIDList);
			out.writeObject(studentList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the assignments belonging to a course after receiving
	 * the course object from client
	 */
	private void getAssignments() {
		ArrayList<Assignment> assignmentList;
		try {
			Course course = (Course)in.readObject();
			assignmentList = dbHelper.getAssignmentHelper().getAssignments(course);
			out.writeObject(assignmentList);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the active status of an assignment
	 */
	private void updateAssignment() {
		try {
			Assignment toUpdate = (Assignment)in.readObject();
			boolean active = (boolean)in.readObject();
			toUpdate.setActive(active);
			dbHelper.getAssignmentHelper().updateAssignment(toUpdate);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches the database for a student using their ID and returns
	 * the result to client
	 */
	private void searchStudentsByID() {
		try {
			String parameter = (String)in.readObject();
			ArrayList<User> results = dbHelper.getUserHelper().searchByID(Integer.parseInt(parameter));
			out.writeObject(results);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) { 
			try {
				out.writeObject(new ArrayList<User>());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Searches the database for a student using their lastname and returns
	 * the result to client
	 */
	private void searchStudentsByLastname() {
		try {
			String parameter = (String)in.readObject();
			ArrayList<User> results = dbHelper.getUserHelper().searchByLastname(parameter);
			out.writeObject(results);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a course from client and adds it 
	 * to the database
	 */
	public void addCourse() {
		try {
			Course course = (Course)in.readObject();
			dbHelper.getCourseHelper().addCourse(course);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Updates the active status of a course in the database
	 */
	public void activateCourse() {
		try {
			boolean active = (boolean)in.readObject();
			Course course = (Course)in.readObject();
			dbHelper.getCourseHelper().modifyActivation(course, active);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a student and course from client and unenrolls
	 * the student by updating the database
	 */
	public void unenrollStudent() {
		try {
			Student student = (Student)in.readObject();
			Course course = (Course)in.readObject();
			dbHelper.getEnrollmentHelper().unenrollStudent(student, course);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives the ID of a student and the course to enroll them in
	 * and updates the database
	 */
	private void enrollStudentByID() {
		try {
			Integer id = (Integer)in.readObject();
			Course course = (Course)in.readObject();
			ArrayList<User> list = dbHelper.getUserHelper().searchByID(id);
			if(!list.isEmpty() && list.get(0).getType() == 'S') {
				dbHelper.getEnrollmentHelper().enrollStudent((Student)list.get(0), course);
				ArrayList<Integer> studentIDs = dbHelper.getEnrollmentHelper().browseEnrollment(course);
				out.writeObject(dbHelper.getUserHelper().extractUsersName(studentIDs));
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives the lastname of a student and the course to enroll them in
	 * and updates the database
	 */
	private void enrollStudentByLastname() {
		try {
			String lastname = (String)in.readObject();
			Course course = (Course)in.readObject();
			ArrayList<User> list = dbHelper.getUserHelper().searchByLastname(lastname);
			if(!list.isEmpty() && list.get(0).getType() == 'S') {
				dbHelper.getEnrollmentHelper().enrollStudent((Student)list.get(0), course);
				ArrayList<Integer> studentIDs = dbHelper.getEnrollmentHelper().browseEnrollment(course);
				out.writeObject(dbHelper.getUserHelper().extractUsersName(studentIDs));
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a course and returns the professor of the course to the client
	 */
	private void getCourseProfessor() {
		try {
			Course course = (Course)in.readObject();
			ArrayList <Integer> profIDList = dbHelper.getCourseHelper().browseCourseProf(course);
			ArrayList<User> profList = dbHelper.getUserHelper().searchByID(profIDList.get(0));
			out.writeObject((Professor)profList.get(0));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a course from the client and returns the assignments
	 * belonging to the course
	 */
	private void getCourseAssignments() {
			try {
				Course course = (Course)in.readObject();
				ArrayList<Assignment> assignmentList = dbHelper.getAssignmentHelper().getAssignments(course);
				ArrayList<Assignment> activeAssignmentList = new ArrayList<Assignment>();
				for(Assignment a: assignmentList) {
					if(a.getActive())
						activeAssignmentList.add(a);
				}
				out.writeObject(activeAssignmentList);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Receives an assignment form the client and returns the contents
	 * of the assignment to be downloaded
	 */
	private void downloadAssignment() {
		try {
			Assignment assignment = (Assignment)in.readObject();
			byte [] content = fileHelper.getFileContent(assignment.getPath());
			out.writeObject(content);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives submission info from client and utilizes file helper
	 * to save submission on server and save info to database
	 */
	private void uploadSubmission() {
		byte[] content;
		String extension;
		Submission submission;
		try {
			content = (byte[])in.readObject();
			extension = (String)in.readObject();
			submission = (Submission)in.readObject();
			submission.setPath(fileHelper.getSubmissionDirectory() + submission.getTitle() + extension);
			fileHelper.writeFileContent(submission, content);
			dbHelper.getSubmissionHelper().addSubmission(submission);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives loginInfo and email object from client
	 * and send the email to the appropriate addresses 
	 * using the email helper
	 */
	private void sendEmail() {
		try {
			EmailLoginInfo loginInfo = (EmailLoginInfo)in.readObject();
			Email email = (Email)in.readObject();
			emailHelper.sendEmail(email, loginInfo);
			out.writeObject("Email successfully sent!");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (AuthenticationFailedException e) {
			try {
				out.writeObject("Authentication Failed.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Receives an assignment and returns the submissions 
	 * associated with that assignment
	 */
	private void getAssignmentSubmissions() {
		ArrayList <Submission> submissionList = new ArrayList <Submission>();
		try { 
			Assignment assignment = (Assignment)in.readObject();
			submissionList = dbHelper.getSubmissionHelper().getSubmissions(assignment);
			out.writeObject(submissionList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a submission object from client and returns the 
	 * content of the submission to client to be downloaded
	 */
	private void downloadSubmission() {
		try {
			Submission submission = (Submission)in.readObject();
			byte [] content = fileHelper.getFileContent(submission.getPath());
			out.writeObject(content);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives submission object and grade from client and updates
	 * the database
	 */
	private void updateSubmissionGrade() {
		try {
			Submission submission = (Submission)in.readObject();
			int grade = (Integer)in.readObject();
			dbHelper.getSubmissionHelper().updateSubmissionGrade(submission, grade);
			out.writeObject("updated grades");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives list of assignments from client and returns the corresponding
	 * submissions with their grades to client
	 */
	private void viewStudentGrades() {
		try {
			ArrayList <Submission> latestSubmissions = new ArrayList<Submission>();
			ArrayList <Assignment> assignmentList = (ArrayList<Assignment>)in.readObject();
			Student student = (Student)in.readObject();
			for (Assignment a: assignmentList) {
				Submission s = dbHelper.getSubmissionHelper().getLatestSubmission(a, student);
				if(s != null)
					latestSubmissions.add(s);
			}
			out.writeObject(latestSubmissions);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a command and executes the appropriate method
	 * @param command the command to be executed
	 */
	private void executeCommand(Command command) {
		switch(command.getCommand()) {
		
		case "login" : authenticateUser();
						break;
						
		case "getProfCourses": getProfCourses();
						break;
		
		case "uploadAssignment": uploadAssignment();
						break;

		case "selectCourse": getCourseStudents();
						break;
		
		case "getAssignmentsForCourse": getAssignments();
						break;
		
		case "updateAssignment": updateAssignment();
						break;
		
		case "searchStudentsByID": searchStudentsByID();
						break;
						
		case "searchStudentsByLastname": searchStudentsByLastname();
						break;
						
		case "createCourse": addCourse();
						break;
		
		case "activateCourse": activateCourse();
						break;
						
		case "unenrollStudent": unenrollStudent();
						break;

		case "enrollStudentByID": enrollStudentByID();
						break;
		
		case "enrollStudentByLastname": enrollStudentByLastname();
						break;
						
		case "getStudentCourses": getStudentCourses();
						break;
						
		case "getCourseProfessor": getCourseProfessor();
						break;
		
		case "getCourseAssignments": getCourseAssignments();
						break;
		
		case "downloadAssignment": downloadAssignment();
						break;
		
		case "uploadSubmission": uploadSubmission();
						break;
		
		case "sendEmail": sendEmail();
						break;
		
		case "getSubmissionsForAssignment": getAssignmentSubmissions();
						break;
						
		case "downloadSubmission": downloadSubmission();
						break;
						
		case "updateSubmissionGrade": updateSubmissionGrade();
						break;
						
		case "viewStudentGrades": viewStudentGrades();
					break;
		}
	}

	/**
	 * Continually receives commands from client and executes
	 * the command
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Command command = (Command)in.readObject();
				executeCommand(command);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
		}
	}
}
