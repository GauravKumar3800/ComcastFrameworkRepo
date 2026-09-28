package practice.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateContact_DataProvider_Test2 {
	
	@Test(dataProvider = "getData")
	public void createContactTest(String firstName,String lastName,long phoneNumber)
	{
		System.out.println("FirstName : " +firstName + ", LastName: "+ lastName +",phoneNumber :"+phoneNumber);
	}

	@DataProvider
	public Object[][] getData()
	{
		Object[][] objarr=new Object[3][3];
		objarr[0][0] ="deepak";
		objarr[0][1] ="hr";
		objarr[0][2] =9012345678l;
		
		objarr[1][0] ="sam";
		objarr[1][1] ="hd";
		objarr[1][2] =9123475433l;
		
		objarr[2][0] ="john";
		objarr[2][1] ="steve";
		objarr[2][2] =9534210879l;
		
		return objarr;
	}
}
