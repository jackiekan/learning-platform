
package dataBase;

import java.sql.*;
import java.util.ArrayList;

import sharedObjects.*;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the database helper for the user table
 */
public class DBUserHelper extends DBTableHelper {
	
	private String psql;
	
	public DBUserHelper(Connection c) {
		super(c, "Users");
	}
	
	/**
	 * Receives login info and returns the user if found or 
	 * empty user with '-1' ID to indicated incorrect login info 
	 * or user not found
	 * @param loginInfo the login info provided
	 * @return the result of the query
	 */
	public User authenticateUser(LoginInfo loginInfo) {
		psql = "SELECT * FROM " + tableName + " WHERE id=? AND password=?";
		ResultSet results;
		ArrayList<User> userList;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, loginInfo.getUsername());
			pStat.setString(2, loginInfo.getPassword());
			results = pStat.executeQuery();
			userList = extractUsers(results);
			if(userList.size() == 1)
				return userList.get(0);
			else
				return new Student(-1, null, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Receives a list of user ID's and returns an array
	 * of the users in the database matching those ID's
	 * @param userIDList the list of ID's to be searched for
	 * @return the list of users found in the database
	 */
	public ArrayList<Student> extractUsersName(ArrayList <Integer> userIDList) {
		psql = "SELECT * FROM " + tableName + " WHERE id=?";
		ResultSet results;
		ArrayList <Student> userList = new ArrayList <Student>();
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			int i = 0;
			while (i < userIDList.size()) {
				int userID = userIDList.get(i);
				pStat.setInt(1, userID);
				results = pStat.executeQuery();
				while (results.next()) {
					userList.add(new Student(results.getInt("id"),results.getString("firstname"), results.getString("lastname"), results.getString("email")));
				}
				i++;
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Searches for a user by ID and returns the result
	 * @param id the ID being searched for
	 * @return the result of the query
	 */
	public ArrayList<User> searchByID(int id){
		psql = "SELECT * FROM " + tableName + " WHERE id=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			pStat.setInt(1, id);
			results = pStat.executeQuery();
			return extractUsers(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Searches for a user by last-name and returns the result
	 * @param lastname the last-name being searched for
	 * @return the result of the query
	 */
	public ArrayList<User> searchByLastname(String lastname){
		psql = "SELECT * FROM " + tableName + " WHERE lastname=?";
		ResultSet results;
		try {
			PreparedStatement pStat = connection.prepareStatement(psql);
			
			pStat.setString(1, lastname);
			results = pStat.executeQuery();
			return extractUsers(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Private helper function which receives a result set and 
	 * returns a list of users extracted from the result set
	 * @param results the result set gathered from the database
	 * @return the list of extracted users
	 */
	private ArrayList<User> extractUsers(ResultSet results){
		ArrayList<User> userList = new ArrayList<User>();
		try {
			while(results.next()) {
				if(results.getString("type").equals("S"))
					userList.add(new Student(results.getInt("id"),results.getString("firstname"), results.getString("lastname"), results.getString("email")));
				else
					userList.add(new Professor(results.getInt("id"),results.getString("firstname"), results.getString("lastname"), results.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
}
