package frontEnd;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JPasswordField;

public class SendEmailPanel extends JPanel {
	private JTextField subjectField;
	private JTextField emailAddressField;
	private JPasswordField passwordField;
	JTextArea contentField = new JTextArea();

	/**
	 * Create the panel.
	 */
	public SendEmailPanel() {
		
		JLabel lblSendingAnEmail = new JLabel("Sending An Email");
		lblSendingAnEmail.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblSendingAnEmail_1 = new JLabel("Sending An Email");
		lblSendingAnEmail_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblYourEmailAddress_1 = new JLabel("Your Email Address:");
		lblYourEmailAddress_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblYourEmailPassword_1 = new JLabel("Your Email Password:");
		lblYourEmailPassword_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblSubject_1 = new JLabel("Subject:");
		lblSubject_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblContent_1 = new JLabel("Content:");
		lblContent_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 180));
		
		subjectField = new JTextField();
		subjectField.setColumns(10);
		
		emailAddressField = new JTextField();
		emailAddressField.setColumns(15);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(12);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblYourEmailPassword_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblYourEmailAddress_1)
							.addGap(18)
							.addComponent(emailAddressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblSendingAnEmail_1)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblContent_1)
								.addComponent(lblSubject_1))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(subjectField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addComponent(lblSendingAnEmail_1)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYourEmailAddress_1)
						.addComponent(emailAddressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYourEmailPassword_1)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubject_1)
						.addComponent(subjectField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblContent_1)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(94))
		);
		
		JScrollPane scrollPane_1 = new JScrollPane(contentField);
		panel.add(scrollPane_1);
		
		
		contentField.setColumns(24);
		contentField.setRows(10);
		//panel.add(contentField);
		setLayout(groupLayout);

	}
	
	public String getSender() {
		return emailAddressField.getText();
	}
	
	@SuppressWarnings("deprecation")
	public String getPassword() {
		return passwordField.getText();
	}
	
	public String getSubject() {
		return subjectField.getText();
	}
	
	public String getContent() {
		return contentField.getText();
	}
}
