
package frontEnd;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 *  This is the class responsible for displaying the student enrollment panel
 *  for the professors
 */
public class ProfEnrollPanel extends JPanel {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField studentField;
	private JRadioButton idRButton;
	private JRadioButton lastNameRButton;

	/**
	 * Create the panel.
	 */
	public ProfEnrollPanel() {
		
		JLabel enrollTitleLabel = new JLabel("Enroll a New Student");
		enrollTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		JLabel viaLabel = new JLabel("Search Students Via:");
		viaLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		idRButton = new JRadioButton("Student ID");
		buttonGroup.add(idRButton);
		idRButton.setSelected(true);
		idRButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		lastNameRButton = new JRadioButton("Student Last Name");
		buttonGroup.add(lastNameRButton);
		lastNameRButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel paramLabel = new JLabel("Search Parameter:");
		paramLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		studentField = new JTextField();
		studentField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		studentField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(enrollTitleLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(91)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(viaLabel)
								.addComponent(paramLabel))
							.addGap(41)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(idRButton)
									.addGap(32)
									.addComponent(lastNameRButton, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE))
								.addComponent(studentField, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(209, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addComponent(enrollTitleLabel)
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idRButton)
						.addComponent(lastNameRButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(viaLabel))
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(paramLabel)
						.addComponent(studentField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(215, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	public String getStudentField() {
		return studentField.getText();
	}
	
	public boolean searchByID() {
		if (idRButton.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}
}
