
package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sharedObjects.Assignment;
import sharedObjects.Course;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the database helper for the assignment table
 */
public class DBAssignmentHelper extends DBTableHelper{

	private String psql;
	
	public DBAssignmentHelper(Connection c) {
		super(c, "Assignments");
	}
	
	/**
	 * Adds a new assignment into the database
	 * @param a the assignment to be added
	 */
	public void addAssignment(Assignment a) {
		psql = "INSERT INTO " + tableName + " (`courseID`, `title`, `path`, `active`, `due_date`) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, a.getCourseID());
			pStat.setString(2, a.getTitle());
			pStat.setString(3, a.getPath());
			pStat.setBoolean(4, a.getActive());
			pStat.setString(5, a.getDueDate());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the active status of an assignment
	 * @param a the assignment to update
	 */
	public void updateAssignment(Assignment a) {
		psql = "UPDATE " + tableName + " SET `active`=? WHERE `id`=?";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setBoolean(1, a.getActive());
			pStat.setInt(2, a.getID());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a course and returns the assignments belonging to that course
	 * @param c the course 
	 * @return the assignments of the course
	 */
	public ArrayList<Assignment> getAssignments(Course c){
		psql = "SELECT * FROM " + tableName + " WHERE courseID=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, c.getID());
			results = pStat.executeQuery();
			return extractAssignments(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Private helper function that receives a result set and 
	 * extracts the assignments contained in that set
	 * @param results the result set obtained from the database
	 * @return the extracted assignments of the result set
	 */
	private ArrayList<Assignment> extractAssignments(ResultSet results){
		ArrayList<Assignment> list = new ArrayList<Assignment>();
		try {
			while(results.next()) {
				Assignment a = new Assignment(results.getInt("id"), results.getInt("courseID"), results.getString("title"), results.getString("path"),
					results.getBoolean("active"), results.getString("due_Date"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
