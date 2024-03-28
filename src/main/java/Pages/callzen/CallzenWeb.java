package Pages.callzen;

import CommonUtility.CommonPlaywright;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.ElementState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    public void bulkMomentTagging(String startDate, String endDate) throws InterruptedException {
        log("Clicking on Tag from Now Button");
        page.locator(tagFromNowBtm);
        LocalDate parsedStartDate = LocalDate.parse(startDate.substring(0, 8), DateTimeFormatter.ofPattern("ddMMyyyy"));
        LocalDate parsedEndDate = LocalDate.parse(endDate.substring(0, 8), DateTimeFormatter.ofPattern("ddMMyyyy"));

        while (true) {
            waitFor(2);
            log("Entering from Bulk Tagging");
            fill(bulkTagFromDateTimeLoc, String.valueOf(startDate));

            log("Entering from Bulk Tagging To" + endDate);
            fill(bulkTagToDateTimeLoc, endDate);
            if (isLocatorPresent(page, totalCallToBeAffected, 3)) {
                int call = Integer.parseInt(page.innerText(totalCallToBeAffected));
                if (call == 0) {
                    parsedStartDate = parsedStartDate.minusDays(1);
                    startDate = parsedStartDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + startDate.substring(8);
                    endDate = parsedEndDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + endDate.substring(8);
                } else {
                    break; // Exit the loop if call is not 0
                }
            } else {
                String warningMsg = page.locator(callLimitWarnings).innerText();
                String text = warningMsg.replaceAll("[^0-9,]", "");
                String[] str = text.split(",");
                int totalCalls = Integer.parseInt(str[0]);
                int callLimit = Integer.parseInt(str[1]);
                if (totalCalls > callLimit) {
                    parsedStartDate = parsedStartDate.plusDays(1);
                    startDate = parsedStartDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + startDate.substring(8);
                } else {
                    click(submitBtn);
                    log("Clicked on submit Button");
                }
            }
        }
        click(submitBtn);
        log("Clicked on submit Button");

        click(finishBtn);
        log("Clicked on submit Button");
    }

    public String getMomentDetails() throws Exception {
        List<ElementHandle> columns = page.querySelectorAll(momentsTable);
        StringBuilder rowData = new StringBuilder();
        for (int i = 1; i < columns.size(); i++) {
            String a = page.locator("//tbody/tr[1]/td[" + i + "]").innerText();
            rowData.append(a).append(",");
        }

        click(finishBtn);
        log("Clicked on Finish Button");
        return rowData.toString();
    }

    public void deleteMoments() {
        click(editAccordion);
        log("Clicked on the 'Accordion' Button");

        click(deleteMomentBt);
        log("Clicked on the 'Delete' Button");

        String name = page.locator(momentsNameText).innerText();
        System.out.println(name);
        fill(textBox, name);
        log("Entered the 'MomentName' in TextBox");

        click(confirmBtn);
        log("Clicked on 'Confirm' button");
    }

    public String getDeletedText() throws Exception {
        String msg = page.locator(momentsNameText).innerText();
        return msg;
    }

    public void selectPhrases(String types, String sentence, int selectCount, String action) throws Exception {
        waitForElement(page, momentType.replace("name", types), 5);
        click(momentType.replace("name", types));
        fill(searchBar, sentence);
        log("Clicked on '" + types + "' and Entered the search " + sentence);
        page.keyboard().press("Enter");
        log("Pressed the 'Enter' Key ");
        waitForLocatorClickable(page, suggetionsList, 120);
        List<ElementHandle> list = page.querySelectorAll(suggetionsList);
        int elementsToClick = Math.min(selectCount, list.size());
        for (int i = 0; i < elementsToClick; i++) {
            list.get(i).click();
            log("Clicking on 'Phrases Add' Button ");
        }
        waitFor(2);
        //page.evaluate("window.scrollTo(0, -500);");
        try {
            handlePhraseTooLongErrors();
            clickNext();
        } catch (Exception e) {
            log("Exception occurred in Handling 'Highlight': " + e.getMessage());
        }
        try {
            HandlePhraseMatchingErrors(action);
            HandleClusterError();
        } catch (Exception e) {
            log("Exception occurred in Handling 'Cluster and Phrase Matching Errors' : " + e.getMessage());
        }
        clickNext();
    }

    public String getMomentCreatedText() {
        isLocatorPresent(page, momentCreatedSuccessfullyLLoc, 3);
        String text = page.locator(momentCreatedSuccessfullyLLoc).innerText();
        return text;
    }

    public void submitMomentFeedback() {
        click(giveFeedBackBtn);
        log("Clicked on 'Feedback' button");
        if (isLocatorPresent(page, feedbackPositivePositive, 5)) {
            submitPositiveFeedback();
        }
        waitFor(3);
        String msg = page.locator(feedbackSubmittedSuccessfully).innerText();
        Assert.assertEquals(msg, "Success! Moment feedback submitted successfully");
        click(NextBtn);
    }

    public void submitPositiveFeedback() {
        boolean moreLists = true;
        do {
            List<ElementHandle> positiveList = page.querySelectorAll(feedbackPositivePositive);
            for (ElementHandle positive : positiveList) {
                positive.click();
            }
            click(submitBtn);
            log("Clicked on 'Submit' button");
            waitFor(5);
            List<ElementHandle> nextList = page.querySelectorAll(feedbackPositivePositive);
            moreLists = !nextList.isEmpty();
        } while (moreLists);
    }

    public void handlePhraseTooLongErrors() {
        if (isLocatorPresent(page, highlightCross, 5)) {  // Check for element within 5 seconds
            log("'Highlight' Button Appeared");
            List<ElementHandle> highlightCrosses = page.querySelectorAll(highlightCross);
            for (int i = 0; i < highlightCrosses.size(); i++) {
                try {
                    ElementHandle highlightCross = highlightCrosses.get(i);
                    highlightCross.click();
                    if (highlightCross.boundingBox() == null) { // Check if bounding box exists
                        highlightCross.scrollIntoViewIfNeeded();
                        highlightCross.click();
                        log("Clicked on Cross icon number " + (i + 1));
                    } else {
                        log("Cross icon number " + (i + 1) + " not found in the DOM");
                    }
                } catch (Exception e) {
                    log("Error occurred while clicking on Cross icon number " + (i + 1) + ": " + e.getMessage());
                }
            }
        } else {
            log("'Highlight' Button not found");
        }

    }

    public void HandlePhraseMatchingErrors(String action) {
        if (isLocatorPresent(page, takeActionBtn, 5)) {
            log("'Phrase does not match.'Take Action button appeared.");
            List<ElementHandle> takeActionList = page.querySelectorAll(takeActionBtn);
            for (ElementHandle takeActionElement : takeActionList) {
                takeActionElement.scrollIntoViewIfNeeded();
                takeActionElement.click();
                log("Clicked on Take Action Button ");
                waitFor(2);
                List<ElementHandle> takeActionOptionList = page.querySelectorAll(takeActionOptions);
                for (ElementHandle ActionOption : takeActionOptionList) {
                    if (ActionOption.innerText().equals(action)) {
                        ActionOption.scrollIntoViewIfNeeded();
                        ActionOption.click();
                        log("Clicked on " + action + " Action Button");
                    } else {
                        if (ActionOption.innerText().equals(action)) { // Use else if here
                            ActionOption.click();
                            waitForLocatorClickable(page, suggestSimplerPhraseOptions, 120);
                            List<ElementHandle> phraseList = page.querySelectorAll(suggestSimplerPhraseOptions);
                            for (ElementHandle phrase : phraseList) {
                                phrase.click();
                            }
                        }
                    }
                }
            }
        }
    }

    public void HandleClusterError() {
        if (isLocatorPresent(page, clusterError, 5)) {
            log("'Error : Cluster Error Occurred'");
            List<ElementHandle> addButtons = page.querySelectorAll(addAnywayBtn);
            for (ElementHandle handle : addButtons) {
                handle.scrollIntoViewIfNeeded();
                handle.click();
                log("Clicked on 'Add Anyway' Button");
            }
        }
    }

    public void createMoment(String MomentName, String Global, String emotion) {
        click(createMomentBtn);
        log("Clicked on Create Moment Button");
        try {
            fill(momentNameTxt, MomentName);
            log("Entered Moment Name '" + MomentName + "' in text Field");

            click(FilterBtn.replace("type", Global));
            log("Clicked on '" + Global + "'Button");

            click(FilterBtn.replace("type", emotion));
            log("Clicked on '" + emotion + "'Button");
        } catch (Exception e) {
            log("Error: An unexpected error occurred while Buttons");
        }
        try {
            click(selectConversationType);
            log("Clicked on Select Conversation Element ");

            click(conversationTypeDrp);
            log("Clicked on'Call' DropDown Options");

            click(callerType);
            log("Clicking on 'Select Dropdown'");

            click(selectAllCallerType);
            log("Clicking on 'Select All'");
        } catch (Exception e) {
            log("Error: An unexpected error occurred while Selecting filter values");
        }
        click(sideEle);
        conditionalMoment();
        clickNext();
    }

    public void clickNext() {
        waitFor(1);
        click(NextBtn);
        log("Clicked on 'Next' Button ");
    }

    public void conditionalMoment() {
        if (!isLocatorPresent(page, conditionalCheckBox, 5)) {
            scrollIntoView(page, conditionalCheckBox);
        }
        click(conditionalCheckBox);
        log("Clicked on the 'Conditional Moment' checkbox");

        click(selectParentMomentDrp);
        log("Clicked on the 'Select Parent Moment' dropdown");

        Locator parentMomentList = page.locator(parentList);
        parentMomentList.nth(0).click();
        log("Clicked on the first parent moment");
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
