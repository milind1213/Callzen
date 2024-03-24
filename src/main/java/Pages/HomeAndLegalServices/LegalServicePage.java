package Pages.HomeAndLegalServices;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import CommonUtility.CommonSelenium;

public class LegalServicePage extends CommonSelenium {
	WebDriver driver;

	public LegalServicePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	private By loginLinkBtn = By.xpath("//div[contains(@class,'nav-item-content') and text()='Log in']");
	private By mobileInput = By.xpath("//input[starts-with(@id,'signUp')]");
	private By otpField = By.xpath("//div[@class='flex items-center justify-center']//input");
	private By loginsubmit = By.id("signUpSubmit");
	private By usernameField = By.xpath("//input[@id='name' and  @name = 'name']");
	private By emailField = By.xpath("//input[@id='email' and  @name = 'email']");
	private By signUpsubmit = By.xpath("//button[text()='Continue']");
	// ----------------------------------------------------------------------------------------------------
	private By moreLocater = By.xpath("//div[contains(text(),'more')]");
	private By getStartedLocater = By.xpath("//div[contains(@class,'3W21B')]//*[@type='button'][text()='Get Started']");
	private By legalServiceloc = By.xpath("//div[text()='Legal Services']");
	private String leagalServices = "//div[@class='item-label' and text()='service']";
	private String selectTvPackage = "(//div[@class='title' and text()='package']/ancestor::div[@class='item']//button[contains(@class,'select-package')])[1]";
	private By selectIdCard = By.xpath("//div[contains(@class,'select') and text()='Select ID card']");
	private String idTypesLocator = "//div[contains(@class,'select__menu-list')]//div[text()='idType']";
	private By idNumberLoc = By.id("cardNumber");
	private By tenantNameloc = By.name("name");
	private By tenantPhoneloc = By.name("mobileNumber");
	private By dobLoc = By.xpath("//input[@class='dob-field']");
	private By tvSummaryTitle = By.xpath("//div[@class='ls-summary-packagedescription desktop-mode']//div[@class='description-container']");
	private By tvTotal = By.xpath("//div[@class='ls-summary-lineitem total']");
	private By tenantGenderLoc = By.xpath("//div[text()='Select Gender']");
	private By SaveAndProceedBtn = By.xpath("//*[contains(text(),'Save & Proceed')]");
	private By helpPopIframe = By.xpath("//iframe[@title='Zendesk Chat widget window']");
	private By helpMinimise = By.xpath("//div[@title='Minimize']");
	private By basicDetailsLabel = By.xpath("//span[contains(text(),'Basic Details')]");
	private By sameAsPermenentAdrs = By.id("sameAsPermanent");

	public void loginSignUpMethod(String mobileNumber, String otp, String username, String email) {
		waitFor(5);
		click(loginLinkBtn);
		log("Clicking on Login Button");

		waitForElementPresence(driver, mobileInput, 5);
		sendKeys(mobileInput, mobileNumber);
		log("Entering PhoneNumber '" + mobileNumber + "'into the PhoneNunumber inputfield ");

		if (isElementPresent(driver, otpField)) {
			sendKeys(otpField, otp);
			log("Entering OTP '" + otp + "'into the OTP inputfield");

			click(loginsubmit);
			log("Clicking on login Button");
		} else {
			waitForElementPresence(driver, usernameField, 5);
			sendKeys(usernameField, username);
			log("Entering UserName'" + username + "' in InputField ");

			waitForElementPresence(driver, emailField, 5);
			sendKeys(emailField, email);
			log("Entering emailid'" + email + "'into the Email inputfield");

			waitForElementToBeClickable(driver, signUpsubmit, 5);
			click(signUpsubmit);
			log("Clicking on login Button");
		}
	}

	public void selectHSServiceType(String service) throws InterruptedException {
		String serviceHSXPath = "//span[text()='" + service + "']";

		if (isElementPresent(By.xpath(serviceHSXPath))) {
			scrollClick(By.xpath(serviceHSXPath));
		} else {
			Thread.sleep(3000);
			scrollClick(By.xpath(serviceHSXPath));
			waitFor(3);
		}
	}

	public void selectHSServiceCity(String city) {
		switchToChildWindow();
		String cityXpath = "//img[@alt='city']".replace("city", city);
		waitForElementToBeClickable(driver, By.xpath(cityXpath), 10);
		click(By.xpath(cityXpath));
	}

	public void selectTenantVerification(String service) {
		waitForElementDisplay(driver, moreLocater, 5);
		click(moreLocater);
		log("Clicking on '+7more' Options");

		waitFor(1);
		click(legalServiceloc);
		log("Clicking on 'Legal Services' Options");

		driver.navigate().refresh();
		waitFor(2);
		String legalServiceXPath = leagalServices.replace("service", service);
		if (isElementPresent(driver, By.xpath(legalServiceXPath))) {
			click(By.xpath(legalServiceXPath));

		} else {
			WebElement legalServiceele = retryingFindClick(
					By.xpath("//div[@class='item-label' and text()='" + service + "']"));
			if (legalServiceele != null) {
				click(legalServiceele);
			}
		}
		waitFor(3);
	}

	public void closingChatHelp() {
		if (isElementPresent(driver, helpPopIframe)) {
			driver.switchTo().frame(driver.findElement(helpPopIframe));
			click(helpMinimise);
			driver.switchTo().defaultContent();
		} else {
			System.out.println("Chat Help PopUp Not Appears");
		}
	}

	public void selectTenantVerificationPackage(String tvpackage) {
		driver.navigate().refresh();
		waitFor(2);
		if (driver.findElement(getStartedLocater).isDisplayed()) {
			click(getStartedLocater);
			log("Clicking on 'Get Started' Button");
		} else {
			waitForElementDisplay(driver, getStartedLocater, 5);
			click(getStartedLocater);
			log("Clicking on 'Get Started' Button");
		}
		driver.navigate().refresh();
		waitFor(2);
		click(driver.findElement(By.xpath("(//div[@class='title' and text()='" + tvpackage
				+ "']/ancestor::div[@class='item']//button[contains(@class,'select-package')])[1]")));
	}

	public void tenantVerificationDetails(String idType, String idNumber, String name, String dob)
			throws InterruptedException {
		Thread.sleep(2000);
		click(selectIdCard);
		log("Clicking on 'Select ID Card' Dropdown");

		click(driver.findElement(By.xpath(idTypesLocator.replace("idType", idType))));
		log("Selecting " + idType + "'from Dropdown Options");

		sendKeys(idNumberLoc, idNumber);
		log("Entering the '" + idNumber + "'in Inputfield");

		sendKeys(tenantNameloc, name);
		log("Entering the '" + name + "'in Inputfield");

		sendKeys(dobLoc, dob);
		log("Entering the '" + dob + "'in Inputfield");
	}

	public void SaveAndContinueBtn() throws InterruptedException {
		if (isElementPresent(driver, SaveAndProceedBtn)) {
			click(SaveAndProceedBtn);
			log("Clicking on 'Save and Continue' Button");
			waitFor(1);
		} else {
			click(SaveAndProceedBtn);
			log("Clicking on 'Save and Continue' Button");
			waitFor(2);
		}
	}

	public String getTvPackageTitle() {
		String packageTitle = driver.findElement(tvSummaryTitle).getText();
		return packageTitle;
	}

	public void tenantBasicVerificationDetails(String idType, String idNumber, String name, String phone, String dob,
			String fatherName) throws InterruptedException {
		driver.navigate().refresh();
		if (isElementPresent(driver, selectIdCard)) {
			click(selectIdCard);
			log("Clicking on 'Select ID Card' Dropdown");
		} else {
			waitFor(5);
			click(selectIdCard);
			log("Clicking on 'Select ID Card' Dropdown");
			waitFor(1);
		}
		click(driver.findElement(By.xpath(idTypesLocator.replace("idType", idType))));
		log("Selecting " + idType + "'from Dropdown Options");

		sendKeys(idNumberLoc, idNumber);
		log("Entering " + idType + " Number '" + idNumber + "'");

		sendKeys(tenantNameloc, name);
		log("Entering Tenant '" + name + "'");

		sendKeys(tenantPhoneloc, phone);
		log("Entering Tenant PhoneNumber '" + phone + "'");

		sendKeys(dobLoc, dob);
		log("Entering Tenant Date Of Birth '" + dob + "'");
		waitFor(1);

		scrollUpto(tenantGenderLoc);
		click(tenantGenderLoc);
		log("CLicking on 'Select Gender' Dropwon");

		waitFor(1);
		click(By.xpath("//div[@class='css-11unzgr nb-select__menu-list']//div[text()='Male']"));
		log("Selecting Tenant Gender'Male' from Dropwon");

		sendKeys(By.name("fatherName"), fatherName);
		log("Entering Tenant FatherName '" + fatherName + "'");

		click(SaveAndProceedBtn);
		log("Clicking  on 'Save & Proceed' Button");
		waitFor(2);
	}

	public void verificationAddress(String flatNo, String locality, String pincode) {
		sendKeys(By.name("line1"), flatNo);
		log("Entering Flat Number '" + flatNo + "' in Inputfield");

		sendKeys(By.name("line2"), locality);
		log("Entering Locality/Socity '" + locality + "' in Inputfield");

		sendKeys(By.name("pincode"), pincode);
		log("Entering Pincode '" + pincode + "' in Inputfield");

		javascriptClick(sameAsPermenentAdrs);
		log("Clicking  on 'Same as Permanent Address' CheckBox");

		click(SaveAndProceedBtn);
		log("Clicking  on 'Save & Proceed' Button");
	}

	public void referenceName(String refName, String refMobNumber, String refEmail) {
		sendKeys(By.name("refName"), refName);
		log("Entering Reference Name '" + refName + "' into the Inputfild");

		sendKeys(By.name("refMobileNumber"), refMobNumber);
		log("Entering Reference Number '" + refMobNumber + "' into the Inputfild");

		sendKeys(By.name("refEmail"), refEmail);
		log("Entering Reference Email'" + refEmail + "' into the Inputfild");

		click(SaveAndProceedBtn);
		log("Clicking  on 'Save & Proceed' Button");

	}

	public WebElement retryingFindClick(By by) {
		int attemptsCount = 0;
		while (attemptsCount < 3) {
			try {
				WebElement element = driver.findElement(by);
				if (element.isDisplayed() && element.isEnabled()) {
					element.click();
					return element;
				}
			} catch (NoSuchElementException | StaleElementReferenceException e) {
			} catch (ElementClickInterceptedException e) {
				System.out.println("Element is present but not clickable: " + by);
			}
			waitFor(3000);
			attemptsCount++;
		}
		throw new NoSuchElementException("Element not found or not clickable after 3 attempts: " + by);
	}
}
