package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PaymentFrame extends BaseFrame{
	JPanel btnPanel;
	JTextField menuField,volumeField;
	DefaultTableModel model;
	JTable table;
	JScrollPane scrollPane;
	
	public PaymentFrame(String title) {
		super(1000,400,"결제");
		
		JLabel mainLabel = createLabel(title, new Font("굴림",1,20));
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		
		int price = 0;
		
		JLabel priceLabel = createLabel(price+"원", new Font("굴림",1,15));
		priceLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		model = new DefaultTableModel(null,"상품번호,품명,수량,금액".split(","));
		table = new JTable(model);
		scrollPane = setComponentSize(new JScrollPane(table), 400, 200);
		
		
		menuField = new JTextField(20);
		volumeField = new JTextField(4);
		
		JPanel rightPanel = setComponentSize(new JPanel(), 400, 350);
		rightPanel.setLayout(new FlowLayout());
		rightPanel.add(createLabel("총결제금액:", new Font("굴림",1,15)));
		rightPanel.add(priceLabel);
		rightPanel.add(scrollPane);
		rightPanel.add(createLabel("선택품명:", null));
		rightPanel.add(menuField);
		rightPanel.add(createLabel("수량:", null));
		rightPanel.add(volumeField);
		
		JPanel leftPanel= setComponentSize(new JPanel(), 600, 350);
		leftPanel.setLayout(new GridLayout(5,5));
		for(int i=0;i<13;i++) {
			leftPanel.add(createButton("버튼", null));
		}
		add(mainLabel,BorderLayout.NORTH);
		add(leftPanel,BorderLayout.CENTER);
		add(rightPanel,BorderLayout.CENTER);
		
		btnPanel = new JPanel();
		
		
	}
	
}
