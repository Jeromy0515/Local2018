package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CouponFrame extends BaseFrame{
	private boolean bool = true;
	private JScrollPane scrollPane;
	public CouponFrame(JTable table) {
		super(300,600,"식권");
		scrollPane = new JScrollPane();
		
//		for(int row=0;row<table.getRowCount();row++) {
//			for(int i=0;i<Integer.parseInt(String.valueOf(table.getValueAt(row, 2)));i++) {
		
		try (ResultSet rs = executeQuery("select o.orderDate, o.memberNo,o.orderCount,m.price from orderlist as o left join meal as m on o.mealNo = m.mealNo where o.memberNo = ? order by o.orderDate desc limit ?;",PaymentFrame.memberNo,table.getRowCount())){
			while(rs.next()) {
				for(int i=0;i<rs.getInt("");i++) {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
				JPanel panel = new JPanel();
				panel.setLayout(null);
				panel.setPreferredSize(new Dimension(280,250));
				if(bool)
					panel.setBackground(new Color(0,255,255));
				else
					panel.setBackground(Color.PINK);
//			}
			if(bool)
				bool = false;
			else
				bool = true;
//		}
		
		
		
//		for(int i=0;i<panels.length;i++) {
//			panels[i] = setComponentSize(new JPanel(), 300, 300);
//			scrollPane.add(panels[i]);
//		}
		
		
	}
	
	private JLabel createLabel(String caption, Font font,int x,int y,int width,int height) {
		JLabel label = new JLabel(caption);
		label.setFont(font);
		label.setBounds(x, y, width, height);
		return label;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
