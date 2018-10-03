//Armin Ghezelbashan & Jacyln Kan

package frontEnd;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ProfStudentSearchPanel extends JPanel {
	private JTextField lastNameField;
	private JTextField idField;
	private JTextField firstNameField;
	private JCheckBox unenrollCheckBox;
	
	public JTextField getLastName() {
		return lastNameField;
	}
	
	public JTextField getFirstName() {
		return firstNameField;
	}
	
	public JTextField getID() {
		return idField;
	}
	
	public boolean unenroll() { 
		if (unenrollCheckBox.isSelected()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Create the panel.
	 */
	public ProfStudentSearchPanel() {
		
		createFields();
		createCheckBox();
		
		JLabel firstNameLabel = new JLabel("Student First Name:");
		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lastNameLabel = new JLabel("Student Last Name:");
		lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel studentIDLabel = new JLabel("Student ID:");
		studentIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		GroupLayout groupLayout = new GroupLayout(this);
		
		createGL(groupLayout, lastNameLabel, firstNameLabel, studentIDLabel);
	}

	private void createGL(GroupLayout groupLayout, JLabel lastNameLabel, JLabel firstNameLabel, JLabel studentIDLabel) {
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(37)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lastNameLabel)
								.addGap(18)
								.addComponent(lastNameField))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(firstNameLabel)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(firstNameField, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(studentIDLabel)
								.addGap(18)
								.addComponent(idField, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
							.addComponent(unenrollCheckBox))
						.addGap(172))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(64)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(firstNameLabel)
							.addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(36)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lastNameLabel)
							.addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(36)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(studentIDLabel)
							.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(47)
						.addComponent(unenrollCheckBox)
						.addContainerGap(73, Short.MAX_VALUE))
			);
			setLayout(groupLayout);

	}

	private void createCheckBox() {
		unenrollCheckBox = new JCheckBox(" Unenroll Student From Course");
		unenrollCheckBox.setForeground(new Color(255, 0, 0));
		unenrollCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		unenrollCheckBox.setActionCommand("unenrollStudent");
	}

	private void createFields() {
		lastNameField = new JTextField();
		lastNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lastNameField.setForeground(new Color(0, 0, 255));
		lastNameField.setBorder(null);
		lastNameField.setEditable(false);
		lastNameField.setColumns(10);
		
		idField = new JTextField();
		idField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idField.setForeground(new Color(0, 0, 255));
		idField.setBorder(null);
		idField.setEditable(false);
		idField.setColumns(10);
		
		firstNameField = new JTextField();
		firstNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		firstNameField.setForeground(new Color(0, 0, 255));
		firstNameField.setBorder(null);
		firstNameField.setEditable(false);
		firstNameField.setColumns(10);
	}
}
