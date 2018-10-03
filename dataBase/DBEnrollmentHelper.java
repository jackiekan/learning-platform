
package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sharedObjects.Course;
import sharedObjects.Student;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the database helper for the student enrollment table
 */
public class DBEnrollmentHelper extends DBTableHelper {
	
	private String psql;

	public DBEnrollmentHelper(Connection c) {
		super(c, "StudentEnrollment");
	}
	
	/**
	 * Receives a course and returns the list of enrolled student ID's
	 * @param course the course
	 * @return the list of ID's enrolled in the course
	 */
	public ArrayList<Integer> browseEnrollment(Course course){
		psql = "SELECT * FROM " + tableName + " WHERE course_id=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, course.getID());
			results = pStat.executeQuery();
			return extractStudentID(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Private helper function that receives a result set and 
	 * extracts the list of student ID's contained in the set
	 * @param results the result set obtained from the database
	 * @return the list of Student ID's
	 */
	private ArrayList<Integer> extractStudentID(ResultSet results){
		ArrayList<Integer> studentIDList = new ArrayList<Integer>();
		try {
			while(results.next()) {
				studentIDList.add(results.getInt("student_id"));	
			}
			return studentIDList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Receives a student and returns a list of course ID's in which
	 * the student is enrolled
	 * @param student the student being searched
	 * @return the list of course ID's
	 */
	public ArrayList<Integer> browseStudentCourses(Student student) {
		psql = "SELECT * FROM " + tableName + " WHERE student_id=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, student.getID());
			results = pStat.executeQuery();
			return extractCourseID(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Private helper function which receives a result set and 
	 * extracts the list of course ID's contained in the set
	 * @param results the result set obtained from the database
	 * @return the list of extracted course ID's
	 */
	private ArrayList<Integer> extractCourseID(ResultSet results) {
		ArrayList<Integer> courseIDList = new ArrayList<Integer>();
		try {
			while(results.next()) {
				courseIDList.add(results.getInt("course_id"));	
			}
			return courseIDList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Unenrolls a student from a course
	 * @param student the student being unenrolled
	 * @param course the course to unenroll the student from
	 */
	public void unenrollStudent(Student student, Course course) {
		psql = "DELETE FROM " + tableName + " WHERE student_id=?";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, student.getID());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Enrolls a student in a course
	 * @param student the student being enrolled
	 * @param course the course to enroll the student in
	 */
	public void enrollStudent(Student student, Course course) {
		psql = "INSERT INTO " + tableName + " (`student_id`, `course_id`) VALUES (?,?)";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, student.getID());
			pStat.setInt(2, course.getID());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
