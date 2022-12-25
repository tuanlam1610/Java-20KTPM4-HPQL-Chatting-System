import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JList;
import javax.swing.JTextArea;

public class ClientReaderThread extends Thread {
	private ThreadUpdateListFriend _updateThread;
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
				System.out.println(_response);
				String[] message = _response.split("-");
				System.out.println(message);
				
				if (message[0].equals("update_online_list") && message.length > 1) {
					_updateThread = new ThreadUpdateListFriend(message[1].split(","), _listFriend, _username);
					_updateThread.start();
				} 
				else if (message[0].equals("friend_request") && message.length > 1) {
					System.out.println(message[1]);
					_friendRequest = new ClientReceiveFriendRequestThread(_listFriendRequest, message[1].split(","));
					_friendRequest.start();
				}
				else
					_textArea.append("\n" + _response);

			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}

}
