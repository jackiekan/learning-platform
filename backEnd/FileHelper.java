//Armin Ghezelbashan & Jacyln Kan

package backEnd;

import java.io.*;

import sharedObjects.Assignment;
import sharedObjects.Submission;

/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is responsible for handling the file input/output 
 * for the server
 */
public class FileHelper {

	private String PATH;
	private String assignmentDirectoryPath;
	private String submissionDirectoryPath;
	
	/**
	 * Constructs a fileHelper with the default assignments and submissions directories
	 */
	public FileHelper() {
		PATH = null;
		assignmentDirectoryPath = "C:\\ENSF409\\TermProject\\assignments\\";
		submissionDirectoryPath = "C:\\ENSF409\\TermProject\\submissions\\";
//		assignmentDirectoryPath = "/Users/Armin/Desktop/ENSF409/FinalProject/Assignments/";
//		submissionDirectoryPath = "/Users/Armin/Desktop/ENSF409/FinalProject/Submissions/";
	}
	
	/**
	 * Receives a file path and returns the byte content of the file
	 * @param path the path of the file
	 * @return the byte content
	 */
	public byte[] getFileContent(String path) {
		PATH = path;
		
		File selectedFile = new File(PATH);
		long length = selectedFile.length();
		byte[] content = new byte[(int)length];
		try {
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(content, 0, (int)length);
			bis.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * Receives an assignment and its byte content and saves it 
	 * to the server
	 * @param a the assignment being saved
	 * @param content the content of the assignment file
	 */
	public void writeFileContent(Assignment a, byte[] content) {
		File newFile = new File(a.getPath());
		try {
			if(!newFile.exists())
				newFile.createNewFile();
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			bos.write(content);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a submission and its byte content and saves it 
	 * to the server
	 * @param s the submission being saved
	 * @param content the content of the submission file
	 */
	public void writeFileContent(Submission s, byte[] content) {
		File newFile = new File(s.getPath());
		try {
			if(!newFile.exists())
				newFile.createNewFile();
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			bos.write(content);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * returns the assignment directory path of the server
	 * @return the directory path
	 */
	public String getAssignmentDirectory() {
		return assignmentDirectoryPath;
	}
	
	/**
	 * returns the submisison directory path of the server
	 * @return the directory path
	 */
	public String getSubmissionDirectory() {
		return submissionDirectoryPath;
	}
}
