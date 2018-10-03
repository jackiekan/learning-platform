//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

import java.io.Serializable;

public class StudentEnrollment implements Serializable{

	private static final long serialVersionUID = 7630041063939473218L;
	
	private int studentID;
	private int courseID;
//	private boolean enrolling;
//	
//	public StudentEnrollment(int studentID, int courseID, boolean enrolling) {
//		this.studentID = studentID;
//		this.courseID = courseID;
//		this.enrolling = enrolling;
//	}
	
	public StudentEnrollment(int studentID, int courseID) {
		this.studentID = studentID;
		this.courseID = courseID;
	}
}
