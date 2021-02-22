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
	private boolean check = false;
	private JComboBox<String> cuisineBox;
	public MenuManagementFrame() {
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
				tableAct();
			}
			
		});
//		JCheckBox checkBox = new JCheckBox();
//		checkBox.setSelected(B);
//		table.getColumn(" ").setCellEditor(new DefaultCellEditor(checkBox));
//		table.getColumn(" ").setCellRenderer();
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
		
		JPanel panel = new JPanel();
		panel.add(selectionBtn);
		panel.add(createLabel("종류:", new Font("굴림",Font.BOLD,13)));
		panel.add(cuisineBox);
		panel.add(createButton("검색", null));
		panel.add(createButton("수정", null));
		panel.add(createButton("삭제", null));
		panel.add(createButton("오늘의메뉴선정", null));
		panel.add(createButton("닫기", e->setVisible(false)));
		
		
		add(panel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		
	}
	
	private void selectAll() {
		for(int i=0;i<table.getRowCount();i++) {
			table.setValueAt(true, i, 0);
		}
		check = false;
		selectionBtn.setEnabled(check);
	}
	
	private void tableAct() {
		if(!check) {
			check = true;
			for(int i=0;i<table.getRowCount();i++) {
				if(Boolean.valueOf(table.getValueAt(i, 0).toString())) {
					selectionBtn.setEnabled(check);
					return;
				}
			}
		}
	}
	
	
	private void search() {
		
	}
	
	private void update() {
		
	}
	
	private void delete() {
		
	}
	
	private void selectTodayMeal() {
		
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
