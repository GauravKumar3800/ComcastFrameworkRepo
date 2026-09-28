package com.crm.generic.baseutility;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.generic.databaseutility.DatabaseUtility;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class BaseClass {

	/*create object */
	public DatabaseUtility dbLib=new DatabaseUtility();
	public FileUtility flib=new FileUtility();
	public ExcelUtility elib=new ExcelUtility();
	public JavaUtility jlib=new JavaUtility();
	public WebDriverUtility wlib=new WebDriverUtility();
	public WebDriver driver=null;
	public static WebDriver sdriver=null;


	@BeforeSuite(groups= {"smokeTest","regressionTest"})
	public void configBS() throws Throwable
	{
		System.out.println("connect to DB ,Report config");
		dbLib.getDbconnection();
	}
	
	@BeforeClass(groups= {"smokeTest","regressionTest"})
	// @Parameters("BROWSER")
	//public void configBC(String browser) throws Throwable
	public void configBC() throws Throwable
	{
		System.out.println("===Launch the browser===");
		//String BROWSER = browser;
		String BROWSER=flib.getDataFromPropertiesFile("browser");

		if(BROWSER.equals("chrome"))
		{
			driver=new ChromeDriver();
		}	else if(BROWSER.equals("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else if(BROWSER.equals("edge")) 
		{
			driver=new EdgeDriver();
		}
		else
		{
			driver= new ChromeDriver();
		}
		sdriver = driver;

		UtilityClassObject.setDriver(driver);
	}

	@BeforeMethod(groups= {"smokeTest","regressionTest"})
	public void configBM() throws Throwable
	{
		System.out.println("==login==");
		String URL=flib.getDataFromPropertiesFile("url");
		String USERNAME=flib.getDataFromPropertiesFile("username");
		String PASSWORD=flib.getDataFromPropertiesFile("password");
		LoginPage lp=new LoginPage(driver);
		lp.loginToapp(URL,USERNAME,PASSWORD);

	}

	@AfterMethod(groups= {"smokeTest","regressionTest"})
	public void configAfterMethod()
	{
		System.out.println("==logout==");
		HomePage hp=new HomePage(driver);
		hp.logOut();
	}

	@AfterClass(groups= {"smokeTest","regressionTest"})
	public void configAC() 
	{
		System.out.println("==close the browser==");
		driver.quit();
	}

	@AfterSuite(groups= {"smokeTest","regressionTest"})
	public void configAS() throws Throwable
	{
		System.out.println("====close DB , Report backup=====");
		dbLib.closeDbconnection();

		//	report.flush();
	}
}
