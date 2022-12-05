import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JEditorPane;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class MainScreen extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField usernameInput;
	private JPasswordField passInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen mainfrm = new MainScreen();
					mainfrm.setLocationRelativeTo(null);
					mainfrm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		// Set Up Top Level Frame
		setResizable(false);
		setTitle("HPQL Chatting System");
		getContentPane().setEnabled(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);  
		getContentPane().setLayout(null);
		
		// Login Pane
		JDesktopPane login_desktopPane = new JDesktopPane();
		login_desktopPane.setForeground(new Color(255, 255, 255));
		login_desktopPane.setBorder(null);
		login_desktopPane.setBounds(0, 0, 720, 480);
		getContentPane().add(login_desktopPane);
		login_desktopPane.setBackground(new Color(255, 255, 255));
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(new Color(255, 255, 255));
		inputPanel.setBounds(192, 168, 336, 97);
		login_desktopPane.add(inputPanel);
		inputPanel.setLayout(null);
		
		JLabel usernameLabel = new JLabel("Tên đăng nhập:");
		usernameLabel.setBounds(0, 0, 140, 28);
		inputPanel.add(usernameLabel);
		usernameLabel.setForeground(new Color(102, 153, 255));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		usernameInput = new JTextField();
		usernameInput.setBounds(150, 0, 186, 28);
		inputPanel.add(usernameInput);
		usernameInput.setHorizontalAlignment(SwingConstants.LEFT);
		usernameInput.setColumns(10);
		
		JLabel passLabel = new JLabel("Mật khẩu:");
		passLabel.setBounds(45, 38, 95, 28);
		inputPanel.add(passLabel);
		passLabel.setForeground(new Color(102, 153, 255));
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		passInput = new JPasswordField();
		passInput.setBounds(150, 38, 186, 28);
		inputPanel.add(passInput);
		passInput.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton forgotPassBtn = new JButton("<HTML><u>Quên mật khẩu?</u></HTML>");
		forgotPassBtn.setBounds(224, 76, 112, 21);
		inputPanel.add(forgotPassBtn);
		forgotPassBtn.setForeground(new Color(0, 0, 0));
		forgotPassBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		forgotPassBtn.setBackground(Color.WHITE);
		forgotPassBtn.setBorder(null);
		forgotPassBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel loginTitle = new JLabel("ĐĂNG NHẬP");
		loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginTitle.setForeground(new Color(102, 153, 255));
		loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		loginTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
		loginTitle.setBounds(0, 80, 720, 53);
		login_desktopPane.add(loginTitle);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(new Color(255, 255, 255));
		btnPanel.setBounds(239, 300, 241, 42);
		login_desktopPane.add(btnPanel);
		btnPanel.setLayout(null);
		
		JButton loginBtn = new JButton("Đăng Nhập");
		loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(usernameInput.getText().equals("user")) {
					MainScreen_User user = new MainScreen_User();
					user.setLocationRelativeTo(null);
					user.setVisible(true);
					dispose();
				}
				else if (usernameInput.getText().equals("admin")) {
					AdminMainScreen admin = new AdminMainScreen();
					admin.setLocationRelativeTo(null);
					admin.setVisible(true);
					dispose();
				}
			}
		});
		loginBtn.setBounds(0, 0, 112, 42);
		btnPanel.add(loginBtn);
		loginBtn.setForeground(new Color(255, 255, 255));
		loginBtn.setBorder(null);
		loginBtn.setBackground(new Color(102, 153, 255));
		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton registerBtn = new JButton("Đăng Ký");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterScreen new_register = new RegisterScreen();
				new_register.setLocationRelativeTo(null);
				new_register.setVisible(true);
				dispose();
				
			}
		});
		registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		registerBtn.setBounds(129, 0, 112, 42);
		btnPanel.add(registerBtn);
		registerBtn.setBorder(new LineBorder(new Color(102, 153, 255), 2));
		registerBtn.setForeground(new Color(102, 153, 255));
		registerBtn.setBackground(new Color(255, 255, 255));
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		// Reset Pane
		JDesktopPane reset_desktopPane = new JDesktopPane();
		reset_desktopPane.setBorder(null);
		reset_desktopPane.setBackground(Color.WHITE);
		reset_desktopPane.setBounds(0, 0, 720, 480);
		getContentPane().add(reset_desktopPane);
		reset_desktopPane.setVisible(false);
		
		JLabel resetTitle = new JLabel("KHÔI PHỤC MẬT KHẨU");
		resetTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		resetTitle.setForeground(new Color(102, 153, 255));
		resetTitle.setHorizontalAlignment(SwingConstants.CENTER);
		resetTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
		resetTitle.setBounds(0, 80, 720, 49);
		reset_desktopPane.add(resetTitle);
		
		JPanel verifyPanel = new JPanel();
		verifyPanel.setBackground(new Color(255, 255, 255));
		verifyPanel.setBounds(192, 168, 336, 66);
		reset_desktopPane.add(verifyPanel);
		verifyPanel.setLayout(null);
		
		JLabel lblNewLabel_resetUsername = new JLabel("Tên đăng nhập:");
		lblNewLabel_resetUsername.setBounds(0, 0, 140, 28);
		verifyPanel.add(lblNewLabel_resetUsername);
		lblNewLabel_resetUsername.setForeground(new Color(102, 153, 255));
		lblNewLabel_resetUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_resetUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_resetUsername.setLabelFor(textField);
		
		textField = new JTextField();
		textField.setBounds(150, 0, 186, 28);
		verifyPanel.add(textField);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(10);
		
		JLabel lblNewLabel_resetEmail = new JLabel("Email:");
		lblNewLabel_resetEmail.setBounds(75, 38, 65, 28);
		verifyPanel.add(lblNewLabel_resetEmail);
		lblNewLabel_resetEmail.setForeground(new Color(102, 153, 255));
		lblNewLabel_resetEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_resetEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		textField_1 = new JTextField();
		textField_1.setBounds(150, 38, 186, 28);
		verifyPanel.add(textField_1);
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(10);
		
		JButton btnConfirm = new JButton("Xác Nhận");
		btnConfirm.setBorder(null);
		btnConfirm.setBackground(new Color(102, 153, 255));
		btnConfirm.setForeground(new Color(255, 255, 255));
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnConfirm.setBounds(300, 300, 120, 44);
		reset_desktopPane.add(btnConfirm);
		
		// Event Listener
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset_desktopPane.setVisible(false);
				login_desktopPane.setVisible(true);
			}
		});
		
		forgotPassBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset_desktopPane.setVisible(true);
				login_desktopPane.setVisible(false);
			}
		});
	}
}
