import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class UserMainScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField msg_field;
	
	private String _username;
	private Socket _clientSocket;
	private PrintWriter _writer;
	private ClientReaderThread _readThread;
	
	
	

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { UserMainScreen frame = new
	 * UserMainScreen(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public UserMainScreen(Socket socket, String username) throws UnknownHostException, IOException {
		
		this._clientSocket = socket;
		this._username = username;
		
		setResizable(false);
		setTitle("User Main Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JDesktopPane desktopPane = new JDesktopPane();
		JPanel userBtnPane = new JPanel();
		JButton btnCreateGroup = new JButton("Tạo Nhóm");
		JButton btnAddFriend = new JButton("Kết Bạn");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JLabel lab = new JLabel();
		// --------------
		// tabbedPane.setTabComponentAt(0, lab);
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		JTextArea msg_area = new JTextArea();
		JPanel SettingPanel = new JPanel();
		JButton btnUnfriend = new JButton("Hủy kết bạn");
		JButton btnSearchHistory = new JButton("Tìm kiếm tin nhắn");
		JButton btnDeleteHistory = new JButton("Xóa lịch sử tin nhắn");
		JButton btnAddToGroup = new JButton("Thêm vào nhóm chat");
		JButton btnRemoveFromGroup = new JButton("Xóa khỏi nhóm chat");
		JButton btnChangeGroupName = new JButton("Đổi tên nhóm chat");
		JButton btnMakeAdmin = new JButton("Cấp quyền admin");
		JPanel panel_2_1 = new JPanel();
		JScrollPane scrollPaneFriend = new JScrollPane();
		JList<String> listFriend = new JList<>();
		JPanel userInfoPane = new JPanel();
		JScrollPane scrollPaneGroup = new JScrollPane();
		JLabel lblUser = new JLabel(_username);
		JLabel lblOnl = new JLabel("(Online)");
		JButton userInfoBtn = new JButton("Thông tin cá nhân");
		JButton btnSENDMSG = new JButton("GỬI");

		
		// Desktop Pane
		desktopPane.setBackground(new Color(255, 255, 255));
		desktopPane.setBounds(0, 0, 350, 690);
		contentPane.add(desktopPane);

		// Panel contains btnCreateGroup and btnAddFriend
		userBtnPane.setBackground(new Color(255, 255, 255));
		userBtnPane.setBorder(null);
		userBtnPane.setBounds(0, 83, 350, 56);
		desktopPane.add(userBtnPane);

		// Button Create Group
		btnCreateGroup.setBounds(17, 10, 150, 36);
		btnCreateGroup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userBtnPane.setLayout(null);
		userBtnPane.add(btnCreateGroup);

		// Button Add Friend
		btnAddFriend.setBounds(183, 10, 150, 36);
		btnAddFriend.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userBtnPane.add(btnAddFriend);

		// TabbedPane
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tabbedPane.setBounds(0, 150, 350, 530);
		desktopPane.add(tabbedPane);
		
		// Panel
		panel.setBackground(new Color(102, 153, 255));
		panel.setBounds(350, 0, 720, 690);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// Msg Field
		msg_field = new JTextField();
		msg_field.setFont(new Font("SansSerif", Font.PLAIN, 16));
		msg_field.setBounds(10, 584, 558, 77);
		msg_field.setColumns(10);
		panel.add(msg_field);
		
		//////////////////////////////////////////////////////////////////////////////////////
		Map<String, String> friendChatMSG = new HashMap<String, String>();
		// tam thoi luu cho ca friend va nhom chat
		// đang input chat dựa trên tên của chat, cần phải sửa ngay lập tức
		/*
		 * friendChatMSG.put("Huy (Online)", " User1: Hello");
		 * friendChatMSG.put("Phú (Offline)", " User2: Hello");
		 * friendChatMSG.put("Java", " User1: Hello \n User2: how are you");
		 * friendChatMSG.put("Web",
		 * " User1: Hello \n User3: Web is really hard to work on");
		 */
		///////////////////////////////////////////////////////////////////////////////////////
		
		
		// ScorllPane and Text Area
		scrollPane.setBounds(10, 83, 689, 491);
		panel.add(scrollPane);

		scrollPane.setViewportView(msg_area);
		msg_area.setEditable(false);
		msg_area.setFont(new Font("SansSerif", Font.PLAIN, 16));

		// Setting Panel
		SettingPanel.setBackground(new Color(255, 255, 255));
		SettingPanel.setBounds(10, 0, 689, 73);
		panel.add(SettingPanel);

		// Button Unfriend
		btnUnfriend.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnUnfriend);

		// Button Search History
		btnSearchHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnSearchHistory);

		// Button Delete History
		btnDeleteHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnDeleteHistory);

		// Button Add To Group
		btnAddToGroup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnAddToGroup);

		// Button Remove User From Group
		btnRemoveFromGroup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnRemoveFromGroup);

		// Button Change Group Name
		btnChangeGroupName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnChangeGroupName);

		// Button Make Admin
		btnMakeAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnMakeAdmin);

		btnUnfriend.setVisible(false);
		btnSearchHistory.setVisible(false);
		btnDeleteHistory.setVisible(false);
		btnAddToGroup.setVisible(false);
		btnRemoveFromGroup.setVisible(false);
		btnChangeGroupName.setVisible(false);
		btnMakeAdmin.setVisible(false);

		// Load msg_area
		msg_area.setText("");

		// ScrollPane Chat Field
		scrollPane.setColumnHeaderView(panel_2_1);
		panel_2_1.setLayout(new BoxLayout(panel_2_1, BoxLayout.X_AXIS));

		// ScrollPane List Of Friend
		scrollPaneFriend.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Bạn bè", null, scrollPaneFriend, null);

		// List Friend
		listFriend.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listFriend.setFont(new Font("Tahoma", Font.PLAIN, 16));
		/*
		 * listFriend.setModel(new AbstractListModel() { String[] values = new String[]
		 * {"Huy (Online)", "Phú (Offline)"}; public int getSize() { return
		 * values.length; } public Object getElementAt(int index) { return
		 * values[index]; } });
		 */
		scrollPaneFriend.setViewportView(listFriend);

		
		// ScrollPane Group
		scrollPaneGroup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Nhóm", null, scrollPaneGroup, null);

		/*
		 * Map<String, Entry<String, Boolean>> userChatList = new HashMap<String,
		 * Entry<String, Boolean>>(); userChatList.put("Huy", new SimpleEntry("U001",
		 * true)); userChatList.put("Phu", new SimpleEntry("U002", true));
		 * 
		 * Map<String, Entry<String, Boolean>> groupChatList = new HashMap<String,
		 * Entry<String, Boolean>>(); groupChatList.put("Java", new SimpleEntry("G001",
		 * true)); groupChatList.put("Web", new SimpleEntry("G002", true));
		 * 
		 * //JList listNhom = new JList(groupChatList.keySet().toArray());
		 */
		
		// List Group
		JList listNhom = new JList();
		listNhom.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listNhom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		/*
		 * listNhom.setModel(new AbstractListModel() { String[] values = new String[] {
		 * "Java", "Web" };
		 * 
		 * public int getSize() { return values.length; }
		 * 
		 * public Object getElementAt(int index) { return values[index]; } });
		 */
		scrollPaneGroup.setViewportView(listNhom);
		
		// UserInfoPane
		userInfoPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		userInfoPane.setBackground(new Color(255, 255, 255));
		userInfoPane.setBounds(0, 0, 350, 72);
		desktopPane.add(userInfoPane);
		userInfoPane.setLayout(null);
		
		// Label
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUser.setBounds(10, 10, 175, 24);
		userInfoPane.add(lblUser);
		
		
		lblOnl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOnl.setBounds(10, 42, 60, 16);
		userInfoPane.add(lblOnl);
		
		
		JButton btnDangXuat = new JButton("Đăng xuất");
		btnDangXuat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDangXuat.setBounds(243, 34, 97, 32);
		userInfoPane.add(btnDangXuat);
		 
		
		// Button View Personal Information
		userInfoBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userInfoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		userInfoBtn.setBounds(99, 34, 134, 32);
		userInfoPane.add(userInfoBtn);

		// Button Send Message
		btnSENDMSG.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSENDMSG.setBounds(578, 595, 121, 52);
		btnSENDMSG.setEnabled(false);
		panel.add(btnSENDMSG);
		
		
		// ----------------------------------------------------------- EVENT -------------------------------------------------------------
		
		_readThread = new ClientReaderThread(socket, msg_area, listFriend, _username);
		_readThread.start();
		
		// Event Add Friend
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to add");
			}
		});
		
		// Event Unfriend
		btnUnfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remove friend from database
			}
		});
		
		// Event Search History
		btnSearchHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Extract data and do a string search
			}
		});
		
		// Event Add To Group
		btnAddToGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to add");
			}
		});
		
		// Event Remove User From Group
		btnRemoveFromGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to remove");
			}
		});
		
		// Event Change Group Name
		btnChangeGroupName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter new name for group");
			}
		});
		
		// Event Make Admin
		btnMakeAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to make admin");
			}
		});
		
		// Event Logout
		btnDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen loginScreen = new MainScreen();
				loginScreen.setLocationRelativeTo(null);
				loginScreen.setVisible(true);
				dispose();
			}
		});
		
		// Event Display Button
		listFriend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// On Mouse Click
				String value = listFriend.getSelectedValue().toString();

				btnUnfriend.setVisible(true);
				btnSearchHistory.setVisible(true);
				btnDeleteHistory.setVisible(true);
				btnAddToGroup.setVisible(false);
				btnRemoveFromGroup.setVisible(false);
				btnChangeGroupName.setVisible(false);
				btnMakeAdmin.setVisible(false);

				msg_area.setText(friendChatMSG.get(value));

			}
		});
		
		listNhom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// On Mouse Click
				String value = listNhom.getSelectedValue().toString();

				btnUnfriend.setVisible(false);
				btnSearchHistory.setVisible(true);
				btnDeleteHistory.setVisible(true);
				btnAddToGroup.setVisible(true);
				btnRemoveFromGroup.setVisible(true);
				btnChangeGroupName.setVisible(true);
				btnMakeAdmin.setVisible(true);

				msg_area.setText(friendChatMSG.get(value));

			}

		});
		
		// Event Input Field != "", Button "Send": Active
		msg_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!msg_field.getText().equals("")) {
					btnSENDMSG.setEnabled(true);
				}
				else {
					btnSENDMSG.setEnabled(false);
				}
			}
		});
		
		// SEND MESSAGE ON CLICK BUTTON
		btnSENDMSG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgOut = "";
				msgOut = msg_field.getText().trim();
				msg_field.setText("");
				btnSENDMSG.setEnabled(false);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy ");
				LocalDateTime now = LocalDateTime.now();

				String value = listFriend.getSelectedValue().toString();

				// area
				msg_area.setText(msg_area.getText().trim() + "\n"+ _username + "(" + dtf.format(now) + "): " + msgOut);
				friendChatMSG.put(value, msg_area.getText());

				// msg_area.setText(msg_area.getText().trim()+ "\n Server: \t" + msgOut);
			}
		});

		btnDeleteHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = "";
				if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Ban be")) {
					value = listFriend.getSelectedValue().toString();
					friendChatMSG.put(value, "");
					msg_area.setText(friendChatMSG.get(value));
				} else if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Nhom")) {
					value = listNhom.getSelectedValue().toString();
					friendChatMSG.put(value, "");
					msg_area.setText(friendChatMSG.get(value));
				}

			}
		});

		// -----------------
		// UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins",
		// new Insets(10, 100, 0, 0));
	}
}