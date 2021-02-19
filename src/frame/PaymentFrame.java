package frame;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class PaymentFrame extends BaseFrame{
	private ArrayList<JButton> btnList;
	private JComboBox<Integer> memberBox;
	private JLabel priceLabel;
	private JPanel menuBtnPanel,loginPanel;
	private JTextField menuField,volumeField;
	private JPasswordField passwdField;
	private DefaultTableModel model;
	private JTable table;
	private	int price = 0;
	private int cuisineNumber;
	
	public PaymentFrame(String title) {
		super(1050,430,"결제");
		
		switch(title) {
			case "한식":
				cuisineNumber = 1;
				break;
			case "중식":
				cuisineNumber = 2;
				break;
			case "일식":
				cuisineNumber = 3;
				break;
			case "양식":
				cuisineNumber = 4;
				break;
		}
		
		btnList = new ArrayList<JButton>();
		
		memberBox = new JComboBox<Integer>();
		try (ResultSet rs = executeQuery("select memberNo from member")){
			while(rs.next()) {
				memberBox.addItem(rs.getInt("memberNo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pstClose();
		}
		
		passwdField = new JPasswordField();
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(2,2));
		loginPanel.add(createLabel("사원번호", new Font("굴림",Font.BOLD,13)));
		loginPanel.add(memberBox);
		loginPanel.add(createLabel("패스워드", new Font("굴림",Font.BOLD,13)));
		loginPanel.add(passwdField);
		
		setLayout(null);
		
		JLabel mainLabel = createLabel(title, new Font("굴림",Font.BOLD,25));
		mainLabel.setBounds(470,10,60,50);

		JLabel totalPriceLabel = createLabel("총결제금액:", new Font("굴림",Font.BOLD,20));
		totalPriceLabel.setHorizontalAlignment(JLabel.LEFT);
		
		priceLabel = createLabel(price+"원", new Font("굴림",Font.BOLD,20));
		priceLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		JPanel labelPanel = setComponentSize(new JPanel(), 200, 60);
		labelPanel.setLayout(new GridLayout(1,2));
		labelPanel.setBounds(620,50,400,50);
		labelPanel.add(totalPriceLabel);
		labelPanel.add(priceLabel);
		
		model = new DefaultTableModel(null,"상품번호,품명,수량,금액".split(","));
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable)e.getSource();
				if(e.getClickCount() == 2) {
					Point point = e.getPoint(); //getPoint 마우스 이벤트가 발생한 좌표를 얻어옴
					int i = table.rowAtPoint(point); //rowAtPoint 좌표의 row값 반환
					if(i>=0) {
						int row = table.convertRowIndexToModel(i); //실제 모델에 저장되어 있는 인덱스를 리턴
						setMenuBtnEnabled((String)table.getValueAt(row, 1), true);
						price -= Integer.parseInt(String.valueOf(table.getValueAt(row, 2))) * Integer.parseInt(String.valueOf(table.getValueAt(row, 3)));
						priceLabel.setText(price+"원");
						model.removeRow(row);
					}
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(620, 100, 400, 200);
		
		menuField = new JTextField(20);
		volumeField = new JTextField(4);
		menuField.setEnabled(false);
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new FlowLayout());
		fieldPanel.add(createLabel("선택품명:", new Font("굴림",Font.BOLD,13)));
		fieldPanel.add(menuField);
		fieldPanel.add(createLabel("수량:", new Font("굴림",Font.BOLD,13)));
		fieldPanel.add(volumeField);
		fieldPanel.setBounds(620,300,400,30);
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(setComponentSize(createButton("입력", e->inputBtnAct()), 100, 35));
		btnPanel.add(setComponentSize(createButton("결제", e->payBtnAct()), 100, 35));
		btnPanel.add(setComponentSize(createButton("닫기", e->setVisible(false)), 100, 35));
		btnPanel.setBounds(620,330,400,50);
		
		
		menuBtnPanel = new JPanel();
		menuBtnPanel.setLayout(new GridLayout(0,5));
		setMenuBtn();
		menuBtnPanel.setBounds(10, 100, 600, 200);
		

		add(mainLabel);
		add(labelPanel);
		add(scrollPane);
		add(fieldPanel);
		add(btnPanel);
		add(menuBtnPanel);
		
	}
	
	
	
	
	private boolean isVolume() {
		try {
			if(volumeField.getText().equals("") || Integer.parseInt(volumeField.getText()) == 0)
				return false;
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	
	
	
	private boolean isOverMaxCount() {
		try (ResultSet rs = executeQuery("select maxCount from meal where mealName=?", menuField.getText())){
			if(rs.next()) {
				if(Integer.parseInt(volumeField.getText()) > rs.getInt("maxCount")) 
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	private boolean isSelectMenu() {
		if(menuField.getText().equals("")) 
			return false;
		
		return true;
	}
	
	
	
	private void inputBtnAct() {
		if(!isSelectMenu()) {
			showErrorMessage("메뉴를 선택해 주세요.", "Message");
			return;
		}
		
		if(!isVolume()) {
			showErrorMessage("수량을 입력해주세요.", "Message");
			return;
		}
		
		if(isOverMaxCount()) {
			showErrorMessage("조리가능수량이 부족합니다.", "Message");
			return;
		}
		
		input();
		
	}
	
	
	private void input() {
		try (ResultSet rs = executeQuery("select mealNo,price from meal where mealName=?", menuField.getText())){
			if(rs.next()) {
				model.addRow(new Object[] {rs.getInt("mealNo"),menuField.getText(),volumeField.getText(),rs.getInt("price")*Integer.parseInt(volumeField.getText())});
				price += rs.getInt("price")*Integer.parseInt(volumeField.getText());
			}
			priceLabel.setText(price+"원");
			setMenuBtnEnabled(menuField.getText(), false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	

	private void setMenuBtnEnabled(String txt,boolean b) {
		for(int i=0;i<btnList.size();i++) {
			if(btnList.get(i).getText().replaceAll("[^가-힣]", "").equals(txt))
				btnList.get(i).setEnabled(b);
		}

	}
	
	
	
	
	private void payBtnAct() {
		if(showYes_No_Dialog(loginPanel, "결제자 인증")==0) {
			if(isMember()) {
				showInformationMessasge("결제가 완료되었습니다.\n식권을 출력합니다.", "Message");
				for(int i=0;i<table.getRowCount();i++) {
						executeNoneQuery("update meal set maxCount = maxCount - ? where mealName = ?", table.getValueAt(i, 2),table.getValueAt(i, 1));			
						executeNoneQuery("insert into orderlist (`cuisineNo`, `mealNo`, `memberNo`, `orderCount`, `amount`, `orderDate`) values(?,?,?,?,?,?)", cuisineNumber,table.getValueAt(i, 0),memberBox.getSelectedItem(),table.getValueAt(i, 2),table.getValueAt(i, 3),CouponIssuanceFrame.time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
			}
			else
				showErrorMessage("패스워드가 일치하지 않습니다.", "Message");
		}
		
		
	}
	
	
	private boolean isMember() {
		try {
			if(executeQuery("select * from member where memberNo=? and passwd=?", memberBox.getSelectedItem(),passwdField.getText()).next()) 
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pstClose();
		}
		return false;
	}
	
	
	private void setMenuBtn() {
		
		try (ResultSet rs = executeQuery("select m.mealName, m.price from meal as m left join cuisine as c on c.cuisineNo = m.cuisineNo where c.cuisineNo = ? and m.maxCount > 0 and m.todayMeal=1;", cuisineNumber)){
			while(rs.next()) {
				String menuName = rs.getString("mealName");
				JButton button = setComponentSize(createButton(menuName+"("+rs.getInt("price")+")", null), 120, 60);
				btnList.add(button);
				button.addActionListener(e->menuBtnAct(button,menuName));
				menuBtnPanel.add(button);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void menuBtnAct(JButton button,String txt) {
		menuField.setText(txt);
	}
	
	
	
	
	
}
