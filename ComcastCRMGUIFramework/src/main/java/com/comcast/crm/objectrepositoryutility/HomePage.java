package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	@FindBy(linkText = "Products")
	private WebElement ProductLink;
	
	public WebElement getProductLink() {
		return ProductLink;
	}

	@FindBy(linkText = "Organizations")
	private WebElement orgLink;
	
	@FindBy(linkText = "Contacts")
	private WebElement Contactlnk;
	
	@FindBy(linkText = "Campaigns")
	private WebElement Campaignlnk;
	
	@FindBy(linkText = "More")
	private WebElement morelnk;
	
	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement adminImg;
	
	@FindBy(linkText = "Sign Out")
	private WebElement signOutlnk;
	
	public WebElement getOrgLink() {
		return orgLink;
	}

	public WebElement getContactlnk() {
		return Contactlnk;
	}
	
	public void navigateToCampaignPage()
	{
		Actions act=new Actions(driver);
		act.moveToElement(morelnk).perform();
		Campaignlnk.click();
	}
	
	public void logOut()
	{
		Actions act=new Actions(driver);
		act.moveToElement(adminImg).perform();
		signOutlnk.click();
	}
	
}
