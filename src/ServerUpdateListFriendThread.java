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
		String listOnl = "";
		String query = "";
		String friend = "";
		
		while (true) {
			_listOnline = _server.getUserThreads();
			listOnl = "update_online_list-";
			query = "";
			
			try {
				st = _conn.createStatement();
				query = "select friend_username from BanBe where user_username = '" + _username + "'";
				ResultSet rs = st.executeQuery(query);
				
				// Update online - offline and send to client
				while (rs.next()) {
					friend = rs.getString(1);
					if (_listOnline.containsKey(friend)) {
						listOnl += (friend.concat(" 1")).concat(",");
					} else {
						listOnl += (friend.concat(" 0")).concat(",");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
			
			// Send to client
			_server.sendMessageToAUser(_listOnline.get(_username), listOnl);
			
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
