package com.comcast.crm.orgtest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class CreateOrgWithPhoneNumberTest {

	public static void main(String[] args) throws Throwable 
	{
		
		FileInputStream fis=new FileInputStream("./configAppData/commondata.properties");
		Properties pobj=new Properties();
		pobj.load(fis);
		
		String BROWSER=pobj.getProperty("browser");
		String URL=pobj.getProperty("url");
		String USERNAME=pobj.getProperty("username");
		String PASSWORD=pobj.getProperty("password");

//		//read common data from json file 
//		//step1 : parse Json Physical file in to Java Object using JsonParse class
//		JSONParser parser=new JSONParser();
//		Object obj= parser.parse(new FileReader("E:\\seleniumfile\\data\\appCommondata.json"));
//
//		//step2 : convert java object in to JSONObject using down casting
//		JSONObject map=(JSONObject)obj;

		//step 3: get the value from xml  file

//		String URL = test.getParameter("url");
//		String BROWSER =test.getParameter("browser");
//		String USERNAME =test.getParameter("username");
//		String PASSWORD =test.getParameter("password;");

		//read common data from CMD line
		//		String URL = System.getProperty("url");
		//		String BROWSER = System.getProperty("browser");
		//		String USERNAME = System.getProperty("username");
		//		String PASSWORD = System.getProperty("password");  

		//generate the random number
		Random random=new Random();
		int randomInt = random.nextInt(1000);

		//read testscript data from excel file
		FileInputStream fis1=new FileInputStream("./testdata/testscriptdata.xlsx");
		Workbook wb=WorkbookFactory.create(fis1);
		Sheet sh=wb.getSheet("org");
		Row row=sh.getRow(7);
		String orgName=row.getCell(2).toString()+randomInt;
		String phoneNumber=row.getCell(3).getStringCellValue();
		
		wb.close();

		/*
		 * Scanner s=new Scanner(System.in); System.out.println("Enter the Browser");
		 * String browser=s.next();
		 */

		WebDriver driver=null;

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
		driver = new ChromeDriver();
		//login to app
		driver.get(URL);

		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		//Navigate to Organization module
		driver.findElement(By.linkText("Organizations")).click();
		
		//click on "create organization" button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		
		//Enter all details & create new organization
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		driver.findElement(By.id("phone")).sendKeys(phoneNumber);
		System.out.println(phoneNumber);
		driver.findElement(By.xpath("(//input[@title=\"Save [Alt+S]\"])[1]")).click();
		//Thread.sleep(2000);
	
		//verify Header PhoneNumber info Expected Result
		String actphoneNumber=driver.findElement(By.id("dtlview_Phone")).getText();
		//Thread.sleep(2000);
		if(actphoneNumber.equals(phoneNumber))
		{
			System.out.println(phoneNumber+ "is verified==PASS");
		}
		else
		{
			System.out.println(phoneNumber+ "is not verified==FAIL");
		}	
		//logout
		driver.quit();
	}
}
