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
		for (String aUser : _listOnline) {
			if (!aUser.equals(_username)) {
				lst.addElement(aUser);
			}
		}
		_listFriend.setModel(lst);
	}
}
