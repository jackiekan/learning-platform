
package frontEnd;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;

import frontEnd.ProfController.ActivateCourseListener;

import sharedObjects.Course;

import sharedObjects.Student;

import sharedObjects.User;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is responsible for displaying the course view of the professor
 * which contains all information belonging to a selected course
 */
public class ProfCourseView extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField filterField;
	private JTextField courseNameField;
	private JTextField selectStudentField;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private JButton enrollButton;
	private JButton selectButton;
	private JButton newAssignmentButton;
	private JButton browseAssignmentsButton;
	private JButton searchButton;
	private JButton emailButton;
	private JCheckBox activeCheckBox;
	private JRadioButton lastNameRButton;
	private JRadioButton idRButton;
	
	private Course course;
	

	private ArrayList <User> studentList;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProfCourseView frame = new ProfCourseView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Constructs a new view for the course
	 */
	public ProfCourseView() {
		initComponents();
		createEvents();
	}

	/**
	 * Initializes all components of the GUI
	 */
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1347, 730);
		
		createContentPane();
		createButtons();
		createFields();
		createCheckBox();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		
		JLabel courseLabel = new JLabel("Learning Platform - Course Page");
		courseLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel filterViaLabel = new JLabel("Filter Student List Via:");
		filterViaLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel filterParameterLabel = new JLabel("Enter Filter Parameter:");
		filterParameterLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel activeLabel = new JLabel("Set Course to Active:");
		activeLabel.setForeground(new Color(255, 0, 0));
		activeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel courseNameLabel = new JLabel("Course Name:");
		courseNameLabel.setForeground(new Color(105, 105, 105));
		courseNameLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel studentListLabel = new JLabel("Student List:");
		studentListLabel.setForeground(new Color(105, 105, 105));
		studentListLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel assignmentLabel = new JLabel("Assignments:");
		assignmentLabel.setForeground(new Color(105, 105, 105));
		assignmentLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JScrollPane scrollPane = new JScrollPane();
		
		createStudentList(scrollPane, gl_contentPane);
		
		createGL(gl_contentPane, courseLabel, courseNameLabel, studentListLabel,
				scrollPane, filterViaLabel, activeLabel, filterParameterLabel, assignmentLabel);
	}
	
	/**
	 * Creates the list action listener
	 */
	private void createEvents() {
		
		listArea.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int index = listArea.getSelectedIndex();
				if (index >= 0) {
					String line = (String) listModel.get(index);
					selectStudentField.setText(line);
				}
			}
		});
	}
	
	/**
	 * Creates the active check-box
	 */
	private void createCheckBox() {
		activeCheckBox = new JCheckBox(" Active");
		activeCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activeCheckBox.setBackground(new Color(176, 224, 230));
		activeCheckBox.setActionCommand("activateCourse");
	}

	/**
	 * Creates the text fields for the GUI
	 */
	private void createFields() {
		filterField = new JTextField();
		filterField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		filterField.setColumns(10);
		
		courseNameField = new JTextField();
		courseNameField.setBorder(null);
		courseNameField.setBackground(new Color(176, 224, 230));
		courseNameField.setForeground(new Color(0, 0, 255));
		courseNameField.setText("Course 1");
		courseNameField.setEditable(false);
		courseNameField.setFont(new Font("Tahoma", Font.PLAIN, 23));
		courseNameField.setColumns(10);
		
		selectStudentField = new JTextField();
		selectStudentField.setEditable(false);
		selectStudentField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectStudentField.setColumns(10);
	}

	/**
	 * Creates the buttons of the GUI
	 */
	private void createButtons() {
		idRButton = new JRadioButton("ID Number");
		buttonGroup.add(idRButton);
		idRButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idRButton.setBackground(new Color(176, 224, 230));
		
		lastNameRButton = new JRadioButton("Last Name");
		lastNameRButton.setSelected(true);
		buttonGroup.add(lastNameRButton);
		lastNameRButton.setBackground(new Color(176, 224, 230));
		lastNameRButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		selectButton = new JButton("Select");
		selectButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectButton.setActionCommand("selectStudent");
		
		enrollButton = new JButton("Click to Enroll a New Student");
		enrollButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		emailButton = new JButton("Click to Send Email to All Students");
		emailButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		newAssignmentButton = new JButton("Upload a New Assignment");
		newAssignmentButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		browseAssignmentsButton = new JButton("Browse Assignments");
		browseAssignmentsButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}
	
	public User getSelectedStudent() {
		int index = listArea.getSelectedIndex();
		return studentList.get(index);
	}
	
	/**
	 * Creates main content pane
	 */
	private void createContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 224, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
	
	/**
	 * Creates the list of the students
	 */
	private void createStudentList(JScrollPane scrollPane, GroupLayout gl_contentPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setCourseName (String course) {
		courseNameField.setText(course);
	}
	
	public void setStudents(ArrayList <User> sList) {
		studentList = sList;
		listModel.removeAllElements();
		for(User s: studentList) {
			listModel.addElement(s.getName());
		}
	}
	
	public void addNewAssignmentListener(ActionListener l) {
		newAssignmentButton.addActionListener(l);
	}
	
	public void addBrowseAssignmentListener(ActionListener l ) {
		browseAssignmentsButton.addActionListener(l);
	}

	public void setCourse(Course c) {
		course = c;
		courseNameField.setText(c.getName());
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void addClosingListener(WindowAdapter w) {
		addWindowListener(w);
	}
	
	public void addSearchListener(ActionListener l) {
		searchButton.addActionListener(l);
	}
	
	public String getSearchField() {
		return filterField.getText();
	}
	
	public int getSearchType() {
		if(idRButton.isSelected())
			return 0;
		else if(lastNameRButton.isSelected())
			return 1;
		else
			return -1;
	}
	
	public boolean IDSelected(ProfEnrollPanel panel) {
		if (panel.searchByID()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public JCheckBox getActiveCheckBox() {
		return activeCheckBox;
	}
	
	public void addCourseListener(ActionListener active, ActionListener select, ActionListener enroll) {
		activeCheckBox.addActionListener(active);
		selectButton.addActionListener(select);
		enrollButton.addActionListener(enroll);
	}
	
	public JTextField getStudentField() {
		return selectStudentField;
	}
	
	public ArrayList<User> getStudentList() {
		return studentList;
	}

	public void addEmailButtonListener(ActionListener l) {
		emailButton.addActionListener(l);
	}
	
	/**
	 * Initializes the group layout with all required components
	 */
	public void createGL(GroupLayout gl_contentPane, JLabel courseLabel, JLabel courseNameLabel, JLabel studentListLabel,
			JScrollPane scrollPane, JLabel filterViaLabel, JLabel activeLabel, JLabel filterParameterLabel, JLabel assignmentLabel) {
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(40)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(newAssignmentButton)
								.addGap(60)
								.addComponent(browseAssignmentsButton)
								.addGap(861))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(assignmentLabel)
									.addContainerGap())
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(selectStudentField, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
										.addGap(56)
										.addComponent(selectButton)
										.addContainerGap())
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(studentListLabel)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 548, GroupLayout.PREFERRED_SIZE)
														.addGap(54)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
															.addComponent(filterParameterLabel)
															.addComponent(filterViaLabel)))
													.addComponent(courseLabel)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(courseNameLabel)
														.addGap(18)
														.addComponent(courseNameField, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
														.addGap(18)
														.addComponent(activeLabel)
														.addGap(31)
														.addComponent(activeCheckBox)
														.addGap(93)))
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addGap(24)
														.addComponent(lastNameRButton)
														.addGap(11)
														.addComponent(idRButton))
													.addGroup(gl_contentPane.createSequentialGroup()
														.addGap(28)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
															.addComponent(enrollButton)
															.addGroup(gl_contentPane.createSequentialGroup()
																.addGap(13)
																.addComponent(filterField, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
																.addGap(18)
																.addComponent(searchButton))))
													.addGroup(gl_contentPane.createSequentialGroup()
														.addGap(11)
														.addComponent(emailButton)))
												.addGap(343)))
										.addContainerGap())))))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(33)
						.addComponent(courseLabel)
						.addGap(36)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(courseNameLabel)
							.addComponent(activeLabel)
							.addComponent(activeCheckBox)
							.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(studentListLabel)
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(37)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(filterViaLabel)
									.addComponent(idRButton)
									.addComponent(lastNameRButton))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(filterParameterLabel)
									.addComponent(filterField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(searchButton))
								.addGap(51)
								.addComponent(enrollButton)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(emailButton))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(27)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(selectStudentField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(selectButton))
						.addGap(33)
						.addComponent(assignmentLabel)
						.addGap(26)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(newAssignmentButton)
							.addComponent(browseAssignmentsButton))
						.addGap(50))
			);
	}
}
