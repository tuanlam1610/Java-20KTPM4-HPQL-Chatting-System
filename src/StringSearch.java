import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StringSearch extends JFrame {

	private JPanel contentPane;
	private JTextField searchTextField;
	private JTextField targetTextField;
	private JTextArea textArea;
	private String _searchUser;
	
	private String _username;
	private Socket _clientSocket;
	private PrintWriter _pw;
	private ClientReaderThread _readThread;
	private ClientWriteThread _writeThread;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//SearchString frame = new SearchString();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public StringSearch(Socket socket, PrintWriter pw, String username, JTextArea textArea, String searchUser) {
		this._clientSocket = socket;
		this._username = username;
		this._pw = pw;
		this.textArea = textArea;
		this._searchUser = searchUser.split(" ")[0];
		
		textArea.setText("");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Search String");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(650, 35, 134, 33);
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String stringMsg = "";
				String targetMsg = "";
				stringMsg = searchTextField.getText().trim();
				//searchTextField.setText("");
				targetMsg = targetTextField.getText().trim();
				//targetTextField.setText("");
				
				if(targetMsg.equals(""))
				{		//do nothing
				}	
				else {
				
				String sendMsg = "";
				sendMsg = "string_search-" + _username + "-" + targetMsg + "-" + stringMsg;
				_pw.println(sendMsg);
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 90, 750, 250);
		contentPane.add(scrollPane);
		
		//JTextArea textArea = new JTextArea();
		textArea.setBounds(31, 90, 750, 250);
		scrollPane.setViewportView(textArea);
		
		JLabel lblNewLabel = new JLabel("Input String:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setBounds(31, 0, 156, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("from Username:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 350, 198, 25);
		contentPane.add(lblNewLabel_1);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!searchTextField.getText().equals("")) {
					btnNewButton.setEnabled(true);
				}
				else {
					btnNewButton.setEnabled(false);
				}
			}
		});
		searchTextField.setBounds(31, 35, 609, 33);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		targetTextField = new JTextField(_searchUser);
		targetTextField.setColumns(10);
		targetTextField.setBounds(31, 385, 609, 33);
		
		contentPane.add(targetTextField);
		
	}
}
