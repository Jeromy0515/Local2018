package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.print.PrinterException;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PaymentRetrieveFrame extends BaseFrame{
	private JTextField menuField;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	public PaymentRetrieveFrame() {
		super(600,700,"결제조회");
		
		menuField = new JTextField(13);
		JPanel northPanel = new JPanel();
		northPanel.add(createLabel("메뉴명:", new Font("굴림",Font.BOLD,13)));
		northPanel.add(menuField);
		northPanel.add(createButton("조회",e->retrieval()));
		northPanel.add(createButton("모두보기", e->showAll()));
		northPanel.add(createButton("인쇄", e->print()));
		northPanel.add(createButton("닫기", e->setVisible(false)));
		
		model = new DefaultTableModel(null,"종류,메뉴명,사원명,결제수량,총결제금액,결제일".split(","));
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		setTable("select c.cuisineName,meal.mealName,m.memberName,o.orderCount,o.amount,date_format(o.orderDate,'%y-%m-%d') as orderDate from orderlist as o left join cuisine as c on o.cuisineNo = c.cuisineNo left join member as m on o.memberNo = m.memberNo left join meal on meal.mealNo = o.mealNo;");
		
		add(northPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
	}
	

	private void showAll() {
		model.setNumRows(0);
		setTable("select c.cuisineName,meal.mealName,m.memberName,o.orderCount,o.amount,date_format(o.orderDate,'%y-%m-%d') as orderDate from orderlist as o left join cuisine as c on o.cuisineNo = c.cuisineNo left join member as m on o.memberNo = m.memberNo left join meal on meal.mealNo = o.mealNo;");
	}
	
	private void retrieval() {
		model.setNumRows(0);
		try (ResultSet rs = executeQuery("select c.cuisineName,meal.mealName,m.memberName,o.orderCount,o.amount,date_format(o.orderDate,'%y-%m-%d') as orderDate from orderlist as o left join cuisine as c on o.cuisineNo = c.cuisineNo left join member as m on o.memberNo = m.memberNo left join meal on meal.mealNo = o.mealNo where meal.mealName like concat('%',?,'%');", menuField.getText())){
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString("cuisineName"),rs.getString("mealName"),rs.getString("memberName"),rs.getInt("orderCount"),rs.getInt("amount"),rs.getString("orderDate")});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTable(String sql) {
		try (ResultSet rs = executeQuery(sql)){
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString("cuisineName"),rs.getString("mealName"),rs.getString("memberName"),rs.getInt("orderCount"),rs.getInt("amount"),rs.getString("orderDate")});
			}
		} catch (Exception e) {
		e.printStackTrace();
		}

	}
	
	private void print() {
		try {
			table.print(JTable.PrintMode.FIT_WIDTH);
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}
	
	
}
