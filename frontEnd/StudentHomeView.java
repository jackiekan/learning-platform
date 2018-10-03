package frontEnd;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sharedObjects.Course;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

public class StudentHomeView extends JFrame {

	private JPanel contentPane;
	private JTextField selectCourseField;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private JButton selectButton;
	private ArrayList<Course> studentCourses;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StudentHomeView frame = new StudentHomeView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Constructs a new view for the home page
	 */
	public StudentHomeView() {
		initComponents();
		createEvents();
	}
	
	/**
	 * adds the courses of the student onto a list 
	 * @param courseList list of courses of the student
	 */
	public void setCourses(ArrayList <Course> courseList) {
		studentCourses = courseList;
		listModel.removeAllElements();
		for(Course c: courseList) {
			listModel.addElement(c.getName());
		}
	}
	
	/**
	 * returns the Course object of the selected course
	 */
	public Course getSelectedCourse() {
		int index = listArea.getSelectedIndex();
		if (index == -1) {
			return null;
		}
		Course course = studentCourses.get(index);
		return course;
	}
	
	/**
	 * adds the listener for the select course button
	 * @param select
	 */
	public void addHomeListener(ActionListener select) {
		selectButton.addActionListener(select);
	}
	
	/**
	 * adds the selection listener for the list area and sets the text field to the selected course
	 */
	private void createEvents() {
		
		listArea.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = listArea.getSelectedIndex();
				if (index >= 0) {
					String line = (String) listModel.get(index);
					selectCourseField.setText(line);
				}
			}
		});
	}
	
	/**
	 * Initializes all components of the GUI
	 */
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 958, 654);
		
		createContentPane();
		createField();
		createButton();
		
		JLabel homeTitleLabel = new JLabel("Learning Platform - Home");
		homeTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
	
		JLabel selectCourseLabel = new JLabel("Select a Course:");
		selectCourseLabel.setForeground(new Color(105, 105, 105));
		selectCourseLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JScrollPane scrollPane = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		createGL(gl_contentPane, selectCourseLabel, homeTitleLabel, scrollPane);
		
		createCourseList(gl_contentPane, scrollPane);
	}
	
	/**
	 * creates list of courses 
	 * @param gl_contentPane GroupLayout object
	 * @param scrollPane JScrollPane object
	 */
	private void createCourseList(GroupLayout gl_contentPane, JScrollPane scrollPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * creates the buttons for the home view
	 */
	private void createButton() {
		selectButton = new JButton("Select");
		selectButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectButton.setActionCommand("selectStudentCourses");
	}
	
	/**
	 * creates the fields for the home view
	 */
	private void createField() {
		selectCourseField = new JTextField();
		selectCourseField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectCourseField.setEditable(false);
		selectCourseField.setColumns(10);
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
	 * @param selectCourseLabel label for select course
	 * @param homeTitleLabel label for the title of the home view
	 * @param scrollPane JScrollPane object
	 */
	private void createGL(GroupLayout gl_contentPane, JLabel selectCourseLabel, JLabel homeTitleLabel, JScrollPane scrollPane) {
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(45)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(selectCourseLabel)
									.addComponent(homeTitleLabel)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(63)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(selectCourseField, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
										.addGap(57)
										.addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
										.addGap(100))
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))))
						.addGap(253))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(42)
						.addComponent(homeTitleLabel)
						.addGap(38)
						.addComponent(selectCourseLabel)
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
						.addGap(36)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(selectCourseField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(79, Short.MAX_VALUE))
			);
	}
}
