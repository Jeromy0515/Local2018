package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CouponIssuanceFrame extends BaseFrame{

	public CouponIssuanceFrame() {
		super(300, 600, "식권 발매 프로그램");
		
		JLabel label = createLabel("식권 발매 프로그램", new Font("굴림",1,20));
		label.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,2));
		centerPanel.setPreferredSize(new Dimension(300,450));
		centerPanel.add(createButton(new ImageIcon("image/menu_1.png"),e->{}));
		centerPanel.add(createButton(new ImageIcon("image/menu_2.png"),e->{}));
		centerPanel.add(createButton(new ImageIcon("image/menu_3.png"),e->{}));
		centerPanel.add(createButton(new ImageIcon("image/menu_4.png"),e->{}));
		
		JPanel southPanel = new JPanel();
		
		add(label,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);
	}
	
	
	
	private JButton createButton(ImageIcon image,ActionListener act) {
		JButton button = new JButton(image);
		button.addActionListener(act);
		return button;
		
		
	}
	
}
