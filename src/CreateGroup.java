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
import javax.swing.DefaultListModel;

public class CreateGroup extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	private String _username;
	private Socket _clientSocket;
	private PrintWriter _pw;
	private ClientReaderThread _readThread;
	private ClientWriteThread _writeThread;
	
	JLabel lblNewLabel = new JLabel("Create Group");
	JScrollPane scrollPane_friendlist = new JScrollPane();
	JList<String> list_friend = new JList<>();
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
		setBounds(100, 100, 550, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblNewLabel.setBounds(167, 11, 202, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		
		scrollPane_friendlist.setBounds(288, 68, 238, 302);
		contentPane.add(scrollPane_friendlist);
		
		
		scrollPane_friendlist.setViewportView(list_friend);
		list_friend.setBackground(UIManager.getColor("CheckBox.highlight"));
		list_friend.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list_friend.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		
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
		
		//Show friendlist
		
		
		
		JButton btnCreateGroup = new JButton("Create");
		btnCreateGroup.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCreateGroup.setBounds(167, 508, 202, 44);
		contentPane.add(btnCreateGroup);
		
		JButton btnAddMember = new JButton("Add member");
		
		btnAddMember.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddMember.setBounds(288, 381, 238, 44);
		contentPane.add(btnAddMember);
		
		textField = new JTextField();
		textField.setBounds(10, 94, 200, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Group name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 68, 200, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Group name:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(10, 130, 200, 20);
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 156, 200, 25);
		contentPane.add(textField_1);
		
		_readThread = new ClientReaderThread(socket, list_friend,_username);
		_readThread.start();
		
		//list.setBounds(425, 84, 1, 1);
		//contentPane.add(list);
		
		
		//--------------------EVENT--------------------
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGroup_Friendlist friendlist_form = new CreateGroup_Friendlist(socket, pw,  username, list_friend);
				friendlist_form.setVisible(true);
			
			}
		});
		
	}
	
	
}
