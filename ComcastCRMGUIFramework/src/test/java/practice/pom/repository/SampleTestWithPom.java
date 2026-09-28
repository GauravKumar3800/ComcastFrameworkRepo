package practice.pom.repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class SampleTestWithPom {

	@FindBy(name="user_name")
	WebElement ele1;
	
	@FindBy(name="user_password")
	WebElement ele2;
	
	@FindAll({@FindBy(id="submitButton1"),@FindBy(xpath="(//input[@value=\"Login\"])[2]")})
	private WebElement ele3;
	
	@Test
	public void sampleTest()
	{
		WebDriver driver=new ChromeDriver();
		driver.get("http://49.249.29.4:8888");
		
		SampleTestWithPom s=PageFactory.initElements(driver,SampleTestWithPom.class);
		
		s.ele1.sendKeys("admin");
		s.ele2.sendKeys("admin");
		s.ele3.click();
	}
}
