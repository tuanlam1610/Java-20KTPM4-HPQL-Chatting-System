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
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class RegisterScreen extends JFrame {
	private JTextField txtUser;
	private JTextField txtMail;
	private JTextField txtPass;
	private JTextField txtRePass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterScreen frame = new RegisterScreen();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
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
	public RegisterScreen() {
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
		lblNewLabel_3.setForeground(new Color(102, 153, 255));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(70, 88, 141, 28);
		Panel.add(lblNewLabel_3);
		
		txtUser = new JTextField();
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setColumns(10);
		txtUser.setBounds(221, 88, 186, 28);
		Panel.add(txtUser);
		
		JLabel lblNewLabel_2_1 = new JLabel("Email:");
		lblNewLabel_2_1.setForeground(new Color(102, 153, 255));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(70, 125, 141, 28);
		Panel.add(lblNewLabel_2_1);
		
		txtMail = new JTextField();
		txtMail.setHorizontalAlignment(SwingConstants.LEFT);
		txtMail.setColumns(10);
		txtMail.setBounds(221, 125, 186, 28);
		Panel.add(txtMail);
		
		JLabel lblNewLabel = new JLabel("Mật khẩu:");
		lblNewLabel.setForeground(new Color(102, 153, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(70, 160, 141, 28);
		Panel.add(lblNewLabel);
		
		txtPass = new JTextField();
		txtPass.setHorizontalAlignment(SwingConstants.LEFT);
		txtPass.setColumns(10);
		txtPass.setBounds(221, 160, 186, 28);
		Panel.add(txtPass);
		
		JLabel lblNewLabel_2 = new JLabel("Xác nhận mật khẩu:");
		lblNewLabel_2.setForeground(new Color(102, 153, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(70, 197, 141, 28);
		Panel.add(lblNewLabel_2);
		
		txtRePass = new JTextField();
		txtRePass.setHorizontalAlignment(SwingConstants.LEFT);
		txtRePass.setColumns(10);
		txtRePass.setBounds(221, 197, 186, 28);
		Panel.add(txtRePass);
		
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
		btnRegister.setForeground(new Color(102, 153, 255));
		btnRegister.setBorder(new LineBorder(new Color(102, 153, 255), 2));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtUser.getText().equals("")||txtMail.getText().equals("")
						||txtPass.getText().equals("")|| txtRePass.getText().equals("")) {
							// show message
							JOptionPane.showMessageDialog(Panel, "Please enter all field in Add/Update Panel");
				} else {
					String user = txtUser.getText();
					String mail = txtMail.getText();
					String password = txtPass.getText();
					String re_password = txtRePass.getText();
					
					if(user.length() > 15) {
						JOptionPane.showMessageDialog(Panel, "User name cannot exceed 15 character");
					}
					else if(!isValidEmailAddress(mail)) {
						JOptionPane.showMessageDialog(Panel, "'" + mail + "' is not a valid Mail format");
					}
					else if(!re_password.equals(password)) {
						JOptionPane.showMessageDialog(Panel, re_password + " is not the same as " + password);
					}
//					TO DO: Add query to check if account already exists in database.
					else {
//						JOptionPane.showMessageDialog(Panel, "Successfully registerd a new account");
					    String message = "Successfully registerd a new account! Return to log in screen?";
					    int answer = JOptionPane.showConfirmDialog(desktopPane, message, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (answer == JOptionPane.YES_OPTION) {
					      // User clicked YES.
					    	MainScreen new_main = new MainScreen();
							new_main.setLocationRelativeTo(null);
							new_main.setVisible(true);
							dispose();
					    } else if (answer == JOptionPane.NO_OPTION) {
					      // User clicked NO.
					    	// Do nothing
					    }
					    
					    
						
					}
					
				}
						
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRegister.setBounds(165, 262, 125, 51);
		Panel.add(btnRegister);


		
	}
}
