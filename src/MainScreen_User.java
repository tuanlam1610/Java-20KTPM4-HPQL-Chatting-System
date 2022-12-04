import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.time.LocalDateTime;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class MainScreen_User extends JFrame {
	private JPanel contentPane;
	private JTextField msg_field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen_User frame = new MainScreen_User();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainScreen_User() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 350, 690);
		contentPane.add(desktopPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(0, 0, 350, 80);
		desktopPane.add(panel_1);
		
		JButton btnCreateGroup = new JButton("Create Group");
		btnCreateGroup.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//link with Create Group
			}
		});
		panel_1.add(btnCreateGroup);
		
		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to add");
			}
		});
		panel_1.add(btnAddFriend);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tabbedPane.setBounds(0, 77, 350, 603);
		desktopPane.add(tabbedPane);
		
		JLabel lab = new JLabel();
		
		//--------------
		//tabbedPane.setTabComponentAt(0, lab);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 128));
		panel.setBounds(350, 0, 720, 690);
		contentPane.add(panel);
		panel.setLayout(null);
		
		msg_field = new JTextField();
		msg_field.setFont(new Font("SansSerif", Font.PLAIN, 16));
		msg_field.setBounds(10, 591, 590, 79);
		panel.add(msg_field);
		msg_field.setColumns(10);
		
		Map<String, String> friendChatMSG = new HashMap<String, String>();
		//tam thoi luu cho ca friend va nhom chat
		//đang input chat dựa trên tên của chat, cần phải sửa ngay lập tức
		friendChatMSG.put("Huy (Online)", " User1: Hello");
		friendChatMSG.put("Phú (Offline)", " User2: Hello");
		friendChatMSG.put("Java", " User1: Hello \n User2: how are you");
		friendChatMSG.put("Web", " User1: Hello \n User3: Web is really hard to work on");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 689, 491);
		panel.add(scrollPane);
		
		JTextArea msg_area = new JTextArea();
		scrollPane.setViewportView(msg_area);
		msg_area.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		JPanel SettingPanel = new JPanel();
		SettingPanel.setBounds(10, 0, 689, 73);
		panel.add(SettingPanel);
		
		JButton btnUnfriend = new JButton("Unfriend");
		btnUnfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//remove friend from database
			}
		});
		btnUnfriend.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnUnfriend);
		
		JButton btnSearchHistory = new JButton("Search convo");
		btnSearchHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Extract data and do a string search
			}
		});
		btnSearchHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnSearchHistory);
		
		JButton btnDeleteHistory = new JButton("Delete history");
		
		btnDeleteHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnDeleteHistory);
		
		JButton btnShowHistory = new JButton("Show History");
		btnShowHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnShowHistory);
		
		JButton btnAddToGroup = new JButton("Add to Group");
		btnAddToGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to add");
			}
		});
		btnAddToGroup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnAddToGroup);
		
		JButton btnRemoveFromGroup = new JButton("Remove from Group");
		btnRemoveFromGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to remove");
			}
		});
		btnRemoveFromGroup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnRemoveFromGroup);
		
		JButton btnChangeGroupName = new JButton("Change Group Name");
		btnChangeGroupName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter new name for group");
			}
		});
		btnChangeGroupName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnChangeGroupName);
		
		JButton btnMakeAdmin = new JButton("Make Admin");
		btnMakeAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Enter username to make admin");
			}
		});
		btnMakeAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SettingPanel.add(btnMakeAdmin);
		
		btnUnfriend.setVisible(false);
		btnSearchHistory.setVisible(false);
		btnDeleteHistory.setVisible(false);
		btnShowHistory.setVisible(false);
		btnAddToGroup.setVisible(false);
		btnRemoveFromGroup.setVisible(false);
		btnChangeGroupName.setVisible(false);
		btnMakeAdmin.setVisible(false);
	
	
		//Load msg_area
		msg_area.setText("");
		
		JPanel panel_2_1 = new JPanel();
		scrollPane.setColumnHeaderView(panel_2_1);
		panel_2_1.setLayout(new BoxLayout(panel_2_1, BoxLayout.X_AXIS));
		
		
		
		JScrollPane scrollPaneBanBe = new JScrollPane();
		scrollPaneBanBe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Ban be", null, scrollPaneBanBe, null);
		
		

		
		
		
		
		JList listBanBe = new JList();
		listBanBe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//On Mouse Click
				String value = listBanBe.getSelectedValue().toString();
				
				btnUnfriend.setVisible(true);
				btnSearchHistory.setVisible(true);
				btnDeleteHistory.setVisible(true);
				btnShowHistory.setVisible(true);
				btnAddToGroup.setVisible(false);
				btnRemoveFromGroup.setVisible(false);
				btnChangeGroupName.setVisible(false);
				btnMakeAdmin.setVisible(false);
				
				msg_area.setText(friendChatMSG.get(value));		
				
				
			}
		});
		listBanBe.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listBanBe.setFont(new Font("Tahoma", Font.PLAIN, 24));
		listBanBe.setModel(new AbstractListModel() {
			String[] values = new String[] {"Huy (Online)", "Phú (Offline)"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneBanBe.setViewportView(listBanBe);
		
		JScrollPane scrollPaneNhom = new JScrollPane();
		
		scrollPaneNhom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Nhom", null, scrollPaneNhom, null);
		
		/*Map<String, Entry<String, Boolean>> userChatList = new HashMap<String, Entry<String, Boolean>>();
		userChatList.put("Huy", new SimpleEntry("U001", true));
		userChatList.put("Phu", new SimpleEntry("U002", true));
		
		Map<String, Entry<String, Boolean>> groupChatList = new HashMap<String, Entry<String, Boolean>>();
		groupChatList.put("Java", new SimpleEntry("G001", true));
		groupChatList.put("Web", new SimpleEntry("G002", true));
		
		//JList listNhom = new JList(groupChatList.keySet().toArray());*/
		
		JList listNhom = new JList();
		listNhom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//On Mouse Click
				String value = listNhom.getSelectedValue().toString();
			    
				btnUnfriend.setVisible(false);
				btnSearchHistory.setVisible(true);
				btnDeleteHistory.setVisible(true);
				btnShowHistory.setVisible(true);
				btnAddToGroup.setVisible(true);
				btnRemoveFromGroup.setVisible(true);
				btnChangeGroupName.setVisible(true);
				btnMakeAdmin.setVisible(true);
			
			
				
				msg_area.setText(friendChatMSG.get(value));	
			
			}
			
		});
		listNhom.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listNhom.setFont(new Font("Tahoma", Font.PLAIN, 24));
		listNhom.setModel(new AbstractListModel() {
			String[] values = new String[] {"Java", "Web"};
			
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneNhom.setViewportView(listNhom);
		
		
		
		JButton btnSENDMSG = new JButton("SEND");
		//SEND MESSAGE ON CLICK BUTTON
		btnSENDMSG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgOut = "";
				msgOut = msg_field.getText().trim();
				msg_field.setText("");
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy ");
				LocalDateTime now = LocalDateTime.now();
				
				String value = listBanBe.getSelectedValue().toString();
				
				//area
				msg_area.setText(msg_area.getText().trim()+ "\n User1 (" + dtf.format(now) + "): " + msgOut);
				friendChatMSG.put(value, msg_area.getText());
				
				
				//msg_area.setText(msg_area.getText().trim()+ "\n Server: \t" + msgOut);
			}
		});
		btnSENDMSG.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSENDMSG.setBounds(614, 591, 85, 76);
		panel.add(btnSENDMSG);
		
		
		btnDeleteHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = "";
				if(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Ban be")) {
					value = listBanBe.getSelectedValue().toString();
					friendChatMSG.put(value, "");
					msg_area.setText(friendChatMSG.get(value));	
				}
				else if(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Nhom")) {
					value = listNhom.getSelectedValue().toString();
					friendChatMSG.put(value, "");
					msg_area.setText(friendChatMSG.get(value));	
				}
				
				
			}
		});
		
		// -----------------
		//UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(10, 100, 0, 0));
	}
}
