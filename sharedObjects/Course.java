//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 2928746341036697935L;
	
	private int id;
	private int profID;
	private String name;
	private boolean active;
	
	public Course(int id, int profID, String name, boolean active) {
		this.id = id;
		this.profID = profID;
		this.name = name;
		this.active = active;
	}
	
	public int getProfID() {
		return profID;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getActive() {
		return active;
	}

	public int getID() {
		return id;
	}
	
	public void setActive(boolean b) {
		active = b;
	}
	
}
