package frame;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CouponFrame extends BaseFrame{
	private boolean b = true;
	private JScrollPane scrollPane;
	public CouponFrame(JTable table) {
		super(300,600,"식권");
		JPanel panels[] = new JPanel[table.getRowCount()];
		scrollPane = new JScrollPane();
		
		for(int row=0;row<table.getRowCount();row++) {
			for(int i=0;i<Integer.parseInt(String.valueOf(table.getValueAt(row, 2)));i++) {
				JPanel panel = new JPanel();
				if(b)
					panel.setBackground(new Color(0,255,255));
			}
		}
		
		for(int i=0;i<panels.length;i++) {
			panels[i] = setComponentSize(new JPanel(), 300, 300);
			scrollPane.add(panels[i]);
		}
		
		
	}
	
	
	
}
