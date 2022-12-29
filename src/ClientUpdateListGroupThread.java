import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ClientUpdateListGroupThread extends Thread {
	private JList<String> _listGroupUI;
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
				lst.addElement(aGroup);
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
