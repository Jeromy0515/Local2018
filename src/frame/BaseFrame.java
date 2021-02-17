package frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BaseFrame extends JFrame{
	
	BaseFrame(int width,int height,String title){
		setSize(width,height);
		setTitle(title);
		setLocationRelativeTo(null);
	}
	
	BaseFrame() {
	}
	
	public static JButton createButton(String title,ActionListener act) {
		JButton button = new JButton(title);
		button.addActionListener(act);
		return button;
	}
	
	public static <T extends Component> T setComponentSize(T comp,int width,int height) {
		comp.setPreferredSize(new Dimension(width,height));
		return comp;
	}
	
}
