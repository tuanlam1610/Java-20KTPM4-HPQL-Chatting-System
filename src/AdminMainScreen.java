import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Font;

public class AdminMainScreen extends JFrame {
	private JPanel contentPane;
	private JTable userTable;
	private JTextField textField;
	private JTable loginTable;
	private JTable groupTable;
	private Socket _clientSocket;
	private PrintWriter _pw;
	ClientWriteThread  _writeThread;
	ClientReaderThreadAdmin _readThread;
	private String _username;
	
	public AdminMainScreen(Socket clientSocket, PrintWriter pw, String username) {
		this._clientSocket = clientSocket;
		this._pw = pw;
		this._username = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Admin Main Screen");
		getContentPane().setBackground(new Color(255, 255, 255));
		setResizable(false);
		JPanel p2 = new JPanel();
		p2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String message = "get_login_history-" + _username;
				System.out.println(message);
				_writeThread = new ClientWriteThread(_clientSocket, _pw, message);
				_writeThread.start();
			}
		});
		p2.setBackground(new Color(255, 255, 255));
		JPanel p3 = new JPanel();
		p3.setBackground(new Color(255, 255, 255));
		JTabbedPane tp = new JTabbedPane();
		tp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tp.setBounds(0, 0, 1066, 683);
		String[][] data = {
				{ "", "", "", "", "", "" }

		};
		// Column Names
		String[] columnNames = { "Username", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email" };
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(255, 255, 255));
		tp.add("Danh sách người dùng", p1);
		p1.setLayout(null);
		// Initializing the JTable
		userTable = new JTable(data, columnNames);
		userTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] data = new String[6];
				int row = userTable.getSelectedRow();
				for (int i = 0; i < 6; i++)
					data[i] = userTable.getModel().getValueAt(row, i).toString();
				InteractAccount new_frame = new InteractAccount(clientSocket, pw, data[0], data[1], data[2], data[3], data[4], data[5]);
				new_frame.setVisible(true);
			}
		});
		userTable.setColumnSelectionAllowed(false);
		userTable.setCellSelectionEnabled(false);
		userTable.setDefaultEditor(Object.class, null);
		userTable.setRowSelectionAllowed(true);
		userTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		JScrollPane sp = new JScrollPane();
		sp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sp.setLocation(0, 127);
		sp.setSize(1061, 529);
		sp.setViewportView(userTable);
		userTable.setBounds(0, 0, 880, 400);
		p1.add(sp);

		JButton btnAdd = new JButton("Thêm tài khoản");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddScreen addFrame = new AddScreen(clientSocket, pw);
				addFrame.setVisible(true);
			}
		});
		btnAdd.setBounds(203, 35, 150, 35);
		p1.add(btnAdd);

		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearch.setBounds(377, 35, 100, 35);
		p1.add(btnSearch);

		textField = new JTextField();
		textField.setBounds(478, 35, 150, 35);
		p1.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Chọn tài khoản để tương tác");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(337, 104, 259, 13);
		p1.add(lblNewLabel);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRefresh.setBounds(26, 35, 150, 35);
		p1.add(btnRefresh);
		
		JButton btnSort = new JButton("Sắp xếp");
		btnSort.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSort.setBounds(650, 35, 150, 35);
		p1.add(btnSort);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(650, 76, 103, 21);
		p1.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
		rdbtnNewRadioButton_1.setBounds(763, 76, 103, 21);
		p1.add(rdbtnNewRadioButton_1);

		tp.add("Danh sách đăng nhập", p2);
		p2.setLayout(null);
		// Initializing the JTable
		
		//Get Data from server
		
		
		String[][] data2 = { { "2022-12-03 12:03:30", "lam123", "Tuấn Lâm" },
				{ "2022-12-02 16:14:30", "quang123", "Ngọc Quang" }, { "2022-12-02 10:30:30", "huy123", "Gia Huy" }, };
		String[] columnNames2 = { "Thời gian đăng nhập", "Username", "Họ tên" };
		
		
		loginTable = new JTable(data2, columnNames2);
		loginTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginTable.setEnabled(false);
		
		loginTable.setDefaultEditor(Object.class, null);
		loginTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		JScrollPane sp2 = new JScrollPane();
		sp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String message = "get_login_history-" + _username;
				System.out.println(message);
				_writeThread = new ClientWriteThread(_clientSocket, _pw, message);
				_writeThread.start();
			}
		});
		sp2.setLocation(0, 0);
		sp2.setSize(1061, 649);
		sp2.setViewportView(loginTable);
		loginTable.setBounds(0, 0, 880, 400);
		p2.add(sp2);
		tp.add("Danh sách nhóm chat", p3);
		p3.setLayout(null);

		JScrollPane sp3 = new JScrollPane();
		sp3.setBounds(0, 46, 1061, 603);
		p3.add(sp3);
		String[][] data3 = { { "Project Java", "2022-11-02", "lam123" }, { "Project Web", "2022-11-01", "quang123" },
				{ "Project SE", "2022-11-01", "huy123" }, };
		String[] columnNames3 = { "Tên nhóm", "Thời gian tạo", "Admin" };
		groupTable = new JTable(data3, columnNames3);
		groupTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		groupTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		groupTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GroupDetails newFrame = new GroupDetails("Project Java");
				newFrame.setVisible(true);
			}
		});
		groupTable.setDefaultEditor(Object.class, null);
		sp3.setViewportView(groupTable);

		JButton btnSortByName = new JButton("Sắp xếp theo tên");
		btnSortByName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSortByName.setBounds(204, 15, 200, 21);
		p3.add(btnSortByName);

		JButton btnSortByDate = new JButton("Sắp xếp theo thời gian tạo");
		btnSortByDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSortByDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSortByDate.setBounds(608, 15, 247, 21);
		p3.add(btnSortByDate);
		getContentPane().add(tp);
		setSize(1080, 720);
		getContentPane().setLayout(null);
		setVisible(true);
		
		// ----------------------------------------------------------- EVENT
		// -------------------------------------------------------------

		_readThread = new ClientReaderThreadAdmin(clientSocket, _username, loginTable, userTable);
		_readThread.start();
		
		// Button Refresh
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String request = "(admin)_display_list_of_users-" + _username + "-";
				
				_writeThread = new ClientWriteThread(_clientSocket, _pw, request);
				_writeThread.start();
			}
		});
	}
}
