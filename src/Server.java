
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Server {
	private int port;
	private Connection conn;
	//private Set<String> userNames = new HashSet<>();
	private HashMap<String, ThreadServer> userThreads = new HashMap<String, ThreadServer>();
	public Server(int port) {
		this.port = port;
	}

	public HashMap<String, ThreadServer> getUserThreads() {
		return userThreads;
	}

	public void setUserThreads(HashMap<String, ThreadServer> userThreads) {
		this.userThreads = userThreads;
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chattingsystem","root","Iuuhoangminh11");
//				JOptionPane.showMessageDialog(null, "Connected to database successfully...");
				final JOptionPane pane = new JOptionPane();

		        Thread t1 = new Thread(new Runnable() {
		            public void run() {
		                try {
		                    Thread.sleep(500);
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		                pane.getRootFrame().dispose();

		            }
		        });
		        t1.start();
		        JOptionPane.showMessageDialog(pane, "Connected to database successfully...");
			}catch (SQLException se) { // Handle errors for JDBC
				se.printStackTrace();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");

				ThreadServer newUser = new ThreadServer(socket, this, conn);
				newUser.start();
			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(1234);
		server.execute();
	}
	
	public void broadcast(String message) {
		for(Map.Entry<String, ThreadServer> entry : userThreads.entrySet()) {
			//String username = entry.getKey();
			ThreadServer userThread = entry.getValue();
			userThread.sendMessage(message);
		}
//		for (ThreadServer aUser : userThreads.get()) {
//			aUser.sendMessage(message);
//		}
	}
	
	public void sendMessageToAUser(ThreadServer threadServer, String message) {
		threadServer.sendMessage(message);
	}
	
	public String getUserName(ThreadServer threadServer) {
//		String username = userThreads.get(threadServer);
//		return username;
		for(Map.Entry<String, ThreadServer> entry : userThreads.entrySet()) {
			if(entry.getValue().equals(threadServer)) {
				return entry.getKey();
			}
		}
		return "Can't find username";
	}

	public void addUserName(ThreadServer threadServer, String userName) {
		userThreads.put(userName, threadServer);
		System.out.println("Add userThread: " + userName + "-" + threadServer);
	}
	 
	public void removeUser(String userName, ThreadServer aUser) {
		userThreads.remove(userName);
		System.out.println("The user " + userName + " quitted");
	}
}