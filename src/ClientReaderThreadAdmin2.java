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

public class ClientReaderThreadAdmin2 extends Thread {
	private ClientUpdateListFriendThread _updateListFriend;
	private ClientUpdateListGroupThread _updateListGroup;
	private ClientReceiveFriendRequestThread _friendRequest;
	private AdminDisplayListOfUsers _displayListOfUser;
	private AdminDisplayListOfFriendUser _displayListOfFriendUser;
	private BufferedReader _reader;
	private Socket _socket;
	private String _username;
	private String _response;
	private JTable _loginHistoryTable;
	private JTable _userTable;
	private JTable _groupChatTable;
	private JTable _listFriendTable;
	
	public ClientReaderThreadAdmin2(Socket socket, String username, JTable listFriendTable) {
		this._socket = socket;
		this._username = username;
		this._listFriendTable = listFriendTable;
		
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
				System.out.println("AAAAAA");
				_response = _reader.readLine();
				System.out.println(_response + "ABC");
				String[] message = _response.split("\\|");
				//System.out.println(message[0]);
				switch (message[0]) {
					case "(admin)_diplay_list_friends": {
						if (message.length > 1) {
							String[] listOfFriendUser = Arrays.copyOfRange(message, 1, message.length);
//							_displayListOfFriendUser = new AdminDisplayListOfFriendUser(listOfFriendUser, _listFriendTable);
							ArrayList<String[]> tableData = new ArrayList<String[]>();
							
							for (int i = 0; i < listOfFriendUser.length; i++) {
								String[] rowData = listOfFriendUser[i].split(",");
								tableData.add(rowData);
								System.out.println("CCCCCCCCCCC");
							}
							System.out.println("AAAAAAAAAAAA");
							String[] columnName = { "Username", "Họ tên"};
							String[][] tableDataArray = tableData.toArray(String[][]::new);
							System.out.println("AAAAAAAAAABB");
							_listFriendTable.setModel(new DefaultTableModel(tableDataArray, columnName));
						}
						
						
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
