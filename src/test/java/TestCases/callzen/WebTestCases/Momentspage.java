package TestCases.callzen.WebTestCases;

import CommonUtility.CommonConstants;
import Pages.callzen.CallzenWeb;
import TestBase.BaseTest;
import Utility.TestListeners;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static CommonUtility.CommonConstants.*;
import static CommonUtility.CommonConstants.MOMENTS_VALUES.*;
import static CommonUtility.CommonConstants.MOMENT_KEYS.MOMENT_NAME;


@Listeners(TestListeners.class)
public class Momentspage extends BaseTest {
    public CallzenWeb user;
    static String MOMENT_NAME="Semantic Moment_test" + RandomStringUtils.randomAlphabetic(3).toLowerCase();
    public static String email, password;
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
    public void Moments_Create_Functionality() throws InterruptedException {

        log("Selecting the '"+ MOMENTS.VALUE() +"' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering"+MOMENT_NAME+",Clicking on "+GLOBAL.VALUE()+" and "+POSITIVE.VALUE());
        user.createMoment(MOMENT_NAME,GLOBAL.VALUE(),POSITIVE.VALUE());

        log("Selecting the '"+SEMANTIC_MOMENT.VALUE()+"' & Entering Search '"+RANDOM_SEMANTIC().SENTENCE()+"' Sentence ");
        user.selectPhrases(SEMANTIC_MOMENT.VALUE(),RANDOM_SEMANTIC().SENTENCE(),10,AAD_ANYWAY.VALUE());

    }


}
