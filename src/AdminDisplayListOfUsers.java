import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminDisplayListOfUsers {
	private String[] _listOfUsers;
	private JTable _userTable;
	
	public AdminDisplayListOfUsers(String[] listOfUsers, JTable userTable) {
		this._listOfUsers = listOfUsers;
		this._userTable = userTable;
		
		ArrayList<String[]> tableData = new ArrayList<String[]>();
		
		for (int i = 0; i < _listOfUsers.length; i++) {
			String[] rowData = _listOfUsers[i].split(",");
			tableData.add(rowData);
		}

		String[] columnName = { "Username", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email" };
		String[][] tableDataArray = tableData.toArray(String[][]::new);
		_userTable.setModel(new DefaultTableModel(tableDataArray, columnName));
		System.out.println("finish");
	}
}
