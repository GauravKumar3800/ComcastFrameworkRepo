package com.comcast.crm.orgtest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class DeleteOrgTest {

	public static void main(String[] args) throws Throwable 
	{
		//create object
		FileUtility flib=new FileUtility();
		ExcelUtility elib=new ExcelUtility();
		JavaUtility jlib=new JavaUtility();
        WebDriverUtility wlib=new WebDriverUtility();
        
        //read common data from Json file
		String BROWSER=flib.getDataFromPropertiesFile("browser");
		String URL=flib.getDataFromPropertiesFile("url");
		String USERNAME=flib.getDataFromPropertiesFile("username");
		String PASSWORD=flib.getDataFromPropertiesFile("password");


		//read testscript data from excel file
		String orgName=elib.getDataFromExcel("org", 10, 2) + jlib.getRandomNumber();


		WebDriver driver=null;

		if(BROWSER.equalsIgnoreCase("chrome"))
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
		driver = new ChromeDriver();

		//step 1 :login to app
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(URL);

		LoginPage lp=new LoginPage(driver);
		lp.loginToapp("url","admin", "admin");

		//Step 2 :Navigate to Organization module

		HomePage hp=new HomePage(driver);
		hp.getOrgLink().click();

		//step 3 :click on "create organization" button

		OrganizationsPage cnp=new OrganizationsPage(driver);
		cnp.getCreateNewOrgBtn().click();

		//step 4:Enter all details & create new organization

		CreatingNewOrganizationPage cnop=new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgName);

		//step 5:verify Header Message Expected Result
		OrganizationInfoPage oip=new OrganizationInfoPage(driver);
		String actOrgName=oip.getHeaderMsg().getText();
		if(actOrgName.contains(orgName))
		{
			System.out.println(orgName + " name is verified ==PASS");
		}else
		{
			System.out.println(orgName + " name is not verified ==FAIL");

		}
		
		//go back to Organizations page
		
		hp.getOrgLink().click();

		//search for organization
		cnp.getSearchEdt().sendKeys(orgName);
		wlib.select(cnp.getSearchDD(), "Organization Name");
		cnp.getSearchbtn().click();
		
		//In dynamic webtable select & delete org
		driver.findElement(By.xpath("//a[text()='"+orgName+"']/../../td[8]/a[text()='del']")).click();

		//step 6:logout
		hp.logOut();
		driver.quit();
	}

}
