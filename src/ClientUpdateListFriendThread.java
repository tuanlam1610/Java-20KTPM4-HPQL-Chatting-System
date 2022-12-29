import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ClientUpdateListFriendThread extends Thread {
	private JList<String> _listFriend;
	private String[] _listOnline;
	private String _username;
	
	public ClientUpdateListFriendThread(String[] listOnline, JList<String> listFriend, String username) {
		this._listFriend = listFriend;
		this._listOnline = listOnline;
		this._username = username;
	}
	 
	public void run() {
		DefaultListModel<String> lst = new DefaultListModel<String>();
		String indexFriend = "";
		if (_listFriend.getSelectedValue() != null) {
			indexFriend = _listFriend.getSelectedValue();
		}
		
		String friend = "";
		
		if (_listOnline != null) {
			for (String status : _listOnline) {
				friend = status.substring(0, status.length() - 2);
//				System.out.println(friend);
				if (!friend.equals(_username)) {
					if (status.endsWith("1")) {
						friend = friend.concat(" (Online)");
						lst.addElement(friend);
					} else {
						lst.addElement(friend);
					}
				}
			}
		}
		System.out.println("A_A_A_A_A");	
		_listFriend.setModel(lst);
		
		//Arrays.asList(lst.toArray());
		for (int i = 0; i < lst.getSize(); i++) {
			if (indexFriend.equals(lst.getElementAt(i))) {
				_listFriend.setSelectedIndex(i);
			}
		}
	}
}
