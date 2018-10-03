//Armin Ghezelbashan & Jacyln Kan

package frontEnd;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import java.awt.ScrollPane;
import java.awt.List;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import frontEnd.ProfController.ActivateCourseListener;
import frontEnd.ProfController.AddCourseListener;
import sharedObjects.Course;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JCheckBox;

public class ProfHomeView extends JFrame {

	private JPanel contentPane;
	private JTextField selectCourseField;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private JButton selectCourseButton;
	private JButton createButton;
	private ArrayList<Course> userCourses;
	private JTextField courseNameField;
	private JCheckBox activateCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfHomeView frame = new ProfHomeView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProfHomeView() {
		initComponents();
		createEvents();
	}

	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1088, 608);
		
		createContentPane();
		createButtons();
		createFields();
		createCheckBox();
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel selectLabel = new JLabel("Select A Course:");
		selectLabel.setForeground(new Color(105, 105, 105));
		selectLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel createLabel = new JLabel("Create a New Course:");
		createLabel.setForeground(new Color(105, 105, 105));
		createLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel homeLabel = new JLabel("Learning Platform - Home");
		homeLabel.setForeground(new Color(0, 0, 0));
		homeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel courseNameLabel = new JLabel("Course Name:");
		courseNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		createGL(gl_contentPane, createLabel, homeLabel, selectLabel, scrollPane, courseNameLabel);
		createCourseList(scrollPane, gl_contentPane);
	}
	
	private void createCheckBox() {
		activateCheckBox= new JCheckBox(" Activate Course");
		activateCheckBox.setBackground(new Color(176, 224, 230));
		activateCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activateCheckBox.setActionCommand("courseActivated");
	}

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

	private void createFields() {
		
		selectCourseField = new JTextField();
		selectCourseField.setEditable(false);
		selectCourseField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectCourseField.setColumns(10);
		
		courseNameField = new JTextField();
		courseNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		courseNameField.setColumns(10);
	}

	private void createButtons() {
		createButton = new JButton("Create");
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		createButton.setActionCommand("createCourse");
		
		selectCourseButton = new JButton("Select");
		selectCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectCourseButton.setActionCommand("selectCourse");
	}

	private void createContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 224, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
	
	private void createCourseList(JScrollPane scrollPane, GroupLayout gl_contentPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void setCourses(ArrayList<Course> courses) {
		userCourses = courses;
		listModel.removeAllElements();
		for(Course c: userCourses) {
			listModel.addElement(c.getName());
		}
	}
	
	public void addHomeListener(ActionListener select, ActionListener course) {
		selectCourseButton.addActionListener(select);
		createButton.addActionListener(course);
	}
	
	public void addToListModel(String s) {
		listModel.addElement(s);
	}
	
	public Course getSelectedCourse() {
		int index = listArea.getSelectedIndex();
		Course course = userCourses.get(index);
		return course;
	}
	
	public String getNewCourseField() {
		return courseNameField.getText();
	}
	
	public boolean isActive() {
		if (activateCheckBox.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addToUserCourses (Course course) {
		userCourses.add(course);
	}
	
	public void clearNewCourse() {
		courseNameField.setText("");
		activateCheckBox.setSelected(false);
	}
	
	private void createGL(GroupLayout gl_contentPane, JLabel createLabel, JLabel homeLabel, JLabel selectLabel, JScrollPane scrollPane, JLabel courseNameLabel) {
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addGap(65)
								.addComponent(courseNameLabel)
								.addGap(26)
								.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
								.addComponent(activateCheckBox))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addGap(31)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(createLabel)
									.addComponent(homeLabel)
									.addComponent(selectLabel)))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addGap(61)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(selectCourseField, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
										.addGap(41)
										.addComponent(selectCourseButton)))))
						.addGap(98)
						.addComponent(createButton, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addGap(84))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(38)
						.addComponent(homeLabel)
						.addGap(30)
						.addComponent(selectLabel)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(selectCourseField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(selectCourseButton))
						.addGap(31)
						.addComponent(createLabel)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(courseNameLabel)
							.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(createButton)
							.addComponent(activateCheckBox))
						.addContainerGap(40, Short.MAX_VALUE))
			);
	}
}
