package JMenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField passwordField;
	private JButton btnAdd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddScreen frame = new AddScreen();
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
	public AddScreen() {
		setResizable(false);
		setTitle("Add new account");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 350, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblID = new JLabel("Username:");
		lblID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID.setAlignmentX(1.0f);
		lblID.setBounds(10, 39, 76, 22);
		contentPane.add(lblID);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(96, 43, 150, 19);
		contentPane.add(textField);
		
		JLabel lblID_1 = new JLabel("Mật khẩu:");
		lblID_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID_1.setAlignmentX(1.0f);
		lblID_1.setBounds(0, 71, 86, 22);
		contentPane.add(lblID_1);
		
		JLabel lblID_2 = new JLabel("Họ tên:");
		lblID_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID_2.setAlignmentX(1.0f);
		lblID_2.setBounds(0, 103, 86, 22);
		contentPane.add(lblID_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(96, 107, 150, 19);
		contentPane.add(textField_2);
		
		JLabel lblID_3 = new JLabel("Địa chỉ:");
		lblID_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID_3.setAlignmentX(1.0f);
		lblID_3.setBounds(10, 135, 76, 22);
		contentPane.add(lblID_3);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(96, 139, 150, 19);
		contentPane.add(textField_3);
		
		JLabel lblID_4 = new JLabel("Ngày sinh:");
		lblID_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID_4.setAlignmentX(1.0f);
		lblID_4.setBounds(10, 167, 76, 22);
		contentPane.add(lblID_4);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(96, 171, 150, 19);
		contentPane.add(textField_4);
		
		JLabel lblID_5 = new JLabel("Email:");
		lblID_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID_5.setAlignmentX(1.0f);
		lblID_5.setBounds(30, 199, 56, 22);
		contentPane.add(lblID_5);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(96, 203, 150, 19);
		contentPane.add(textField_5);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(96, 75, 150, 19);
		contentPane.add(passwordField);
		
		btnAdd = new JButton("Thêm tài khoản");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Add succesfully!");
				dispose();
			}
		});
		btnAdd.setBounds(114, 248, 107, 21);
		contentPane.add(btnAdd);
	}
}
