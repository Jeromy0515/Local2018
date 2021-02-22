package frame;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagementFrame extends BaseFrame{
	public ManagementFrame() {
		super(588,470,"관리");
		setLayout(new FlowLayout());
		add(createButton("메뉴등록", e->new MenuRegistrationFrame().setVisible(true)));
		add(createButton("메뉴관리", e->new MenuManagementFrame().setVisible(true)));
		add(createButton("결제조회", null));
		add(createButton("메뉴별주문현황", null));
		add(createButton("종료", e->setVisible(false)));
		add(new JLabel(new ImageIcon("image/main.jpg")));
	}
}
