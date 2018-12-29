package com.ibm.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ibm.pages.UserPage;
import com.ibm.utilities.ExcelReader;
import com.ibm.utilities.ExcelUtil;
import com.ibm.utilities.PropertiesFileHandler;

public class BaseTest extends ExcelReader{
	WebDriverWait wait;
	WebDriver driver;	 
    @Test(dataProvider="CategoryData")
    
    public void testcase9(String CategoryName, String MegaTagTitle, String SortOrder) throws InterruptedException, IOException, SQLException{
    	
    	FileInputStream file = new FileInputStream("./TestData/data.properties");
    	Properties prop = new Properties();
    	prop.load(file);
    	String url = prop.getProperty("url");
    	String username = prop.getProperty("user");
    	String password = prop.getProperty("password");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 60);
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Login login = new Login(driver, wait);
		driver.get(url);
		
		login.enterEmailAddress(username);
		login.enterPassword(password);
		login.clickOnLogin();
		
		WebElement systemEle=driver.findElement(By.linkText("Catalog"));
		systemEle.click();
		
		WebElement usersEle=driver.findElement(By.linkText("Categories"));
		usersEle.click();
		
		Thread.sleep(3000);
		WebElement newUserEle=driver.findElement(By.cssSelector("a.btn.btn-primary"));
		newUserEle.click();
		
		WebElement categoryName=driver.findElement(By.name("name"));
		categoryName.sendKeys(CategoryName);
		
		WebElement metaTagName=driver.findElement(By.name("tag_title"));
		metaTagName.sendKeys(MegaTagTitle);
		
		WebElement sortOrder=driver.findElement(By.name("sort"));
		sortOrder.sendKeys(SortOrder);
		
		WebElement statusEle=driver.findElement(By.name("status"));
		Select select=new Select(statusEle);
		select.selectByVisibleText("Enabled");
		
		WebElement toTopEle=driver.findElement(By.id("toTop"));
		toTopEle.click();
		Thread.sleep(3000);
		WebElement saveEle=driver.findElement(By.cssSelector("button.btn.btn-primary"));
		saveEle.click();
		
		//Assert.assertTrue(driver.findElement(By.linkText("oats")).isDisplayed());
		
		WebElement logOutEle=driver.findElement(By.cssSelector("i.fa.fa-sign-out"));
		logOutEle.click();
		
		Thread.sleep(3000);
		
		db_connect("as_category","name=\"oats\"");
		
		driver.get("https://atozgroceries.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		UserPage user = new UserPage(driver, wait);
		
		WebElement loginEle=driver.findElement(By.linkText("Login"));
		loginEle.click();
		
		user.userName("7675058941");
		user.enterPassword("456789");
		user.clickOnLogin();
		Thread.sleep(3000);
		WebElement categoryEle=driver.findElement(By.cssSelector("#categories-menu > ul.menu > li.menu-item > span.click-categories.flaticon-bars"));
		categoryEle.click();
//the below will fail if oats are not added
		WebElement fruitsEle=driver.findElement(By.linkText("oats"));
		fruitsEle.click();
		//("as_category","name=\"oats\"");
		//Assert.assertTrue(driver.findElement(By.linkText("oats")).isDisplayed());
		
    }
  
    @DataProvider(name="CategoryData")
    public Object[][] categoryData() throws IOException {
        return ExcelReader.DataTable("./TestData/TestData.xlsx","Category");
    }

}