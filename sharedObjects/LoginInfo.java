//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 4664483144046796242L;
	
	private int username;
	private String password;
	
	public LoginInfo(int username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public int getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

}
