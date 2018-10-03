//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

import frontEnd.Client;

abstract public class User implements Serializable {

	private static final long serialVersionUID = 1574648520451823502L;
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private char type;
	
	public User(int id, String firstname, String lastname, String email, char type) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.type = type;
	}
	
	public String getName() {
		return firstname + " " + lastname;
	}
	
	public String getFirstName() {
		return firstname;
	}
	
	public String getLastName() {
		return lastname;
	}
	
	public int getID() {
		return id;
	}

	public char getType() {
		return type;
	}
	
	public String getEmail() {
		return email;
	}
}
