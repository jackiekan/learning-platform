package backEnd;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sharedObjects.Email;
import sharedObjects.EmailLoginInfo;

/**
 * 
 * @author Armin Ghezelbashan & Jaycln Kan
 * This class is responsible for handling email communication 
 * for the server
 */
public class EmailHelper {
	
	private String SERVER_EMAIL;
	private String SERVER_PASSWORD;
	private Properties properties;
	private Session session;
	
	/**
	 * Constructs the email helper object and prepares
	 * it for gmail communicaiton
	 */
	public EmailHelper() {
		SERVER_EMAIL = "ensf409.2018@gmail.com";
		SERVER_PASSWORD = "ENSF-409";
		
		properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true"); 
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.smtp.host", "smtp.gmail.com"); 
		properties.put("mail.smtp.port", "587"); 
	}
	
	/**
	 * Receives the email login info of the client and the email to send
	 * and if properly authenticated, sends the email
	 * @param email the email to send
	 * @param loginInfo the login info of the client's email
	 * @throws AuthenticationFailedException if client not properly authenticated
	 */
	public void sendEmail(Email email, EmailLoginInfo loginInfo) throws AuthenticationFailedException {
		
		session = Session.getInstance(properties,
				new javax.mail.Authenticator(){
				 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication(loginInfo.getEmail(), loginInfo.getPassword());
				 }
				});
		
		ArrayList<String> recipients = email.getReceivers();
		if(!recipients.isEmpty()) {
			try {
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(email.getSender()));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients.get(0)));
					for(int i = 1; i < recipients.size(); i++) {
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipients.get(i)));	
					}
					message.setSubject(email.getSubject());
					message.setText(email.getContent());
					Transport.send(message); 
				} catch(AuthenticationFailedException e) { 
					throw e;
				}
				catch (MessagingException e) {
					e.printStackTrace();
				}
		}	
	}
}
