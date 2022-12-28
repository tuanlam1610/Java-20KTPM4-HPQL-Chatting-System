import java.awt.EventQueue;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class CreateGroup extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private String _username;
	private Socket _clientSocket;
	private PrintWriter _pw;
	private ClientReaderThread _readThread;
	private ClientWriteThread _writeThread;
	
	JLabel lblNewLabel = new JLabel("Create Group");
	JScrollPane scrollPane_friendlist = new JScrollPane();
	JList<String> list_member = new JList<>();
	JButton btnKickMember = new JButton("Kick");
	JButton btnCreateGroup = new JButton("Create");
	private final JScrollPane scrollPane_friendlist_1 = new JScrollPane();
	private final JList<String> list_friend = new JList<String>();
	private final JButton btnAddMember = new JButton("Add");
	private final JLabel lblNewLabel_1_2 = new JLabel("Member:");
	private final JLabel lblNewLabel_1_2_1 = new JLabel("Friend List:");
	private final JLabel lblNewLabel_1_1 = new JLabel("*The person who created the group will automatically be the admin");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					CreateGroup frame = new CreateGroup(args[0],args[1],args[2]);
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
	public CreateGroup(Socket socket, PrintWriter pw, String username) {
		
		this._clientSocket = socket;
		this._username = username;
		this._pw = pw;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblNewLabel.setBounds(178, 11, 202, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		
		scrollPane_friendlist.setBounds(20, 161, 238, 302);
		contentPane.add(scrollPane_friendlist);
		
		
		scrollPane_friendlist.setViewportView(list_member);
		list_member.setBackground(UIManager.getColor("CheckBox.highlight"));
		list_member.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list_member.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		
		list_member.setSelectionModel(new DefaultListSelectionModel() {
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(super.isSelectedIndex(index0)) {
		            super.removeSelectionInterval(index0, index1);
		        }
		        else {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
		
		list_member.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		btnCreateGroup.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCreateGroup.setBounds(178, 568, 202, 44);
		contentPane.add(btnCreateGroup);
		
		btnKickMember.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnKickMember.setBounds(20, 474, 238, 44);
		contentPane.add(btnKickMember);
		
		textField = new JTextField();
		textField.setBounds(20, 85, 200, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Group name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(20, 59, 200, 20);
		contentPane.add(lblNewLabel_1);
		
		scrollPane_friendlist_1.setBounds(300, 161, 238, 302);
		
		contentPane.add(scrollPane_friendlist_1);
		list_friend.setFont(new Font("Tahoma", Font.PLAIN, 24));
		list_friend.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list_friend.setBackground(UIManager.getColor("CheckBox.highlight"));
		
		scrollPane_friendlist_1.setViewportView(list_friend);
		
		list_friend.setSelectionModel(new DefaultListSelectionModel() {
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(super.isSelectedIndex(index0)) {
		            super.removeSelectionInterval(index0, index1);
		        }
		        else {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
		
		list_friend.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		btnAddMember.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddMember.setBounds(300, 474, 238, 44);
		
		contentPane.add(btnAddMember);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(20, 130, 200, 20);
		
		contentPane.add(lblNewLabel_1_2);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_2_1.setBounds(300, 130, 200, 20);
		
		contentPane.add(lblNewLabel_1_2_1);
		
		lblNewLabel_1_1.setForeground(new Color(128, 128, 128));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_1.setBounds(20, 529, 518, 20);
		
		contentPane.add(lblNewLabel_1_1);
		
//		_readThread = new ClientReaderThread(socket, list_friend,_username);
//		_readThread.start();
		
		//list.setBounds(425, 84, 1, 1);
		//contentPane.add(list);
		
		
		//--------------------EVENT--------------------
		_readThread = new ClientReaderThread(socket, list_friend, username);
		_readThread.start();
			
		DefaultListModel model_member = new DefaultListModel<>();
//		DefaultListModel model_friend = new DefaultListModel<>();
			
//		for(int i = 0;i < list_friend.getModel().getSize();i++) {
//			if(!model_friend.contains(list_friend.getModel().getElementAt(i)))
//			model_friend.addElement(list_friend.getModel().getElementAt(i));
//		}
		
//		list_friend.setModel(model_friend);
		
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list_friend.getSelectedIndex();
				String selected_value = list_friend.getSelectedValue();
				if (selectedIndex != -1) {
					if(!model_member.contains(selected_value)) {
						model_member.addElement(selected_value);
					    list_member.setModel(model_member);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Already added!",
					               "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Please choose someone!",
				               "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});	
		
		btnKickMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel temp_model = (DefaultListModel) list_member.getModel();
				int selectedIndex = list_member.getSelectedIndex();
				if (selectedIndex != -1) {
				    temp_model.remove(selectedIndex);    
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Please choose someone!",
				               "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		
	}
	
	
}
