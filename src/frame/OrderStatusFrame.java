package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderStatusFrame extends BaseFrame{
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	public OrderStatusFrame() {
		super(300,200,"메뉴별 주문현황");
		
		model = new DefaultTableModel(null,"종류,주문수량".split(","));
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(table);
		String cuisine[] = "한식,중식,일식,양식".split(",");
		int sum = 0;
		for(int i=1;i<=4;i++) {
			try (ResultSet rs = executeQuery("select count(cuisineNo) as count from orderlist where cuisineNo=?", i)){
				if(rs.next()) {
					model.addRow(new Object[] {cuisine[i-1],rs.getInt("count")});
					sum += rs.getInt("count");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		JButton closeBtn = createButton("닫기", null);

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		northPanel.add(closeBtn);
		
		
		JLabel label = createLabel("합계:"+sum+"개", new Font("굴림",Font.BOLD,13));
		label.setHorizontalAlignment(JLabel.RIGHT);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(label);
		 
		add(northPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);
	}
	
}
