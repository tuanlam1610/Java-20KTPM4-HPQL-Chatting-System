import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateGroup_Friendlist extends JFrame {

	private JPanel contentPane;
	private ClientReaderThread _readThread;
	
	private String SelectedFriend;
	
	DefaultListModel<String> added_list = new DefaultListModel<String>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					CreateGroup_Friendlist frame = new CreateGroup_Friendlist();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateGroup_Friendlist(Socket socket, PrintWriter pw, String username, JList<String> org_list) {
		setResizable(false);
		setTitle("Friendlist");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane_friendlist = new JScrollPane();
		scrollPane_friendlist.setBounds(10, 11, 238, 302);
		contentPane.add(scrollPane_friendlist);
		
		JList<String> list_friend = new JList<String>();
		list_friend.setFont(new Font("Tahoma", Font.PLAIN, 24));
		list_friend.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list_friend.setBackground(Color.WHITE);
		scrollPane_friendlist.setViewportView(list_friend);
		
		JButton btnNewButton = new JButton("Choose");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(55, 324, 150, 23);
		contentPane.add(btnNewButton);
		
		_readThread = new ClientReaderThread(socket, list_friend, username);
		_readThread.start();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list_friend.getSelectedIndex()!= -1)
				{
					String friend_to_add= (String) list_friend.getSelectedValue();
					setFriend(friend_to_add, org_list);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Please choose someone!",
				               "Warning", JOptionPane.WARNING_MESSAGE);
				}
	            
			}
		});
	}
	
	public String getSelectedValue(){
		return this.SelectedFriend;
	}
	
	public void setFriend(String Friend, JList<String> org_list) {
		added_list.addElement(Friend);
		org_list.setModel(added_list);
		
	}
}
