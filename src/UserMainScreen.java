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
import java.io.OutputStream;
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
	private PrintWriter _pw;
	private ClientReaderThread _readThread;
	private ClientWriteThread _writeThread;

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
	public UserMainScreen(Socket socket, PrintWriter pw, String username) throws UnknownHostException, IOException {
		
		this._clientSocket = socket;
		this._username = username;
//		OutputStream output = socket.getOutputStream();
//		_pw = new PrintWriter(output, true);
		this._pw = pw;
		
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
		
		JTextArea stringSearchTextArea = new JTextArea();
		JPanel SettingPanel = new JPanel();
		JButton btnUnfriend = new JButton("Hủy kết bạn");
		JButton btnSearchHistory = new JButton("Tìm kiếm tin nhắn");
		JButton btnDeleteHistory = new JButton("Xóa lịch sử tin nhắn");
		JButton btnAddToGroup = new JButton("Thêm vào nhóm chat");
		JButton btnRemoveFromGroup = new JButton("Xóa khỏi nhóm chat");
		JButton btnChangeGroupName = new JButton("Đổi tên nhóm chat");
		JButton btnMakeAdmin = new JButton("Cấp quyền admin");
		JPanel panel_2_1 = new JPanel();
		JList<String> listFriend = new JList<>();
		JList<String> listGroup = new JList<>();
		JList<String> listFriendRequest = new JList<>();
		JPanel userInfoPane = new JPanel();
		JScrollPane scrollPaneFriend = new JScrollPane();
		JScrollPane scrollPaneGroup = new JScrollPane();
		JScrollPane scrollPaneFriendRequest = new JScrollPane();
		JLabel lblUser = new JLabel(_username);
		JLabel lblOnl = new JLabel("(Online)");
		JButton userInfoBtn = new JButton("Thông tin cá nhân");
		JButton btnSENDMSG = new JButton("GỬI");
		
		// Container stores message
		Map<String, String> friendChatMSG = new HashMap<String, String>();

		
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
		scrollPaneFriend.setViewportView(listFriend);

		
		// ScrollPane Group
		scrollPaneGroup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Nhóm", null, scrollPaneGroup, null);
		
		// List Group
		listGroup.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listGroup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPaneGroup.setViewportView(listGroup);

		// ScrollPane Friend Request
		scrollPaneFriendRequest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Lời mời kết bạn", null, scrollPaneFriendRequest, null);
		
		// List Group
		listFriendRequest.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listFriendRequest.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPaneFriendRequest.setViewportView(listFriendRequest);
		
		
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
		
		_readThread = new ClientReaderThread(socket, msg_area, listFriend, listFriendRequest, _username, stringSearchTextArea);
		_readThread.start();
		
		
		
		// Event Add Friend
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usernameFriend = JOptionPane.showInputDialog("Enter username to add");
				String friendRequest = "friend_request-".concat(_username);
				friendRequest = friendRequest.concat("-");
				friendRequest = friendRequest.concat(usernameFriend);
				
				_writeThread = new ClientWriteThread(_clientSocket, _pw, friendRequest);
				_writeThread.start();
			}
		});
		
		// Event Unfriend
		btnUnfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = listFriend.getSelectedValue().toString();
				String req = "remove_friend-".concat(_username);
				value = value.split(" ")[0];
				req = req.concat("-");
				req = req.concat(value);
				
				// req = remove_friend-username-friend
				_writeThread = new ClientWriteThread(_clientSocket, _pw, req);
				_writeThread.start();
			}
		});
		
		// Event Search History
		btnSearchHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Extract data and do a string search
				StringSearch searchNewStringForm = new StringSearch(socket, pw, username, stringSearchTextArea, listFriend.getSelectedValue());
				searchNewStringForm.setLocationRelativeTo(null);
				searchNewStringForm.setVisible(true);
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
		
		// Event Mouse Click
		listFriend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// On Mouse Click
				String value = listFriend.getSelectedValue().toString();
				value = value.split(" ")[0];
				
				btnUnfriend.setVisible(true);
				btnSearchHistory.setVisible(true);
				btnDeleteHistory.setVisible(true);
				btnAddToGroup.setVisible(false);
				btnRemoveFromGroup.setVisible(false);
				btnChangeGroupName.setVisible(false);
				btnMakeAdmin.setVisible(false);
				
				String message = "get_chat_history-" + _username + "-"+ value;
				System.out.println(message);
				_writeThread = new ClientWriteThread(_clientSocket, _pw, message ); 
				_writeThread.start();
				
				//msg_area.setText(friendChatMSG.get(value));

			}
		});
		
		listGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// On Mouse Click
				String value = listGroup.getSelectedValue().toString();

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
		
		listFriendRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// On Mouse Click
				String value = listFriendRequest.getSelectedValue().toString();
				String reply = "";
				
				Object[] options = {"Đồng ý", "Từ chối"};
				
				int click = JOptionPane.showOptionDialog(null, "Hello, Chào bạn", "Lời mời kết bạn", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (click==JOptionPane.YES_OPTION) {
                	reply = "reply_friend_request-".concat("YES-");
                	reply = (reply.concat(value)).concat("-");
                	reply = reply.concat(_username);
                	
                	_writeThread = new ClientWriteThread(_clientSocket, _pw, reply);
                }
                else if (click==JOptionPane.NO_OPTION) {
                	reply = "reply_friend_request-".concat("NO-");
                	reply = (reply.concat(value)).concat("-");
                	reply = reply.concat(_username);
                	
                	_writeThread = new ClientWriteThread(_clientSocket, _pw, reply);	
                }
                _writeThread.start();

				//msg_area.setText(friendChatMSG.get(value));

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
				String sendmsg = "";
				msgOut = msg_field.getText().trim();
				msg_field.setText("");
				btnSENDMSG.setEnabled(false);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy ");
				LocalDateTime now = LocalDateTime.now();

				String value = listFriend.getSelectedValue().toString();
				value = value.split(" ")[0];
				// area
//				msg_area.setText(msg_area.getText().trim() + "\n"+ _username + "(" + dtf.format(now) + "): " + msgOut);
				msg_area.append(username + "(" + dtf.format(now) + "): " + msgOut + "\n");
				friendChatMSG.put(value, msg_area.getText());
				sendmsg = "message-" + _username + "-" + value + "-" + _username + "(" + dtf.format(now) + "): " + msgOut;
				_pw.println(sendmsg);
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
					value = listGroup.getSelectedValue().toString();
					friendChatMSG.put(value, "");
					msg_area.setText(friendChatMSG.get(value));
				}

			}
		});
		
		//Create group
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGroup creategrp_form = new CreateGroup(_clientSocket, _pw, _username);
				creategrp_form.setLocationRelativeTo(null);
				creategrp_form.setVisible(true);
				
			}
		});

		// -----------------
		// UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins",
		// new Insets(10, 100, 0, 0));
	}
}
