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
		super(400, 300, "������");
		
		numField = new JTextField();
		numField.setEnabled(false);
		nameField = new JTextField();
		passwdField = new JPasswordField();
		passwdField2 = new JPasswordField();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel.add(createLabel("�����ȣ:",null));
		panel.add(numField);
		panel.add(createLabel("�� �� ��:",null));
		panel.add(nameField);
		panel.add(createLabel("�н�����:",null));
		panel.add(passwdField);
		panel.add(createLabel("�н����� ���Է�:",null));
		panel.add(passwdField2);
		panel.add(createButton("���", e->regist()));
		panel.add(createButton("�ݱ�", e->setVisible(false)));
		
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
			showErrorMessage("�׸� ����", "Message");
			return;
		}

		
		if(isDifferent()) {
			showErrorMessage("�н����� Ȯ�� ���", "Message");
			return;
		}
			
		
	}
	
}
