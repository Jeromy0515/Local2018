package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel.add(createLabel("사원번호:",null));
		panel.add(numField);
		panel.add(createLabel("사 원 명:",null));
		panel.add(nameField);
		panel.add(createLabel("패스워드:",null));
		panel.add(passwdField);
		panel.add(createLabel("패스워드 재입력:",null));
		panel.add(passwdField2);
		panel.add(createButton("등록", e->regist()));
		panel.add(createButton("닫기", e->setVisible(false)));
		
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
		
		if(isEmpty()) {
			showErrorMessage("항목 누락", "Message");
			return;
		}

		
		if(isDifferent()) {
			showErrorMessage("패스워드 확인 요망", "Message");
			return;
		}
			
		
	}
	
}
