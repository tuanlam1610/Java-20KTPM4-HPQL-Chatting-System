
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerUpdateFriendRequestThread extends Thread {
	private Server _server;
	private ThreadServer _threadReceiver;
	private Connection _conn;
	private String _receiver;

	public ServerUpdateFriendRequestThread(Server server, ThreadServer threadReceiver, Connection conn, String receiver) {
		this._server = server;
		this._threadReceiver = threadReceiver;
		this._conn = conn;
		this._receiver = receiver;
	}

	// Còn chỉnh sửa những test case ngoại lệ

	public void run() {
		Statement st;
		String listReq = "friend_request-";

		try {
			st = _conn.createStatement();
			String query = "select sender_username from LoiMoiKetBan where receiver_username = '" + _receiver
					+ "'";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) { 
				listReq = listReq.concat(rs.getString(1).concat(","));
				System.out.println("--------- " + listReq);
			}
			
			_server.sendMessageToAUser(_threadReceiver, listReq);
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
}
