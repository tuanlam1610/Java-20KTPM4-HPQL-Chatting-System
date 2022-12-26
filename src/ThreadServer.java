
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
	private ServerUpdateListFriendThread _updateListFriend;
	private ServerUpdateFriendRequestThread _updateFriendRequest;
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

									// Thread update list friend
									updateListFriend();

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
				case "register": {
					try {
						Statement st = conn.createStatement();
						String query = "select username from taikhoan where username ='" + data[1] + "';";
						ResultSet rs = st.executeQuery(query);

						if (rs.next()) {
							writer.println("Fail-username");
							break;
						}
						query = "select username from taikhoan where email ='" + data[2] + "';";
						rs = st.executeQuery(query);
						if (rs.next()) {
							writer.println("Fail-email");
							break;
						}
						query = "insert into TaiKhoan(username, pass, email, isAdmin, ngaytao)"
								+ "values ('" + data[1] + "', '" + data[3] + "', '" + data[2] + "', false, current_timestamp());" ;
						System.out.println(query);
						st.executeUpdate(query);
						writer.println("Success");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}

				case "update_list_friend_online": {
					// updateListFriend();
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
								// writer.println("friend");
							} else {
								query = "insert into LoiMoiKetBan(sender_username, receiver_username) " + "values ('"
										+ data[1] + "', '" + data[2] + "')";
								st.executeUpdate(query);
								// writer.print("successful");

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

				case "reply_friend_request": {
					// replyFriendRequest(status, sender, receiver);
					replyFriendRequest(data[1], data[2], data[3]);

					// updateFriendRequest(Thread_receiver, receiver)
					updateFriendRequest(server.getUserThreads().get(data[3]), data[3]);

					// Update List Friend
					updateListFriend();
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

			ex.printStackTrace();
		}
	}

	// Update Online List

	public void updateListFriend() {
		_updateListFriend = new ServerUpdateListFriendThread(server, conn, _username);
		_updateListFriend.start();
	}

	// Update Friend Request
	public void updateFriendRequest(ThreadServer threadReceiver, String receiver) {
		_updateFriendRequest = new ServerUpdateFriendRequestThread(server, threadReceiver, conn, receiver);
		_updateFriendRequest.start();
	}

	public void replyFriendRequest(String status, String sender, String receiver) {
		Statement st;
		String query = "";
		ResultSet rs;

		try {
			st = conn.createStatement();

			if (status.equals("YES")) {
				// Xóa 1 dòng trong LoiMoiKetBan va thêm 2 dòng trong BanBe
				query = "delete from LoiMoiKetBan where receiver_username = '" + receiver + "'";
				st.executeUpdate(query);

				// Insert
				query = "insert into BanBe(user_username, friend_username) " + "values ('" + sender + "', '" + receiver
						+ "')";
				st.executeUpdate(query);

				query = "insert into BanBe(user_username, friend_username) " + "values ('" + receiver + "', '" + sender
						+ "')";
				st.executeUpdate(query);
			} else {
				query = "delete from LoiMoiKetBan where receiver_username = '" + receiver + "'";
				st.executeUpdate(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}
}