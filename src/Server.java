
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	private int port;
	//private Set<String> userNames = new HashSet<>();
	private HashMap<ThreadServer, String> userThreads = new HashMap<ThreadServer, String>();

	public Server(int port) {
		this.port = port;
	}

	public HashMap<ThreadServer, String> getUserThreads() {
		return userThreads;
	}

	public void setUserThreads(HashMap<ThreadServer, String> userThreads) {
		this.userThreads = userThreads;
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");

				ThreadServer newUser = new ThreadServer(socket, this);
				newUser.start();
			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(1234);
		server.execute();
	}
}