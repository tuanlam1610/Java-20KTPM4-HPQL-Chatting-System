import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ClientReaderThreadAdmin extends Thread {
	private ClientUpdateListFriendThread _updateListFriend;
	private ClientUpdateListGroupThread _updateListGroup;
	private ClientReceiveFriendRequestThread _friendRequest;
	private AdminDisplayListOfUsers _displayListOfUser;
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
	private JTable _loginHistoryTable;
	private JTable _userTable;
	
	public ClientReaderThreadAdmin(Socket socket, String username, JTable loginHTable, JTable userTable) {
		this._socket = socket;
		this._username = username;
		this._loginHistoryTable = loginHTable;
		this._userTable = userTable;
		
		try {
			InputStream input = this._socket.getInputStream();
			_reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error while getting inputstream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public ClientReaderThreadAdmin(Socket socket, JList<String> listFriend, String username) {
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
				System.out.println(_response);
				String[] message = _response.split("\\|");
				//System.out.println(message[0]);
				switch (message[0]) {
				//admin get login history case:
					case "get_login_history": {
						System.out.println("data received");
						ArrayList<String[]> tableData = new ArrayList<String[]>();
						for (int i = 1; i < message.length; i++) {
							String msg = message[i];
							String[] rowData = msg.split(",");
							tableData.add(rowData);
							
							System.out.println("row" + i);
						}
	
						String[] columnNames2 = { "Thời gian đăng nhập", "Username", "Họ tên" };
						String[][] tableDataArray = tableData.toArray(String[][]::new);
						_loginHistoryTable.setModel(new DefaultTableModel(tableDataArray, columnNames2));
						System.out.println("finish");
						break;
					}
					
					case "(admin)_display_list_of_users":
						if (message.length > 1) {
							String[] listOfUser = Arrays.copyOfRange(message, 1, message.length);
							_displayListOfUser = new AdminDisplayListOfUsers(listOfUser, _userTable);
						}
						else {
							DefaultTableModel model = (DefaultTableModel) _userTable.getModel();
							model.setRowCount(0);
						}
					
						break;
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
