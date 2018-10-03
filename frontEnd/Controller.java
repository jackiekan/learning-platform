
package frontEnd;
import java.util.ArrayList;

import sharedObjects.Course;
import sharedObjects.User;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is an abstract controller class which provides the basic 
 * attributes of the GUI controller classes for student and professor
 */
public abstract class Controller {
	
	protected User user;
	protected Client client;
	
	/**
	 * Constructs a new controller with the provided user and client
	 * @param u the user 
	 * @param c the client
	 */
	public Controller (User u, Client c) {
		user = u;
		client = c;
	}
	
	public abstract void initializeHomeView();
	public abstract void initializeCourseView();
	
	public abstract ArrayList<Course> retrieveCourses();
}
