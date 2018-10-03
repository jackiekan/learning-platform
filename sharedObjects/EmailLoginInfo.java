package sharedObjects;

import java.io.Serializable;

public class EmailLoginInfo implements Serializable{

	private static final long serialVersionUID = 8133600436365497300L;

	private String emailAddress;
	private String password;
	
	public EmailLoginInfo(String address, String pass) {
		emailAddress = address;
		password = pass;
	}
	
	public String getEmail() {
		return emailAddress;
	}
	
	public String getPassword() {
		return password;
	}
}
