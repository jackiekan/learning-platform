//Armin Ghezelbashan & Jacyln Kan

package sharedObjects;

public class Professor extends User {

	private static final long serialVersionUID = -7900316930867958390L;

	public Professor(int id, String firstname, String lastname, String email) {
		super(id, firstname, lastname, email, 'P');
	}
}
