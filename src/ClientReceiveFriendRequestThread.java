import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ClientReceiveFriendRequestThread extends Thread {
	private JList<String> _listFriendRequest;
	private String[] _username;
	
	public ClientReceiveFriendRequestThread(JList<String> listFriendRequest, String[] username) {
		this._listFriendRequest = listFriendRequest;
		this._username = username;
	}
	 
	public void run() {
		DefaultListModel<String> lst = new DefaultListModel<String>();
		
		if (_username != null) {
			for (String aUser : _username) {
				lst.addElement(aUser);
			}
		}
		_listFriendRequest.setModel(lst);
	}
}