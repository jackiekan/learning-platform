package frontEnd;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sharedObjects.Assignment;
import sharedObjects.Submission;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is responsible for displaying the drop-box of a particular assignment 
 */
public class ProfDropBoxView extends JFrame {

	private JPanel contentPane;
	private JTextField assignmentNameField;
	private JTextField submissionField;
	private JComboBox<String> comboBox;
	private JButton selectButton;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private ArrayList <Submission> submissionList;
	private Assignment assignment;
	private JTextField gradeField;
	private JPanel gradePanel;
	private JButton enterButton;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProfDropBoxView frame = new ProfDropBoxView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Creates a new frame for the drop-box
	 */
	public ProfDropBoxView() {
		initComponents();
		createEvents();
	}

	/**
	 * Creates the action listener for the submissions list
	 */
	private void createEvents() {
		
		listArea.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = listArea.getSelectedIndex();
				if (index >= 0) {
					String line = submissionList.get(index).getTitle();
					submissionField.setText(line);
				}
			}
		});
	}

	/**
	 * Initializes all components of the GUI
	 */
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1219, 686);
		
		createContentPane();
		createFields();
		createComboBox();
		createButton();
		createGradePanel();
		
		JLabel dropboxTitleLabel = new JLabel("Learning Platform - Dropbox");
		dropboxTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel assignmentLabel = new JLabel("Assignment:");
		assignmentLabel.setForeground(new Color(105, 105, 105));
		assignmentLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(52, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(assignmentLabel)
							.addGap(34)
							.addComponent(assignmentNameField, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 831, GroupLayout.PREFERRED_SIZE)
						.addComponent(dropboxTitleLabel)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(submissionField, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addGap(67)
							.addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
					.addGap(304))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addComponent(gradePanel, GroupLayout.PREFERRED_SIZE, 639, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(512, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(41)
					.addComponent(dropboxTitleLabel)
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(assignmentLabel)
						.addComponent(assignmentNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(submissionField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(gradePanel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel gradeLabel = new JLabel("Grade: (0-100)");
		gradeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		GroupLayout gl_gradePanel = new GroupLayout(gradePanel);
		gl_gradePanel.setHorizontalGroup(
			gl_gradePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gradePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(gradeLabel)
					.addGap(18)
					.addComponent(gradeField, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(enterButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(248, Short.MAX_VALUE))
		);
		gl_gradePanel.setVerticalGroup(
			gl_gradePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gradePanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_gradePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(gradeLabel)
						.addComponent(enterButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(gradeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18))
		);
		gradePanel.setLayout(gl_gradePanel);
		
		createList(gl_contentPane, scrollPane);

	}

	/**
	 * Creates the grade panel
	 */
	private void createGradePanel() {
		gradePanel = new JPanel();
		gradePanel.setBorder(null);
		gradePanel.setBackground(new Color(176, 224, 230));
		gradePanel.setVisible(false);
	}

	/**
	 * Creates the submission list
	 */
	private void createList(GroupLayout gl_contentPane, JScrollPane scrollPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
	}
	
	public int getSelectedAction() {
		return comboBox.getSelectedIndex();
	}
	
	public Submission getSelectedSubmission() {
		return submissionList.get(listArea.getSelectedIndex());
	}
	
	public JPanel getGradePanel() {
		return gradePanel;
	}
	
	public String getGrade() {
		return gradeField.getText();
	}
	
	public void clearGrade() {
		gradeField.setText("");
	}

	public void setSubmissions(ArrayList <Submission> submissions) {
		submissionList = submissions;
		listModel.removeAllElements();
		for(Submission s: submissionList)
			listModel.addElement("Student ID: " + s.getStudentID() + " - " + s.getTitle());
	}
	
	public void setAssignmentName (Assignment a) {
		assignment = a;
		assignmentNameField.setText(assignment.getTitle());
	}
	
	public void addEnterGradeListener (ActionListener l) {
		enterButton.addActionListener(l);
	}
	
	public void addSubmissionActionListener (ActionListener l) {
		selectButton.addActionListener(l);
	}

	/**
	 * Creates the buttons of the GUI
	 */
	private void createButton() {
		selectButton = new JButton("Select");
		selectButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		enterButton = new JButton("Enter");
		enterButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}

	/**
	 * Creates the combo box for the assignment options
	 */
	private void createComboBox() {
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"(Choose Action)", "Download Submission", "Grade Submission"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}

	/**
	 * Creates the text fields of the GUI
	 */
	private void createFields() {
		assignmentNameField = new JTextField();
		assignmentNameField.setEditable(false);
		assignmentNameField.setText("Assignment 1");
		assignmentNameField.setBorder(null);
		assignmentNameField.setBackground(new Color(176, 224, 230));
		assignmentNameField.setForeground(new Color(0, 0, 255));
		assignmentNameField.setFont(new Font("Tahoma", Font.PLAIN, 23));
		assignmentNameField.setColumns(10);
		
		submissionField = new JTextField();
		submissionField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		submissionField.setColumns(10);
		
		gradeField = new JTextField();
		gradeField.setText("");
		gradeField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		gradeField.setColumns(10);
	}

	/**
	 * Creates the main content pane
	 */
	private void createContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 224, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
	
	public void addClosingListener(WindowAdapter w) {
		addWindowListener(w);
	}
}
