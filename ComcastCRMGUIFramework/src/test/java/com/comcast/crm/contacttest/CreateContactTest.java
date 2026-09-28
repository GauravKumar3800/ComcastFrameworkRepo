package com.comcast.crm.contacttest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.objectrepositoryutility.ContactPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.crm.generic.baseutility.BaseClass;
/**
 * @author Gaurav
 */
public class CreateContactTest extends BaseClass{

	@Test(groups="smokeTest")
	public void CreateContactTest() throws Throwable 
	{

		/*read testscript data from excel file*/
		String lastName=elib.getDataFromExcel("contact", 1, 2) + jlib.getRandomNumber();

		//Navigate to contact module
		HomePage hp=new HomePage(driver);
		hp.getContactlnk().click();

		//click on "create Contacts" button
		ContactPage cp=new ContactPage(driver);
		cp.getCreateNewOrgBtn().click();

		//Enter all details & create new contact
		CreatingNewContactPage ccp=new CreatingNewContactPage(driver);
		ccp.createContact(lastName);

		//verify Header PhoneNumber info Expected Result
		String actHeader=cp.getHeadermsg().getText();
		boolean status=actHeader.contains(lastName);
		Assert.assertEquals(status, true);
		
		
		//verify lastname info Expected Result
		String actLastName=driver.findElement(By.id("dtlview_Last Name")).getText();
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(actLastName, lastName);
	}

	@Test(groups="regressionTest")
	public void createContactWithsupportDateTest() throws Throwable
	{
		//read testscript data from excel file
		String lastName=elib.getDataFromExcel("contact", 4, 2)+jlib.getRandomNumber();


		//Navigate to contact module
		driver.findElement(By.linkText("Contacts")).click();

		//click on "create Contacts" button
		driver.findElement(By.xpath("//img[@alt=\"Create Contact...\"]")).click();

		//Enter all details & create new Organization

		String startDate=jlib.getSystemDateYYYYDDMM();
		String endDate=jlib.getRequiredDateYYYYDDMM(30);

		driver.findElement(By.name("lastname")).sendKeys(lastName);

		driver.findElement(By.name("support_start_date")).clear();
		driver.findElement(By.name("support_start_date")).sendKeys(startDate);
        Thread.sleep(2000);
		driver.findElement(By.name("support_end_date")).clear();
		driver.findElement(By.name("support_end_date")).sendKeys(endDate);
        Thread.sleep(2000);
        System.out.println(endDate);
		driver.findElement(By.xpath("(//input[@title=\"Save [Alt+S]\"])[1]")).click();

		//verify 

		String actStartDate=driver.findElement(By.id("dtlview_Support Start Date")).getText();
		if(actStartDate.equals(startDate))
		{
			System.out.println(startDate+ " Information is verified==PASS");
		}
		else
		{
			System.out.println(startDate+ "  Information is not verified==FAIL");
		}

		String actEndDate=driver.findElement(By.id("dtlview_Support End Date")).getText();
		if(actEndDate.equals(endDate))
		{
			System.out.println(endDate+ " Information is verified==PASS");
		}
		else
		{
			System.out.println(endDate+ " Information is not verified==FAIL");
		}
	}

	@Test(groups="regressionTest")
	public void createContactWithorgTest() throws Throwable
	{
		//read testscript data from excel file

		String orgName=elib.getDataFromExcel("contact", 7, 2)+jlib.getRandomNumber();
		String contactLastName=elib.getDataFromExcel("contact", 7, 3);


		//Navigate to Organization module
		driver.findElement(By.linkText("Organizations")).click();

		//click on "create organization" button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		//Enter all details & create new organization
		driver.findElement(By.name("accountname")).sendKeys(orgName);

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();



		//verify Header PhoneNumber info Expected Result
		String headerInfo=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(headerInfo.contains(orgName)) {
			System.out.println(orgName + "header verified==PASS");
		}else
		{
			System.out.println(orgName + "header is verified==FAIL");
		}

		//step 5: Navigate to Organization module
		driver.findElement(By.linkText("Contacts")).click();

		//step 6: click on "create Contacts" button
		driver.findElement(By.xpath("//img[@alt=\"Create Contact...\"]")).click();

		//step 7: Enter all details & create new contact
		driver.findElement(By.name("lastname")).sendKeys(contactLastName);
		driver.findElement(By.xpath("//input[@name=\"account_name\"]/following-sibling::img")).click();

		//switch To child window
		wlib.switchToTabOnURL(driver, "module=Accounts");

		driver.findElement(By.name("search_text")).sendKeys(orgName);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.xpath("//a[text()=\'"+orgName+"']")).click();

		//Switch To Parent window
		wlib.switchToTabOnURL(driver, "Contacts&action");

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		Thread.sleep(2000);
		//step 8: verify Header message as Expected Result
		String headerinfo=driver.findElement(By.xpath("//span[@id='dtlview_Last Name']")).getText();
		System.out.println(headerInfo);
		if(headerinfo.contains(contactLastName))
		{
			System.out.println(contactLastName +" header verified==PASS");
		}else
		{
			System.out.println(contactLastName +" header verified==FAIL");
		}
		//verify header orgname info Expected Result
		String actOrgName=driver.findElement(By.id("mouseArea_Organization Name")).getText();
		if(actOrgName.trim().equals(orgName))
		{
			System.out.println(orgName + "information is created==PASS");
		}else
		{
			System.out.println(orgName + "information is not created==FAIL");
		}
	}
}
