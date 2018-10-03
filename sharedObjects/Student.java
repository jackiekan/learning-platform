//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

public class Student extends User{
	
	private static final long serialVersionUID = 925539797962717774L;

	public Student(int id, String firstname, String lastname, String email) {
		super(id, firstname, lastname, email, 'S');
	}
}
