package practice.test;

import org.testng.annotations.Test;

import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.crm.generic.baseutility.BaseClass;

/**
 * test class for Contact module
 * @author Gaurav
 */
public class SearchContactTest extends BaseClass{
	/**
	 * Scenario : login()=>navigateContact==>createcontact()==verify
	 */
	@Test
	public void searchContactTest()
	{
		/* step 1 : login to app*/
	   LoginPage lp=new LoginPage(driver);
	   lp.loginToapp("url", "username", "password");
	}

}
