import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminDisplayListOfFriendUser {
	private String[] _listOfUsers;
	private JTable _tableListFriend;
	
	public AdminDisplayListOfFriendUser(String[] listOfUsers, JTable tableListFriend) {
		this._listOfUsers = listOfUsers;
		this._tableListFriend = tableListFriend;
		
		ArrayList<String[]> tableData = new ArrayList<String[]>();
		
		for (int i = 0; i < _listOfUsers.length; i++) {
			String[] rowData = _listOfUsers[i].split(",");
			tableData.add(rowData);
		}

		String[] columnName = { "Username", "Họ tên"};
		String[][] tableDataArray = tableData.toArray(String[][]::new);
		_tableListFriend.setModel(new DefaultTableModel(tableDataArray, columnName));
		System.out.println("finish");
	}
}
