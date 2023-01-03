import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.CardLayout;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class RegisterScreen extends JFrame {
	private JTextField txtUser;
	private JTextField txtMail;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private Socket clientSocket;
	private String gender = "1";
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	/**
	 * Launch the application.
	 */
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
	}

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
	
	public RegisterScreen(Socket clientSocket) {
		this.clientSocket = clientSocket;
		setTitle("HPQL Chatting System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane);
		
		JPanel Panel = new JPanel();
		Panel.setBackground(new Color(255, 255, 255));
		Panel.setBounds(0, 0, 706, 443);
		desktopPane.add(Panel);
		Panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Đăng Ký");
		lblNewLabel_1.setForeground(new Color(102, 153, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(257, 23, 192, 53);
		Panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Tên đăng nhập:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setForeground(new Color(102, 153, 255));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(152, 86, 172, 28);
		Panel.add(lblNewLabel_3);
		
		txtUser = new JTextField();
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setColumns(10);
		txtUser.setBounds(334, 86, 186, 28);
		Panel.add(txtUser);
		
		JLabel lblNewLabel_2_1 = new JLabel("Email:");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_1.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(152, 123, 172, 28);
		Panel.add(lblNewLabel_2_1);
		
		txtMail = new JTextField();
		txtMail.setHorizontalAlignment(SwingConstants.LEFT);
		txtMail.setColumns(10);
		txtMail.setBounds(334, 123, 186, 28);
		Panel.add(txtMail);
		
		JLabel lblNewLabel = new JLabel("Mật khẩu:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setForeground(new Color(102, 153, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(152, 158, 172, 28);
		Panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Xác nhận mật khẩu:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setForeground(new Color(102, 153, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(152, 195, 172, 28);
		Panel.add(lblNewLabel_2);
		
		JButton btnNewButton_2_1 = new JButton("Đã có tài khoản?");
		btnNewButton_2_1.setForeground(new Color(125, 168, 255));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen new_main = new MainScreen();
				new_main.setLocationRelativeTo(null);
				new_main.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		btnNewButton_2_1.setBorder(null);
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setAlignmentX(0.5f);
		btnNewButton_2_1.setBounds(408, 382, 112, 21);
		Panel.add(btnNewButton_2_1);
		
		JButton btnRegister = new JButton("Đăng Ký");
		btnRegister.setBackground(new Color(102, 153, 255));
		btnRegister.setForeground(new Color(255, 255, 255));
		btnRegister.setBorder(new LineBorder(new Color(102, 153, 255), 2));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtUser.getText().equals("")||txtMail.getText().equals("")
						||passwordField_1.getText().equals("")|| passwordField_2.getText().equals("") || textField.equals("")) {
							// show message
							JOptionPane.showMessageDialog(Panel, "Bạn phải nhập đầy đủ username, email, password, họ tên.");
				} else {
					if (textField_2.getText().equals("") == false) {
						if (isValidFormat("yyyy/MM/dd", textField_2.getText(), Locale.ENGLISH) == false && isValidFormat("yyyy-MM-dd", textField_2.getText(), Locale.ENGLISH) == false ) {
							JOptionPane.showMessageDialog(null, "Bạn phải ngày sinh có dạng 'yyyy/MM/dd' hoặc 'yyyy-MM-dd'!");
							return;
						}
					}
					OutputStream output = null;
					try {
						output = clientSocket.getOutputStream();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PrintWriter pw = new PrintWriter(output, true);
					String user = txtUser.getText();
					String mail = txtMail.getText();
					String password = passwordField_1.getText();
					String re_password = passwordField_2.getText();
					String hoten = textField.getText();
					String diachi = textField_1.getText();
					String ngaysinh = textField_2.getText();
					
					if(user.length() > 20) {
						JOptionPane.showMessageDialog(Panel, "Tên đăng nhập có độ dài không quá 20.");
					}
					else if(!isValidEmailAddress(mail)) {
						JOptionPane.showMessageDialog(Panel, "Bạn phải nhập đúng định dạng email.");
					}
					else if(!re_password.equals(password)) {
						JOptionPane.showMessageDialog(Panel, "Mật khẩu không khớp.");
					}
//					TO DO: Add query to check if account already exists in database.
					else {
						pw.println("register-" + user + "-" + password + "-" + hoten + "-" + mail + "-" + ngaysinh + "-" + diachi + "-" + gender);
						
						BufferedReader reader;
						InputStream input;
						try {
							input = clientSocket.getInputStream();
							reader = new BufferedReader(new InputStreamReader(input));
							String msg = reader.readLine();
							String[] data = msg.split("-");
							if (data[0].equals("Success")) {
								JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
								MainScreen frame = new MainScreen();
								frame.setLocationRelativeTo(null);
								frame.setVisible(true);
								dispose();
							}
							else {
								if (data[1].equals("username")) {
									JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại!");
								}
								else {
									JOptionPane.showMessageDialog(null, "Email đã tồn tại!");
								}
								
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
						
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRegister.setBounds(286, 391, 112, 42);
		Panel.add(btnRegister);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(334, 158, 186, 28);
		Panel.add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(334, 195, 186, 28);
		Panel.add(passwordField_2);
		
		JLabel lblNewLabel_2_2 = new JLabel("Họ tên:");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_2.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_2.setBounds(152, 233, 172, 28);
		Panel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("Địa chỉ:");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_3.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_3.setBounds(152, 271, 172, 28);
		Panel.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_4 = new JLabel("Ngày sinh:");
		lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_4.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_4.setBounds(152, 309, 172, 28);
		Panel.add(lblNewLabel_2_4);
		
		JLabel lblNewLabel_2_5 = new JLabel("Giới tính:");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_5.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_5.setBounds(152, 347, 172, 28);
		Panel.add(lblNewLabel_2_5);
		
		JRadioButton radioBtn1 = new JRadioButton("Nam");
		radioBtn1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				gender = (e.getStateChange() == 1 ? "1" : "0");
			}
		});
		radioBtn1.setForeground(new Color(0, 0, 0));
		radioBtn1.setBackground(new Color(255, 255, 255));
		radioBtn1.setSelected(true);
		radioBtn1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioBtn1.setBounds(334, 354, 73, 21);
		Panel.add(radioBtn1);
		
		JRadioButton radioBtn2 = new JRadioButton("Nữ");
		radioBtn2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				gender = (e.getStateChange() == 1 ? "0" : "1");
			}
		});
		radioBtn2.setBackground(new Color(255, 255, 255));
		radioBtn2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioBtn2.setBounds(409, 354, 103, 21);
		Panel.add(radioBtn2);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(10);
		textField.setBounds(334, 233, 186, 28);
		Panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(10);
		textField_1.setBounds(334, 271, 186, 28);
		Panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setColumns(10);
		textField_2.setBounds(334, 309, 186, 28);
		Panel.add(textField_2);


		
	}
}
