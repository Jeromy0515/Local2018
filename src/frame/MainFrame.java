package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame{

	public MainFrame(){
		super(300, 300, "����");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.add(createButton("������", e->new EmployeeRegistrationFrame().setVisible(true)));
		panel.add(createButton("�����", e->new CouponIssuanceFrame().setVisible(true)));
		panel.add(createButton("������", e->{}));
		panel.add(createButton("����", e->System.exit(0)));
		panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		add(panel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
