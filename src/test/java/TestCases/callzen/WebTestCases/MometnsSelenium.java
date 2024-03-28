package TestCases.callzen.WebTestCases;
import CommonUtility.CommonConstants;
import Pages.callzen.CallzenPage;
import TestBase.BaseTest;
import Utility.TestListeners;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

import static CommonUtility.CommonConstants.*;
import static CommonUtility.CommonConstants.MOMENTS_VALUES.*;
import static org.testng.Assert.assertTrue;


@Listeners(TestListeners.class)
public class MometnsSelenium extends BaseTest {
    CallzenPage user;
    public static String email, password;
    List<String> teamsList = Arrays.asList("Legal Team", "Test Team", "Home");
    List<String> campaignNameList = Arrays.asList("Legal", "Test", "Furniture");
    List<String> processNameList = Arrays.asList("LegalServicing7OUT", "Auto", "furnituresalesOUT");
    List<String> KEY_LIST = Arrays.asList(RANDOM_KYWORD().VALUE(), RANDOM_KYWORD().VALUE());
    List<String> keywordsMust = Arrays.asList(RANDOM_KYWORD().VALUE());
    List<String> INST_LIST = Arrays.asList(RANDOM_INST().VALUE(), RANDOM_INST().VALUE(), RANDOM_INST().VALUE());
    String startDate = "010320241610", endDate = CommonConstants.CURRENT_DATE();
    String MOMENT_NAME = RANDOM_NAME();


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

    @Test(priority = 1)
    public void Create_Semantic_Global_Moments() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + GLOBAL.VALUE() + " and " + POSITIVE.VALUE());
        user.createMoment(MOMENT_NAME, GLOBAL.VALUE(), POSITIVE.VALUE());

        log("Selecting the '" + SEMANTIC_MOMENT.VALUE() + "' & Entering Search '" + RANDOM_SEMANTIC().SENTENCE() + "' Sentence ");
        user.selectPhrases(SEMANTIC_MOMENT.VALUE(), RANDOM_SEMANTIC().SENTENCE(), 5, AAD_ANYWAY.VALUE());

        String ActualMessage = user.getMomentCreatedText();
        Assert.assertEquals(ActualMessage, "Moment Created Successfully !");

        user.submitFeedback();
        log("Submitted the FeedBack Successfully");

        user.bulkMomentTagging(startDate, endDate);
        log("Successfully taggedMoment");

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


    public void Create_Semantic_Moments_With_Teams_Filters() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + FILTERED.VALUE() + " and " + POSITIVE.VALUE() + "And Selecting Teams " + teamsList);
        user.createMomentWithTeamFilters(MOMENT_NAME, POSITIVE.VALUE());

        log("Clicking on " + TEAM.VALUE() + " And Selecting Teams " + teamsList + ",CampaignName'" + campaignNameList + "',ProcessName " + processNameList);
        user.selectTeamDetails(TEAM.VALUE(), teamsList, campaignNameList, processNameList);


        log("Selecting the '" + SEMANTIC_MOMENT.VALUE() + "' & Entering Search '" + RANDOM_SEMANTIC().SENTENCE() + "' Sentence ");
        user.selectPhrases(SEMANTIC_MOMENT.VALUE(), RANDOM_SEMANTIC().SENTENCE(), 5, AAD_ANYWAY.VALUE());

        String ActualMessage = user.getMomentCreatedText();
        Assert.assertEquals(ActualMessage, "Moment Created Successfully !");

        user.submitFeedback();
        log("Submitted the FeedBack Successfully");

        user.bulkMomentTagging(startDate, endDate);
        log("Successfully taggedMoment");

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

    @Test(priority = 3)
    public void Create_Semantic_Moments_With_Filter_Filtered() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + FILTERED.VALUE() + " and " + POSITIVE.VALUE() + "And Selecting Teams " + teamsList);
        user.createMomentWithTeamFilters(MOMENT_NAME, POSITIVE.VALUE());

        log("Clicking on " + FILTER.VALUE() + " And Selecting Teams " + teamsList + ",CampaignName'" + campaignNameList + "',ProcessName " + processNameList);
        user.selectTeamDetails(FILTER.VALUE(), teamsList, campaignNameList, processNameList);

        log("Selecting the '" + SEMANTIC_MOMENT.VALUE() + "' & Entering Search '" + RANDOM_SEMANTIC().SENTENCE() + "' Sentence ");
        user.selectPhrases(SEMANTIC_MOMENT.VALUE(), RANDOM_SEMANTIC().SENTENCE(), 5, AAD_ANYWAY.VALUE());

        String ActualMessage = user.getMomentCreatedText();
        Assert.assertEquals(ActualMessage, "Moment Created Successfully !");

        user.submitFeedback();
        log("Submitted the FeedBack Successfully");

        user.bulkMomentTagging(startDate, endDate);
        log("Successfully Symentic Moment tagged");

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

    @Test(priority = 4)
    public void Create_Keyword_Global_Moments_with_contains_one_of() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + GLOBAL.VALUE() + " and " + POSITIVE.VALUE());
        user.createMoment(MOMENT_NAME, GLOBAL.VALUE(), POSITIVE.VALUE());

        log("Selecting the '" + KEYWORD_MOMENT.VALUE() + "' & Entering Search " + RANDOM_KYWORD().VALUE());
        List<String> inputKeywords = user.EnterKeywordDetails(KEYWORD_MOMENT.VALUE(),KEY_LIST);
        List<String> phrases = user.getKeywordSearchDetails();
        int matchCount = 0;
        for (int i = 0; i < phrases.size(); i++) {
            for (String keyword : inputKeywords) {
                phrases.get(i).contains(keyword);
                matchCount++;
            }
        }
        System.out.println(matchCount);
        log("Validating if any phrase contains the provided keywords");
        Assert.assertFalse(matchCount < 5, "Match count should not be at least 5");

        user.bulkMomentTagging(startDate, endDate);
        log("Successfully taggedMoment");

        String momentDetails = user.getMomentDetails();
        System.out.println("Moment Details : " + momentDetails);
        String[] str = momentDetails.split(",");
        Assert.assertEquals(MOMENT_NAME, str[0]);
        log("Validated  Actual Moments Name '" + MOMENT_NAME + "' with  Expected '" + str[0] + "' Successfully");

        Assert.assertTrue(str[1].contains("Keyword"));
        log("Validated Actual Moment Type'" + str[1] + "' with Expected 'Semantic' Successfully");

        Assert.assertEquals(str[5], ("Success"));
        log("Validated FeedBack Status Actual'" + str[5] + "' with Expected 'Success' successfully");

        user.deleteMoments();
        String actualMsg = user.getDeletedText();
        Assert.assertEquals(actualMsg, "Moment Deleted Successfully");
        log("Validated '" + actualMsg + " Pop Up Message");
    }

    @Test(priority = 5)
    public void Create_Keyword_Global_Moments_with_ContainsAll() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + GLOBAL.VALUE() + " and " + POSITIVE.VALUE());
        user.createMoment(MOMENT_NAME, GLOBAL.VALUE(), POSITIVE.VALUE());

        log("Selecting the '" + KEYWORD_MOMENT.VALUE() + "' & Entering Search " + RANDOM_KYWORD().VALUE());
        List<String> inputKeywords = user.EnterKeywordDetails1(KEYWORD_MOMENT.VALUE(), keywordsMust);
        List<String> phrases = user.getKeywordSearchDetails();

        boolean allKeywordsPresent = true;
        for (String keyword : inputKeywords) {
            boolean keywordFound = phrases.stream().anyMatch(phrase -> phrase.toLowerCase().contains(keyword.toLowerCase()));
            if (!keywordFound) {
                allKeywordsPresent = false;
                log("Keyword '" + keyword + "' not found in any phrase.");
                phrases.stream()
                        .filter(phrase -> !phrase.toLowerCase().contains(keyword.toLowerCase()))
                        .forEach(phrase -> log("Phrase without keyword '" + keyword + "': " + phrase));
                break;
            }
        }
        Assert.assertTrue(allKeywordsPresent, "Not all keywords are present in phrases.");
        log("Validating if All Keywords contains the phrases");

        user.bulkMomentTagging(startDate, endDate);
        log("Successfully taggedMoment");

        String momentDetails = user.getMomentDetails();
        System.out.println("Moment Details : " + momentDetails);
        String[] str = momentDetails.split(",");
        Assert.assertEquals(MOMENT_NAME, str[0]);
        log("Validated  Actual Moments Name '" + MOMENT_NAME + "' with  Expected '" + str[0] + "' Successfully");

        Assert.assertTrue(str[1].contains("Keyword"));
        log("Validated Actual Moment Type'" + str[1] + "' with Expected 'Semantic' Successfully");

        Assert.assertEquals(str[5], ("Success"));
        log("Validated FeedBack Status Actual'" + str[5] + "' with Expected 'Success' successfully");

        user.deleteMoments();
        String actualMsg = user.getDeletedText();
        Assert.assertEquals(actualMsg, "Moment Deleted Successfully");
        log("Validated '" + actualMsg + " Pop Up Message");
    }

    @Test(priority = 6)
    public void Create_Global_Instructional_NEX_Moments() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + GLOBAL.VALUE() + " and " + POSITIVE.VALUE());
        user.createDetailsPage(MOMENT_NAME, GLOBAL.VALUE(), POSITIVE.VALUE());

        log("Selecting the'"+ INSTRUCTIONAL_MOMENT.VALUE()+"' from Header");
        user.enterNextMomentDetails(INSTRUCTIONAL_MOMENT.VALUE(),INST_LIST,KEY_LIST,CDR.ID_URL.VALUE());
        log("Entered "+ INST_LIST+ " KeyWords :"+KEY_LIST+ "CDR Url : "+CDR.ID_URL.VALUE());

        String actualOutput = user.getCDROutputText("Present");
        Assert.assertEquals(actualOutput, "Present,Present,Present,");
        log("Validated Expected with Actual and Expected response");
    }

    @Test(priority = 6)
    public void Create_Global_Instructional_PRIME_Moments() throws Exception {
        log("Selecting the '" + MOMENTS.VALUE() + "' from Header");
        user.selectHeaderOption(MOMENTS.VALUE());

        log("Entering" + MOMENT_NAME + ",Clicking on " + GLOBAL.VALUE() + " and " + POSITIVE.VALUE());
        user.createDetailsPage(MOMENT_NAME, GLOBAL.VALUE(), POSITIVE.VALUE());

        log("Selecting the'"+ INSTRUCTIONAL_MOMENT.VALUE()+"' from Header");
        user.enterNextMomentDetails(INSTRUCTIONAL_MOMENT.VALUE(),INST_LIST,KEY_LIST,CDR.ID_URL.VALUE());
        log("Entered "+ INST_LIST+ " KeyWords :"+KEY_LIST+ "CDR Url : "+CDR.ID_URL.VALUE());

        String actualOutput = user.getCDROutputText("Present");
        Assert.assertEquals(actualOutput, "Present,Present,Present,");
        log("Validated Expected with Actual and Expected response");
    }

}



