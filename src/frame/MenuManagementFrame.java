package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MenuManagementFrame extends BaseFrame{
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton selectionBtn;
	private JComboBox<String> cuisineBox;
	public MenuManagementFrame(JFrame frame) {
		super(600,700,"메뉴 관리");
		model = new DefaultTableModel(null," ,mealName,price,maxCount,todayMeal".split(",")){
			@Override
			public Class<?> getColumnClass(int column){
				switch(column) {
				case 0:
					 return Boolean.class;
				case 1:
					 return String.class;
				case 2:
					 return Integer.class;
				case 3:
					 return Integer.class;
				case 4:
					 return Character.class;
				default:
					 return String.class;
				}
			}
		};
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(getSelectedCellCount() < table.getRowCount()) {
					selectionBtn.setEnabled(true);
				}
			}
			
		});
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i=1;i<table.getColumnCount();i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		cuisineBox = new JComboBox<String>();
		try (ResultSet rs = executeQuery("select cuisineName from cuisine")){
			while(rs.next()) {
				cuisineBox.addItem(rs.getString("cuisineName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cuisineBox.addActionListener(e->setTable());
		
		table.getColumn(" ").setPreferredWidth(10);
		setTable();
		
		
		
		selectionBtn = createButton("모두 선택", e->selectAll());
		
		JPanel northPanel = new JPanel();
		northPanel.add(selectionBtn);
		northPanel.add(createLabel("종류:", new Font("굴림",Font.BOLD,13)));
		northPanel.add(cuisineBox);
		northPanel.add(createButton("검색", e->search()));
		northPanel.add(createButton("수정", e->update()));
		northPanel.add(createButton("삭제", e->delete()));
		northPanel.add(createButton("오늘의메뉴선정", e->selectTodayMeal()));
		northPanel.add(createButton("닫기", e->openFrame(frame)));
		
		
		add(northPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		
	}
	
	private void selectAll() {
		for(int i=0;i<table.getRowCount();i++) {
			table.setValueAt(true, i, 0);
		}
		selectionBtn.setEnabled(false);
	}

	private void search() {
		System.out.println();
	}
	
	private void update() {
		
		if(getSelectedCellCount() == 0) {
			showErrorMessage("수정할 메뉴를 선택해 주세요.", "Message");
			return;
		}
		
		if(getSelectedCellCount() > 1) {
			showErrorMessage("하나씩 수정가능합니다.", "Message");
			return;
		}
		
		
		MenuRegistrationFrame menuUpdateFrame = new MenuRegistrationFrame(this,"메뉴 수정") {
			
			@Override
			public void regist() {
				if(isEmpty()) {
					showErrorMessage("메뉴명을 입력해주세요.", "Message");
					return;
				}
				
				showInformationMessasge("메뉴가 수정되었습니다.", "Message");
				for(int row=0;row<table.getRowCount();row++) {
					if(Boolean.valueOf(table.getValueAt(row,0).toString())) {
						executeNoneQuery("update `meal` set cuisineNo=?,mealName=?,price=?,maxCount=? where mealName=?",getCuisineBox().getSelectedIndex()+1,getMenuField().getText(),getPriceBox().getSelectedItem(),getVolumeBox().getSelectedItem(),(String)table.getValueAt(row, 1)); 
						break;
					}
				}
				setTable();
			}
		};
		menuUpdateFrame.getCuisineBox().setSelectedItem(cuisineBox.getSelectedItem());
		for(int row=0;row<table.getRowCount();row++) {
			if(Boolean.valueOf(table.getValueAt(row,0).toString())) {
				menuUpdateFrame.getMenuField().setText((String)table.getValueAt(row,1));
				menuUpdateFrame.getPriceBox().setSelectedItem(table.getValueAt(row, 2));
				menuUpdateFrame.getVolumeBox().setSelectedItem(table.getValueAt(row, 3));
				break;
			}
		}
		menuUpdateFrame.setVisible(true);
	}
	
	private void delete() {
		if(getSelectedCellCount() == 0) {
			showErrorMessage("삭제할 메뉴를 선택해주세요.", "Message");
			return;
		}
		
		for(int row=0;row<table.getRowCount();row++) {
			if(Boolean.valueOf(table.getValueAt(row, 0).toString())) {
				executeNoneQuery("delete from `meal` where mealName=?", table.getValueAt(row, 1));
			}
		}
		setTable();
	}
	
	private void selectTodayMeal() {
		if(getSelectedCellCount() > 25) {
			showErrorMessage("25개를 초과할 수 없습니다.", "Message");
			return;
		}
		
		for(int row=0;row<table.getRowCount();row++) {
			if(Boolean.valueOf(table.getValueAt(row, 0).toString())) {
				table.setValueAt('Y', row, 4);
				executeNoneQuery("update `meal` set todayMeal='1' where mealName = ?", table.getValueAt(row, 1));
			}
		}

	}
	
	private int getSelectedCellCount() {
		int cnt = 0;
		for(int row=0;row<table.getRowCount();row++) {
			if(Boolean.valueOf(table.getValueAt(row, 0).toString())) {
				cnt++;
			}
		}
		return cnt;
	}
	
	
	private void setTable() {
		model.setNumRows(0);
		try (ResultSet rs = executeQuery("select mealName,price,maxCount,todayMeal from meal where cuisineNo=?;",cuisineBox.getSelectedIndex()+1)){
			while(rs.next()) {
				model.addRow(new Object[] {false,rs.getString("mealName"),rs.getInt("price"),rs.getInt("maxCount"),rs.getInt("todayMeal") == 1 ? 'Y' : 'N'});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
}
