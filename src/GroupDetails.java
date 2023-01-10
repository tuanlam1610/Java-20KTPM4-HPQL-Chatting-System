import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class GroupDetails extends JFrame {
	private JPanel contentPane;
	private JTable memberTable;
	private JTable adminTable;
	private String[][] _members;
	private String[][] _admins;
	private String _groupName;
	public GroupDetails(String GroupName, String[][] members, String[][] admins) {
		this._groupName = GroupName;
		this._members = members;
		this._admins = admins;
		setTitle("Danh Sách Thành Viên (" + _groupName + ")");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel adminLabel = new JLabel("Danh Sách Admin");
		adminLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
		adminLabel.setBounds(197, 10, 232, 40);
		contentPane.add(adminLabel);
		
		JScrollPane memberSP = new JScrollPane();
		memberSP.setFont(new Font("Tahoma", Font.PLAIN, 10));
		memberSP.setBounds(10, 231, 606, 202);
		contentPane.add(memberSP);
		memberTable = new JTable();
		String[] memberCols = { "Username", "Họ và tên", "Email", "Ngày sinh"}; 
		memberTable.setModel(new DefaultTableModel(_members, memberCols));
		DefaultTableCellRenderer memberTableRenderer = (DefaultTableCellRenderer) memberTable.getDefaultRenderer(String.class);
		memberTableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		memberTable.setRowHeight(24);
		memberTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		memberTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		memberTable.setEnabled(false);
		memberSP.setViewportView(memberTable);
		
		JLabel memberLabel = new JLabel("Danh Sách Thành Viên");
		memberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		memberLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		memberLabel.setBounds(197, 181, 232, 40);
		contentPane.add(memberLabel);
		
		JScrollPane adminSP = new JScrollPane();
		adminSP.setBounds(10, 60, 606, 110);
		contentPane.add(adminSP);
		String[] adminCols = { "Username", "Họ và tên", "Email", "Ngày sinh"}; 
		adminTable = new JTable();
		adminTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		adminTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		adminTable.setEnabled(false);
		adminTable.setModel(new DefaultTableModel(_admins, adminCols));
		DefaultTableCellRenderer adminTableRenderer = (DefaultTableCellRenderer) adminTable.getDefaultRenderer(String.class);
		adminTableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		adminTable.setRowHeight(24);
		adminSP.setViewportView(adminTable);
		
	}
}
