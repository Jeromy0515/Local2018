package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MealSetting {
	static Statement statement = null;
	static Connection connection = null;
	
	static {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?serverTimezone=UTC&allowLoadLocalInfile=true","root","1234");
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		execute("drop database if exists `meal`;");
		execute("CREATE SCHEMA IF NOT EXISTS `meal` DEFAULT CHARACTER SET utf8 ;");
		
		execute("CREATE TABLE IF NOT EXISTS `meal`.`member` (\r\n"
				+ "  `memberNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `memberName` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `passwd` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`memberNo`))\r\n"
				+ "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `meal`.`cuisine` (\r\n"
				+ "  `cuisineNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `cuisineName` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`cuisineNo`))\r\n"
				+ "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `meal`.`meal` (\r\n"
				+ "  `mealNo` INT(11) NOT NULL,\r\n"
				+ "  `cuisineNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mealName` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `price` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `maxCount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `todayMeal` TINYINT(1) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`mealNo`))\r\n"
				+ "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `meal`.`orderlist` (\r\n"
				+ "  `orderNo` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `cuisineNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `mealNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `memberNo` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `orderCount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `amount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `orderDate` DATETIME NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`orderNo`))\r\n"
				+ "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");
		
		execute("set global local_infile=1");
		
		execute("use meal");
		execute("drop user if exists 'user'@'%'");
		execute("create user 'user'@'%' identified by '1234'");
		execute("grant select, insert, delete, update on `meal`.* to 'user'@'%';");
		execute("flush privileges");
		
		for(String table:"cuisine,meal,member,orderlist".split(",")) {
			execute("load data local infile 'table/"+table+".txt' into table "+table+
					" fields terminated by '\t' lines terminated by '\n' ignore 1 lines");
		}
		
	}
	
	static void execute(String sql) {
		try {
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
