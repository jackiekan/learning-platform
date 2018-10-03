package frontEnd;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class ProfNewCoursePanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ProfNewCoursePanel() {
		
		JLabel newCourseTitleLabel = new JLabel("Create a New Course");
		newCourseTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel courseNameLabel = new JLabel("Course Name:");
		courseNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblSetCourseActive = new JLabel("Activate Course:");
		lblSetCourseActive.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(42)
							.addComponent(newCourseTitleLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(79)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(courseNameLabel)
								.addComponent(lblSetCourseActive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(15)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(207, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(newCourseTitleLabel)
					.addGap(52)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(courseNameLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(99)
					.addComponent(lblSetCourseActive, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(152, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
