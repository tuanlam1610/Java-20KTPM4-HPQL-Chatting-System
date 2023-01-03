import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ClientUpdateListGroupThread extends Thread {
	private JList<String> _listGroupUI;
	public Map<String, String> Nhom_Map = new HashMap<>();
	private String[] _listGroup;
	private String _username;
	
	public ClientUpdateListGroupThread(String[] listGroup, JList<String> listGroupUI, String username) {
		this._listGroupUI = listGroupUI;
		this._listGroup = listGroup;
		this._username = username;
	}
	 
	public void run() {
		DefaultListModel<String> lst = new DefaultListModel<String>();
		String indexGroup = "";
		if (_listGroupUI.getSelectedValue() != null) {
			indexGroup = _listGroupUI.getSelectedValue();
		}
		
		if (_listGroup != null) {
			for (String aGroup : _listGroup) {
				String[] Nhom_arr = aGroup.split("#");
				Nhom_Map.put(Nhom_arr[1], Nhom_arr[0]);
				lst.addElement(Nhom_arr[0]);
			}
		}
				
		_listGroupUI.setModel(lst);
		
		//Arrays.asList(lst.toArray());
		for (int i = 0; i < lst.getSize(); i++) {
			if (indexGroup.equals(lst.getElementAt(i))) {
				_listGroupUI.setSelectedIndex(i);
			}
		}
	}
}
