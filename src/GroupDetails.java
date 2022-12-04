import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;

public class GroupDetails extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

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
	public GroupDetails(String GrouopName) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Danh sách Admin");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(143, 25, 150, 13);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 416, 132);
		contentPane.add(scrollPane);
		String[][] dataAdmin = {
				{"lam123", "Tuan Lam"},
				{"quang123", "Ngoc Quang"},
		};
		String[] colNames = {"username", "Họ tên"};
		table = new JTable(dataAdmin, colNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
		JLabel lblDanhSchThnh = new JLabel("Danh sách thành viên");
		lblDanhSchThnh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDanhSchThnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchThnh.setBounds(143, 219, 203, 13);
		contentPane.add(lblDanhSchThnh);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 246, 416, 132);
		contentPane.add(scrollPane_1);
		String[][] dataMember = {
				{"lam123", "Ha Tuan Lam"},
				{"quang123", "Ngoc Quang"},
				{"lam123", "Ha Tuan Lam"},
				{"lam123", "Ha Tuan Lam"},
				{"lam123", "Ha Tuan Lam"},
				{"lam123", "Ha Tuan Lam"},
				{"lam123", "Ha Tuan Lam"},
		};
		table_1 = new JTable(dataMember, colNames);
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table_1.setEnabled(false);
		scrollPane_1.setViewportView(table_1);
	}
}
