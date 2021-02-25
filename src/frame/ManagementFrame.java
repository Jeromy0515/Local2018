package frame;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagementFrame extends BaseFrame{
	public ManagementFrame(MainFrame frame) {
		super(588,470,"관리");
		setLayout(new FlowLayout());
		add(createButton("메뉴등록", e->openFrame(new MenuRegistrationFrame(this,"신규 메뉴 등록"))));
		add(createButton("메뉴관리", e->openFrame(new MenuManagementFrame(this))));
		add(createButton("결제조회", e->openFrame(new PaymentRetrieveFrame(this))));
		add(createButton("메뉴별주문현황", e->openFrame(new OrderStatusFrame(this))));
		add(createButton("종료", e->openFrame(frame)));
		add(new JLabel(new ImageIcon("image/main.jpg")));
	}
}
