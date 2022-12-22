
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class ThreadServer extends Thread {
	private Socket socket;
	private Server server;
	private PrintWriter writer;
	private String _username;
	private Connection conn;

	public ThreadServer(Socket socket, Server server, Connection conn) {
		this.socket = socket;
		this.server = server;
		this.conn = conn;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			while(true) {
				String message = reader.readLine();
				System.out.println(message);
				String[] data = message.split("-");
				for (String w: data) {
					System.out.println(w);
				}
				switch (data[0])
				{
				case "login":{
					try {
						Statement st = conn.createStatement();
						String query = "select username, isAdmin, pass from taikhoan where username ='" + data[1] + "';";
						ResultSet rs = st.executeQuery(query);
						if (rs.next()) {
							System.out.println(rs.getString(3));
							System.out.println(data[2]);
							
							if (data[2].equals(rs.getString(3))) {
								if (rs.getString(2).equals("1")) {
									writer.println("logined-1");
								}
								else {
									writer.println("logined-0");
								}
							}
							else {
								writer.println("notlogined");
							}
						}
						else {
							writer.println("notlogined");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				default:{
					System.out.println("Message is invalid!");
				}
				}
			}
			// Add vao HashMap
//			server.addUserName(this, _username);
//		
//			// Update Online List
//			updateOnlineList(server.getUserThreads().values(), this);
//			//------------------//
//			
//			String serverMessage = _username + " has joined";
//			server.broadcast(serverMessage, this);
//
//			String clientMessage;
//
//			do {
//				clientMessage = reader.readLine();
//				serverMessage = _username + ": " + clientMessage;
//				server.broadcast(serverMessage, this);
//
//			} while (!clientMessage.equals("bye"));
//
//			server.removeUser(_username, this);
//			updateOnlineList(server.getUserThreads().values(), this);
//			socket.close();
//
//			serverMessage = _username + " has left";
//			server.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in ThreadServer: " + ex.getMessage());
			server.removeUser(_username, this);
			updateOnlineList(server.getUserThreads().values(), this);
			//socket.close();
			ex.printStackTrace();
		}
	}
	
	// Update Online List
	
	public void updateOnlineList(Collection<String> listUser, ThreadServer threadServer) {
		String listOfUserName = "";
		
		for (String username : listUser) {
			listOfUserName += username.concat("-");
		}
		listOfUserName = "update_online_list," + listOfUserName;
		server.broadcast(listOfUserName, threadServer);
	}
	
	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}
}