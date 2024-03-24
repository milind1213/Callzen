package TestCases.callzen.WebTestCases;
import Pages.callzen.CallzenPage;
import TestBase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static CommonUtility.CommonConstants.MOMENTS_VALUES.*;
import static CommonUtility.CommonConstants.RANDOM_SEMANTIC;
import static TestCases.callzen.WebTestCases.Momentspage.MOMENT_NAME;

public class MometnsSelenium extends BaseTest {
    public static String email, password;
    CallzenPage user;
    @BeforeClass
    public void loadConfiguration() {
        email = config.callzenProp("Web").getProperty("email");
        password = config.callzenProp("Web").getProperty("password");
    }
    @BeforeMethod(alwaysRun = true)
    public void login() {
        user = new CallzenPage(getWebDriver());
        user.login(email, password);
    }

    @Test
    public void Create_Semantic_Global_Moments() throws InterruptedException {
        log("Selecting the '"+ MOMENTS.VALUE() +"' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering"+MOMENT_NAME+",Clicking on "+GLOBAL.VALUE()+" and "+POSITIVE.VALUE());
        user.createMoment(MOMENT_NAME,GLOBAL.VALUE(),POSITIVE.VALUE());

        log("Selecting the '"+SEMANTIC_MOMENT.VALUE()+"' & Entering Search '"+RANDOM_SEMANTIC().SENTENCE()+"' Sentence ");
        user.selectPhrases(SEMANTIC_MOMENT.VALUE(),RANDOM_SEMANTIC().SENTENCE(),10,AAD_ANYWAY.VALUE());

        String ActualMessage = user.getMomentCreatedText();
        Assert.assertEquals(ActualMessage,"Moment Created Successfully !");
    }
    @Test
    public void Create_Semantic_Moments_With_Teams_Filters() throws InterruptedException {

    }


}


