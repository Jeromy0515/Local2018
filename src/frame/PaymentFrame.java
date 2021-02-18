package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PaymentFrame extends BaseFrame{
	private JPanel btnPanel;
	private JTextField menuField,volumeField;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private	int price = 0;
	
	public PaymentFrame(String title) {
		super(1000,400,"결제");
		
		setLayout(null);
		
		JLabel mainLabel = createLabel(title, new Font("굴림",Font.BOLD,25));
		mainLabel.setBounds(470,10,60,50);

		JLabel totalPriceLabel = createLabel("총결제금액:", new Font("굴림",Font.BOLD,15));
		totalPriceLabel.setHorizontalAlignment(JLabel.LEFT);
		
		JLabel priceLabel = createLabel(price+"원", new Font("굴림",Font.BOLD,15));
		priceLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		JPanel labelPanel = setComponentSize(new JPanel(), 200, 60);
		labelPanel.setLayout(new GridLayout(1,2));
		labelPanel.setBounds(100,100,200,60);
		labelPanel.add(totalPriceLabel);
		labelPanel.add(priceLabel);
		
		add(mainLabel);
		add(labelPanel);
		
		
	}
	
	private void inputBtnAct() {
		
	}
	
	
	
	
	
	
	
}
