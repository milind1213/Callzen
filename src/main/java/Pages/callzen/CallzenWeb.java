package Pages.callzen;

import CommonUtility.CommonPlaywright;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static Pages.callzen.Locators.*;

public class CallzenWeb extends CommonPlaywright {
    public Page page;
    Map<String, Object> logMap = new LinkedHashMap<>();
    public Logger logger = LogManager.getLogger(this.getClass());

    public void log(String message) {
        logger.info(message);
    }

    public CallzenWeb(Page page) {
        super(page);
        this.page = page;
    }


    public void phraseAction(String action) throws InterruptedException {
        try {
            if (isLocatorPresent(page, takeActionBtn, 5)) {
                click(takeActionBtn);
                log("Clicked on Take Action Button ");
                Locator locators = page.locator(takeActionOptions);
                for (int i = 0; i < locators.count(); i++) {
                    if (locators.innerText() == action) {
                        locators.nth(i).click();
                    } else if (locators.innerText() == action) {
                        locators.nth(i).click();
                        waitForElement(page, suggestSimplerPhraseOptions, 120);
                        Locator list = page.locator(suggestSimplerPhraseOptions);
                        for (int j = 0; j < list.count(); j++) {
                            list.nth(j).click();
                        }
                    } else {
                        if (locators.innerText() == action) {
                            locators.nth(i).click();
                        }
                    }
                }
            } else {
                log("Take Action Button not present ");
            }
        } catch (Exception e) {
            log("Exception occurred in phraseAction: " + e.getMessage());
        }
    }

    public void highlightAction() throws InterruptedException {
        try {
            if (isLocatorPresent(page, highlight, 5)) {
                Locator locList = page.locator(highlight);
                for (int i = 0; i < locList.count(); i++) {
                    click(highlightCross);
                    log("Clicked on the 'Highlight Cross' Button");
                }
            } else {
                log("Highlight Button Not not present");
            }
        } catch (Exception e) {
            log("Exception occurred in highlightAction: " + e.getMessage());
        }
    }

    public void selectPhrases(String types, String sentence, int selectCount, String action) throws InterruptedException {
        click(momentType.replace("name", types));
        fill(searchBar, "discount");
        log(" Clicked on '" + types + "' and Entered the search " + sentence + "");

        page.keyboard().press("Enter");
        log("Pressed the 'Enter' Key ");

        waitForElement(page, suggetionsList, 120);
        Locator listEle = page.locator(suggetionsList);
        int sentenceCount = 0;
        int totalCount = listEle.count();
        for (int i = 0; i < Math.min(selectCount, totalCount); i++) {
            listEle.nth(i).click();
        }

        highlightAction();
        log("Clicking on 'Next' Button");
        waitForElement(page,NextBtn, 2);
        click(NextBtn);

        phraseAction(action);
        click(NextBtn);
    }


     /* String text = listEle.nth(i).innerText();
            int wordCount = text.split(" ").length;
            if (wordCount >= 22) {
                listEle.nth(i).click();
                sentenceCount++;
                if (sentenceCount == 5) {
                    break;
                }
            }*/

    public void createMoment(String MomentName, String Global, String emotion) {
        page.setViewportSize(1400, 1000);
        click(createMomentBtn);
        log("Clicked on Create Moment Button");

        fill(momentNameTxt, MomentName);
        log("Entered Moment Name '" + MomentName + "' in text Field");

        click(FilterBtn.replace("type", Global));
        log("Clicked on '" + Global + "'Button");

        click(FilterBtn.replace("type", emotion));
        log("Clicked on '" + emotion + "'Button");

        click(selectConversationType);
        log("Clicked on Select Conversation Element ");

        click(conversationTypeDrp);
        log("Clicked on'Call' DropDown Options");

        click(NextBtn);
        log("Clicked on'Next' Button");
    }


    public void login(String email, String password) {
        log("Entering Email: " + email);
        fill(emailField, email);

        log("Entering Password: " + password);
        fill(passwordField, password);

        log("Clicking on 'Sign In' Button");
        click(loginBtn);
    }

    public String getCheckedColumns() throws InterruptedException {
        click(columnFilterBtn);
        Locator loc = page.locator(checkedColumns);
        StringBuilder sb = new StringBuilder();
        try {
            page.waitForSelector(checkedColumns);
            List<String> all = loc.allInnerTexts();
            System.out.println("Number of elements: " + all.size());
            for (String s : all) {
                sb.append(s).append(",");
            }
        } catch (PlaywrightException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getAgentTableColumnsNames() {
        StringBuilder sb = new StringBuilder();
        String checklist = page.locator(checkListCoverageColumn).innerText();
        String actions = page.locator(actionsColumn).innerText();
        sb.append(checklist).append(",");
        sb.append(actions).append(",");
        for (int i = 1; i <= 7; i++) {
            String columnName = page.locator("//tr/th[" + i + "]//p").innerText();
            sb.append(columnName).append(",");
        }
        return sb.toString();
    }

    public String selectHeaderOption(String header) {
        String headerLocators = headerLocator.replace("menu", header);
        try {
            click(headerLocators);
            return "Header option '" + header + "' selected successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to select header option '" + header + "'.";
        }
    }

    public void searchColumnFilter(String... columns) throws InterruptedException {
        click(columnFilterBtn);
        log("Clicking on Column Filter Button");
        for (String col : columns) {
            fill(SearchBar, col);
            log("Entering ColumnName '" + col + "' in Searchbar");
            click("//input[@type='checkbox']");
            log("CLicking on Checkbox");
            click(clearTextIcon);
            log("Clearing the text from Searchbar");
        }
        click(columnFilterBtn);
        log("Clicking on Column Filter Button");
    }

    public void removeColumnFilter(String... columns) throws InterruptedException {
        click(columnFilterBtn);
        log("Clicking on Column Filter Button");
        for (String col : columns) {
            fill(SearchBar, col);
            log("Entering ColumnName '" + col + "' in Searchbar");
            click("//input[@type='checkbox']");
            log("CLicking on Checkbox");
            click(clearTextIcon);
            log("Clearing the text from Searchbar");
        }
        click(columnFilterBtn);
        log("Clicking on Column Filter Button");
    }


}
