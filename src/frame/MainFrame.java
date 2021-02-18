package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame{

	public MainFrame(){
		super(300, 300, "메인");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.add(createButton("사원등록", e->new EmployeeRegistrationFrame().setVisible(true)));
		panel.add(createButton("사용자", e->new CouponIssuanceFrame().setVisible(true)));
		panel.add(createButton("관리자", e->{}));
		panel.add(createButton("종료", e->System.exit(0)));
		panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		add(panel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);;
	}

}
