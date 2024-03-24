package Pages.HomeAndLegalServices;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import CommonUtility.CommonAppium;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ResidentHomePage extends CommonAppium {
	AndroidDriver driver;

	public ResidentHomePage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.app.nobrokerhood:id/btnGetStarted")
	public WebElement getStarted;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/editTextEmailMobile")
	public WebElement userEmailID;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/editTextPassword")
	public WebElement password;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/buttonLogin")
	public WebElement loginButton;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/loginCheckbox")
	public WebElement loginWithOTP;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/textViewSignUp")
	public WebElement signUp;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/denyTextView")
	public WebElement Deny;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/imageCross")
	public WebElement closeButton;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/skipTextView")
	public WebElement skip;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/loginCheckbox")
	public WebElement loginWithOTPCheckbox;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/textViewSubmit")
	public WebElement submit;

	@AndroidFindBy(accessibility = "Home")
	public WebElement home;

	@AndroidFindBy(id = "permission_allow_button")
	public WebElement allowAudioRecordPermission;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/allowTextView")
	public WebElement allowMissingNotification;

	@AndroidFindBy(id = "android:id/button1")
	public WebElement allowBacgroundRun;

	@AndroidFindBy(id = "com.android.packageinstaller:id/permission_deny_button")
	public WebElement denyAudioRecordPermission;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/denyTextView")
	public WebElement denyMissingNotification;

	@AndroidFindBy(id = "android:id/button")
	public WebElement denyBacgroundRun;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/fbCloseBtn")
	public WebElement closeContinueBooking;

	@AndroidFindBy(id = "com.app.nobrokerhood:id/frame_layout")
	WebElement frameLayout;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.app.nobrokerhood:id/titleTextView' and @text='Food']")
	WebElement exploreOptions;

	@AndroidFindBy(id = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout")
	WebElement exploreframeLayout;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.app.nobrokerhood:id/titleTextView' and @text='Explore']")
	WebElement exploreMenu;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.app.nobrokerhood:id/titleTextView' and @text='Explore']")
	WebElement Explore;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='service-page-proceed' or @text='Proceed']")
	WebElement proceedBtn;

	@AndroidFindBy(xpath = "//android.widget.Image[@text=\"google-maps\"]")
	WebElement googleMapIcon;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='service included']")
	WebElement serviceIncluded;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Add New Address\"]")
	WebElement newAdd;
	

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Full House Cleaning : ')]")
	List<WebElement> cancelationChargeFullHouse;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Sofa/Kitchen/Bathroom/Mini')]")
	List<WebElement> cancelationChargeothers;
	
	@AndroidFindBy(xpath = "//android.view.View[@text='Cancellation Policy']")
	WebElement cancellationPolicy;
	
	
	
	
	
	
	private String numberOfBathroomstext = "//android.view.View[@text='bathrooms']";
	private By serviceAdd = By
			.xpath("//android.view.View[@text='Premium']/following-sibling::android.widget.Button[@text='ADD']");
	private By serviceAdded = By
			.xpath("//android.view.View[@text='Premium']/following-sibling::android.widget.Button[@text='ADDED']");
	private By searchAddressTextBox = By.xpath("//android.widget.EditText");
	private By suggestAddress = By
			.xpath("//android.view.View[@resource-id=\"autocomplete-dropdown-container\"]//android.view.View");
	private By confirmLocation = By.xpath("//android.view.View[@text=\"Confirm location\"]");
	private String addressTag = "//android.widget.Button[@resource-id=\"saveAs\" and @text=\" tag\"]";
	private By submitAddress = By.xpath("//android.widget.Button[@resource-id='submit-address' and @text='Proceed']");
	private By doneBtn = By.xpath("//android.view.View[@text=\"Done\"]");
	private By datelocator = By.xpath("(//android.view.View[@resource-id=\"date-container\"]//android.view.View)[3]");
	private By checkOuttDetails = By.xpath("//android.view.View[@text=\"Packages\"]");
	private By houseNo = By.xpath("//android.widget.EditText[@resource-id=\"houseNumber\"]");
	private By socName = By.xpath("//android.widget.EditText[@resource-id=\"address\"]");
	private By slotProceed = By.xpath("//*[@resource-id='slots-proceed-mobile' or @text='Proceed']");
	private By NbCashAmt = By
			.xpath("//android.view.View[@text=\"Redeem NBcash on this order\"]//following-sibling::android.view.View");
	private By totalAmt = By.xpath("//android.view.View[@text=\"Total Amount\"]//following-sibling::android.view.View");
	private By totalAmt1= By.xpath("//android.view.View[@resource-id=\"checkoutTotal\"]//android.view.View[2]");
	private By trainingAmt = By.xpath("(//android.view.View[@resource-id=\"checkoutGST\"])[1]//android.view.View[2]");
	private By gstAmt = By.xpath("(//android.view.View[@resource-id=\"checkoutGST\"])[2]//android.view.View[2]");
	private By aapliedDiscountlocator= By.xpath(
			"(//android.view.View[@resource-id=\"checkoutNbDiscount\"]//following-sibling::android.view.View)[2]");
	private By actualTotal = By.xpath("//android.view.View[@resource-id=\"checkoutGrandTotal\"]//android.view.View[2]");
	
	public void cancellationPolicy() {
		cancellationPolicy.click();
		wait(2);
			String  fullHouseBeforeOnehrs = cancelationChargeFullHouse.get(0).getText();
			System.out.println(fullHouseBeforeOnehrs);
			String  fullHouseBeforeFourhrs = cancelationChargeFullHouse.get(1).getText();
			System.out.println(fullHouseBeforeFourhrs);
			String  otherBeforeOnehrs = cancelationChargeothers.get(0).getText();
			System.out.println(otherBeforeOnehrs);
			String  otherBeforeFourhrs = cancelationChargeothers.get(0).getText();
			System.out.println(otherBeforeFourhrs);
	}
	
	
	
	
	
	public int appliedDiscountAmount() {
		int appliedDiscount = 0;
		try {
			WebElement discountElement = driver.findElement(aapliedDiscountlocator);
			String discountText = discountElement.getText();
			String discount = discountText.replaceAll("[^0-9]", "");
			appliedDiscount = Integer.parseInt(discount);
		} catch (NoSuchElementException e) {
			
		}
		return appliedDiscount;
	}


	public void orderSummerysummeryPage() {
	    swipeDown(3, driver);
	    By nbCash = By.xpath("//android.view.View[@text=\"Redeem NBcash on this order\"]");
	    if (!isElementPresent(nbCash)) {
	        System.out.println("NB Discount Element Not Appeared");
	        log("NB Discount Element Not Appeared");
	    } else { 
	        click(driver.findElement(nbCash));
	        log("Successfully Applied 'NBcash' Discount");
	    }
	}
	
	public int calculatedGSTAmount(double gstPercentage) {
		int lineItemTotalAmount = 0;
		if (isElementPresent(totalAmt1)) {
			String lineItemAmtText = driver.findElement(totalAmt1).getText();
			String itemPrice = lineItemAmtText.replaceAll("[^0-9]", "");
			lineItemTotalAmount = Integer.parseInt(itemPrice);
		} else {
			String lineItemAmtText = driver.findElement(totalAmt).getText();
			String itemPrice = lineItemAmtText.replaceAll("[^0-9]", "");
			lineItemTotalAmount = Integer.parseInt(itemPrice);
		}

		String trainingAmtText = driver.findElement(trainingAmt).getText();
		String trainingAmt = trainingAmtText.replaceAll("[^0-9]", "");

		int trainingAmount = Integer.parseInt(trainingAmt);
		int calculatedGSTAmount = (int) Math.round((lineItemTotalAmount + trainingAmount) * gstPercentage);

		return calculatedGSTAmount;
	} 

	public int actualGSTAmount() {
		String gstText = driver.findElement(gstAmt).getText();
		String gstAmt = gstText.replaceAll("[^0-9]", "");
		int gstActualAmount = Integer.parseInt(gstAmt);
		return gstActualAmount;
	}

	public int actualPayableAmount() {
		String payebleTotalTxt = driver.findElement(actualTotal).getText();
		String payeble = payebleTotalTxt.replaceAll("[^0-9]", "");
		int payebleAmount = Integer.parseInt(payeble);
		return payebleAmount;
	}

	public int calculatedPayebleAmount() {
		int appliedDiscount = 0;
		
		String lineItemAmtText = driver.findElement(totalAmt).getText();
		String itemPrice = lineItemAmtText.replaceAll("[^0-9]", "");
		int lineItemTotalAmount = Integer.parseInt(itemPrice);

		String trainingAmtText = driver.findElement(trainingAmt).getText();
		String trainingAmt = trainingAmtText.replaceAll("[^0-9]", "");
		int trainingAmount = Integer.parseInt(trainingAmt);

		String gstText = driver.findElement(gstAmt).getText();
		String gstAmt = gstText.replaceAll("[^0-9]", "");
		int gstActualAmount = Integer.parseInt(gstAmt);

		try {
			WebElement discountElement = driver.findElement(aapliedDiscountlocator);
			String discountText = discountElement.getText();
			String discount = discountText.replaceAll("[^0-9]", "");
			appliedDiscount = Integer.parseInt(discount);
		} catch (NoSuchElementException e) {
			
		}

		int calculatedTotalAmount = lineItemTotalAmount + trainingAmount + gstActualAmount - appliedDiscount;
		return calculatedTotalAmount;
	}

	public void selectingServiceDateAndTime() {
		click(driver.findElement(datelocator));
		log("Clicking on Service 'Date' Options");
		wait(2);
		String serviceSlot = "(//android.view.View[@resource-id=\"timeSlots\"]/android.view.View)[6]";
		if (isElementPresent(driver.findElement(By.xpath(serviceSlot)))) {
			click(driver.findElement(By.xpath(serviceSlot)));
			log("Selecting Service Slot ");
		} else {
			wait(2);
			click(driver.findElement(By.xpath(serviceSlot)));
			log("Selecting Service Slot ");
		}
		wait(2);
		driver.findElement(slotProceed).click();
		log("Clicking On 'Proceed' Button");
	}

	public void selectBathroomCleaning(String bathrooms) {
		swipeDown(1, driver);
		log("Scrolling Down till Element");
		wait(1);
		WebElement serviceAddButton = driver.findElement(serviceAdd);
		if (isElementDisplayed(proceedBtn)) {
			click(proceedBtn);
			log("Clicking on 'Proceed' Button from Catalogue Page");
			wait(3);
		} else {
			click(serviceAddButton);
			log("Clicking on 'ADD' Button ");
			click(driver.findElement(By.xpath(numberOfBathroomstext.replace("bathrooms", bathrooms))));
			wait(2);

			click(proceedBtn);
			log("Clicking on Number of'" + bathrooms + "'And'Proceed' Button");
			wait(2);

			click(proceedBtn);
			log("Clicking on 'Proceed' Button from Catalogue Page");
			wait(3);
		}

	}

	public void addingNewServiceAddress(String locality, String houseNumber, String societyName, String tag) {
		wait(2);
		if (isElementDisplayed(newAdd)) {
			newAdd.click();
			log("Clicking on 'Add New Address'");
		}else {
			waitForWebElementToBeClickable(newAdd,Duration.ofSeconds(10));
			newAdd.click();
			log("Clicking on 'Add New Address'");
		}
		waitForWebElement(driver.findElement(searchAddressTextBox),Duration.ofSeconds(5));
		sendKeys(driver.findElement(searchAddressTextBox), locality);
		log("Entering Address in Searchbar '" + locality + "'");

		click(driver.findElement(suggestAddress));
		log("Selecting Auto-Suggest Address from Options");

		click(driver.findElement(confirmLocation));
		log("Confirming locality ");

		sendKeys(driver.findElement(houseNo), houseNumber);
		log("Entering House Number '" + houseNumber + "'");

		sendKeys(driver.findElement(socName), societyName);
		log("Entering Society Name as '" + societyName + "'");

		driver.findElement(By.xpath(addressTag.replace("tag", tag))).click();
		log("Selecting Address tag as '" + tag + "'");

		click(driver.findElement(submitAddress));
		log("Clicking on 'Proceed' Button");

		click(driver.findElement(doneBtn));
		log("Clicking on Choose an Address 'Done' Button");
		wait(2);
	}

	public void selectHeaderOptions(String headerOptions) {
		WebElement Options = driver.findElement(By.xpath(
				"//android.widget.TextView[@resource-id='com.app.nobrokerhood:id/titleTextView' and @text='Explore']"
						.replace("Explore", headerOptions)));
		waitForWebElementToBeClickable(Options, Duration.ofSeconds(10));
		click(Options);
	} 

	public void selectExploreService(String service) {
		WebElement expService = driver.findElement(By.xpath(
				"//android.widget.TextView[@resource-id='com.app.nobrokerhood:id/titleTextView' and @text='service']"
						.replace("service", service)));
		waitForWebElementToBeClickable(expService, Duration.ofSeconds(5));
		expService.click();
	}

	public void selectHomeServiceType() {
		wait(2);
		WebElement hs = driver
				.findElement(By.xpath("//android.view.View[contains(@text, 'best seller')]/parent::android.view.View"));
		if (isElementDisplayed(hs)) {
			hs.click();
		} else {
			waitForWebElementToBeClickable(hs, Duration.ofSeconds(5));
			hs.click();
		}
		wait(5);
		scrollIntoViewByText("Premium");
	}


	public void addButton() {
		WebElement ele = driver.findElement(By.xpath("(//android.widget.Button[@text=\"ADD\"])[2]"));
		ele.click();
	}

	public void clickingProccedingBtn() {
		WebElement ele = driver.findElement(By.xpath("//android.widget.Button[@resource-id='service-page-proceed']"));
		if (isElementDisplayed(ele)) {
			click(ele);
		} else {
			waitForWebElementToBeClickable(ele, Duration.ofSeconds(2));
			click(ele);
		}
	}

	public void clickingCart() {
		WebElement bh = driver.findElement(By.xpath("//android.widget.Image[@text='cart']"));
		waitForWebElementToBeClickable(bh, Duration.ofSeconds(10));
		click(bh);
	}

	public void userLogin(String email, String pass) throws InterruptedException {
		try {
			waitForWebElementToBeClickable(getStarted, Duration.ofSeconds(3));
			click(getStarted);
			wait(1);
			userEmailID.sendKeys(email);
			password.sendKeys(pass);
			loginButton.click();
			if (isElementDisplayed(closeContinueBooking)) {
				click(closeContinueBooking);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to perform user login. Exception details: " + e.getMessage());
		}
		wait(2);
	}

	public void allowPermission() {
		if (isElementPresent(allowAudioRecordPermission)) {
			allowAudioRecordPermission.click();
			log("Allowing AudioRecord Permission");
		}
		if (isElementPresent(allowMissingNotification)) {
			allowMissingNotification.click();
			log("Allowing MissingNotification Permission");
		}
		if (isElementPresent(allowBacgroundRun)) {
			allowBacgroundRun.click();
			log("Allowing BackgroundRun Permission");
		}
	}

}
