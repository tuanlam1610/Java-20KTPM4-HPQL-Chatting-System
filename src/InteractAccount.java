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
import java.awt.event.ActionEvent;

public class InteractAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldFullname;
	private JTextField textFieldAddress;
	private JTextField textFieldDOB;
	private JTextField textFieldGender;
	private JTextField textFieldEmail;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InteractAccount(String username, String Fullname, String Address, String DOB, String Email) {
		setResizable(false);
		setTitle("Interact account");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(21, 25, 63, 15);
		contentPane.add(lblUsername);
		
		JLabel lblUsername_1 = new JLabel("Username");
		lblUsername_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername_1.setBounds(94, 25, 63, 15);
		contentPane.add(lblUsername_1);
		
		JLabel lblFullname = new JLabel("Họ tên:");
		lblFullname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFullname.setBounds(21, 50, 63, 15);
		contentPane.add(lblFullname);
		
		textFieldFullname = new JTextField();
		textFieldFullname.setBounds(94, 48, 150, 19);
		contentPane.add(textFieldFullname);
		textFieldFullname.setColumns(10);
		
		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress.setBounds(21, 77, 63, 15);
		contentPane.add(lblAddress);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(94, 75, 150, 19);
		contentPane.add(textFieldAddress);
		
		JLabel lblDob = new JLabel("Ngày sinh:");
		lblDob.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDob.setBounds(21, 104, 63, 15);
		contentPane.add(lblDob);
		
		textFieldDOB = new JTextField();
		textFieldDOB.setColumns(10);
		textFieldDOB.setBounds(94, 102, 150, 19);
		contentPane.add(textFieldDOB);
		
		JLabel lblGender = new JLabel("Giới tính:");
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGender.setBounds(21, 131, 63, 15);
		contentPane.add(lblGender);
		
		textFieldGender = new JTextField();
		textFieldGender.setColumns(10);
		textFieldGender.setBounds(94, 129, 150, 19);
		contentPane.add(textFieldGender);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(21, 158, 63, 15);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(94, 156, 150, 19);
		contentPane.add(textFieldEmail);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 203, 405, 138);
		contentPane.add(scrollPane);
		String[] columnNames = { "Username", "Họ tên"};
		String[][] data = {
	            { "quang123", "Ngọc Quang"}
	            
	    };
		table = new JTable(data, columnNames);
		table.setEnabled(false);
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		
		JLabel lblDanhSchBn = new JLabel("Danh sách bạn bè");
		lblDanhSchBn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchBn.setBounds(143, 185, 150, 15);
		contentPane.add(lblDanhSchBn);
		
		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Update succesfully!");
				dispose();
			}
		});
		btnUpdate.setBounds(45, 351, 100, 21);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Xóa");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Delete succesfully!");
				dispose();
			}
		});
		btnDelete.setBounds(45, 382, 100, 21);
		contentPane.add(btnDelete);
		
		JButton btnBlock = new JButton("Chặn");
		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Block succesfully!");
				dispose();
			}
		});
		btnBlock.setBounds(240, 382, 100, 21);
		contentPane.add(btnBlock);
		
		JButton btnResetPassword = new JButton("Khởi tạo lại mật khẩu");
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Reset password succesfully!\nNew password was send to email for user.");
				dispose();
			}
		});
		btnResetPassword.setBounds(190, 351, 200, 21);
		contentPane.add(btnResetPassword);
	}
}
