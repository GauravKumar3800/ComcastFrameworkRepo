package practice.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class GetProductInfoTest {

	@Test(dataProvider = "getData")
	public void getProductInfoTest(String brandName,String productName) throws InterruptedException
	{
		WebDriver driver =new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.amazon.in/");
		
		//search product
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(brandName,Keys.ENTER);
		
		//capture product info
		String x="//span[text()='"+productName+"']/ancestor::div[@class=\"puisg-col-inner\"]//span[@class=\"a-price-whole\"]";
		String price=driver.findElement(By.xpath(x)).getText();
		System.out.println(price);	
		
		driver.quit();
	}
	@DataProvider
	public Object[][] getData() throws Throwable
	{
		ExcelUtility elib=new ExcelUtility();
		int rowcount=elib.getRowcount("product");
		
		Object[][] objarr=new Object[rowcount][2];
		
		for(int i=0;i<rowcount;i++) {
		objarr[i][0] =elib.getDataFromExcel("product", i+1, 0);
		objarr[i][1] =elib.getDataFromExcel("product", i+1, 1);
		
		}
		return objarr;
	}
}
