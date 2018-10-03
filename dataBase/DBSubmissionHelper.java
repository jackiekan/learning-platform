
package dataBase;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import sharedObjects.Assignment;
import sharedObjects.Course;
import sharedObjects.Student;
import sharedObjects.Submission;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the database helper for the submissions table
 */
public class DBSubmissionHelper extends DBTableHelper{
	
	private String psql;

	public DBSubmissionHelper(Connection c) {
		super(c, "Submissions");
	}
	
	/**
	 * Adds a new submission to the database
	 * @param s the new submission to add
	 */
	public void addSubmission(Submission s) {
		psql = "INSERT INTO " + tableName + " (`assignID`, `studentID`, `path`, `title`, `submissionGrade`, `comments`, `timestamp`) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, s.getAssignID());
			pStat.setInt(2, s.getStudentID());
			pStat.setString(3, s.getPath());
			pStat.setString(4, s.getTitle());
			pStat.setInt(5, s.getGrade());
			pStat.setString(6, s.getComment());
			pStat.setString(7, s.getTimestamp());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives an assignment and returns the list of all submissions
	 * belonging to that assignment
	 * @param a the assignment being searched for
	 * @return the list of all submissions
	 */
	public ArrayList<Submission> getSubmissions(Assignment a) {
		psql = "SELECT * FROM " + tableName + " WHERE assignID=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, a.getID());
			results = pStat.executeQuery();
			return (extractSubmissions(results));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Private helper function which receives a result set and 
	 * extracts the list of submissions contained in the set
	 * @param results the result set obtained from the database
	 * @return the list of extracted submissions
	 */
	private ArrayList<Submission> extractSubmissions(ResultSet results) {
		ArrayList <Submission> submissionList = new ArrayList<Submission>();
		try {
			while (results.next()) {
				submissionList.add(new Submission(results.getInt("id"), results.getInt("assignID"), results.getInt("studentID"), results.getString("path"), results.getInt("submissionGrade"),
						results.getString("comments"), results.getString("title"), results.getString("timeStamp")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return submissionList;
	}
	
	/**
	 * Receives an assignment and returns the most recent submission
	 * of the received student for that particular assignment
	 * @param a the assignment being searched for
	 * @param s the student being searched for
	 * @return the most recent submission
	 */
	public Submission getLatestSubmission(Assignment a, Student s) {
		psql = "SELECT * FROM " + tableName + " WHERE assignID=? AND studentID=?";
		ResultSet results;
		PreparedStatement pStat;
		try {
			pStat = connection.prepareStatement(psql);
			pStat.setInt(1, a.getID());
			pStat.setInt(2, s.getID());
			results = pStat.executeQuery();
			return extractLatestSubmission(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Private helper function which receives a result set and 
	 * extracts the most recent submission contained in the set
	 * @param results the result set obtained from the database
	 * @return the most recent submission
	 */
	private Submission extractLatestSubmission(ResultSet results) {
		try {
			Submission latestSub = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			while (results.next()) {
				Submission temp = new Submission(results.getInt("id"), results.getInt("assignID"), results.getInt("studentID"), results.getString("path"), results.getInt("submissionGrade"),
						results.getString("comments"), results.getString("title"), results.getString("timeStamp"));
				if (latestSub == null) {
					latestSub = temp;
				}
				else {
					if (sdf.parse(temp.getTimestamp()).after(sdf.parse(latestSub.getTimestamp()))) {
						latestSub = temp;
					}
				}
			}
			return latestSub;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Updates the grade of a submission
	 * @param s the submission to update
	 * @param g the new grade of the submissions
	 */
	public void updateSubmissionGrade(Submission s, int g) {
		psql = "UPDATE " + tableName + " SET submissionGrade=? WHERE id=?";
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, g);
			pStat.setInt(2, s.getID());
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
