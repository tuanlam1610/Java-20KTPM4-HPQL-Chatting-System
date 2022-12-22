
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

public class ThreadServer extends Thread {
	private Socket socket;
	private Server server;
	private PrintWriter writer;
	private String _username;

	public ThreadServer(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			_username = reader.readLine();
			
			// Add vao HashMap
			server.addUserName(this, _username);
		
			// Update Online List
			updateOnlineList(server.getUserThreads().values(), this);
			//------------------//
			
			String serverMessage = _username + " has joined";
			server.broadcast(serverMessage, this);

			String clientMessage;

			do {
				clientMessage = reader.readLine();
				serverMessage = _username + ": " + clientMessage;
				server.broadcast(serverMessage, this);

			} while (!clientMessage.equals("bye"));

			server.removeUser(_username, this);
			updateOnlineList(server.getUserThreads().values(), this);
			socket.close();

			serverMessage = _username + " has left";
			server.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in ThreadServer: " + ex.getMessage());
			server.removeUser(_username, this);
			updateOnlineList(server.getUserThreads().values(), this);
			//socket.close();
			ex.printStackTrace();
		}
	}
	
	// Update Online List
	
	public void updateOnlineList(Collection<String> listUser, ThreadServer threadServer) {
		String listOfUserName = "";
		
		for (String username : listUser) {
			listOfUserName += username.concat("-");
		}
		listOfUserName = "update_online_list," + listOfUserName;
		server.broadcast(listOfUserName, threadServer);
	}
	
	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}
}