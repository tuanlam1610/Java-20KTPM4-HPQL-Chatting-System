import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class InteractAccountLoginHistory extends JFrame {
	private static final long serialVersionUID = 1L;
	private ClientWriteThread  _writeThread;
	private ClientReaderThreadAdmin _readThread;
	private JPanel contentPane;
	private JTable table;
	private JTable _loginUserTable;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public InteractAccountLoginHistory(Socket clientSocket, PrintWriter pw,String admin, String username, JTable LoginUserTable) {
		this._loginUserTable = LoginUserTable;
		setResizable(false);
		setTitle("Interact account");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(20, 25, 84, 15);
		contentPane.add(lblUsername);

		JLabel lblUsername_1 = new JLabel("Username");
		lblUsername_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername_1.setBounds(114, 25, 200, 15);
		contentPane.add(lblUsername_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 75, 405, 266);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(_loginUserTable);

		JLabel lblDanhSchBn = new JLabel("Lịch sử đăng nhập");
		lblDanhSchBn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDanhSchBn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchBn.setBounds(143, 50, 150, 15);
		contentPane.add(lblDanhSchBn);
		lblUsername_1.setText(username);
		
		// ------------------------------------ EVENT --------------------------------------
		String message = "get_specific_login_history-" + admin + "-" +username;
		System.out.println(message);
		_writeThread = new ClientWriteThread(clientSocket, pw, message);
		_writeThread.start();
	}
}
