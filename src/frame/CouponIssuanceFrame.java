package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

class KoreanFood extends PaymentFrame{
	public KoreanFood(JFrame frame) {
		super(frame,"한식");
	}
}

class ChineseFood extends PaymentFrame{
	public ChineseFood(JFrame frame) {
		super(frame,"중식");
	}
}

class JapaneseFood extends PaymentFrame{
	public JapaneseFood(JFrame frame) {
		super(frame,"일식");
	}
}

class WesternFood extends PaymentFrame{
	public WesternFood(JFrame frame) {
		super(frame,"양식");
	}
}


public class CouponIssuanceFrame extends BaseFrame implements Runnable{
	public static LocalDateTime time;
	private JLabel timeLabel;
	
	public CouponIssuanceFrame() {
		super(300, 600, "식권 발매 프로그램");
		
		JLabel mainLabel = createLabel("식권 발매 프로그램", new Font("굴림",1,20));
		mainLabel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,2));
		centerPanel.setPreferredSize(new Dimension(300,450));
		centerPanel.add(createButton(new ImageIcon("image/menu_1.png"),"한식",e -> openFrame(new KoreanFood(this))));
		centerPanel.add(createButton(new ImageIcon("image/menu_2.png"),"중식",e -> openFrame(new ChineseFood(this))));
		centerPanel.add(createButton(new ImageIcon("image/menu_3.png"),"일식",e -> openFrame(new JapaneseFood(this))));
		centerPanel.add(createButton(new ImageIcon("image/menu_4.png"),"양식",e -> openFrame(new WesternFood(this))));
		
		
		JTabbedPane panel = new JTabbedPane();
		panel.add("메뉴",centerPanel);
		
		time = LocalDateTime.now();
		
		timeLabel = new JLabel("현재시간 : "+time.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
		timeLabel.setForeground(Color.WHITE);
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.BLACK);
		southPanel.add(timeLabel);

		new Thread(this).start();
		
		add(mainLabel,BorderLayout.NORTH);
		add(panel,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				time = time.plusSeconds(1);
				timeLabel.setText("현재시간 : "+time.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private JButton createButton(ImageIcon imgae,String toolTipText,ActionListener act) {
		JButton button = new JButton(new ImageIcon(imgae.getImage().getScaledInstance(140, 260, Image.SCALE_SMOOTH)));
		button.setToolTipText(toolTipText);
		button.addActionListener(act);
		return button;
	}
	
}
