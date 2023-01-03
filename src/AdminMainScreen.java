import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class AdminMainScreen extends JFrame {
	private JPanel contentPane;
	private JTable userTable;
	private JTextField textFieldSearch;
	private JTable loginTable;
	private JTable groupTable;
	private Socket _clientSocket;
	private PrintWriter _pw;
	ClientWriteThread _writeThread;
	ClientReaderThreadAdmin _readThread;
	private String _username;
	private JTable _tableListFriend;
	private JTable _loginUserTable;
	private AddScreen addFrame;
	
	public AdminMainScreen(Socket clientSocket, PrintWriter pw, String username) {
		this._clientSocket = clientSocket;
		this._pw = pw;
		this._username = username;
		this.addFrame = new AddScreen(clientSocket, pw);
		String[] columnB = { "Username", "Thời Gian Đăng Nhập" };
		String[][] dataB = { { "", "" }};
		_loginUserTable = new JTable(dataB, columnB);
		_loginUserTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		_loginUserTable.setEnabled(false);
		_loginUserTable.setDefaultEditor(Object.class, null);
		_loginUserTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Admin Main Screen");
		getContentPane().setBackground(new Color(255, 255, 255));
		setResizable(false);
		JPanel p2 = new JPanel();
		p2.setBackground(new Color(255, 255, 255));
		JPanel p3 = new JPanel();
		p3.setBackground(new Color(255, 255, 255));
		JTabbedPane tp = new JTabbedPane();
		tp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tp.setBounds(0, 0, 1066, 683);
		
		// Table List Of Friend
		String[] columnTableListFriend = { "Username", "Họ tên" };
		String[][] dataListFriend = { { "", "" }

		};
		_tableListFriend = new JTable(dataListFriend, columnTableListFriend);
		_tableListFriend.setFont(new Font("Tahoma", Font.PLAIN, 16));
		_tableListFriend.setEnabled(false);
		_tableListFriend.setDefaultEditor(Object.class, null);
		_tableListFriend.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		String[][] data = {
				{ "", "", "", "", "", "", "" }

		};
		// Column Names
		String[] columnNames = { "Username", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Trạng thái"};
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(255, 255, 255));
		tp.add("Danh sách người dùng", p1);
		p1.setLayout(null);
		// Initializing the JTable
		userTable = new JTable(data, columnNames);
		DefaultTableCellRenderer userTableRenderer = (DefaultTableCellRenderer) userTable.getDefaultRenderer(String.class);
		userTableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		userTable.setRowHeight(24);
		userTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		btnAdd.setBounds(203, 35, 150, 35);
		p1.add(btnAdd);

		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearch.setBounds(377, 35, 100, 35);
		p1.add(btnSearch);

		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(478, 35, 150, 35);
		p1.add(textFieldSearch);
		textFieldSearch.setColumns(10);

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
		
		JRadioButton rdbtnHoTen = new JRadioButton("Họ tên");
		rdbtnHoTen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnHoTen.setSelected(true);
		rdbtnHoTen.setBounds(650, 76, 72, 21);
		p1.add(rdbtnHoTen);
		
		JRadioButton rdbtnNgayTao = new JRadioButton("Ngày tạo");
		rdbtnNgayTao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnNgayTao.setBounds(737, 76, 83, 21);
		p1.add(rdbtnNgayTao);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(rdbtnHoTen);
		bg.add(rdbtnNgayTao);

		tp.add("Danh sách đăng nhập", p2);
		p2.setLayout(null);
		String[][] data2 = { { "2022-12-03 12:03:30", "lam123", "Tuấn Lâm" },
				{ "2022-12-02 16:14:30", "quang123", "Ngọc Quang" }, { "2022-12-02 10:30:30", "huy123", "Gia Huy" }, };
		String[] columnNames2 = { "Thời gian đăng nhập", "Username", "Họ tên" };
		loginTable = new JTable(data2, columnNames2);
		DefaultTableCellRenderer loginTableRenderer = (DefaultTableCellRenderer) loginTable
				.getDefaultRenderer(String.class);
		loginTableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		loginTable.setRowHeight(24);
		loginTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginTable.setEnabled(false);
		loginTable.setDefaultEditor(Object.class, null);
		loginTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		JScrollPane sp2 = new JScrollPane();

		sp2.setLocation(0, 0);
		sp2.setSize(1061, 649);
		sp2.setViewportView(loginTable);
		loginTable.setBounds(0, 0, 880, 400);
		p2.add(sp2);
		tp.add("Danh sách nhóm chat", p3);
		p3.setLayout(null);
		JScrollPane sp3 = new JScrollPane();
		sp3.setBounds(0, 52, 1061, 597);
		p3.add(sp3);
		groupTable = new JTable();
		DefaultTableCellRenderer groupTableRenderer = (DefaultTableCellRenderer) groupTable.getDefaultRenderer(String.class);
		groupTableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		groupTable.setRowHeight(24);
		groupTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		groupTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		groupTable.setDefaultEditor(Object.class, null);
		sp3.setViewportView(groupTable);
		JButton btnGroupSort = new JButton("Sắp Xếp");
		btnGroupSort.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnGroupSort.setBounds(10, 10, 98, 32);
		p3.add(btnGroupSort);
		JRadioButton sortNameOpt = new JRadioButton("Tên nhóm");
		sortNameOpt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sortNameOpt.setBounds(114, 15, 84, 24);
		sortNameOpt.setSelected(true);
		p3.add(sortNameOpt);
		JRadioButton sortDateOpt = new JRadioButton("Ngày tạo");
		sortDateOpt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sortDateOpt.setBounds(200, 15, 73, 24);
		p3.add(sortDateOpt);
		ButtonGroup groupChatSortBG = new ButtonGroup();
		groupChatSortBG.add(sortNameOpt);
		groupChatSortBG.add(sortDateOpt);
		getContentPane().add(tp);
		setSize(1080, 720);
		getContentPane().setLayout(null);
		setVisible(true);

		// ----------------------------------------------------------- EVENT
		// -------------------------------------------------------------
		_readThread = new ClientReaderThreadAdmin(clientSocket, _username, loginTable, userTable, groupTable, _tableListFriend, _loginUserTable, addFrame);
		_readThread.start();
		String request = "(admin)_display_list_of_users-" + _username + "-";
		_pw.println(request);
		// Tabbed Pane Change State Listener
		tp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int currentTab = tp.getSelectedIndex();
				switch (currentTab) {
				case 0: {
					System.out.println("Current tab is 'Danh sách người dùng'");
					String request = "(admin)_display_list_of_users-" + _username + "-";
					_pw.println(request);
					break;
				}
				case 1: {
					System.out.println("Current tab is 'Lịch sử đăng nhập'");
					String message = "get_login_history-" + _username;
					_pw.println(message);
					break;
				}
				case 2: {
					System.out.println("Current tab is 'Danh sách nhóm chat'");
					String sortValue = "";
					if (sortNameOpt.isSelected())
						sortValue = "tennhom";
					else
						sortValue = "ngaytaonhom";
					_pw.println("admin_updateGroup-" + _username + "-" + sortValue);
					break;
				}
				}
			}
		});
		// p2 Listener
		p2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String message = "get_login_history-" + _username;
				System.out.println(message);
				_writeThread = new ClientWriteThread(_clientSocket, _pw, message);
				_writeThread.start();
			}
		});
		userTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] data = new String[7];
				int row = userTable.getSelectedRow();
				for (int i = 0; i < 7; i++)
					data[i] = userTable.getModel().getValueAt(row, i).toString();
				InteractAccount new_frame = new InteractAccount(clientSocket, pw, _tableListFriend, _username, data[0], data[1], data[2], data[3], data[4]
																, data[5], data[6], _loginUserTable);
				new_frame.setVisible(true);
			}
		});
		// Button Add
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddScreen addFrame = new AddScreen(clientSocket, pw);
				addFrame.setVisible(true);
			}
		});
		// Button Refresh
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String request = "(admin)_display_list_of_users-" + _username + "-";
				textFieldSearch.setText("");
				_writeThread = new ClientWriteThread(_clientSocket, _pw, request);
				_writeThread.start();
			}
		});
		// Button Search
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputUser = textFieldSearch.getText().trim();

				if (!inputUser.equals("")) {
					String request = "(admin)_search_user-" + _username + "-" + inputUser + "-";
					System.out.println(request);

					_writeThread = new ClientWriteThread(_clientSocket, _pw, request);
					_writeThread.start();
				}
			}
		});
		// Button Sort
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filter = "";
				if (rdbtnHoTen.isSelected()) {
					filter = "hoten";
				} else {
					filter = "ngaytao";
				}
				String request = "(admin)_sort_user-" + _username + "-" + filter + "-";

				_writeThread = new ClientWriteThread(_clientSocket, _pw, request);
				_writeThread.start();
			}
		});
		
		//Sort Group Chat
		btnGroupSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sortValue = "";
				if (sortNameOpt.isSelected())
					sortValue = "tennhom";
				else
					sortValue = "ngaytaonhom";
				_pw.println("admin_updateGroup-" + _username + "-" + sortValue);
			}
		});
		
		// Pop Up Member List
		groupTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String selectedGroupName = groupTable.getValueAt(groupTable.getSelectedRow(), 0).toString();
				_pw.println("admin_getGroupMemberList-" + _username + "-" + selectedGroupName);
				_pw.println("admin_getGroupAdminList-" + _username + "-" + selectedGroupName);
				System.out.println("Selected Row: " + groupTable.getSelectedRow());
				System.out.println("Selected Group: " + selectedGroupName);
			}
		});
	}
}
