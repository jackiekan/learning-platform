package frontEnd;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sharedObjects.Course;
import sharedObjects.Submission;
import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class StudentGradeView extends JFrame {

	private JPanel contentPane;
	private JTextField courseNameField;
	private JTextField overallGradeField;
	private JList<String> listArea;
	private DefaultListModel<String> listModel;
	private ArrayList <Submission> gradesList;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StudentGradeView frame = new StudentGradeView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Constructs a new view for the grade page, and creates the various fields,
	 * buttons, and lists on the view
	 */
	public StudentGradeView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 933, 714);
		
		createContentPane();
		createFields();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		
		JLabel gradeTitleLabel = new JLabel("Learning Platform - Grade Page");
		gradeTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel courseNameLabel = new JLabel("Course:");
		courseNameLabel.setForeground(new Color(105, 105, 105));
		courseNameLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel gradesLabel = new JLabel("Grades:");
		gradesLabel.setForeground(SystemColor.controlDkShadow);
		gradesLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel overallGradeLabel = new JLabel("Overall Course Grade:");
		overallGradeLabel.setForeground(new Color(105, 105, 105));
		overallGradeLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JScrollPane scrollPane = new JScrollPane();
		
		createGradesList(gl_contentPane, scrollPane);
		
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(38)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(gradeTitleLabel)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(gradesLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(courseNameLabel)
										.addGap(18)
										.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 588, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(overallGradeLabel)
										.addGap(30)
										.addComponent(overallGradeField, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap(262, Short.MAX_VALUE))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(49)
						.addComponent(gradeTitleLabel)
						.addGap(28)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(courseNameLabel)
							.addComponent(courseNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(26)
						.addComponent(gradesLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
						.addGap(39)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(overallGradeLabel)
							.addComponent(overallGradeField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(69, Short.MAX_VALUE))
			);
	}
	
	
	/**
	 * sets the course name field to the selected course
	 * @param c selected course 
	 */
	public void setCourseName(Course c) {
		courseNameField.setText(c.getName());
	}
	

	public void setGradesList(ArrayList <Submission> gList) {
		gradesList = gList;
	}
	
	public ArrayList <Submission> getGradesList(){
		return gradesList;
	}
	
	/**
	 * adds the titles of the submissions for the course onto the listModel to be displayed, 
	 * along with its grade
	 * @param assignments the list of assignments for the course
	 */
	public void setGrades() {
		listModel.removeAllElements();
			for(Submission s: gradesList) {
				String grade = String.format("%-50s%s", s.getTitle(), "MARK: " + s.getGrade());
				listModel.addElement(grade);
			}
	}
	
	/**
	 * sets the overall grade field as the averaged grade of all submissions of the student
	 */
	public void setOverallGrade() {
		double sum = 0;
		double numOfGraded = 0;
		double overall = 0;
		for (Submission s: gradesList) {
			if (s.getGrade() != -1) {
				numOfGraded++;
				sum += s.getGrade();
			}
		}
		if (numOfGraded != 0) {
			overall = sum/numOfGraded;
		}
		String overallGrade = String.format("%.2f%%", overall);
		overallGradeField.setText(overallGrade);
	}
	
	/**
	 * creates list of grades
	 * @param gl_contentPane GroupLayout object
	 * @param scrollPane JScrollPane object
	 */
	private void createGradesList(GroupLayout gl_contentPane, JScrollPane scrollPane) {
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		listArea.setForeground(new Color(70, 130, 180));
		listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listArea);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * creates fields for grade view
	 */
	private void createFields() {
		courseNameField = new JTextField();
		courseNameField.setBorder(null);
		courseNameField.setBackground(new Color(176, 224, 230));
		courseNameField.setEditable(false);
		courseNameField.setText("Course 1");
		courseNameField.setForeground(new Color(0, 0, 255));
		courseNameField.setFont(new Font("Tahoma", Font.PLAIN, 23));
		courseNameField.setColumns(10);
		
		overallGradeField = new JTextField();
		overallGradeField.setText("85%");
		overallGradeField.setForeground(new Color(255, 0, 0));
		overallGradeField.setFont(new Font("Tahoma", Font.PLAIN, 23));
		overallGradeField.setEditable(false);
		overallGradeField.setColumns(10);
		overallGradeField.setBorder(null);
		overallGradeField.setBackground(new Color(176, 224, 230));
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
	 * adds the closing listener for the course view
	 * @param w abstract adapter object for receiving window events
	 */
	public void addClosingListener(WindowAdapter w) {
		addWindowListener(w);
	}
}
