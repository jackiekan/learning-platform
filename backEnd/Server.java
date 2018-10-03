package backEnd;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dataBase.DBHelper;
/**
 * 
 * @author Armin Ghezelbashan & Jacyln Kan
 * This class is the main server class responsible for accepting new clients and 
 * assigning them to a worker thread within a thread pool
 */
public class Server {
	
	private ServerSocket serverSocket;
	private ExecutorService pool;
	
	private DBHelper dbHelper;
	private FileHelper fileHelper;
	private EmailHelper emailHelper;
	
	/**
	 * Constructs a new server with port 9090 and 
	 * new helpers
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(9090);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbHelper = new DBHelper();
		fileHelper = new FileHelper();
		emailHelper = new EmailHelper();
		System.out.println("Server is running...");
	}
	
	/**
	 * Continuously looks for new clients and assigns them to a worker thread
	 * as they join the server
	 */
	public void runServer() {
		while(true) {
			try {
				Worker w = new Worker(serverSocket.accept(), dbHelper, fileHelper, emailHelper);
				pool.execute(w);
				System.out.println("New client connected!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args) {
		Server theServer = new Server();
		theServer.runServer();
	}
}
