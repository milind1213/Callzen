package TestCases.callzen;

import Pages.callzen.CallzenWeb;
import TestBase.BaseTest;
import Utility.TestListeners;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Arrays;
import java.util.List;

@Listeners(TestListeners.class)
public class WebTest extends BaseTest {
    public CallzenWeb user;
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

    @Test(priority=1)
    public void Agent_TableColumn_Verifications() throws InterruptedException {
        user.selectHeaderOption("Agents");
        log("Clicked on 'Agent' Module");

        String defaultColumns = user.getAgentTableColumnsNames().toLowerCase();
        List<String> tableColsList = Arrays.asList(defaultColumns.split(","));
        tableColsList.replaceAll(String::trim);
        System.out.print("Total Columns : " + defaultColumns);

        String checkedColumns = user.getCheckedColumns().toLowerCase();
        List<String> checkedColsList = Arrays.asList(checkedColumns.split(","));
        checkedColsList.replaceAll(String::trim);
        System.out.println("Checked Columns : " + checkedColumns);
        SoftAssert softAssert = new SoftAssert();
        List<String> excludedCols = Arrays.asList("checklist coverage", "inbound calling", "actions", "agent name", "checklists"); // Lowercase
        for (String col : checkedColsList) {
            String colLowerCase = col.trim().toLowerCase(); // Ensure lowercase for comparison
            if (!excludedCols.contains(colLowerCase)) {
                softAssert.assertTrue(tableColsList.contains(colLowerCase), "Column " + col + " is not present");
            }
        }
    }

    @Test(priority=2)
    public void Verify_Search_select_AddColumn_Functionality() throws InterruptedException {
        user.selectHeaderOption("Agents");
        log("Clicked on 'Agent' Module");
        user.searchColumnFilter("customers", "connected Calls", "meaningful Calls");

        String defaultColumns = user.getAgentTableColumnsNames().toLowerCase();
        List<String> tableColsList = Arrays.asList(defaultColumns.split(","));
        tableColsList.replaceAll(String::trim);
        log("Agent Table 'Total Columns :"+defaultColumns+"'");

        String checkedColumns = user.getCheckedColumns().toLowerCase();
        List<String> checkedColsList = Arrays.asList(checkedColumns.split(","));
        checkedColsList.replaceAll(String::trim);
        log("Checked 'Total Columns :"+checkedColumns+"'");
    }

    @Test(priority=2)
    public void Verify_Search_select_RemoveColumn_Functionality() throws InterruptedException {
        user.selectHeaderOption("Agents");
        log("Clicked on 'Agent' Module");
        user.removeColumnFilter("average Handling Time", "calls Made", "checklists","long Silence","negative Emotion","total Talktime");

        String defaultColumns = user.getAgentTableColumnsNames().toLowerCase();
        List<String> tableColsList = Arrays.asList(defaultColumns.split(","));
        tableColsList.replaceAll(String::trim);
        log("Agent Table 'Total Columns :"+defaultColumns+"'");

        String checkedColumns = user.getCheckedColumns().toLowerCase();
        List<String> checkedColsList = Arrays.asList(checkedColumns.split(","));
        checkedColsList.replaceAll(String::trim);
        log("Checked 'Total Columns :"+checkedColumns+"'");
    }



}
