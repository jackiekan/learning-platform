//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

public class Submission implements Serializable{

	private static final long serialVersionUID = 5520754685332699648L;
	
	private int id;
	private int assignID;
	private int studentID;
	private String path;
	private int grade;
	private String comment;
	private String title;
	private String timeStamp;
	
	public Submission(int id, int assignID, int studentID, String path, int grade, String comment, String title, String timeStamp) {
		this.id = id;
		this.assignID = assignID;
		this.studentID = studentID;
		this.path = path;
		this.grade = grade;
		this.comment = comment;
		this.title = title;
		this.timeStamp = timeStamp;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getAssignID() {
		return assignID;
	}
	
	public int getStudentID() {
		return studentID;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public String getComment() {
		return comment;
	}
	
	public String getTimestamp() {
		return timeStamp;
	}
	
	public void setPath(String p) {
		path = p;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setGrade(int g) {
		grade = g;
	}
	
	public int getID() {
		return id;
	}
	
}
