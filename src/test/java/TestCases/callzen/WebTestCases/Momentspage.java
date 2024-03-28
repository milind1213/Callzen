package TestCases.callzen.WebTestCases;

import CommonUtility.CommonConstants;
import Pages.callzen.CallzenWeb;
import TestBase.BaseTest;
import Utility.TestListeners;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static CommonUtility.CommonConstants.*;
import static CommonUtility.CommonConstants.MOMENTS_VALUES.*;
import static CommonUtility.CommonConstants.MOMENT_KEYS.MOMENT_NAME;
import static org.testng.Assert.assertTrue;


@Listeners(TestListeners.class)
public class Momentspage extends BaseTest {
    public CallzenWeb user;
    public static String email, password;
    String MOMENT_NAME = RANDOM_NAME();
    String startDate = "010320241610", endDate = CommonConstants.CURRENT_DATE();
    @BeforeClass
    public void loadConfiguration() {
        email = config.callzenProp("Web").getProperty("email");
        password = config.callzenProp("Web").getProperty("password");
    }
    @BeforeMethod(alwaysRun = true)
    public void login() {
        user = new CallzenWeb(getPlaywrightPage());
        user.login(email, password);
    }
    @Test(priority = 1)
    public void Moments_Create_Functionality() throws Exception {
        log("Selecting the '"+ MOMENTS.VALUE() +"' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering"+MOMENT_NAME+",Clicking on "+GLOBAL.VALUE()+" and "+POSITIVE.VALUE());
        user.createMoment(MOMENT_NAME,GLOBAL.VALUE(),POSITIVE.VALUE());

        log("Selecting the '" + SEMANTIC_MOMENT.VALUE() + "' & Entering Search '" + RANDOM_SEMANTIC().SENTENCE() + "' Sentence ");
        user.selectPhrases(SEMANTIC_MOMENT.VALUE(), "Is the property still available for rent?", 10, AAD_ANYWAY.VALUE());

        String ActualMessage = user.getMomentCreatedText();
        Assert.assertEquals(ActualMessage, "Moment Created Successfully !");

        user.submitMomentFeedback();
        log("Submitted the FeedBack Successfully");

        //user.bulkMomentTagging(startDate,endDate);
        // log("Successfully taggedMoment");

        String momentDetails = user.getMomentDetails();
        System.out.println("Moment Details : " + momentDetails);
        String[] str = momentDetails.split(",");
        Assert.assertEquals(MOMENT_NAME, str[0]);
        log("Validated  Actual Moments Name '" + MOMENT_NAME + "' with  Expected '" + str[0] + "' Successfully");

        assertTrue(str[1].contains("Semantic"));
        log("Validated Actual Moment Type'" + str[1] + "' with Expected 'Semantic' Successfully");

        Assert.assertEquals(str[5], ("Success"));
        log("Validated FeedBack Status Actual'" + str[5] + "' with Expected 'Success' successfully");

        user.deleteMoments();
        String actualMsg = user.getDeletedText();
        Assert.assertEquals(actualMsg, "Moment Deleted Successfully");
        log("Validated '" + actualMsg + " Pop Up Message");
    }


}
