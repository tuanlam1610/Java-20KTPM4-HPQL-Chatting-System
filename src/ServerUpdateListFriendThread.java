import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerUpdateListFriendThread extends Thread {
	private Server _server;
	private Connection _conn;
	private String _username;
	//private List<String> _listFriend = new ArrayList<String>();
	private HashMap<String, ThreadServer> _listOnline = new HashMap<>();
	
	public ServerUpdateListFriendThread(Server server, Connection conn, String username) {
		this._server = server;
		this._conn = conn;
		this._username = username;
	}
	
	public void run() {
		Statement st;
		ResultSet rs;
		String listFriendOnlAndGroup = "";
		String query = "";
		String friend = "";
		String group = "";
		
		while (true) {
			_listOnline = _server.getUserThreads();
			listFriendOnlAndGroup = "update_friend_group_list-";
			query = "";
			
			try {
				st = _conn.createStatement();
				
				// List Of Friend -----------------------------------------------------------------------
				query = "SELECT friend_username FROM BanBe WHERE user_username = '" + _username + "'";
				rs = st.executeQuery(query);
				
				// Update online - offline and send to client
				while (rs.next()) {
					friend = rs.getString(1);
					if (_listOnline.containsKey(friend)) {
						listFriendOnlAndGroup += (friend.concat(" 1")).concat(",");
					} else {
						listFriendOnlAndGroup += (friend.concat(" 0")).concat(",");
					}
				}
				
				listFriendOnlAndGroup = listFriendOnlAndGroup.concat("-");
				
				// List Of Group -----------------------------------------------------------------------
				query = "SELECT N.tennhom FROM Nhom as N JOIN ThanhVienNhom as TV "
						+ "ON N.ID_nhom = TV.ID_nhom "
						+ "WHERE TV.username = '" + _username + "'";
				rs = st.executeQuery(query);
				
				while (rs.next()) {
					group = rs.getString(1).concat(",");
					listFriendOnlAndGroup = listFriendOnlAndGroup.concat(group);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
			
			// Send to client
			_server.sendMessageToAUser(_listOnline.get(_username), listFriendOnlAndGroup);
			
			// Sleep 2s 
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
