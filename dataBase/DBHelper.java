
package dataBase;

import java.sql.*;
import java.util.ArrayList;

import sharedObjects.Course;
import sharedObjects.Professor;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the central database helper which contains the helpers for the specific 
 * tables and estbalishes initial connections with the database
 */
public class DBHelper {
	
	private Connection connection;
	private String connectionInfo = "jdbc:mysql://localhost:3306/ENSF409_FinalProject";
	private String login = "root", password = "ENSF409";
	
	private DBUserHelper dbUserHelper;
	private DBCourseHelper dbCourseHelper;
	private DBEnrollmentHelper dbEnrollmentHelper;
	private DBAssignmentHelper dbAssignmentHelper;
	private DBSubmissionHelper dbSubmissionHelper;
	private DBGradeHelper dbGradeHelper;
	
	/**
	 * Establishes connection with database and constructs all 
	 * database table helpers
	 */
	public DBHelper() {
		try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(connectionInfo, login, password);
				System.out.println("Connection to: " + connectionInfo);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		dbUserHelper = new DBUserHelper(connection);
		dbCourseHelper = new DBCourseHelper(connection);
		dbEnrollmentHelper = new DBEnrollmentHelper(connection);
		dbAssignmentHelper = new DBAssignmentHelper(connection);
		dbSubmissionHelper = new DBSubmissionHelper(connection);
		dbGradeHelper = new DBGradeHelper(connection);
		
	}
	
	public DBUserHelper getUserHelper() {
		return dbUserHelper;
	}
	
	public DBCourseHelper getCourseHelper() {
		return dbCourseHelper;
	}
	
	public DBEnrollmentHelper getEnrollmentHelper() {
		return dbEnrollmentHelper;
	}
	
	public DBAssignmentHelper getAssignmentHelper() {
		return dbAssignmentHelper;
	}
	
	public DBSubmissionHelper getSubmissionHelper() {
		return dbSubmissionHelper;
	}
	
	public DBGradeHelper getGradeHelper() {
		return dbGradeHelper;
	}
}
