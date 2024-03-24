package TestCases.homeService;
import Utility.TestListeners;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Pages.HomeAndLegalServices.ResidentHomePage;
import TestBase.BaseTest;

@Listeners(TestListeners.class)
public class BathroomCleaning_Android extends BaseTest {
    ResidentHomePage user;
	private int tolerance = 2;
	
	@BeforeMethod
	public void setUp() throws Exception {
		user = new ResidentHomePage(getAndroidDriver());
	}
	@Test
	public void Bathroom_Cleaning_Flow() throws Exception {
		log("clicking on 'Get Started' Button");
		user.userLogin(EMAIL, PASSWORD);
		log("Successfully Logged in with Email'" + EMAIL + "'And Password'" + PASSWORD + "'");

		user.selectHeaderOptions("Explore");
		log("Clicked on 'Explore' Menu");

		user.selectExploreService("Home\nServices");
		log("Clicked on 'Home Services'");

		user.selectHomeServiceType();
		log("Clicked on 'Bathroom Cleaning Service");

		user.selectBathroomCleaning("2 Bathrooms");
		log("Successfully Added 2 Premium Bathroom Cleaning Service to Cart");

		user.addingNewServiceAddress("HSR Layout", "A-123", "Bren Mercury", "Home");
		log("Successfully Added Service Address");

		user.selectingServiceDateAndTime();
		log("Successfully Selected Service Date & Slot");
		user.orderSummerysummeryPage();
		
		int appliedDiscount = user.appliedDiscountAmount();
		Assert.assertTrue(appliedDiscount == 48 || appliedDiscount == 0, "Discount Not applied");
		log("Verified Applied Discount[" + appliedDiscount + "] with Actual Discount[0] or [48]");

		int actualGST = user.actualGSTAmount();
		int calculatedGST = user.calculatedGSTAmount(0.18);
		Assert.assertTrue(Math.abs(actualGST - calculatedGST) <= tolerance, "GST Amounts are Not Equal");
		log("Verified Actual GST[" + actualGST + "] with Expected[" + calculatedGST + "]");

		int payebleAmount = user.actualPayableAmount();
		int calculatedPayeble = user.calculatedPayebleAmount();
		Assert.assertTrue(Math.abs(payebleAmount - calculatedPayeble) <= tolerance); 
		log("Verified Actual Payable[" + payebleAmount + "] with Expected[" + payebleAmount + "]");
	}
	
	 @Test(dependsOnMethods = "Bathroom_Cleaning_Flow")
	 public void TC02_Verifying_CancelationPolicy() {
		 user.cancellationPolicy();
	 } 
}
