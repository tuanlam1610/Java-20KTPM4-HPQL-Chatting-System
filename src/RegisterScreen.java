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
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class RegisterScreen extends JFrame {
	private JTextField txtUser;
	private JTextField txtMail;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private Socket clientSocket;
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
		Panel.setBounds(124, 45, 455, 350);
		desktopPane.add(Panel);
		Panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Đăng Ký");
		lblNewLabel_1.setForeground(new Color(102, 153, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(131, 23, 192, 53);
		Panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Tên đăng nhập:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setForeground(new Color(102, 153, 255));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(39, 88, 172, 28);
		Panel.add(lblNewLabel_3);
		
		txtUser = new JTextField();
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setColumns(10);
		txtUser.setBounds(221, 88, 186, 28);
		Panel.add(txtUser);
		
		JLabel lblNewLabel_2_1 = new JLabel("Email:");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_1.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(39, 125, 172, 28);
		Panel.add(lblNewLabel_2_1);
		
		txtMail = new JTextField();
		txtMail.setHorizontalAlignment(SwingConstants.LEFT);
		txtMail.setColumns(10);
		txtMail.setBounds(221, 125, 186, 28);
		Panel.add(txtMail);
		
		JLabel lblNewLabel = new JLabel("Mật khẩu:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setForeground(new Color(102, 153, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(39, 160, 172, 28);
		Panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Xác nhận mật khẩu:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setForeground(new Color(102, 153, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(39, 197, 172, 28);
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
		btnNewButton_2_1.setBounds(295, 232, 112, 21);
		Panel.add(btnNewButton_2_1);
		
		JButton btnRegister = new JButton("Đăng Ký");
		btnRegister.setBackground(new Color(102, 153, 255));
		btnRegister.setForeground(new Color(255, 255, 255));
		btnRegister.setBorder(new LineBorder(new Color(102, 153, 255), 2));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtUser.getText().equals("")||txtMail.getText().equals("")
						||passwordField_1.getText().equals("")|| passwordField_2.getText().equals("")) {
							// show message
							JOptionPane.showMessageDialog(Panel, "Please input all field in Panel.");
				} else {
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
					
					if(user.length() > 20) {
						JOptionPane.showMessageDialog(Panel, "User name cannot exceed 20 character");
					}
					else if(!isValidEmailAddress(mail)) {
						JOptionPane.showMessageDialog(Panel, "Your email is not a valid format");
					}
					else if(!re_password.equals(password)) {
						JOptionPane.showMessageDialog(Panel, "Your password is not match.");
					}
//					TO DO: Add query to check if account already exists in database.
					else {
						pw.println("register-" + user + "-" + mail + "-" + password);
						BufferedReader reader;
						InputStream input;
						try {
							input = clientSocket.getInputStream();
							reader = new BufferedReader(new InputStreamReader(input));
							String msg = reader.readLine();
							String[] data = msg.split("-");
							if (data[0].equals("Success")) {
								JOptionPane.showMessageDialog(null, "Registered successfully!");
								MainScreen frame = new MainScreen();
								frame.setLocationRelativeTo(null);
								frame.setVisible(true);
								dispose();
							}
							else {
								if (data[1].equals("username")) {
									JOptionPane.showMessageDialog(null, "Username existed!");
								}
								else {
									JOptionPane.showMessageDialog(null, "Email existed!");
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
		btnRegister.setBounds(171, 262, 112, 42);
		Panel.add(btnRegister);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(221, 160, 186, 28);
		Panel.add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(221, 197, 186, 28);
		Panel.add(passwordField_2);


		
	}
}
