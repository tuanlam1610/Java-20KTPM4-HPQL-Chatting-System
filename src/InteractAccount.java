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

public class InteractAccount extends JFrame {
	private static final long serialVersionUID = 1L;
	private ClientWriteThread  _writeThread;
	private ClientReaderThreadAdmin _readThread;
	private JPanel contentPane;
	private JTextField textFieldFullname;
	private JTextField textFieldAddress;
	private JTextField textFieldDOB;
	private JTextField textFieldGender;
	private JTable table;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public InteractAccount(Socket clientSocket, PrintWriter pw, String admin, String username, String Fullname, String Address,
			String DOB, String Gender, String Email, String status) {
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

		JLabel lblFullname = new JLabel("Họ tên:");
		lblFullname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFullname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFullname.setBounds(41, 49, 63, 15);
		contentPane.add(lblFullname);

		textFieldFullname = new JTextField();
		textFieldFullname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldFullname.setBounds(114, 47, 200, 20);
		contentPane.add(textFieldFullname);
		textFieldFullname.setColumns(10);

		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress.setBounds(41, 76, 63, 15);
		contentPane.add(lblAddress);

		textFieldAddress = new JTextField();
		textFieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(114, 74, 200, 20);
		contentPane.add(textFieldAddress);

		JLabel lblDob = new JLabel("Ngày sinh:");
		lblDob.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDob.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDob.setBounds(20, 103, 84, 15);
		contentPane.add(lblDob);

		textFieldDOB = new JTextField();
		textFieldDOB.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDOB.setColumns(10);
		textFieldDOB.setBounds(114, 101, 200, 20);
		contentPane.add(textFieldDOB);

		JLabel lblGender = new JLabel("Giới tính:");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGender.setBounds(20, 130, 84, 15);
		contentPane.add(lblGender);

		textFieldGender = new JTextField();
		textFieldGender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldGender.setColumns(10);
		textFieldGender.setBounds(114, 128, 200, 20);
		contentPane.add(textFieldGender);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(41, 157, 63, 15);
		contentPane.add(lblEmail);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 203, 405, 138);
		contentPane.add(scrollPane);
		String[] columnNames = { "Username", "Họ tên" };
		String[][] data = { { "quang123", "Ngọc Quang" }

		};
		table = new JTable(data, columnNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setEnabled(false);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(table);

		JLabel lblDanhSchBn = new JLabel("Danh sách bạn bè");
		lblDanhSchBn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDanhSchBn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchBn.setBounds(143, 185, 150, 15);
		contentPane.add(lblDanhSchBn);

		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Update succesfully!");
				dispose();
			}
		});
		btnUpdate.setBounds(45, 351, 100, 21);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("Xóa");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa tài khoản này chứ?",
						"Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					pw.println("xoa_tai_khoan-" + lblUsername_1.getText());
					JOptionPane.showMessageDialog(null, "Delete succesfully!");
					dispose();
				} else {
					return;
				}

			}
		});
		btnDelete.setBounds(45, 382, 100, 21);
		contentPane.add(btnDelete);

		JButton btnBlock = new JButton("Chặn");
		btnBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBlock.setBounds(240, 382, 100, 21);
		contentPane.add(btnBlock);
		
		
		JButton btnUnBlock = new JButton("Bỏ chặn");
		btnUnBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUnBlock.setBounds(240, 382, 100, 21);
		contentPane.add(btnUnBlock);

		JButton btnResetPassword = new JButton("Khởi tạo lại mật khẩu");
		btnResetPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Reset password succesfully!\nNew password was send to email for user.");
				dispose();
			}
		});
		btnResetPassword.setBounds(190, 351, 200, 21);
		contentPane.add(btnResetPassword);
		textFieldFullname.setText(Fullname);
		textFieldAddress.setText(Address);
		textFieldDOB.setText(DOB);
		textFieldGender.setText(Gender);
		lblUsername_1.setText(username);

		JLabel lblUsername_1_1 = new JLabel(Email);
		lblUsername_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername_1_1.setBounds(114, 158, 200, 15);
		contentPane.add(lblUsername_1_1);
		
		// ------------------------------------ EVENT --------------------------------------
		
		// Update The status of btn Block
		if (status.equals("Bị khóa")) {
			btnBlock.setVisible(false);
			btnUnBlock.setVisible(true);
		}
		else {
			btnBlock.setVisible(true);
			btnUnBlock.setVisible(false);
		}
		
		// ---------------------------------------------------------------------------------
		
		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String request = "(admin)_lock_user-" + username + "-1-";
				
				_writeThread = new ClientWriteThread(clientSocket, pw, request);
				_writeThread.start();
				
				JOptionPane.showMessageDialog(null, "Lock succesfully!");
				dispose();
			}
		});
		
		btnUnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String request = "(admin)_lock_user-" + username + "-0-";
				
				_writeThread = new ClientWriteThread(clientSocket, pw, request);
				_writeThread.start();
				
				JOptionPane.showMessageDialog(null, "Unlock succesfully!");
				dispose();
			}
		});
	}
}
