package frame;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EmployeeRegistrationFrame extends BaseFrame{
	private JTextField numField,nameField;
	private JPasswordField passwdField,passwdField2;
	
	public EmployeeRegistrationFrame() {
		super(400, 300, "사원등록");
		
		numField = new JTextField();
		numField.setEnabled(false);
		nameField = new JTextField();
		passwdField = new JPasswordField();
		passwdField2 = new JPasswordField();
		
		try (ResultSet rs = executeQuery("select memberNo+1 as memberNo from member order by memberNo desc limit 1;")){
			if(rs.next())
				numField.setText(Integer.toString(rs.getInt("memberNo")));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pstClose();
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel.add(createLabel("사원번호:",new Font("굴림",Font.BOLD,13)));
		panel.add(numField);
		panel.add(createLabel("사 원 명:",new Font("굴림",Font.BOLD,13)));
		panel.add(nameField);
		panel.add(createLabel("패스워드:",new Font("굴림",Font.BOLD,13)));
		panel.add(passwdField);
		panel.add(createLabel("패스워드 재입력:",new Font("굴림",Font.BOLD,13)));
		panel.add(passwdField2);
		panel.add(createButton("등록", e->registBtnAct()));
		panel.add(createButton("닫기", e->{setVisible(false);}));
		
		add(panel);
		
	}
	
	
	
	
	
	private boolean isDifferent() {
		if(!passwdField.getText().equals(passwdField2.getText()))
			return true;
		
		return false;
	}
	
	
	
	
	private boolean isEmpty() {
		if(nameField.getText().equals("") || passwdField.getText().equals("") || passwdField2.getText().equals(""))
			return true;
		
		return false;
	}
	
	
	
	
	private void regist() {
		executeNoneQuery("insert into `member` values(?,?,?)", numField.getText(),nameField.getText(),passwdField.getText());
	}
	
	
	
	
	
	private void registBtnAct() {
		
		if(isEmpty()) {
			showErrorMessage("항목 누락", "Message");
			return;
		}

		if(isDifferent()) {
			showErrorMessage("패스워드 확인 요망", "Message");
			return;
		}
			
		showInformationMessasge("사원이 등록되었습니다.", "Message");
		regist();
		
	}
	
	
	
}
