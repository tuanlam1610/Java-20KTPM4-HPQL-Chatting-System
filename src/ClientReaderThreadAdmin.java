import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ClientReaderThreadAdmin extends Thread {
	private ClientUpdateListFriendThread _updateListFriend;
	private ClientUpdateListGroupThread _updateListGroup;
	private ClientReceiveFriendRequestThread _friendRequest;
	private AdminDisplayListOfUsers _displayListOfUser;
	private BufferedReader _reader;
	private Socket _socket;
	private String _username;
	private String _response;
	private JTable _loginHistoryTable;
	private JTable _userTable;
	private JButton _btnBlock;
	private JButton _btnUnBlock;
	private JTable _groupChatTable;
	
	public ClientReaderThreadAdmin(Socket socket, String username, JTable loginHTable, JTable userTable, JTable groupTable) {
		this._socket = socket;
		this._username = username;
		this._loginHistoryTable = loginHTable;
		this._userTable = userTable;
		this._groupChatTable = groupTable;
		
		try {
			InputStream input = this._socket.getInputStream();
			_reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error while getting inputstream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public ClientReaderThreadAdmin(Socket socket, JButton btnBlock, JButton btnUnBlock, String username) {
		this._socket = socket;
		this._btnBlock = btnBlock;
		this._username = username;
		this._btnUnBlock = btnUnBlock;
		
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
					
					case "(admin)_display_list_of_users":{
						if (message.length > 1) {
							String[] listOfUser = Arrays.copyOfRange(message, 1, message.length);
							_displayListOfUser = new AdminDisplayListOfUsers(listOfUser, _userTable);
						}
						else {
							DefaultTableModel model = (DefaultTableModel) _userTable.getModel();
							model.setRowCount(0);
						}
					
						break;
				case "admin_updateGroup": {
					String msg = message[1];
					ArrayList<String[]> tableData = new ArrayList<String[]>();
					String[] rows = msg.split("_");
					for(int i = 0; i < rows.length; i++) {
						 String[] cols = rows[i].split(",");
						 tableData.add(cols);
						 System.out.println("Row " + i + ": " + cols[0] + cols[1]);
					}
					String[] columnNames3 = { "Tên nhóm", "Thời gian tạo"};
					String[][] tableValue = tableData.toArray(String[][]::new);
					_groupChatTable.setModel(new DefaultTableModel(tableValue, columnNames3));
					break;
				}
				}
			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}

}
