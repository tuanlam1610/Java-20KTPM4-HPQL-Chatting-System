import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ClientReaderThread extends Thread {
	private ClientUpdateListFriendThread _updateListFriend;
	private ClientUpdateListGroupThread _updateListGroup;
	private ClientReceiveFriendRequestThread _friendRequest;
	private BufferedReader _reader;
	private Socket _socket;
	private JTextArea _textArea;
	private JTabbedPane _tabbedPane;
	private JList<String> _listFriend;
	private JList<String> _listGroup;
	private JList<String> _listFriendRequest;
	private String _username;
	private String _response;
	private JTextArea _stringTextArea;

	public ClientReaderThread(Socket socket, JTextArea textArea, JList<String> listFriend, JList<String> listGroup,
			JList<String> listFriendRequest, String username, JTextArea stringSearchTextArea, JTabbedPane tabbedPane) {
		this._socket = socket;
		this._textArea = textArea;
		this._listFriend = listFriend;
		this._listGroup = listGroup;
		this._listFriendRequest = listFriendRequest;
		this._username = username;
		this._stringTextArea = stringSearchTextArea;
		this._tabbedPane = tabbedPane;
		
		try {
			InputStream input = this._socket.getInputStream();
			_reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error while getting inputstream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public ClientReaderThread(Socket socket, JList<String> listFriend, String username) {
		this._socket = socket;
//		this._textArea = textArea;
		this._listFriend = listFriend;
//		this._listFriendRequest = listFriendRequest;
		this._username = username;

		try {
			InputStream input = this._socket.getInputStream();
			_reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error while getting inputstream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				_response = _reader.readLine();
//				System.out.println(_response);
				String[] message = _response.split("-");
				switch (message[0]) {
				case "update_friend_group_list": {
					String[] lstFriend = null;
					String[] lstGroup = null;
					
					if (message.length > 1 && message[1].split(",").length > 0) {
						lstFriend = Arrays.copyOfRange(message[1].split(","), 0, message[1].split(",").length);
					}
					
					if (message.length > 2 && message[2].split(",").length > 0) {
						lstGroup = Arrays.copyOfRange(message[2].split(","), 0, message[2].split(",").length);
					}
					_updateListFriend = new ClientUpdateListFriendThread(lstFriend, _listFriend, _username);
					_updateListGroup = new ClientUpdateListGroupThread(lstGroup, _listGroup, _username);
					_updateListFriend.start();
					_updateListGroup.start();
					
					break;
				}
				case "friend_request": {
					String[] lstReq = null;
					if (message.length > 1) {
						lstReq = Arrays.copyOfRange(message[1].split(","), 0, message[1].split(",").length); 
					}
					_friendRequest = new ClientReceiveFriendRequestThread(_listFriendRequest, lstReq);
					_friendRequest.start();
					break;
				}
				case "message": {
					String msgType = message[1];
					String senderName = message[2];
					String receiverName = message[3];
					String msg = message[4];
					if (!msgType.equals("user") && !msgType.equals("group")) {
						System.out.println("Can't read message type (Must be 'user' or 'group')");
						break;
					}
					System.out.println(msg + " " + senderName + " " + receiverName + " " + msg);
					String currentTab = this._tabbedPane.getTitleAt(this._tabbedPane.getSelectedIndex()).toString();
					String selectedName = "";
					if(msgType.equals("user") && currentTab.equals("Bạn bè")) {
						if(!(_listFriend.getSelectedValue() == null)) {
							selectedName = _listFriend.getSelectedValue().toString();
							selectedName = selectedName.split(" ")[0];
						}
						if(selectedName.equals(senderName)) {
							_textArea.append(msg + "\n");
						}
					}
					else if(msgType.equals("group") && currentTab.equals("Nhóm")) {
						if(!(_listGroup.getSelectedValue() == null) && _listGroup.getSelectedValue().toString().equals(receiverName)) {
							_textArea.append(msg + "\n");
						}
					}
					break;
				}
				case "get_chat_history": {
					String msg = message[2];
					String str = "";
					while((str = _reader.readLine())!= null) {
						if(!str.equals("EndOfString"))
							msg = msg + "\n" + str;
						else 
							break;
					}
					if(message.length > 2) {
					String senderName = message[1];
					//String receiverName = message[2];
					//System.out.println(msg);
					String selectedName = "";
					if(!(_listFriend.getSelectedValue() == null)) {
						selectedName = _listFriend.getSelectedValue().toString();
						selectedName = selectedName.split(" ")[0];
					}
					if(selectedName.equals(senderName)) {
						_textArea.setText(msg);
					}
					}
					break;
				}
				case "string_search": {
					String targetString = message[1];
					String msg = message[2];
					int once = 0;
					if(!msg.contains(targetString)) {
						msg = "";
						once = 1;
					}
					
					String str = "";
					while((str = _reader.readLine())!= null) {
						if(!str.equals("EndOfString")) {
							if(str.contains(targetString)) {
								if(once == 1) {
									msg = str;
									once = 0;
								}
								else {
								 msg = msg + "\n" + str;
								}
							}
						}
							
						else 
							break;
					}
					//String senderName = message[1];
					//String receiverName = message[2];
					//System.out.println(m
					
						_stringTextArea.setText(msg);
					break;
				}
				//admin get login history case:
				case "get_login_history": {
					ArrayList<String[]> tableData = new ArrayList<String[]>();
					for (int i = 1; i < message.length; i++) {
						String msg = message[i];
						String[] rowData = msg.split(",");
						tableData.add(rowData);
					}
					String[] columnNames2 = { "Thời gian đăng nhập", "Username", "Họ tên" };
					//loginTable.setModel(new DefaultTableModel(tableData, columnNames2));
					String msg = message[2];
					String str = "";
					while((str = _reader.readLine())!= null) {
						if(!str.equals("EndOfString"))
							msg = msg + "\n" + str;
						else 
							break;
					}
					if(message.length > 2) {
					String senderName = message[1];
					//String receiverName = message[2];
					//System.out.println(msg);
					String selectedName = "";
					if(!(_listFriend.getSelectedValue() == null)) {
						selectedName = _listFriend.getSelectedValue().toString();
						selectedName = selectedName.split(" ")[0];
					}
					if(selectedName.equals(senderName)) {
						_textArea.setText(msg);
					}
					}
					break;
				}
				}
//				if (message[0].equals("update_online_list") && message.length > 1) {
//					_updateThread = new ThreadUpdateListFriend(_socket, message[1].split(","), _jList, _username);
//					_updateThread.start();
//				}
//				else
//					_textArea.append("\n" + _response);

			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}

}
