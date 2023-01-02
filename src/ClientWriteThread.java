import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWriteThread extends Thread{
	private PrintWriter _pw;
	private Socket _socket;
	private String _message;

	public ClientWriteThread(Socket socket, PrintWriter pw, String message) {
		this._socket = socket;
		this._message = message;
		this._pw = pw;
	}

	public void run() {	
		_pw.println(_message);
		_pw.flush();
		
		/*
		 * if (_message.equals("bye")) { try { _pw.flush(); _pw.close();
		 * _socket.close(); } catch (IOException ex) {
		 * System.out.println("Error writing to server: " + ex.getMessage()); } }
		 */
	}
}
