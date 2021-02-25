package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CouponFrame extends BaseFrame{
	private boolean bool = true;
	private JScrollPane scrollPane;
	public CouponFrame(JTable table) {
		super(300,600,"식권");
//		setLayout(new GridLayout(5,1));
		int y=0;
		
		JPanel panPanel = new JPanel();
		Color color = new Color(0, 255, 255);
		try (ResultSet rs = executeQuery("select o.orderDate, o.memberNo,o.orderCount,m.price from orderlist as o left join meal as m on o.mealNo = m.mealNo where o.memberNo = ? order by o.orderDate desc limit ?;",/*PaymentFrame.memberNo,table.getRowCount()*/10007,5)){
			while(rs.next()) {
				for(int i=0;i<rs.getInt("orderCount");i++) {
					JPanel panel = setComponentSize(new JPanel(), 300, 300);
					panel.setLayout(null);
					panel.setBackground(color);
					panPanel.add(panel);
					y+=300;
				}
				if(color.equals(new Color(0, 255, 255)))
					color = Color.PINK;
				else
					color = new Color(0, 255, 255);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			scrollPane = new JScrollPane(panPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			add(scrollPane);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String[] args) {
		new CouponFrame(null).setVisible(true);
	}	
	
	private JLabel createLabel(String caption, Font font,int x,int y,int width,int height) {
		JLabel label = new JLabel(caption);
		label.setFont(font);
		label.setBounds(x, y, width, height);
		return label;
	}
}
