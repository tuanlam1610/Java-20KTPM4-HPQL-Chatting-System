
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ThreadServer extends Thread {
	private Socket socket;
	private Server server;
	private PrintWriter writer;
	private String _username;
	private Connection conn;
	private List<String> _listFriend = new ArrayList<String>();

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

			while (true) {
				String message = reader.readLine();
				System.out.println(message);
				String[] data = message.split("-");
				for (String w : data) {
					System.out.println(w);
				}
				switch (data[0]) {
				case "login": {
					try {
						Statement st = conn.createStatement();
						String query = "select username, isAdmin, pass from taikhoan where username ='" + data[1]
								+ "';";
						ResultSet rs = st.executeQuery(query);
						if (rs.next()) {
							System.out.println(rs.getString(3));
							System.out.println(data[2]);

							if (data[2].equals(rs.getString(3))) {
								if (rs.getString(2).equals("1")) {
									writer.println("logined-1");
								} else {
									server.addUserName(this, data[1]);
									_username = server.getUserName(this);
									writer.println("logined-0");
									query = "select friend_username from BanBe where user_username = '" + data[1] + "'";
									rs = st.executeQuery(query);
									_listFriend.add(data[1]);

									while (rs.next()) {
										_listFriend.add(rs.getString(1));
									}
									updateListFriend(server.getUserThreads());
								}
							} else {
								writer.println("notlogined");
							}
						} else {
							writer.println("notlogined");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "message": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String senderName = data[1];
					String receiverName = data[2];
					String msg = data[3];
					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sendermsgDB;
						String receivermsgDB;
						// Get msg from sender DB
						String sql = "SELECT tinnhan FROM banbe WHERE user_username = '" + senderName
								+ "' AND friend_username = '" + receiverName + "'";
						ResultSet rs = stmt.executeQuery(sql);
						if (rs.next()) sendermsgDB = rs.getNString("tinnhan");
						else sendermsgDB = "";
						sql = "SELECT tinnhan FROM banbe WHERE user_username = '" + receiverName
								+ "' AND friend_username = '" + senderName + "'";
						rs = stmt.executeQuery(sql);
						if (rs.next()) receivermsgDB = rs.getNString("tinnhan");
						else receivermsgDB = "";
						sendermsgDB = sendermsgDB + msg + "\n";
						receivermsgDB = receivermsgDB + msg + "\n";
						sql = "UPDATE banbe SET tinnhan = ? WHERE user_username = ? AND friend_username = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, sendermsgDB);
						pstmt.setString(2, senderName);
						pstmt.setString(3, receiverName);
						pstmt.execute();
						pstmt.setString(1, receivermsgDB);
						pstmt.setString(2, receiverName);
						pstmt.setString(3, senderName);
						pstmt.execute();
						pstmt.close();
						// Send MSG
						// When Receiver is online
						if (listOnline.containsKey(receiverName)) {
							ThreadServer receiverThread = listOnline.get(receiverName);
							server.sendMessageToAUser(receiverThread, message);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				default: {
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
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			server.removeUser(_username, this);
			updateListFriend(server.getUserThreads());
			ex.printStackTrace();
		}
	}

	// Update Online List
	public void updateListFriend(HashMap<String, ThreadServer> listOnline) {
		String listOnl = "";
		for (String friend : _listFriend) {
			if (listOnline.containsKey(friend)) {
				listOnl += (friend.concat(" 1")).concat(",");
			} else {
				listOnl += (friend.concat(" 0")).concat(",");
			}
		}
		listOnl = "update_online_list-" + listOnl;

		for (String friend : _listFriend) {
//			for (ThreadServer aUser : listOnline.keySet()) {
//				if (listOnline.get(aUser).equals(friend)) {
//					server.sendMessageToAUser(aUser, listOnl);
//					;
//				}
//			}
			if (listOnline.containsKey(friend)) {
				server.sendMessageToAUser(listOnline.get(friend), listOnl);
			}
		}
	}

	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}
}