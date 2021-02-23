package frame;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class MenuRegistrationFrame extends BaseFrame{
	private JTextField menuField;
	private JComboBox cuisineBox,priceBox,volumeBox;
	private int cuisineNo;
	
	public MenuRegistrationFrame(String title) {
		super(400,300,title);
		setLayout(new GridLayout(5,2));
		
		menuField = new JTextField();
		
		cuisineBox = new JComboBox<String>();
		priceBox = new JComboBox<Integer>();
		volumeBox = new JComboBox<Integer>();
	
		try (ResultSet rs = executeQuery("select cuisineName from cuisine")){
				while(rs.next()) {
					cuisineBox.addItem(rs.getString("cuisineName"));
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		for(int price=1000;price<=12000;price+=500) {
			priceBox.addItem(price);
		}
		for(int i=0;i<=50;i++) {
			volumeBox.addItem(i);
		}
		
		cuisineBox.addActionListener(e->setCuisineNo());
		
		add(createLabel("종류", new Font("굴림",Font.BOLD,13)));
		add(cuisineBox);
		add(createLabel("메뉴명", new Font("굴림",Font.BOLD,13)));
		add(menuField);
		add(createLabel("가격", new Font("굴림",Font.BOLD,13)));
		add(priceBox);
		add(createLabel("조리가능수량", new Font("굴림",Font.BOLD,13)));
		add(volumeBox);
		add(createButton("등록", e->regist()));
		add(createButton("닫기", e->setVisible(false)));
	}
	
	public boolean isEmpty() {
		if(menuField.getText().equals(""))
			return true;
			
		return false;
	}
	
	public void regist() {
		if(isEmpty()) {
			showErrorMessage("메뉴명을 입력해주세요.", "Message");
			return;
		}
		
		showInformationMessasge("메뉴가 등록되었습니다.", "Message");
		executeNoneQuery("insert into meal (`cuisineNo`,`mealName`,`price`,`maxCount`,`todayMeal`) values (?,?,?,?,?)",cuisineNo,menuField.getText(),priceBox.getSelectedItem(),volumeBox.getSelectedItem(),0);
	}
	
	private void setCuisineNo() {
		switch(String.valueOf(cuisineBox.getSelectedItem())) {
		case "한식":
			cuisineNo = 1;
			break;
		case "중식":
			cuisineNo = 2;
			break;
		case "일식":
			cuisineNo = 3;
			break;
		case "양식":
			cuisineNo = 4;
			break;
		}
	}
	
	public int getCuisineNo() {
		return cuisineNo;
	}
	
	public JTextField getMenuField() {
		return menuField;
	}
	
	public JComboBox<String> getCuisineBox(){
		return cuisineBox;
	}
	
	public JComboBox<Integer> getPriceBox(){
		return priceBox;
	}
	
	public JComboBox<Integer> getVolumeBox(){
		return volumeBox;
	}
	
}
