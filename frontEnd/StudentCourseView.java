package frontEnd;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sharedObjects.Assignment;
import sharedObjects.Course;
import sharedObjects.Professor;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

public class StudentCourseView extends JFrame {

	private JPanel contentPane;
	private JTextField courseNameField;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private JTextField textField;
	private JTextField profField;
	private JComboBox<String> comboBox;
	private JButton gradesButton;
	private JButton emailButton;
	private JButton selectButton;
	private Course course;

	private ArrayList<Assignment> assignmentList;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StudentCourseView frame = new StudentCourseView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Constructs a new view for the course, and creates the various fields, buttons, and lists on the view
	 */
	public StudentCourseView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1276, 715);
		
		createContentPane(); 
		createFields();
		createButtons();
		createComboBox();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		
		JLabel courseTitleLabel = new JLabel("Learning Platform - Course Page");
		courseTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel courseNameLabel = new JLabel("Course Name:");
		courseNameLabel.setForeground(new Color(105, 105, 105));
		courseNameLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel assignmentListLabel = new JLabel("Assignment List:");
		assignmentListLabel.setForeground(SystemColor.controlDkShadow);
		assignmentListLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel profLabel = new JLabel("Professor:");
		profLabel.setForeground(new Color(105, 105, 105));
		profLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JScrollPane scrollPane = new JScrollPane();

		createGL(gl_contentPane, assignmentListLabel, scrollPane, courseTitleLabel, courseNameLabel, profLabel);
		
		createAssignmentList(gl_contentPane, scrollPane);
		createEvents();
	}
	
	public void setCourseName(Course c) {
		course = c;
		courseNameField.setText(course.getName());
	}
	
	public void setProfName(Professor prof) {
		profField.setText(prof.getName());
	}
	
	/**
	 * adds the names of the assignments for the course onto the listModel to be displayed, 
	 * along with its due date
	 * @param assignments the list of assignments for the course
	 */
	public void setAssignments(ArrayList<Assignment> assignments) {
		assignmentList = assignments;
		listModel.removeAllElements();
		for(Assignment a: assignmentList)
			listModel.addElement(a.getTitle() + " - due: " + a.getDueDate());
	}
	
	/**
	 * adds the listener for the select button
	 * @param l ActionListener object
	 */
	public void addAssignmentActionListener(ActionListener l) {
		selectButton.addActionListener(l);
	}
	
	/**
	 * adds the listener for the email professor button
	 * @param l ActionListener object
	 */
	public void addEmailListener(ActionListener l) {
		emailButton.addActionListener(l);
	}
	
	/**
	 * adds the listener for the view grade page button 
	 * @param l ActionListener object
	 */
	public void addGradeListener(ActionListener l) {
		gradesButton.addActionListener(l);
	}
	
	/**
	 * adds the closing listener for the course view
	 * @param w abstract adapter object for receiving window events
	 */
	public void addClosingListener(WindowAdapter w) {
		addWindowListener(w);
	}
	
	/**
	 * returns the index of selected action for the selected assignment
	 */
	public int getSelectedAction() {
		return comboBox.getSelectedIndex();
	}
	
	/**
	 * returns the assignment object of the selected assignment in the list
	 */
	public Assignment getSelectedAssignment() {
		if (listArea.getSelectedIndex() == -1) {
			return null;
		}
		return assignmentList.get(listArea.getSelectedIndex());
	}
	
	public ArrayList <Assignment> getAssignmentList() {
		return assignmentList;
	}
	
	/**
	 * adds the selection listener for the list area and sets the text field to the selected assignment
	 */
	private void createEvents() {
		
		listArea.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = listArea.getSelectedIndex();
				if (index >= 0) {
					String line = assignmentList.get(index).getTitle();
					textField.setText(line);
				}
			}
		});
	}
	
	/**
	 * creates the combo box for assignment actions
	 */
	private void createComboBox() {
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"(Choose Action)", "Download Assignment", "Submit File "}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}
	
	/**
	 * creates list of assignments 
	 * @param gl_contentPane GroupLayout object
	 * @param scrollPane JScrollPane object
	 */
	private void createAssignmentList(GroupLayout gl_contentPane, JScrollPane scrollPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * creates the buttons for the course view
	 */
	private void createButtons() {
		selectButton = new JButton("Select");
		selectButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		gradesButton = new JButton("View Grade Page");
		gradesButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		emailButton = new JButton("Send Email to Professor");
		emailButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
	}
	
	/**
	 * creates the text fields for the course view
	 */
	private void createFields() {
		courseNameField = new JTextField();
		courseNameField.setText("Course 1");
		courseNameField.setBorder(null);
		courseNameField.setForeground(new Color(0, 0, 255));
		courseNameField.setBackground(new Color(176, 224, 230));
		courseNameField.setEditable(false);
		courseNameField.setFont(new Font("Tahoma", Font.PLAIN, 23));
		courseNameField.setColumns(10);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setEditable(false);
		textField.setColumns(10);
		
		profField = new JTextField();
		profField.setText("Professor 1");
		profField.setBorder(null);
		profField.setBackground(new Color(176, 224, 230));
		profField.setEditable(false);
		profField.setForeground(new Color(0, 0, 255));
		profField.setFont(new Font("Tahoma", Font.PLAIN, 23));
		profField.setColumns(10);
	}
	
	/**
	 * creates the content pane
	 */
	private void createContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 224, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
	
	/**
	 * sets the placement of all components on the content pane using group layout
	 * @param gl_contentPane GroupLayout object 
	 * @param assignmentListLabel label for the assignment list
	 * @param scrollPane JScrollPane object
	 * @param courseTitleLabel label for the course title
	 * @param courseNameLabel label for the course name
	 * @param profLabel label for the professor
	 */
	private void createGL(GroupLayout gl_contentPane, JLabel assignmentListLabel, JScrollPane scrollPane, JLabel courseTitleLabel,
			JLabel courseNameLabel, JLabel profLabel) {
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(58)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(assignmentListLabel, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE)
										.addGap(28)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(selectButton))
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 669, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(133)
										.addComponent(emailButton))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(154)
										.addComponent(gradesButton, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))))
							.addComponent(courseTitleLabel)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(courseNameLabel)
								.addGap(18)
								.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(profLabel)
								.addGap(28)
								.addComponent(profField, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(141, Short.MAX_VALUE))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(34)
								.addComponent(courseTitleLabel)
								.addGap(39)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(courseNameLabel)
									.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addComponent(profLabel)
									.addComponent(profField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addGap(37)
								.addComponent(assignmentListLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
								.addGap(29)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addComponent(selectButton)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(265)
								.addComponent(gradesButton)
								.addGap(18)
								.addComponent(emailButton)))
						.addGap(90))
			);
	}
}
