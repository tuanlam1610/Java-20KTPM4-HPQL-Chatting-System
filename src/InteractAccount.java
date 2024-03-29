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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class InteractAccount extends JFrame {
	private static final long serialVersionUID = 1L;
	private ClientWriteThread _writeThread;
	private JPanel contentPane;
	private JTextField textFieldFullname;
	private JTextField textFieldAddress;
	private JTextField textFieldDOB;
	private JTable _tableListFriend;
	private JTable table;
	private JTable _loginUserTable;
	private JTextField textFieldGender;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	
	public static boolean isValidFormat(String format, String value, Locale locale) {
	    LocalDateTime ldt = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

	    try {
	        ldt = LocalDateTime.parse(value, fomatter);
	        String result = ldt.format(fomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
	        try {
	            LocalDate ld = LocalDate.parse(value, fomatter);
	            String result = ld.format(fomatter);
	            return result.equals(value);
	        } catch (DateTimeParseException exp) {
	            try {
	                LocalTime lt = LocalTime.parse(value, fomatter);
	                String result = lt.format(fomatter);
	                return result.equals(value);
	            } catch (DateTimeParseException e2) {
	                // Debugging purposes
	                //e2.printStackTrace();
	            }
	        }
	    }

	    return false;
	}
	
	public InteractAccount(Socket clientSocket, PrintWriter pw, JTable tableListFriend, String admin, String username,
			String Fullname, String Address, String DOB, String Gender, String Email, String status, JTable LoginUserTable) {

		this._tableListFriend = tableListFriend;
		this._loginUserTable = LoginUserTable;
		
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

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(41, 157, 63, 15);
		contentPane.add(lblEmail);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 203, 405, 138);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(_tableListFriend);

		JLabel lblDanhSchBn = new JLabel("Danh sách bạn bè");
		lblDanhSchBn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDanhSchBn.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchBn.setBounds(143, 185, 150, 15);
		contentPane.add(lblDanhSchBn);

		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String DOB = textFieldDOB.getText();
				if (isValidFormat("yyyy/MM/dd", textFieldDOB.getText(), Locale.ENGLISH) == false && isValidFormat("yyyy-MM-dd", textFieldDOB.getText(), Locale.ENGLISH) == false ) {
					JOptionPane.showMessageDialog(null, "Ngày sinh phải có dạng 'yyyy/MM/dd' hoặc 'yyyy-MM-dd'!");
					return;
				}
				else {
					DOB = DOB.replace('-', '/');
				}
				String gender;
				if (textFieldGender.getText().equalsIgnoreCase("Nam")) {
					gender = "1";
				}
				else if (textFieldGender.getText().equalsIgnoreCase("Nữ")) {
					gender = "0";
				}
				else {
					JOptionPane.showMessageDialog(null, "Giới tính phải là Nam hoặc Nữ!");
					return;
				}
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn cập nhật tài khoản này chứ?",
						"Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					pw.println("cap_nhat_tai_khoan-" + lblUsername_1.getText() + "-" + textFieldFullname.getText() + "-" + 
							textFieldAddress.getText() + "-" + DOB + "-" + gender);
					JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
					dispose();
				} else {
					return;
				}
			}
		});
		btnUpdate.setBounds(20, 351, 100, 21);
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
					JOptionPane.showMessageDialog(null, "Xóa thành công!");
					dispose();
				} else {
					return;
				}

			}
		});
		btnDelete.setBounds(20, 382, 100, 21);
		contentPane.add(btnDelete);

		JButton btnBlock = new JButton("Khóa");
		btnBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBlock.setBounds(290, 382, 100, 21);
		contentPane.add(btnBlock);

		JButton btnUnBlock = new JButton("Mở khóa");
		btnUnBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUnBlock.setBounds(290, 382, 100, 21);
		contentPane.add(btnUnBlock);

		JLabel lblEmail_1 = new JLabel(Email);
		lblEmail_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail_1.setBounds(114, 158, 200, 15);
		contentPane.add(lblEmail_1);
		
		JButton btnResetPassword = new JButton("Khởi tạo lại mật khẩu");
		btnResetPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn reset mật khẩu của tài khoản này chứ?",
						"Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					pw.println("doi_mat_khau_tai_khoan-" + lblUsername_1.getText() + "-" + lblEmail_1.getText());
					JOptionPane.showMessageDialog(null, "Đổi mật khẩu của tài khoản thành công!\nMật khẩu mới đã được gửi đến email của người dùng.");
				} else {
					return;
				}
				dispose();
			}
		});
		btnResetPassword.setBounds(190, 351, 200, 21);
		contentPane.add(btnResetPassword);
		textFieldFullname.setText(Fullname);
		textFieldAddress.setText(Address);
		textFieldDOB.setText(DOB);
		
		lblUsername_1.setText(username);
		
		textFieldGender = new JTextField();
		textFieldGender.setText(Gender);
		textFieldGender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldGender.setColumns(10);
		textFieldGender.setBounds(114, 130, 200, 20);
		contentPane.add(textFieldGender);
		
		JButton btnLchSng = new JButton("Lịch sử đ/n");
		btnLchSng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InteractAccountLoginHistory loginPopup = new InteractAccountLoginHistory(clientSocket, pw, admin, username, _loginUserTable);
				loginPopup.setVisible(true);;
			}
		});
		btnLchSng.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLchSng.setBounds(130, 382, 150, 21);
		contentPane.add(btnLchSng);
		
		
		// ------------------------------------ EVENT --------------------------------------
		
		// Update The status of btn Block
		if (status.equals("Bị khóa")) {
			btnBlock.setVisible(false);
			btnUnBlock.setVisible(true);
		} else {
			btnBlock.setVisible(true);
			btnUnBlock.setVisible(false);
		}

		// ---------------------------------------------------------------------------------

		// Update friend-list

		String requestUpdateListFriend = "(admin)_diplay_list_friends-" + admin + "-" + username + "-";

		_writeThread = new ClientWriteThread(clientSocket, pw, requestUpdateListFriend);

		_writeThread.start();

		// Button Lock And Unlock

		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String request = "(admin)_lock_user-" + admin + "-" + username + "-1-";

				_writeThread = new ClientWriteThread(clientSocket, pw, request);
				_writeThread.start();

				JOptionPane.showMessageDialog(null, "Lock succesfully!");
				dispose();
			}
		});

		btnUnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String request = "(admin)_lock_user-" + admin + "-" + username + "-0-";

				_writeThread = new ClientWriteThread(clientSocket, pw, request);
				_writeThread.start();

				JOptionPane.showMessageDialog(null, "Unlock succesfully!");
				dispose();
			}
		});
	}
}
