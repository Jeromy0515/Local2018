package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

class KoreanFood extends PaymentFrame{
	public KoreanFood() {
		super("한식");
	}
}

class Covid19 extends PaymentFrame{
	public Covid19() {
		super("중식");
	}
}

class JapaneseFood extends PaymentFrame{
	public JapaneseFood() {
		super("일식");
	}
}

class WesternFood extends PaymentFrame{
	public WesternFood() {
		super("양식");
	}
}


public class CouponIssuanceFrame extends BaseFrame implements Runnable{
	private LocalDateTime time;
	private JLabel timeLabel;
	private JPanel southPanel;
	
	public CouponIssuanceFrame() {
		super(300, 600, "식권 발매 프로그램");
		
		JLabel mainLabel = createLabel("식권 발매 프로그램", new Font("굴림",1,20));
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,2));
		centerPanel.setPreferredSize(new Dimension(300,450));
		centerPanel.add(createButton(new ImageIcon("image/menu_1.png"),e->new KoreanFood().setVisible(true)));
		centerPanel.add(createButton(new ImageIcon("image/menu_2.png"),e->new Covid19().setVisible(true)));
		centerPanel.add(createButton(new ImageIcon("image/menu_3.png"),e->new JapaneseFood().setVisible(true)));
		centerPanel.add(createButton(new ImageIcon("image/menu_4.png"),e->new WesternFood().setVisible(true)));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		
		time = LocalDateTime.now();
		
		timeLabel = new JLabel("현재시간 : "+time.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
		timeLabel.setForeground(Color.WHITE);
		southPanel = new JPanel();
		southPanel.setBackground(Color.BLACK);
		southPanel.add(timeLabel);

		new Thread(this).start();
		
		add(mainLabel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
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


	private JButton createButton(ImageIcon imgae,ActionListener act) {
		JButton button = new JButton(new ImageIcon(imgae.getImage().getScaledInstance(140, 260, Image.SCALE_SMOOTH)));
		button.addActionListener(act);
		return button;
		
	}
	
}
