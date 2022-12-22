import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ThreadUpdateListFriend extends Thread {
	private Socket _socket;
	private JList<String> _listFriend;
	private String[] _listOnline;
	private String _username;
	
	public ThreadUpdateListFriend(Socket socket, String[] listOnline, JList<String> listFriend, String username) {
		this._socket = socket;
		this._listFriend = listFriend;
		this._listOnline = listOnline;
		this._username = username;
	}
	 
	public void run() {
		DefaultListModel<String> lst = new DefaultListModel<String>();
		String friend = "";
		
		for (String status : _listOnline) {
			friend = status.substring(0, status.length() - 2);
			System.out.println(friend);
			if (!friend.equals(_username)) {
				if (status.endsWith("1")) {
					friend = friend.concat(" (Online)");
					lst.addElement(friend);
				} else {
					lst.addElement(friend);
				}
			}
		}
		_listFriend.setModel(lst);
	}
}
