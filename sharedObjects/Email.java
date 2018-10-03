//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Email implements Serializable {

	private static final long serialVersionUID = 807056106162901684L;
	
	private String from;
	private ArrayList<String> to;
	private String subject;
	private String content;
	
	public Email(String sender, ArrayList<String> receivers, String subject, String content) {
		from = sender;
		to = receivers;
		this.subject = subject;
		this.content = content;
	}
	
	public String getSender() {
		return from;
	}
	
	public ArrayList<String> getReceivers(){
		return to;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}

}
