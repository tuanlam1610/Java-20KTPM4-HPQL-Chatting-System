import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class MainScreen_User extends JFrame {
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen_User frame = new MainScreen_User();
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
	public MainScreen_User() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 350, 690);
		contentPane.add(desktopPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(0, 0, 350, 80);
		desktopPane.add(panel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 77, 350, 603);
		desktopPane.add(tabbedPane);
		
		JScrollPane scrollPaneBanBe = new JScrollPane();
		scrollPaneBanBe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Ban be", null, scrollPaneBanBe, null);
		
		JList listBanBe = new JList();
		listBanBe.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listBanBe.setFont(new Font("Tahoma", Font.PLAIN, 24));
		listBanBe.setModel(new AbstractListModel() {
			String[] values = new String[] {"Huy", "Phú"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneBanBe.setViewportView(listBanBe);
		
		JScrollPane scrollPaneNhom = new JScrollPane();
		scrollPaneNhom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Nhom", null, scrollPaneNhom, null);
		
		JList listNhom = new JList();
		listNhom.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listNhom.setFont(new Font("Tahoma", Font.PLAIN, 24));
		listNhom.setModel(new AbstractListModel() {
			String[] values = new String[] {"Java", "Web"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneNhom.setViewportView(listNhom);
		
		JLabel lab = new JLabel();
		
		//--------------
		//tabbedPane.setTabComponentAt(0, lab);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 128));
		panel.setBounds(350, 0, 720, 690);
		contentPane.add(panel);
		// -----------------
		//UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(10, 100, 0, 0));
	}
}
