package com.ibm.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public static void db_connect(String table, String data) throws SQLException {
		Connection c=DriverManager.getConnection("jdbc:mysql://foodsonfinger.com:3306/foodsonfinger_atozgroceries","foodsonfinger_atoz","welcome@123");
		Statement s=c.createStatement();
		ResultSet rs=s.executeQuery("SELECT * from " +table+ " where " +data);
		while(rs.next()) {
			
			System.out.println("database check for added element:"+rs.getString("name"));
		}
	
		//Assert.assertEquals("Grains", rs.getString("name"));
	}
	public static Object[][] DataTable(String WBLoc, String sheetName) throws IOException
	{
	XSSFWorkbook TestDataWB=new XSSFWorkbook(new FileInputStream(WBLoc));
	XSSFSheet loginSheet=TestDataWB.getSheet(sheetName);
	Object[][] loginInfo=new Object[loginSheet.getPhysicalNumberOfRows()-1][loginSheet.getRow(0).getPhysicalNumberOfCells()];
	int rowcount=loginSheet.getPhysicalNumberOfRows();
	int cellcount=loginSheet.getRow(0).getPhysicalNumberOfCells();
	for(int i=1;i<rowcount;i++)
	{
		for(int j=0;j<cellcount;j++)
		{
			DataFormatter format=new DataFormatter();
			String cellvalue=format.formatCellValue(loginSheet.getRow(i).getCell(j));
			loginInfo[i-1][j]=cellvalue;
		}
	}
	return loginInfo;
	}

}
