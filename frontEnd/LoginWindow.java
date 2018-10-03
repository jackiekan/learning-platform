
package frontEnd;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

import sharedObjects.LoginInfo;

import java.awt.Font;
import javax.swing.JPasswordField;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the GUI class responsible for displaying the login window
 */
public class LoginWindow extends JFrame{

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton clearButton;
	private JButton loginButton;

	/**
	 * Create a new login window
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setMinimumSize(new Dimension(300, 200));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(176, 224, 230));
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut1 = Box.createVerticalStrut(20);
		verticalStrut1.setMinimumSize(new Dimension(0, 12));
		mainPanel.add(verticalStrut1);

		mainPanel.add(createTitlePanel());
		
		Component verticalStrut2 = Box.createVerticalStrut(20);
		verticalStrut2.setPreferredSize(new Dimension(0, 15));
		mainPanel.add(verticalStrut2);
		
		mainPanel.add(createUsernamePanel());
		mainPanel.add(createPasswordPanel());
		mainPanel.add(createButtonPanel());
	}
	
	/**
	 * Creates the title panel of the login window
	 * @return the title panel
	 */
	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setOpaque(false);
		titlePanel.setBackground(SystemColor.window);
		
		JLabel lblNewLabel = new JLabel("Welcome to the Learning Platform!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titlePanel.add(lblNewLabel);
		return titlePanel;
	}
	
	/**
	 * Creates the username panel of the login window
	 * @return the username panel
	 */
	private JPanel createUsernamePanel() {
		JPanel usernamePanel = new JPanel();
		usernamePanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) usernamePanel.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		usernamePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		usernamePanel.add(lblNewLabel_1);
		
		usernameField = new JTextField();
		usernameField.setColumns(14);
		usernamePanel.add(usernameField);
		
		return usernamePanel;
	}
	
	/**
	 * Creates the password panel of the login window
	 * @return the password panel
	 */
	private JPanel createPasswordPanel() {
		JPanel passwordPanel = new JPanel();
		passwordPanel.setOpaque(false);
		FlowLayout flowLayout_1 = (FlowLayout) passwordPanel.getLayout();
		flowLayout_1.setHgap(15);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		passwordPanel.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(14);
		passwordPanel.add(passwordField);
		
		return passwordPanel;
	}
	
	/**
	 * Creates the buttons of the login window
	 * @return the buttons panel
	 */
	private JPanel createButtonPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				usernameField.setText("");
				passwordField.setText("");
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		buttonsPanel.add(clearButton);
		
		loginButton = new JButton("Login");
		loginButton.setActionCommand("login");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		buttonsPanel.add(loginButton);
		
		return buttonsPanel;
	}
	
	public void addLoginActionListener(ActionListener l) {
		loginButton.addActionListener(l);
	}
	
	/**
	 * Extracts the login info entered by the user into the fields
	 * @return the login info
	 */
	@SuppressWarnings("deprecation")
	public LoginInfo getLoginInfo() {
		try{
			return new LoginInfo(Integer.parseInt(usernameField.getText()), passwordField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Username may only contain numerical digists.");
			return null;
		}
	}
	
	/**
	 * Displays an error message to the user
	 * @param errorMessage the message to be displayed
	 */
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
}
