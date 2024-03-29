
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
				case "them_tai_khoan":{
					try {
						Statement st = conn.createStatement();
						String query = "select username from taikhoan where username ='" + data[1] + "';";
						ResultSet rs = st.executeQuery(query);

						if (rs.next()) {
							writer.println("admin_add_user|Fail|username");
							break;
						}
						query = "select username from taikhoan where email ='" + data[4] + "';";
						rs = st.executeQuery(query);
						if (rs.next()) {
							writer.println("admin_add_user|Fail|email");
							break;
						}
//						for (int i = 3; i < data.length; i++) {
//							if (data[i].equals("")) {
//								data[i] = null;
//							}
//						}
						query = "insert into TaiKhoan(username, pass, hoten, email, gioitinh, isAdmin, ngaytao, isLocked)" + "values ('" + data[1]
								+ "', '" + data[2] + "', '" + data[3] + "', '" + data[4] + "', '"  + data[7] + "', false, current_timestamp(), false);";
						st.executeUpdate(query);
						if (data[5].equals("") == false) {
							query = "update taikhoan set dob ='" + data[5] +"' where username ='" + data[1] + "';";
							st.executeUpdate(query);
						}
						if (data[6].equals("") == false) {
							query = "update taikhoan set diachi ='" + data[6] +"' where username ='" + data[1] + "';";
							st.executeUpdate(query);
						}
						writer.println("admin_add_user|Success");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "doi_mat_khau_tai_khoan":{
					try {
						Statement st = conn.createStatement();
						String query = "select username,email from taikhoan where username ='" + data[1]
								+ "' AND email ='" + data[2] + "';";
						ResultSet rs = st.executeQuery(query);
						
						if (rs.next()) {
							int leftLimit = 97; // letter 'a'
							int rightLimit = 122; // letter 'z'
							int targetStringLength = 10;
							Random random = new Random();
							StringBuilder buffer = new StringBuilder(targetStringLength);
							for (int i = 0; i < targetStringLength; i++) {
								int randomLimitedInt = leftLimit
										+ (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
								buffer.append((char) randomLimitedInt);
							}
							String generatedString = buffer.toString();
							SendEmail.sendMail(rs.getString(1), rs.getString(2), generatedString);
							query = "update taikhoan set pass = '" + generatedString + "' where username ='"
									+ rs.getString(1) + "';";
							st.executeUpdate(query);
							break;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "xoa_tai_khoan":{
					try {
						Statement st = conn.createStatement();
						String query = "delete from banbe where user_username = '" + data[1] + "' or friend_username = '"+ data[1] + "';";
						st.executeUpdate(query);
						query = "delete from lichsudangnhap where username = '" + data[1] + "';";
						st.executeUpdate(query);
						query = "delete from loimoiketban where receiver_username = '" + data[1] + "' or sender_username = '"+ data[1] + "';";
						st.executeUpdate(query);
						query = "delete from thanhviennhom where username = '" + data[1] + "';";
						st.executeUpdate(query);
						query = "delete from taikhoan where username = '" + data[1] +"';";
						st.executeUpdate(query);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "cap_nhat_tai_khoan":{
					try {
						Statement st = conn.createStatement();
						if (data[2].equals("") == false) {
							String query = "update taikhoan set hoten ='" + data[2] + "' where username = '" + data[1] + "';";
							st.executeUpdate(query);
						}
						if (data[3].equals("") == false) {
							String query = "update taikhoan set diachi ='" + data[3] + "' where username = '" + data[1] + "';";
							st.executeUpdate(query);
						}
						if (data[4].equals("") == false) {
							String query = "update taikhoan set dob ='" + data[4] + "' where username = '" + data[1] + "';";
							st.executeUpdate(query);
						}
						if (data[5].equals("") == false) {
							int gender = 0;
							if (data[5].equals("Nam")) gender = 1;
							String query = "update taikhoan set gioitinh ='" + gender + "' where username = '" + data[1] + "';";
							st.executeUpdate(query);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "login": {
					try {
						Statement st = conn.createStatement();
						String query = "select username, isAdmin, pass, isLocked from taikhoan where username ='" + data[1]
								+ "';";
						ResultSet rs = st.executeQuery(query);
						if (rs.next()) {
							System.out.println(rs.getString(3));
							System.out.println(data[2]);
							if (rs.getString(4).equals("0")) {
								if (data[2].equals(rs.getString(3))) {
									Statement stmt = conn.createStatement();
									query = "update taikhoan set thoigiandangnhap = current_timestamp() where username = '" + data[1] +"';";
									stmt.executeUpdate(query);
									//add to login history
									query = "insert into lichsudangnhap (username, thoigiandangnhap) values ('"+ data[1] + "', current_timestamp());";
									stmt.executeUpdate(query);
									if (rs.getString(2).equals("1")) {
										server.addUserName(this, data[1]); 
										_username = server.getUserName(this); //them login cho admin
										writer.println("logined-1");
									} else {
										server.addUserName(this, data[1]);
										_username = server.getUserName(this);
										writer.println("logined-0");

										// Thread update list friend
										updateListFriend();
										
										updateFriendRequest(this, _username);
									}
									break;
								} else {
									writer.println("notlogined");
								}
							}
							else {
								writer.println("lock");
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
				case "logout":{
					String username = data[1];
					server.sendMessageToAUser(server.getUserThreads().get(username), message);
					server.removeUser(username, this);
					return;
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
						query = "select username from taikhoan where email ='" + data[4] + "';";
						rs = st.executeQuery(query);
						if (rs.next()) {
							writer.println("Fail-email");
							break;
						}
						
						query = "insert into TaiKhoan(username, pass, hoten, email, gioitinh, isAdmin, ngaytao, isLocked)" + "values ('" + data[1]
								+ "', '" + data[2] + "', '" + data[3] + "', '" + data[4] + "', '"  + data[7] + "', false, current_timestamp(), false);";
						st.executeUpdate(query);
						if (data[5].equals("") == false) {
							query = "update taikhoan set dob ='" + data[5] +"' where username ='" + data[1] + "';";
							st.executeUpdate(query);
						}
						if (data[6].equals("") == false) {
							query = "update taikhoan set diachi ='" + data[6] +"' where username ='" + data[1] + "';";
							st.executeUpdate(query);
						}
						writer.println("Success");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "resetpw": {
					try {
						Statement st = conn.createStatement();
						String query = "select username,email from taikhoan where username ='" + data[1]
								+ "' AND email ='" + data[2] + "';";
						ResultSet rs = st.executeQuery(query);

						if (rs.next()) {
							writer.println("Success");
							int leftLimit = 97; // letter 'a'
							int rightLimit = 122; // letter 'z'
							int targetStringLength = 10;
							Random random = new Random();
							StringBuilder buffer = new StringBuilder(targetStringLength);
							for (int i = 0; i < targetStringLength; i++) {
								int randomLimitedInt = leftLimit
										+ (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
								buffer.append((char) randomLimitedInt);
							}
							String generatedString = buffer.toString();
							SendEmail.sendMail(rs.getString(1), rs.getString(2), generatedString);
							query = "update taikhoan set pass = '" + generatedString + "' where username ='"
									+ rs.getString(1) + "';";
							st.executeUpdate(query);
							break;
						} else {
							writer.println("Fail");
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
				case "delete_chat": {
					deleteChat(data[1],data[2]);
					break;
				}
				case "remove_friend": {
					removeFriend(data[1], data[2]);

					break;
				}

				case "message": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String msgType = data[1];
					if (!msgType.equals("user") && !msgType.equals("group")) {
						System.out.println("Invalid message type(Must be 'user' or 'group')");
						break;
					}
					String senderName = data[2];
					String receiverName = data[3];
					String msg = data[4];
					try {
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sql = "";
						if (msgType.equals("user")) {
							String sendermsgDB = "";
							String receivermsgDB = "";
							// Update Database
							// Update 2 row for sender and receiver in DB
							// Get msg from sender DB
							sql = "SELECT tinnhan FROM banbe WHERE user_username = '" + senderName
									+ "' AND friend_username = '" + receiverName + "'";
							ResultSet rs = stmt.executeQuery(sql);
							if (rs.next())
								sendermsgDB = rs.getNString("tinnhan");
							else
								sendermsgDB = "";
							sql = "SELECT tinnhan FROM banbe WHERE user_username = '" + receiverName
									+ "' AND friend_username = '" + senderName + "'";
							rs = stmt.executeQuery(sql);
							if (rs.next())
								receivermsgDB = rs.getNString("tinnhan");
							else
								receivermsgDB = "";
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
						} else {
							// Update Database
							// Get msg from DB
							String groupmsgDB = "";
							sql = "SELECT tinnhan FROM nhom WHERE tennhom = '" + receiverName + "'";
							ResultSet rs = stmt.executeQuery(sql);
							if (rs.next())
								groupmsgDB = rs.getNString("tinnhan");
							else
								groupmsgDB = "";
							groupmsgDB = groupmsgDB + msg + "\n";
							sql = "UPDATE nhom SET tinnhan = ? WHERE tennhom = ?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, groupmsgDB);
							pstmt.setString(2, receiverName);
							pstmt.execute();
							pstmt.close();
							System.out.println("Update group chat to DB successfully");
							// Send to online member
							// Query members in group chat except sender
							sql = "SELECT username FROM thanhviennhom JOIN nhom ON (thanhviennhom.ID_nhom = nhom.ID_nhom AND nhom.tennhom = '"
									+ receiverName + "')";
							rs = stmt.executeQuery(sql);
							ArrayList<String> membersName = new ArrayList<>();
							while (rs.next()) {
								String username = rs.getString("username");
								if (!username.equals(senderName))
									membersName.add(username);
							}
							System.out.println(membersName);
							// Send message to online members
							for (String member : membersName) {
								if (listOnline.containsKey(member)) {
									System.out.println("Send to " + member);
									ThreadServer receiverThread = listOnline.get(member);
									server.sendMessageToAUser(receiverThread, message);
								}
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}

				case "get_chat_history": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String senderName = data[1];
					String friendName = data[2];
					// String msg = data[3];
					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sendermsgDB;
						// String receivermsgDB;
						// Get msg from sender DB
						String sql = "SELECT tinnhan FROM banbe WHERE user_username = '" + senderName
								+ "' AND friend_username = '" + friendName + "'";
						ResultSet rs = stmt.executeQuery(sql);
						if (rs.next())
							sendermsgDB = rs.getNString("tinnhan");
						else
							sendermsgDB = "";

						if (sendermsgDB.equals(""))
							sendermsgDB = " ";

						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread,
								"get_chat_history-" + friendName + "-" + sendermsgDB + "\nEndOfString");
						// }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "get_group_chat_history": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String senderName = data[1];
					String groupName = data[2];
					// String msg = data[3];
					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sendermsgDB;
						// String receivermsgDB;
						// Get msg from sender DB
						String sql = "SELECT tinnhan FROM nhom WHERE tennhom = '" + groupName+ "'";
						ResultSet rs = stmt.executeQuery(sql);
						if (rs.next())
							sendermsgDB = rs.getNString("tinnhan");
						else
							sendermsgDB = "";
						
						if (sendermsgDB.equals(""))
							sendermsgDB = " ";
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread,
								"get_group_chat_history-" + groupName + "-" + sendermsgDB + "\nEndOfString");
						// }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				
				case "string_search": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String senderName = data[1];
					String friendName = data[2];
					String targetString = data[3];
					// String msg = data[3];
					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sendermsgDB;
						// String receivermsgDB;
						// Get msg from sender DB
						String sql = "SELECT tinnhan FROM banbe WHERE user_username = '" + senderName
								+ "' AND friend_username = '" + friendName + "'";
						ResultSet rs = stmt.executeQuery(sql);
						if (rs.next())
							sendermsgDB = rs.getString("tinnhan");
						else
							sendermsgDB = "";

						if (sendermsgDB.equals(""))
							sendermsgDB = " ";
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread,
								"string_search-" + targetString + "-" + sendermsgDB + "\nEndOfString");
						// }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "get_login_history": {
					// Initial Data
					//System.out.println("access_login_history");
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String senderName = data[1];
					//String friendName = data[2];
					// String msg = data[3];
					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sendermsgDB = "";
						// String receivermsgDB;
						// Get msg from sender DB
						String sql = "SELECT lichsudangnhap.thoigiandangnhap, lichsudangnhap.username, taikhoan.hoten  FROM lichsudangnhap Join taikhoan ON (lichsudangnhap.username = taikhoan.username);";
						System.out.println(0);
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println(1);
						while(rs.next()) {
							Timestamp dateCol = rs.getTimestamp("thoigiandangnhap");
							//System.out.println(dateCol.toString());
							String usernameCol = rs.getNString("username");
							//System.out.println(usernameCol);
							String nameCol = rs.getNString("hoten");
							//System.out.println(nameCol);
							
							sendermsgDB= sendermsgDB +"|" + dateCol.toString() + "," + usernameCol + "," + nameCol;
							//System.out.println(sendermsgDB);
						}

						if (sendermsgDB.equals(""))
							sendermsgDB = " ";
						
						System.out.println(sendermsgDB);
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread,"get_login_history" + sendermsgDB);
						// }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "create_group": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String group_admin = data[1];
					String group_name = data[2];
					String[] members = data[3].split(" ,");
					String date_created = data[4];

					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();

						String sql = "select tennhom from nhom;";
						ResultSet rs = stmt.executeQuery(sql);		
						List<String> list = new ArrayList<>();

						while(rs.next()){
						   list.add(rs.getString("tennhom"));
						}
						
						ThreadServer senderThread = listOnline.get(group_admin);
						
						if(list.contains(group_name)) {
							server.sendMessageToAUser(senderThread,"change_group_name-Group existed");
						}
						else {

							sql = "insert into nhom(tennhom, tinnhan, ngaytaonhom)\r\n"
									+ "values('" + group_name + "', '', '" + date_created + "');";
							stmt.executeUpdate(sql);

							sql = "insert into thanhviennhom(id_nhom, username, isgroupadmin)\r\n"
									+ "select n.id_nhom, '"+group_admin+"', '1'\r\n"
									+ "from nhom as n\r\n"
									+ "where n.tennhom = '"+group_name+"';";
							stmt.executeUpdate(sql);

							for (int i = 0; i < members.length; i++) {
								sql = "insert into thanhviennhom(id_nhom, username, isgroupadmin)\r\n"
										+ "select n.id_nhom, '"+members[i]+"', '0'\r\n"
										+ "from nhom as n\r\n"
										+ "where n.tennhom = '"+group_name+"';";
								stmt.executeUpdate(sql);
							}
							server.sendMessageToAUser(senderThread,"create_group-Group " + group_name + " created!");

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "change_group_name": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String group_member = data[1];
					String current_group_name = data[2];
					String new_group_name = data[3];
					try {

						Statement stmt = conn.createStatement();
						String sql = "select tennhom from nhom;";
						ResultSet rs = stmt.executeQuery(sql);					
						List<String> list = new ArrayList<>();

						while(rs.next()){
						   list.add(rs.getString("tennhom"));
						}
						
						ThreadServer senderThread = listOnline.get(group_member);
						
						if(list.contains(new_group_name)) {
							server.sendMessageToAUser(senderThread,"change_group_name-Group name existed");
						}
						else {
							sql = "update Nhom\r\n"
									+ "set tennhom = '"+new_group_name+"'\r\n"
									+ "where tennhom = '"+current_group_name+"';";
							stmt.executeUpdate(sql);
							server.sendMessageToAUser(senderThread,"change_group_name-Group name changed to " + new_group_name);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "grant_admin": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String group_member = data[1];
					String current_group_name = data[2];
					String new_admin = data[3];

					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();

						String sql = "SELECT tv.isgroupadmin FROM Nhom as N JOIN ThanhVienNhom as TV \r\n"
								+ "						ON N.ID_nhom = TV.ID_nhom \r\n"
								+ "						WHERE N.tennhom = '"+ current_group_name +"' and tv.username = '"+ group_member +"';";
						ResultSet rs = stmt.executeQuery(sql);
//						
						String is_admin = "";
						
						if(rs.next()) {
							is_admin = rs.getString("isgroupadmin");
						}
						else {
							is_admin = "error";
						}
						
						ThreadServer senderThread = listOnline.get(group_member);
						
						if(is_admin.equals("0")) {					
							server.sendMessageToAUser(senderThread,"grant_admin-User is not an admin!");
						}
						else if(is_admin.equals("1")) {
							sql = "update thanhviennhom\r\n"
									+ "set isgroupadmin = '1'\r\n"
									+ "where username = '"+new_admin+"';";
							stmt.executeUpdate(sql);
							server.sendMessageToAUser(senderThread,"grant_admin-user "+new_admin+" is now an admin \nEndOfString");
							
						}
						else {
							server.sendMessageToAUser(senderThread,"grant_admin-Error: User not found!");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "add_user_to_group": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String group_member = data[1];
					String current_group_name = data[2];
					String new_user = data[3];

					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();

						String sql = "SELECT username FROM Nhom as N JOIN ThanhVienNhom as TV \r\n"
								+ "						ON N.ID_nhom = TV.ID_nhom \r\n"
								+ "						WHERE N.tennhom = '"+current_group_name+"';";
						ResultSet rs = stmt.executeQuery(sql);
						
						List<String> list = new ArrayList<>();

						while(rs.next()){
						   list.add(rs.getString("username"));
						}
						
						sql = "select username from taikhoan where username = '"+new_user+"';";
						rs = stmt.executeQuery(sql);
						
						boolean is_exists = false;
						
						if(rs.next()){
							is_exists = true;
						}
						
						ThreadServer senderThread = listOnline.get(group_member);
						
						if(is_exists == false) {
							server.sendMessageToAUser(senderThread,"add_user_to_group-User doesn't exist!");
						}	
						else if(list.contains(new_user)) {					
							server.sendMessageToAUser(senderThread,"add_user_to_group-User already exists in group!");
						}
						else {
							sql = "insert into thanhviennhom(id_nhom, username, isgroupadmin)\r\n"
									+ "select n.id_nhom, '"+new_user+"', '0'\r\n"
									+ "from nhom as n\r\n"
									+ "where n.tennhom = '"+current_group_name+"';";
							stmt.executeUpdate(sql);
							server.sendMessageToAUser(senderThread,"add_user_to_group-user "+new_user+" is now added to the group "+current_group_name+"\nEndOfString");
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "remove_user_from_group": {
					// Initial Data
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String group_member = data[1];
					String current_group_name = data[2];
					String user_to_remove = data[3];

					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();

						String sql = "SELECT tv.isgroupadmin FROM Nhom as N JOIN ThanhVienNhom as TV \r\n"
								+ "						ON N.ID_nhom = TV.ID_nhom \r\n"
								+ "						WHERE N.tennhom = '"+ current_group_name +"' and tv.username = '"+ group_member +"';";
						ResultSet rs = stmt.executeQuery(sql);				
						String is_admin = "";
						
						if(rs.next()) {
							is_admin = rs.getString("isgroupadmin");
						}
						else {
							is_admin = "error";
						}
						
						sql = "SELECT username FROM Nhom as N JOIN ThanhVienNhom as TV \r\n"
								+ "						ON N.ID_nhom = TV.ID_nhom \r\n"
								+ "						WHERE N.tennhom = '"+current_group_name+"' and tv.username = '"+user_to_remove+"';";
						rs = stmt.executeQuery(sql);
						
						boolean is_exists = false;
						
						if(rs.next()){
							is_exists = true;
						}
						
						List<String> list = new ArrayList<>();
						
						
						ThreadServer senderThread = listOnline.get(group_member);
						
						
						
						
						
						if(is_exists == false) {
							server.sendMessageToAUser(senderThread,"remove_user_from_group-User does not exists in group!");
						}
						else if(is_admin.equals("0")) {					
							server.sendMessageToAUser(senderThread,"remove_user_from_group-User is not an admin!");
						}
						else if(is_admin.equals("1")) {
							
							if(user_to_remove.equals(group_member)) {
								sql = "delete from thanhviennhom \r\n"
										+ "where username = '"+user_to_remove+"' and id_nhom = (select n.id_nhom \r\n"
										+ "											from nhom as n \r\n"
										+ "                                            where n.tennhom = '"+current_group_name+"');  ";
								stmt.executeUpdate(sql);
								
								sql = "SELECT username FROM Nhom as N JOIN ThanhVienNhom as TV \r\n"
										+ "						ON N.ID_nhom = TV.ID_nhom \r\n"
										+ "						WHERE N.tennhom = '"+current_group_name+"';";
								rs = stmt.executeQuery(sql);
								
								

								while(rs.next()){
								   list.add(rs.getString("username"));
								}
								
								if(list.size() > 0) {
									int Random_User= Rndmbtwn(1, list.size());
									
									sql = "update thanhviennhom\r\n"
											+ "set isgroupadmin = '1'\r\n"
											+ "where username = '"+list.get(Random_User)+"';";
									stmt.executeUpdate(sql);
									
									server.sendMessageToAUser(senderThread,"remove_user_from_group-user "+user_to_remove+" is removed from group, "+list.get(Random_User)+" is now the admin "+current_group_name+"\nEndOfString");
								}
								
								else {
									
									
									sql = "delete from nhom where tennhom = '"+current_group_name+"';";
									stmt.executeUpdate(sql);
									
									server.sendMessageToAUser(senderThread,"remove_user_from_group-All user is removed from group, "+current_group_name+" is now deleted \nEndOfString");
								}
							}
							else {
								sql = "delete from thanhviennhom \r\n"
										+ "where username = '"+user_to_remove+"' and id_nhom = (select n.id_nhom \r\n"
										+ "											from nhom as n \r\n"
										+ "                                            where n.tennhom = '"+current_group_name+"');  ";
								stmt.executeUpdate(sql);
								server.sendMessageToAUser(senderThread,"remove_user_from_group-user "+user_to_remove+" is removed from group "+current_group_name+"\nEndOfString");
								
								sql = "SELECT username FROM Nhom as N JOIN ThanhVienNhom as TV \r\n"
										+ "						ON N.ID_nhom = TV.ID_nhom \r\n"
										+ "						WHERE N.tennhom = '"+current_group_name+"';";
								rs = stmt.executeQuery(sql);

								while(rs.next()){
								   list.add(rs.getString("username"));
								}
								
								if(list.size() <= 0) {
									sql = "delete from nhom where tennhom = '"+current_group_name+"';";
									stmt.executeUpdate(sql);
									
									server.sendMessageToAUser(senderThread,"remove_user_from_group-All user is removed from group, "+current_group_name+" is now deleted \nEndOfString");
								}
								
							}
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case "admin_updateGroup": {
					try {
						HashMap<String, ThreadServer> listOnline = server.getUserThreads();
						System.out.println("Message to server: " + message);
						String senderName = data[1];
						String sortValue = data[2];
						Statement stmt = conn.createStatement();
						String sql = "SELECT tennhom, ngaytaonhom FROM Nhom ORDER BY " + sortValue + " ASC";
						String msg = "admin_updateGroup|";
						ResultSet rs = stmt.executeQuery(sql);
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/YYYY");
						while(rs.next()) {
							msg = msg + rs.getNString("tennhom") + "," + dateformat.format(rs.getDate("ngaytaonhom")) + "_";
						}
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread, msg);
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "admin_getGroupMemberList":{
					try {
						HashMap<String, ThreadServer> listOnline = server.getUserThreads();
						String senderName = data[1];
						String selectedGroupName = data[2];
						System.out.println("Sender:" + senderName);
						Statement stmt = conn.createStatement();
						String sql = "SELECT t2.username, t2.hoten, t2.email, t2.dob "
									+ "FROM nhom AS n JOIN thanhviennhom t ON (n.ID_nhom = t.ID_nhom AND n.tennhom = '"
									+ selectedGroupName +"') JOIN taikhoan t2 on (t.username = t2.username)";
						String msg = "admin_getGroupMemberList|" + selectedGroupName + "|";
						ResultSet rs = stmt.executeQuery(sql);
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/YYYY");
						while(rs.next()) {
							msg = msg + rs.getNString("username") + "," + rs.getNString("hoten") + "," 
									  + rs.getNString("email") + "," + dateformat.format(rs.getDate("dob")) + "_";
						}
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread, msg);
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "admin_getGroupAdminList":{
					try {
						HashMap<String, ThreadServer> listOnline = server.getUserThreads();
						String senderName = data[1];
						String selectedGroupName = data[2];
						System.out.println("Sender:" + senderName);
						Statement stmt = conn.createStatement();
						String sql = "SELECT tk.username, tk.hoten, tk.email, tk.dob "
									+ "FROM nhom AS n JOIN thanhviennhom AS t ON (n.ID_nhom = t.ID_nhom and n.tennhom = '"
									+ selectedGroupName +"') JOIN taikhoan AS tk on (t.username = tk.username and t.isGroupAdmin = 1)";
						String msg = "admin_getGroupAdminList|" + selectedGroupName + "|";
						ResultSet rs = stmt.executeQuery(sql);
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/YYYY");
						while(rs.next()) {
							msg = msg + rs.getNString("username") + "," + rs.getNString("hoten") + "," 
									  + rs.getNString("email") + "," + dateformat.format(rs.getDate("dob")) + "_";
						}
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread, msg);
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				case "get_specific_login_history": {
					// Initial Data
					//System.out.println("access_login_history");
					HashMap<String, ThreadServer> listOnline = server.getUserThreads();
					String senderName = data[1];
					String targetName = data[2];
					// String msg = data[3];
					try {
						// Update Database
						// Update 2 row for sender and receiver in DB
						Statement stmt = conn.createStatement();
						PreparedStatement pstmt;
						String sendermsgDB = "";
						// String receivermsgDB;
						// Get msg from sender DB
						String sql = "SELECT  lichsudangnhap.username, lichsudangnhap.thoigiandangnhap  FROM lichsudangnhap where (lichsudangnhap.username = '" + targetName  + "');";
						System.out.println(0);
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println(1);
						while(rs.next()) {
							String usernameCol = rs.getNString("username");
							//System.out.println(usernameCol);
							Timestamp dateCol = rs.getTimestamp("thoigiandangnhap");
							//System.out.println(dateCol.toString());
							sendermsgDB= sendermsgDB +"|"  + usernameCol + "," + dateCol.toString();
							//System.out.println(sendermsgDB);
						}

						if (sendermsgDB.equals(""))
							sendermsgDB = " ";
						
						System.out.println(sendermsgDB);
						ThreadServer senderThread = listOnline.get(senderName);
						server.sendMessageToAUser(senderThread,"get_specific_login_history" + sendermsgDB);
						
						// }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				// -------------------------------- ADMIN -------------------------------
				case "(admin)_display_list_of_users": {
					
					displayListOfUsers(server.getUserThreads().get(data[1]));
					
					break;
				}
				
				case "(admin)_search_user": {
					
					searchUserbyUsernameAndName(server.getUserThreads().get(data[1]), data[2]);
					
					break;
				}
				
				case "(admin)_sort_user": {
					
					// sort (threadServer, filter)
					sortUserByFilter(server.getUserThreads().get(data[1]), data[2]);
					
					break;
				}
				
				case "(admin)_lock_user": {
					
					// block (threadServer, LockedUser, status)
					lockAndUnlockUser(server.getUserThreads().get(data[1]), data[2], data[3]);
					
					break;
				}
				
				case "(admin)_diplay_list_friends" : {
					
					// display...(threadServer, admin, username)
					displayListOfFriends(server.getUserThreads().get(data[1]), data[2]);
					
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

		try {
			st = conn.createStatement();

			if (status.equals("YES")) {
				// Xóa 1 dòng trong LoiMoiKetBan va thêm 2 dòng trong BanBe
				query = "delete from LoiMoiKetBan where receiver_username = '" + receiver + "'"
						+ "and sender_username = '" + sender + "'";
				st.executeUpdate(query);

				// Insert
				query = "insert into BanBe(user_username, friend_username, tinnhan) " + "values ('" + sender + "', '"
						+ receiver + "', " + "''" + ")";
				st.executeUpdate(query);

				query = "insert into BanBe(user_username, friend_username, tinnhan) " + "values ('" + receiver + "', '"
						+ sender + "', " + "''" + ")";
				st.executeUpdate(query);
			} else {
				query = "delete from LoiMoiKetBan where receiver_username = '" + receiver + "'"
						+ "and sender_username = '" + sender + "'";
				st.executeUpdate(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void removeFriend(String user, String friend) {
		Statement st;
		String query = "";

		try {
			st = conn.createStatement();
			query = "delete from BanBe where user_username = '" + user + "'" + "and friend_username = '" + friend + "'";
			st.executeUpdate(query);

			query = "delete from BanBe where user_username = '" + friend + "'" + "and friend_username = '" + user + "'";
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteChat(String user, String friend) {
		Statement st;
		String query = "";
		try {
			st = conn.createStatement();
			query = "update banbe set tinnhan = '' where user_username ='"
					+ user + "' and friend_username = '" + friend + "';";
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int Rndmbtwn(int min, int max) {
	    Random random = new Random();
	    return random.nextInt(max - min) + min;
	}
	
	// ------------------------------ ADMIN -------------------------------
	
	public void displayListOfUsers(ThreadServer threadSender) {
		Statement st;
		String query ="";
		String respond = "|";
		
		try {
			st = conn.createStatement();
			query = "SELECT username, hoten, diachi, dob, gioitinh, email, isLocked "
					+ "FROM TaiKhoan";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String usernameCol = rs.getString("username");
				String nameCol = rs.getString("hoten");
				String addressCol = rs.getString("diachi");
				String dobCol = rs.getString("dob");
				String gender = rs.getString("gioitinh");
				String emailCol = rs.getString("email");
				String isLocked = rs.getString("isLocked");
				
				String genderCol = "";
				if (gender.equals("1")) 
					genderCol = "Nam";
				else 
					genderCol = "Nữ";
				
				String isLockedCol = "";
				if (isLocked.equals("1")) 
					isLockedCol = "Bị khóa";
				else 
					isLockedCol = "Hoạt động";

				
				respond = respond + usernameCol + "," + nameCol + "," + addressCol + "," + dobCol + "," + genderCol + "," + emailCol + "," + isLockedCol + "|";
				//System.out.println(sendermsgDB);
			}
			
			System.out.println(respond);
			server.sendMessageToAUser(threadSender, "(admin)_display_list_of_users" + respond);
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchUserbyUsernameAndName(ThreadServer threadSender, String inputUser) {
		Statement st;
		String query ="";
		String respond = "|";
		
		try {
			st = conn.createStatement();
			query = "SELECT username, hoten, diachi, dob, gioitinh, email, isLocked "
					+ "FROM TaiKhoan "
					+ "WHERE username = '" + inputUser + "' OR hoten = '" + inputUser + "'";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String usernameCol = rs.getString("username");
				String nameCol = rs.getString("hoten");
				String addressCol = rs.getString("diachi");
				String dobCol = rs.getString("dob");
				String gender = rs.getString("gioitinh");
				String emailCol = rs.getString("email");
				String isLocked = rs.getString("isLocked");
				
				String genderCol = "";
				if (gender.equals("1")) 
					genderCol = "Nam";
				else 
					genderCol = "Nữ";
				
				String isLockedCol = "";
				if (isLocked.equals("1")) 
					isLockedCol = "Bị khóa";
				else 
					isLockedCol = "Hoạt động";

				
				respond = respond + usernameCol + "," + nameCol + "," + addressCol + "," + dobCol + "," + genderCol + "," + emailCol + "," + isLockedCol + "|";
				//System.out.println(sendermsgDB);
			}
			
			System.out.println(respond);
			server.sendMessageToAUser(threadSender, "(admin)_display_list_of_users" + respond);
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sortUserByFilter(ThreadServer threadSender, String filter) {
		Statement st;
		String query ="";
		String respond = "|";
		
		try {
			st = conn.createStatement();
			query = "SELECT username, hoten, diachi, dob, gioitinh, email, isLocked "
					+ "FROM TaiKhoan "
					+ "ORDER BY "+ filter + " ASC"; 
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String usernameCol = rs.getString("username");
				String nameCol = rs.getString("hoten");
				String addressCol = rs.getString("diachi");
				String dobCol = rs.getString("dob");
				String gender = rs.getString("gioitinh");
				String emailCol = rs.getString("email");
				String isLocked = rs.getString("isLocked");
				
				String genderCol = "";
				if (gender.equals("1")) 
					genderCol = "Nam";
				else 
					genderCol = "Nữ";
				
				String isLockedCol = "";
				if (isLocked.equals("1")) 
					isLockedCol = "Bị khóa";
				else 
					isLockedCol = "Hoạt động";

				
				respond = respond + usernameCol + "," + nameCol + "," + addressCol + "," + dobCol + "," + genderCol + "," + emailCol + "," + isLockedCol + "|";
				//System.out.println(sendermsgDB);
			}
			
			System.out.println(respond);
			server.sendMessageToAUser(threadSender, "(admin)_display_list_of_users" + respond);
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lockAndUnlockUser(ThreadServer threadSender, String username, String status) {
		Statement st;
		String query = "";
		String sts = "TRUE";
		
		if (status.equals("0")) {
			sts = "FALSE";
		}

		try {
			st = conn.createStatement();
			query = "UPDATE TaiKhoan "
					+ "SET isLocked = " + sts
					+ " WHERE username = '" + username + "'";
					
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		displayListOfUsers(threadSender);
	}
	
	public void displayListOfFriends(ThreadServer threadSender, String username) {
		Statement st;
		String query ="";
		String respond = "|";
		
		try {
			st = conn.createStatement();
			query = "SELECT username, hoten "
					+ "FROM TaiKhoan "
					+ "WHERE username in ("
						+ "SELECT BB.friend_username "
						+ "FROM TaiKhoan AS TK JOIN BanBe AS BB "
						+ "ON TK.username = BB.user_username "
						+ "WHERE TK.username = '" + username + "')";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String usernameCol = rs.getString("username");
				String nameCol = rs.getString("hoten");
				
				respond = respond + usernameCol + "," + nameCol + "|";
			}
			
			System.out.println(respond);
			server.sendMessageToAUser(threadSender, "(admin)_diplay_list_friends" + respond);
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------
	
	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}
}