//Armin Ghezelbashan & Jacyln Kan

package frontEnd;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;

import sharedObjects.Assignment;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ProfAssignmentView extends JFrame {

	private JPanel contentPane;
	private JTextField assignmentField;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private JButton selectButton;
	private JCheckBox activeCheckBox;
	private JButton uploadButton;
	
	ArrayList<Assignment> assignments;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProfAssignmentView frame = new ProfAssignmentView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Creates the frame
	 */
	public ProfAssignmentView() {
		initComponents();
		createEvents();
	}

	/**
	 * Creates the action listener for the assignment list
	 */
	private void createEvents() {
		listArea.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = listArea.getSelectedIndex();
				if (index >= 0) {
					Assignment selected = assignments.get(listArea.getSelectedIndex());
					assignmentField.setText(selected.getTitle());
					activeCheckBox.setEnabled(true);
					activeCheckBox.setSelected(selected.getActive());
				}
			}
		});
	}
	
	/**
	 * Receives a list of assignments and updates the view with the 
	 * new set of assignments
	 * @param list the new list of assignments
	 */
	public void setAssignments(ArrayList<Assignment> list) {
		assignments = list;
		listModel.removeAllElements();
		for(Assignment a: assignments)
			listModel.addElement(a.getTitle());
	}
	
	/**
	 * Returns the currently selected assignment within the list
	 * @return the selected assignment
	 */
	public Assignment getSelectedAssignment() {
		if (listArea.getSelectedIndex() == -1) {
			return null;
		}
		return assignments.get(listArea.getSelectedIndex());
	}
	
	public boolean getActivatedStatus() {
		return activeCheckBox.isSelected();
	}
	
	public void viewDropboxListener(ActionListener l) {
		selectButton.addActionListener(l);
	}
	
	public void addActiveListener(ActionListener l) {
		activeCheckBox.addActionListener(l);
	}
	
	public void addNewAssignmentListener(ActionListener l) {
		uploadButton.addActionListener(l);
	}
	
	public void addCloseWindowListener(WindowAdapter w) {
		addWindowListener(w);
	}

	/**
	 * Initializes all components of the GUI
	 */
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1145, 670);
		
		createContentPane();
		createFields();
		createButtons();
		createCheckBox();
		
		JLabel assignmentTitleLabel = new JLabel("Learning Platform - Assignment Page");
		assignmentTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel assignmentListLabel = new JLabel("Assignment List:");
		assignmentListLabel.setForeground(new Color(105, 105, 105));
		assignmentListLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		createGL(gl_contentPane, assignmentListLabel, scrollPane, assignmentTitleLabel);
		createList(scrollPane, gl_contentPane);
	}
	
	/**
	 * Lays out all different components of the GUI in a group layout
	 */
	private void createGL(GroupLayout gl_contentPane, JLabel assignmentListLabel, JScrollPane scrollPane, JLabel assignmentTitleLabel) {
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(51)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 653, GroupLayout.PREFERRED_SIZE)
								.addGap(83)
								.addComponent(uploadButton))
							.addComponent(assignmentListLabel)
							.addComponent(assignmentTitleLabel)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(assignmentField, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(selectButton)
								.addGap(92)
								.addComponent(activeCheckBox)))
						.addContainerGap(87, Short.MAX_VALUE))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(46)
						.addComponent(assignmentTitleLabel)
						.addGap(37)
						.addComponent(assignmentListLabel)
						.addGap(28)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(assignmentField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(activeCheckBox)
							.addComponent(selectButton))
						.addContainerGap(76, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap(293, Short.MAX_VALUE)
						.addComponent(uploadButton)
						.addGap(280))
			);
	}

	/**
	 * Creates the list containing the assignments
	 * @param scrollPane the scrollpane of the list
	 * @param gl_contentPane the main content pane
	 */
	private void createList(JScrollPane scrollPane, GroupLayout gl_contentPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Creates the check-box panel for activating/deactivating an assignment
	 */
	private void createCheckBox() {
		activeCheckBox = new JCheckBox(" Activate Assignment");
		activeCheckBox.setEnabled(false);
		activeCheckBox.setForeground(new Color(255, 0, 0));
		activeCheckBox.setBackground(new Color(176, 224, 230));
		activeCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}

	/**
	 * Creates the "View Dropbox" and "Upload a new assignment" buttons
	 */
	private void createButtons() {
		selectButton = new JButton("View Dropbox");
		selectButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectButton.setActionCommand("viewDropbox");
		
		uploadButton = new JButton("Upload a New Assignment");
		uploadButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}

	/**
	 * Creates the text fields of the GUI
	 */
	private void createFields() {
		assignmentField = new JTextField();
		assignmentField.setEditable(false);
		assignmentField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		assignmentField.setColumns(10);
		assignmentField.setText("");
	}

	/**
	 * Constructs the main content pane of the GUI
	 */
	private void createContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 224, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
