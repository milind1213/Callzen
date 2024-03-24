package TestCases.homeService;

import Utility.TestListeners;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Pages.HomeAndLegalServices.LegalServicePage;
import TestBase.BaseTest;

@Listeners(TestListeners.class)
public class TenantVerificationPackages extends BaseTest{
	LegalServicePage service;


    @Test(priority=1)
	public void Instant_TV_Package() throws Exception {
		service = new LegalServicePage(getWebDriver());
		service.loginSignUpMethod(randomMobileNumber(),"9999",randomName(),randomEmail());
		log("Successfully logged in with valid credentials.");
		
		service.selectHSServiceType("Painting & Cleaning");  
		log("Clicking on 'Painting & Cleaning' Service");
		
		service.selectHSServiceCity("Bangalore");
		log("Selected 'Bangalore' City");
		
		service.selectTenantVerification("Tenant Verification");
		log("Clicking on 'Tenant Verification' Package");
		
		service.closingChatHelp();
		log("Minimising the 'Help' Pop Up");
		
		service.selectTenantVerificationPackage("Instant Verification");
		log("Selecting the 'Instant Verification' Package");
	
		service.tenantVerificationDetails("PAN Card","BREPG1234F","Milind","11112000");
		log("Successfully Entered Tenant Verification Details ");
	
		service.SaveAndContinueBtn();
		
		String summaryTitle = service.getTvPackageTitle();
        Assert.assertEquals(summaryTitle,"Instant Verification");
	}
	
	@Test(priority=2)
	public void Basic_TV_Package() throws Exception {
		service = new LegalServicePage(getWebDriver());
		service.loginSignUpMethod(randomMobileNumber(),"9999",randomName(),randomEmail());
		log("Successfully logged in with valid credentials.");
		
		service.selectHSServiceType("Painting & Cleaning");
		log("Clicking on 'Painting & Cleaning' Service");
		
		service.selectHSServiceCity("Bangalore");
		log("Selected 'Bangalore' City");
		
		service.selectTenantVerification("Tenant Verification");
		log("Clicking on 'Tenant Verification' Package");
		
		service.closingChatHelp();
		log("Minimising the 'Help' Pop Up"); 
		
		service.selectTenantVerificationPackage("Basic Verification");
		log("Selecting the 'Basic Verification' Package");
		
		service.tenantBasicVerificationDetails("PAN Card","BREPG1234F","Shown","9876543210","11112000","John Doe");
		log("Succfully Entered Tenant Verification Details ");
		
		service.verificationAddress("A-12011","HSR","560035");
		log("Succfully Entered Verification 'Address' Details ");

		String summaryTitle = service.getTvPackageTitle();
        Assert.assertEquals(summaryTitle,"Basic Verification");
	}
	
	@Test(priority=3)
	public void Standard_TV_Package() throws Exception {
		service = new LegalServicePage(getWebDriver());
		service.loginSignUpMethod(randomMobileNumber(),"9999",randomName(),randomEmail());
		log("Successfully logged in with valid credentials.");
		
		service.selectHSServiceType("Painting & Cleaning");
		log("Clicking on 'Painting & Cleaning' Service");
		
		service.selectHSServiceCity("Bangalore");
		log("Selected 'Bangalore' City");
		
		service.selectTenantVerification("Tenant Verification");
		log("Clicking on 'Tenant Verification' Package");
		
		service.closingChatHelp();
		log("Minimising the 'Help' Pop Up"); 
		
		service.selectTenantVerificationPackage("Standard Verification");
		log("Selecting the 'Standard Verification' Package");
		
		service.tenantBasicVerificationDetails("PAN Card","BREPG1234F","Shown","9876543210","11112000","John Doe");
		log("Succfully Entered Tenant Verification Details ");
		
		service.verificationAddress("A-12011","HSR","560035");
		log("Succfully Entered Verification 'Address' Details ");
		

		String summaryTitle = service.getTvPackageTitle();
        Assert.assertEquals(summaryTitle,"Standard Verification");
	}
	
	
	@Test(priority=4)
	public void Comprehensive_TV_Package() throws Exception {
		service = new LegalServicePage(getWebDriver());
		service.loginSignUpMethod(randomMobileNumber(),"9999",randomName(),randomEmail());
		log("Successfully logged in with valid credentials.");
		
		service.selectHSServiceType("Painting & Cleaning");
		log("Clicking on 'Painting & Cleaning' Service");
		
		service.selectHSServiceCity("Bangalore");
		log("Selected 'Bangalore' City");
		
		service.selectTenantVerification("Tenant Verification");
		log("Clicking on 'Tenant Verification' Package");
		
		service.closingChatHelp();
		log("Minimising the 'Help' Pop Up"); 
		
		service.selectTenantVerificationPackage("Comprehensive Verification");
		log("Selecting the 'Comprehensive Verification' Package");
		
		service.tenantBasicVerificationDetails("PAN Card","BREPG1234F","Shown","9876543210","11112000","John Doe");
		log("Succfully Entered Tenant Verification Details ");
		
		service.verificationAddress("A-12011","HSR","560035");
		log("Succfully Entered Verification 'Address' Details ");
	
        service.referenceName("John","8766773456","refrence@g.com");
	    log("Succfully Entered Reference Details ");
	    
	    String summaryTitle = service.getTvPackageTitle();
        Assert.assertEquals(summaryTitle,"Comprehensive Verification");
	}
	
}
