import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientReaderThread extends Thread {
	private BufferedReader reader;
	private Socket socket;
	
	ClientReaderThread(Socket socket) {
		this.socket = socket;
		try {
			InputStream input = this.socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error while getting inputstream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			
		}
	}

}
