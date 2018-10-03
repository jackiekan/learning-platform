//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

public class Command implements Serializable {

	private static final long serialVersionUID = 7473071151997536426L;
	
	private String command;
	
	public Command(String c) {
		command = c;
	}
	
	public String getCommand() {
		return command;
	}
}
