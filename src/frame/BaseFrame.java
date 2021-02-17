package frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class BaseFrame extends JFrame{
	static Connection connection = null;
	
	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal?serverTimezone=UTC","user","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public BaseFrame(int width,int height,String title){
		setSize(width,height);
		setTitle(title);
		setLocationRelativeTo(null);
	}
	
	public BaseFrame() {
	}
	
	public static JLabel createLabel(String caption,Font font) {
		JLabel label = new JLabel(caption);
		label.setFont(font);
		return label;
	}
	
	
	public JButton createButton(String title,ActionListener act) {
		JButton button = new JButton(title);
		button.addActionListener(act);
		return button;
	}
	
	public static <T extends Component> T setComponentSize(T comp,int width,int height) {
		comp.setPreferredSize(new Dimension(width,height));
		return comp;
	}
	
	public static void getSqlResults(String sql,Object...objects) {
		ResultSet rs = null;
		ArrayList<Object> dataList = new ArrayList<Object>();
		try (PreparedStatement pst = connection.prepareStatement(sql)){
			for(int i=0;i<objects.length;i++) {
				pst.setObject(i+1, objects[i]);
			}
			rs = pst.executeQuery();
			while(rs.next()) {
//				dataList.add(rs.getObject(ABORT, null));
			}
		} catch (SQLException e) {

		}
		
	}
	
	
	public static void showErrorMessage(String caption, String txt) {
		JOptionPane.showMessageDialog(null, caption,txt,JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showInformationMessasge(String caption, String txt) {
		JOptionPane.showMessageDialog(null, caption,txt,JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
	
	
}
