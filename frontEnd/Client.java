
package frontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import sharedObjects.*;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the primary client class responsible for handling 
 * all communication with the server
 */
public class Client {
	
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	LoginWindow loginWindow;
	Controller viewController;
	
	/**
	 * 
	 * @author Armin Ghezelbashan & Jacyln Kan
	 * The action listener class for the login button which sends the provided
	 * login info to the server in order to authenticate the user
	 */
	public class LoginActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				LoginInfo info = loginWindow.getLoginInfo();
				if(info != null) {
					out.writeObject(new Command(e.getActionCommand()));
					out.writeObject(info);
					User user = (User)in.readObject();
					if(user.getID() == -1)
						loginWindow.displayErrorMessage("Incorrect username or password. Please try again.");
					else
						userAuthenticated(user);
				}
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates a new GUI controller for the client based on the type of 
	 * user that they are
	 * @param user
	 */
	private void userAuthenticated(User user) {
		if(user.getType() == 'P') {
			viewController = new ProfController((Professor)user, this);
			loginWindow.setVisible(false);
		}
		else if(user.getType() == 'S') {
			viewController = new StudentController((Student)user, this);
			loginWindow.setVisible(false);
		}
	}
	
	/**
	 * Send an object to the server
	 * @param object the object to send
	 */
	public void sendToServer(Object object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives an object from the server
	 * @return the received object
	 */
	public Object getObjectFromServer() {
		try {
			return in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * Constructs a new client and establishes connection with the server
	 * through port 9090. Constructs new login window
	 */
	public Client() {
		try {
			socket = new Socket("localhost", 9090);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
		loginWindow.addLoginActionListener(new LoginActionListener());
	}
	
	public static void main(String [] args) {
		Client client = new Client();
	}
}
