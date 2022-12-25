
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreadServer extends Thread {
	private Socket socket;
	private Server server;
	private PrintWriter writer;
	private String _username;
	private Connection conn;
	private List<String> _listFriend = new ArrayList<String>();
	private List<String> _listFriendRequest = new ArrayList<String>();

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
									
									updateListFriend(server.getUserThreads());
									updateFriendRequest(this, _username);
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
				
				case "friend_request": {
					try {
						Statement st = conn.createStatement();
						String query = "select username from taikhoan where username ='" + data[2] + "';";
						ResultSet rs = st.executeQuery(query);
						
						if (rs.next()) {
							query = "select * from BanBe where user_username = '" + data[1] + "'"
									+ "and friend_username = '" + data[2] + "'";
							rs = st.executeQuery(query);
							
							if (rs.next()) {
								//writer.println("friend");
							} else {
								query = "insert into LoiMoiKetBan(sender_username, receiver_username) "
										+ "values ('" + data[1] + "', '" + data[2] + "')"; 
								st.executeUpdate(query);
								//writer.print("successful");
								
								// Send friend-request to client if onl
								if (server.getUserThreads().containsKey(data[2])) {
									updateFriendRequest(server.getUserThreads().get(data[2]), data[2]);
								}
							}
							
						} else {
							writer.println("not_exist");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				
				case "message": {

					break;
				}
				default: {
					System.out.println("Message is invalid!");
				}
				}
			}

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
		Statement st;
		String listOnl = "";
		
		try {
			st = conn.createStatement();
			String query = "select friend_username from BanBe where user_username = '" + _username + "'";
			ResultSet rs = st.executeQuery(query);
			_listFriend.add(_username);

			while (rs.next()) {
				_listFriend.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Update online - offline and send to client
		for (String friend : _listFriend) {
			if (listOnline.containsKey(friend)) {
				listOnl += (friend.concat(" 1")).concat(",");
			} else {
				listOnl += (friend.concat(" 0")).concat(",");
			}
		}
		listOnl = "update_online_list-" + listOnl;

		for (String friend : _listFriend) {
			if (listOnline.containsKey(friend)) {
				server.sendMessageToAUser(listOnline.get(friend), listOnl);
			}
		}
	}
	
	// Update Friend Request
	public void updateFriendRequest(ThreadServer receiver, String usernameReceiver) {
		Statement st;
		String listReq = "friend_request-";
		
		try {
			st = conn.createStatement();
			String query = "select sender_username from LoiMoiKetBan where receiver_username = '" + usernameReceiver + "'";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				_listFriendRequest.add(rs.getString(1));
				listReq = listReq.concat(rs.getString(1).concat(","));
				System.out.println(listReq);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.sendMessageToAUser(receiver, listReq);
	}

	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}
}