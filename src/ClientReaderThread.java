import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JList;
import javax.swing.JTextArea;

public class ClientReaderThread extends Thread {
	private ClientUpdateListFriendThread _updateThread;
	private ClientReceiveFriendRequestThread _friendRequest;
	private BufferedReader _reader;
	private Socket _socket;
	private JTextArea _textArea;
	private JList<String> _listFriend;
	private JList<String> _listFriendRequest;
	private String _username;
	private String _response;
	
	public ClientReaderThread(Socket socket, JTextArea textArea, JList<String> listFriend, JList<String> listFriendRequest, String username) {
		this._socket = socket;
		this._textArea = textArea;
		this._listFriend = listFriend;
		this._listFriendRequest = listFriendRequest;
		this._username = username;
		
		try {
			InputStream input = this._socket.getInputStream();
			_reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error while getting inputstream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				_response = _reader.readLine();
				String[] message = _response.split("-");
				switch (message[0]) {
				case "update_online_list": {
					_updateThread = new ThreadUpdateListFriend(_socket, message[1].split(","), _jList, _username);
					_updateThread.start();
					break;
				}
				case "friend_request": {
					String[] lstReq = null;
					if (message.length > 1) {
						lstReq = Arrays.copyOfRange(message[1].split(","), 0, message[1].split(",").length); 
					}
					_friendRequest = new ClientReceiveFriendRequestThread(_listFriendRequest, lstReq);
					_friendRequest.start();
					break;
				}
				case "message": {
					String senderName = message[1];
					String receiverName = message[2];
					String msg = message[3];
					System.out.println(senderName + " " + receiverName + " " + msg);
					String selectedName = "";
					if(!_jList.getSelectedValue().equals(null)) {
						selectedName = _jList.getSelectedValue().toString();
						selectedName = selectedName.split(" ")[0];
					}
					if(selectedName.equals(senderName)) {
						_textArea.append(msg + "\n");
					}
					break;
				}
				}
//				if (message[0].equals("update_online_list") && message.length > 1) {
//					_updateThread = new ThreadUpdateListFriend(_socket, message[1].split(","), _jList, _username);
//					_updateThread.start();
//				}
//				else
//					_textArea.append("\n" + _response);

			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}

}
