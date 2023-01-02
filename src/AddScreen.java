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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AddScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField passwordField;
	private JButton btnAdd;
	private String gender = "1";
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public void setNull__textField_1() {
		textField.setText("");
	}
	public void setNull__textField_2() {
		textField_2.setText("");
	}
	public void setNull__textField_3() {
		textField_3.setText("");
	}
	public void setNull__textField_4() {
		textField_4.setText("");
	}
	public void setNull__textField_5() {
		textField_5.setText("");
	}
	public void setNull_password() {
		passwordField.setText("");
	}
	
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
	
	public AddScreen(Socket clientSocket, PrintWriter pw) {

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
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID.setAlignmentX(1.0f);
		lblID.setBounds(0, 39, 86, 22);
		contentPane.add(lblID);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(96, 43, 186, 19);
		contentPane.add(textField);
		
		JLabel lblID_1 = new JLabel("Mật khẩu:");
		lblID_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID_1.setAlignmentX(1.0f);
		lblID_1.setBounds(0, 71, 86, 22);
		contentPane.add(lblID_1);
		
		JLabel lblID_2 = new JLabel("Họ tên:");
		lblID_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID_2.setAlignmentX(1.0f);
		lblID_2.setBounds(0, 103, 86, 22);
		contentPane.add(lblID_2);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_2.setColumns(10);
		textField_2.setBounds(96, 107, 186, 19);
		contentPane.add(textField_2);
		
		JLabel lblID_3 = new JLabel("Địa chỉ:");
		lblID_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID_3.setAlignmentX(1.0f);
		lblID_3.setBounds(10, 135, 76, 22);
		contentPane.add(lblID_3);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_3.setColumns(10);
		textField_3.setBounds(96, 139, 186, 19);
		contentPane.add(textField_3);
		
		JLabel lblID_4 = new JLabel("Ngày sinh:");
		lblID_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID_4.setAlignmentX(1.0f);
		lblID_4.setBounds(10, 167, 76, 22);
		contentPane.add(lblID_4);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_4.setColumns(10);
		textField_4.setBounds(96, 171, 186, 19);
		contentPane.add(textField_4);
		
		JLabel lblID_5 = new JLabel("Email:");
		lblID_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID_5.setAlignmentX(1.0f);
		lblID_5.setBounds(30, 199, 56, 22);
		contentPane.add(lblID_5);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_5.setColumns(10);
		textField_5.setBounds(96, 203, 186, 19);
		contentPane.add(textField_5);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(96, 75, 186, 19);
		contentPane.add(passwordField);
		
		JLabel lblID_5_1 = new JLabel("Giới tính:");
		lblID_5_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID_5_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID_5_1.setAlignmentX(1.0f);
		lblID_5_1.setBounds(0, 231, 86, 22);
		contentPane.add(lblID_5_1);
        
		JRadioButton radioBtn1 = new JRadioButton("Nam");
		radioBtn1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				gender = (e.getStateChange() == 1 ? "1" : "0");
			}
		});
		radioBtn1.setSelected(true);
		radioBtn1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioBtn1.setBounds(96, 234, 73, 21);
		contentPane.add(radioBtn1);
		
		JRadioButton radioBtn2 = new JRadioButton("Nữ");
		radioBtn2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				gender = (e.getStateChange() == 1 ? "0" : "1");
			}
		});
		radioBtn2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioBtn2.setBounds(171, 234, 103, 21);
		contentPane.add(radioBtn2);
		
		ButtonGroup bg = new ButtonGroup();
        bg.add(radioBtn1);
        bg.add(radioBtn2);
        
		btnAdd = new JButton("Thêm tài khoản");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("") || textField_2.getText().equals("") || textField_5.getText().equals("") || passwordField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn phải nhập đầy đủ username, password, họ tên, email!");
					return;
				}
				if (textField_4.getText().equals("") == false) {
					if (isValidFormat("yyyy/MM/dd", textField_4.getText(), Locale.ENGLISH) == false) {
						JOptionPane.showMessageDialog(null, "Bạn phải ngày sinh có dạng 'yyyy/MM/dd'!");
						return;
					}
				}
				pw.println("them_tai_khoan-" + textField.getText() + "-" + passwordField.getText() + "-" + textField_2.getText() + "-" +
						 textField_5.getText() + "-" + textField_4.getText() + "-" + textField_3.getText() + "-" + gender);
				BufferedReader reader;
				InputStream input;
				try {
					input = clientSocket.getInputStream();
					reader = new BufferedReader(new InputStreamReader(input));
					String msg = reader.readLine();
					System.out.println(msg);
					String[] data = msg.split("-");
					System.out.println(data);
					if (data[0].equals("Success")) {
						JOptionPane.showMessageDialog(null, "Add successfully!");
						dispose();
					}
					else {
						if (data[1].equals("username")) {
							JOptionPane.showMessageDialog(null, "Username existed!");
							textField.setText("");
						}
						else {
							JOptionPane.showMessageDialog(null, "Email existed!");
							textField_5.setText("");
						}
						
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(84, 269, 168, 21);
		contentPane.add(btnAdd);
		
		
	}
}
