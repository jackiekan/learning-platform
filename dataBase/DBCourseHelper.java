
package dataBase;

import java.sql.*;
import java.util.ArrayList;

import sharedObjects.*;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the database helper for the course table
 */
public class DBCourseHelper extends DBTableHelper{

	private String psql;
	
	public DBCourseHelper(Connection c) {
		super(c, "Courses");
	}
	
	/**
	 * Updates the active status of a course
	 * @param course the course to update
	 * @param active the new status of the course
	 */
	public void modifyActivation(Course course, boolean active) {
		psql = "UPDATE " + tableName + " SET `active`=? " + "WHERE `id`=?";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setBoolean(1, active);
			pStat.setInt(2, course.getID());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a new course to the database
	 * @param course the course to add
	 */
	public void addCourse(Course course) {
		psql = "INSERT INTO " + tableName + " (`prof_id`, `name`, `active`) VALUES (?,?,?)";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, course.getProfID());
			pStat.setString(2, course.getName());
			pStat.setBoolean(3, course.getActive());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a list of the courses belonging to a professor
	 * @param prof the professor who's courses are required
	 * @return the list of courses found
	 */
	public ArrayList<Course> browseProfCourses(Professor prof){
		psql = "SELECT * FROM " + tableName + " WHERE prof_id=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, prof.getID());
			results = pStat.executeQuery();
			return extractCourses(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Private helper function that receives a result set and 
	 * extracts a list of courses from it
	 * @param results the result set
	 * @return the list of extracted courses
	 */
	private ArrayList<Course> extractCourses(ResultSet results){
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			while(results.next()) 
				courses.add(new Course(results.getInt("id"), results.getInt("prof_id"), results.getString("name"), results.getBoolean("active")));	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	/**
	 * Returns the professor teaching a course
	 * @param course the course who's professor is requested
	 * @return the professor of the course
	 */
	public ArrayList<Integer> browseCourseProf(Course course) {
		psql = "SELECT * FROM " + tableName + " WHERE id=?";
		ResultSet results;
		ArrayList<Integer> profIDList = new ArrayList <Integer>();
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, course.getID());
			results = pStat.executeQuery();
			while (results.next()) {
				profIDList.add(results.getInt("prof_id"));
			}
			return profIDList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns the courses based on a list of course ID's
	 * @param courseIDList the list of the course ID's
	 * @return the courses found
	 */
	public ArrayList <Course> extractStudentCourses(ArrayList <Integer> courseIDList) {
		psql = "SELECT * FROM " + tableName + " WHERE id=?";
		ResultSet results;
		ArrayList<Course> courseList = new ArrayList<Course>();
		
		PreparedStatement pStat;
		try {
			pStat = connection.prepareStatement(psql);
			int i = 0;
			while (i < courseIDList.size()) {
				int courseID = courseIDList.get(i);
				pStat.setInt(1, courseID);
				results = pStat.executeQuery();
				while (results.next()) {
					courseList.add(new Course(results.getInt("id"), results.getInt("prof_id"), results.getString("name"), results.getBoolean("active")));
				}
				i++;
			}
			return courseList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
