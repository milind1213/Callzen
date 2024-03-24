package TestCases.callzen;

import TestBase.BaseTest;
import Utility.TestListeners;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Pages.callzen.CallzenPage;

@Listeners(TestListeners.class)
public class AgentcallpageFiltersWeb extends BaseTest {
    public CallzenPage user;

    @BeforeMethod
    public void setUp() throws Exception {
        user = new CallzenPage(getWebDriver());
    }

    @Test(priority = 1)
    public void Validating_DefaultApplied_Filters() {
        user.login("system@callzen.ai", "bfM6PVoXLaop5w");
        log("Successfully logged in with valid login Credentials");

        String defaultFilters = user.getDefaultAppliedFilters();
        Assert.assertTrue(defaultFilters.contains("Call Duration:") && defaultFilters.contains("> 30 secs"));
        log("Successfully Validated Default Applied 'Call Duration:> 30 secs'");

        Assert.assertTrue(defaultFilters.contains("View By:") && defaultFilters.contains("Yesterday, Transcribed Calls"));
        log("Validated Default Applied 'View By:Yesterday, Transcribed Calls'");
    }

    @Test(priority=2)
    public void TeamSearch_Select_Filters() {
        user.login("system@callzen.ai", "bfM6PVoXLaop5w");
        log("Successfully logged in with valid login Credentials");

        user.callTeamsSearchSelectFilter("Team 1");
        log("Successfully Selected Teams");

        String selectedTeam = user.teamValidation();
        Assert.assertEquals(selectedTeam, "Team 1");
    }
} 
