//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

public class Assignment implements Serializable{


	private static final long serialVersionUID = 1919470910714687302L;
	
	private int id;
	private int courseID;
	private String title;
	private String path;
	private boolean active;
	private String dueDate;
	
	public Assignment(int id, int courseID, String title, String path, boolean active, String dueDate) {
		this.id = id;
		this.courseID = courseID;
		this.title = title;
		this.path = path;
		this.active = active;
		this.dueDate = dueDate;
	}
	
	public int getID() {
		return id;
	}
	
	public int getCourseID() {
		return courseID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPath() {
		return path;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setActive(boolean status) {
		active = status;
	}
}
