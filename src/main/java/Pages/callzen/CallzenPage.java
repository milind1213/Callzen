package Pages.callzen;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import CommonUtility.CommonSelenium;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static Pages.callzen.Locators.*;

public class CallzenPage extends CommonSelenium {
    WebDriver driver;

    public CallzenPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    public String getMomentDetails() {
        driver.navigate().refresh();
        waitForElementToBeClickable(driver, By.xpath(momentsTable), 3);
        List<WebElement> columns = driver.findElements(By.xpath(momentsTable));
        StringBuilder rowData = new StringBuilder();
        for (int i = 1; i < columns.size(); i++) {
            String a = getText(By.xpath("//tbody/tr[1]/td[" + i + "]"));
            rowData.append(a).append(",");
        }
        return rowData.toString();
    }

    public void clickNext() {
        waitFor(1);
        click(By.xpath(NextBtn));
        log("Clicking on 'Next' button");
    }

    public void clickNotifyMe() throws Exception {
        clickNext();
        Thread.sleep(3000);
        click(By.cssSelector(notifyBtn));
        log("Clicked on 'Notify Me' Button");
    }
    public void clickFinish() {
        waitFor(1);
        click(By.xpath(finishBtn));
        log("Clicking on 'Finish' button");
    }


    public void submitFeedback() {
        click(By.cssSelector(giveFeedBackBtn));
        log("Clicked on 'Feedback' button");
        submitMomentsFeedback();
        if (isElementPresent(By.xpath(feedbackPositivePositive))) {
            submitMomentsFeedback();
        }
        String msg = getText(By.cssSelector(".nb__25gBr"));
        Assert.assertEquals(msg, "Success! Moment feedback submitted successfully");
        clickNext();
    }

    public void bulkMomentTagging(String startDate, String endDate) {
        log("Clicking on Tag from Now Button");
        click(By.xpath(tagFromNowBtm));

        LocalDate parsedStartDate = LocalDate.parse(startDate.substring(0, 8), DateTimeFormatter.ofPattern("ddMMyyyy"));
        LocalDate parsedEndDate = LocalDate.parse(endDate.substring(0, 8), DateTimeFormatter.ofPattern("ddMMyyyy"));
        while (true) {
            log("Entering from Bulk Tagging " + startDate);
            sendKeys(By.xpath(bulkTagFromDateTime), startDate);

            log("Entering from Bulk Tagging To" + endDate);
            sendKeys(By.xpath(bulkTagToDateTime), endDate);

            if (isWebElementPresent(By.xpath(totalCallToBeAffected), 3)) {
                int call = Integer.parseInt(getText(By.xpath(totalCallToBeAffected)));
                if (call == 0) {
                    parsedStartDate = parsedStartDate.minusDays(1);
                    startDate = parsedStartDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + startDate.substring(8);
                    endDate = parsedEndDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + endDate.substring(8);
                } else {
                    break; // Exit the loop if call is not 0
                }
            } else {
                String warningMsg = getText(By.xpath(callLimitWarnings));
                String text = warningMsg.replaceAll("[^0-9,]", "");
                String[] str = text.split(",");
                int totalCalls = Integer.parseInt(str[0]);
                int callLimit = Integer.parseInt(str[1]);
                if (totalCalls > callLimit) {
                    parsedStartDate = parsedStartDate.plusDays(1);
                    startDate = parsedStartDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + startDate.substring(8);
                } else {
                    click(By.xpath(submitBtn));
                    log("Clicked on submit Button");
                }
            }
        }
        click(By.xpath(submitBtn));
        log("Clicked on submit Button");
        clickFinish();
    }

    public void deleteMoments() {
        waitForElementToBeClickable(By.xpath(editAccordion), 3);
        click(By.xpath(editAccordion));
        log("Clicked on the 'Accordion' Button");

        click(By.cssSelector(deleteMomentBt));
        log("Clicked on the 'Delete' Button");

        String name = getText(By.xpath(momentsNameText));
        System.out.println(name);
        sendKeys(By.cssSelector(textBox), name);
        log("Entered the 'MomentName' in TextBox");

        click(By.xpath(confirmBtn));
        log("Clicked on 'Confirm' button");
    }

    public String getDeletedText() throws Exception {
        if(!isElementPresent(By.xpath(deleteSuccessMsg))) {
            wait(3);
        }
        String msg = getText(By.xpath(deleteSuccessMsg));
        return msg;
    }

    public void submitMomentsFeedback() {
        try {
            waitForElementToBeClickable(driver, By.xpath(feedbackPositivePositive), 10);
            List<WebElement> positiveList = driver.findElements(By.xpath(feedbackPositivePositive));
            for (WebElement ele : positiveList) {
                try {
                    ele.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException ex) {
                    handleEleClickInterException(driver, ele);
                }
            }
            click(By.xpath(submitBtn));
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging purposes
        }
    }

    public void conditionalMoment1(){
        if(!isElementPresent(By.xpath(conditionalCheckBox))){
        scrollIntoView(driver,By.xpath(conditionalCheckBox));
        }
        click(By.xpath(conditionalCheckBox));
        log("Clicked on the 'Conditional Moment' checkbox");

        click(By.cssSelector(selectParentMomentDrp));
        log("Clicked on the 'Select Parent Moment' dropdown");

        List<WebElement> parentMomentList = driver.findElements(By.xpath(parentList));
        click(parentMomentList.get(0));
        log("Clicked on the first parent moment");
    }


    public void conditionalMoment(){
        if(!isElementPresent(By.xpath(conditionalCheckBox))) {
            scrollDown();
        }
        click(By.xpath(conditionalCheckBox));
        log("Clicked on the 'Conditional Moment' checkbox");

        click(By.cssSelector(selectParentMomentDrp));
        log("Clicked on the 'Select Parent Moment' dropdown");

        List<WebElement> parentMomentList = driver.findElements(By.xpath(parentList));
        click(parentMomentList.get(0));
        log("Clicked on the first parent moment");
    }
    public String getMomentCreatedText() {
        waitForElementDisplay(driver, By.xpath(momentCreatedSuccessfullyLLoc), 3);
        String msg = driver.findElement(By.xpath(momentCreatedSuccessfullyLLoc)).getText();
        return msg;
    }


    public void TakeAction_PhraseSuggestions(String action) {
        if (isElementPresent(By.xpath(takeActionBtn))) {
            List<WebElement> takeActionList = driver.findElements(By.xpath(takeActionBtn));
            for (int k = 0; k < takeActionList.size(); k++) {
                takeActionList.get(k).click();
                log("Clicked on Take Action Button ");
                List<WebElement> list = driver.findElements(By.xpath(takeActionOptions));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getText().equals(action)) {
                        list.get(i).click();
                        log("Clicking on " + action + " Action Button ");
                    } else if (list.get(i).getText().equals(action)) {
                        list.get(i).click();
                        log("Clicking on " + action + " Action Button ");
                        waitForElementToBeClickable(driver, By.xpath(suggestSimplerPhraseOptions), 120);
                        List<WebElement> elements = driver.findElements(By.xpath(suggestSimplerPhraseOptions));
                        for (int j = 0; j < elements.size(); j++) {
                            elements.get(j).click();
                        }
                    }
                }
            }
        } else {
            log("Take Action Button not present ");
        }
    }
    public void HighlightSentence_Action() {
        try {
            if (isElementPresent(By.xpath(highlight))) {
                List<WebElement> list = driver.findElements(By.xpath(highlight));
                for (int i = 0; i < list.size(); i++) {
                    click(By.xpath(highlightCross));
                    log("Clicked on the 'Highlight Cross' Button");
                }
            } else {
                log("Highlight Button Not not present");
            }
        } catch (Exception e) {
            log("Highlight Button Not not present");
        }
    }

    public List<String> EnterKeywordDetails(String type, List<String> keywords) {
        List<String> result = new ArrayList<String>();
        waitForElementToBeClickable(By.xpath(momentType.replace("name", type)), 5);
        click(By.xpath(momentType.replace("name", type)));
        try {
            for (int i = 0; i < keywords.size(); i++) {
                String keyword = keywords.get(i); // Get the keyword at index i
                sendKeys(By.cssSelector(keywordSearchBar), keyword);
                result.add(keyword);
                driver.findElement(By.cssSelector(keywordSearchBar)).sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            log("Failed to Enter Keywords in SearchBar :" + e.getMessage());
        }
        return result;
    }

    public List<String> EnterKeywordDetails1(String type, List<String> keywords) throws Exception {
        List<String> result = new ArrayList<String>();
        waitForElementToBeClickable(By.xpath(momentType.replace("name", type)), 5);
        click(By.xpath(momentType.replace("name", type)));
        try {
            click(By.cssSelector(".css-1wy0on6"));
            log("Clicked on the 'Search Accordion'");
            List<WebElement> list = driver.findElements(By.xpath(keywordSearchType));
            for (WebElement element : list) {
                String text = element.getText();
                if (text.equals("Contains All")) {
                    element.click();
                }
            }
        } catch (Exception e) {
            log("Failed to Select Dropdown Options");
        }
        try {
            for (int i = 0; i < keywords.size(); i++) {
                String keyword = keywords.get(i); // Get the keyword at index i
                sendKeysWithWait(By.cssSelector(keywordSearchBar), keyword, 5);
                result.add(keyword);
                driver.findElement(By.cssSelector(keywordSearchBar)).sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            log("Failed to Enter Keywords in SearchBar :" + e.getMessage());
        }
        return result;
    }

    public List<String> getKeywordSearchDetails() {
        waitForElementToBeClickable(driver, By.xpath(keywordSamplePhraseBtn), 5);
        click(By.xpath(keywordSamplePhraseBtn));
        try { //click(By.cssSelector(refreshResultBtn));// click(By.cssSelector(clearResultsBtn));

            waitForElementToBeClickable(By.xpath(keywordSamplePhraseList), 3);
            List<WebElement> samplePhrases = driver.findElements(By.xpath(keywordSamplePhraseList));
            List<String> list = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                list.add(samplePhrases.get(i).getText());
            }
            clickNext();
            return list;
        } catch (Exception e) {
            e.printStackTrace(); // or log the exception
            return new ArrayList<>(); // or handle the exception as needed
        }
    }

    public void clickSaveAsDraft() {
        waitForElementToBeClickable(By.xpath(saveAsDraftBtn), 5);
        click(By.xpath(saveAsDraftBtn));
    }

    public void enterNextMomentDetails(String types, List<String> searchTextList, List<String> keywords, String url) {
        waitForElementToBeClickable(By.xpath(momentType.replace("name", types)), 5);
        click(By.xpath(momentType.replace("name", types)));
        WebElement element = driver.findElement(By.cssSelector(searchTextBoxInstructional));
        int searchTextSize = searchTextList.size();
        for (int i = 0; i < searchTextSize; i++) {
            String txt = searchTextList.get(i);
            sendKeys(By.cssSelector(searchTextBoxInstructional), txt);
            waitFor(1);
            if (isElementPresent(driver, By.xpath(urlInputField))) {
                sendKeys(By.xpath(urlInputField), url);
                click(By.xpath(submitBtn));
            } else {
                click(By.xpath(addMoreAudioBtn));
                sendKeys(By.xpath(urlInputField), url);
                click(By.xpath(submitBtn));
            }
            if (i != searchTextSize - 1) {
                element.sendKeys(Keys.CONTROL + "a");
                element.sendKeys(Keys.DELETE);
            }
            for (String keyword : keywords) {
                    sendKeys(By.cssSelector(additionalTextbox), keyword);
                    waitFor(1);
                    driver.findElement(By.cssSelector(additionalTextbox)).sendKeys(Keys.ENTER);
            }
        }
    }

    public String getCDROutputText(String result) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<WebElement> list = driver.findElements(By.xpath(presentTxt.replace("txt",result)));
        for (WebElement ele : list) {
            sb.append(ele.getText()).append(",");
        }
        clickNotifyMe();
        return sb.toString();
    }



    public void selectPhrases(String types, String sentence, int selectCount, String action) throws InterruptedException {
        waitForElementToBeClickable(By.xpath(momentType.replace("name", types)),5);
        click(By.xpath(momentType.replace("name", types)));

        WebElement searchBarElement = driver.findElement(By.cssSelector(searchBar));
        log(" Clicked on '" + types + "' and Entered the search " + sentence + "");
        searchBarElement.sendKeys(sentence);

        searchBarElement.sendKeys(Keys.ENTER);
        log("Pressed the 'Enter' Key ");
        waitFor(2);
        waitForElementToBeClickable(By.cssSelector(suggetionsList), 120);
        List<WebElement> list = driver.findElements(By.cssSelector(suggetionsList));
        int elementsToClick = Math.min(selectCount, list.size());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Create wait object once
        for (int i = 0; i < elementsToClick; i++) {
            WebElement elementToClick = list.get(i);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
            } catch (StaleElementReferenceException e) {
                elementToClick = driver.findElement(By.cssSelector(suggetionsList + ":nth-child(" + (i + 1) + ")"));
                wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
            }
        }
        HighlightSentence_Action();
        clickNext();
        try {
            TakeAction_PhraseSuggestions(action);
        } catch (Exception e) {
            if (isElementPresent(driver, By.xpath(clusterError))) {
                List<WebElement> errorList = driver.findElements(By.xpath(clusterError));
                for (int i = 0; i < errorList.size(); i++) {
                    click(By.xpath(addAnywayBtn));
                    log("Clicking on 'Add Anyway' Button");
                }
            }
        }
        clickNext();
    }

    public void selectHeaderOption(String header) throws Exception {
        waitFor(5);
        String headerLocators = headerLocator.replace("menu", header);
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(headerLocators))).click();
                log("Header option '" + header + "' selected successfully.");
                break;
            } catch (Exception e) {
                log("Error: An unexpected error occurred while selecting '" + header + "'.");
                driver.navigate().refresh();
                waitFor(3);
            }
            if (attempt < maxAttempts) {
                log("Attempt " + attempt + " failed. Retrying...");
                Thread.sleep(3000); // Adjust retry wait time as needed
            }
        }
    }

    public void createMoment(String MomentName, String Global, String emotion) {
        waitForElementToBeClickable(driver, By.cssSelector(createMomentBtn), 5);
        click(By.cssSelector(createMomentBtn));
        log("Clicked on Create Moment Button");
        try {
            sendKeys(By.xpath(momentNameTxt), MomentName);
            log("Entered Moment Name '" + MomentName + "' in text Field");

            click(By.xpath(FilterBtn.replace("type", Global)));
            log("Clicked on '" + Global + "'Button");

            click(By.xpath(FilterBtn.replace("type", emotion)));
            log("Clicked on '" + emotion + "'Button");
        } catch (Exception e) {
            log("Error: An unexpected error occurred while Buttons");
        }
        try {
            click(By.xpath(selectConversationType));
            log("Clicked on Select Conversation Element ");

            click(By.xpath(conversationTypeDrp));
            log("Clicked on'Call' DropDown Options");

            click(By.xpath(callerType));
            log("Clicking on 'Select Dropdown'");

            click(By.xpath(selectAllCallerType));
            log("Clicking on 'Select All'");
        } catch (Exception e) {
            log("Error: An unexpected error occurred while Selecting filter values");
        }
        click(By.xpath(sideEle));
        conditionalMoment();
        clickNext();
    }

    public void createDetailsPage(String MomentName, String Global, String emotion) {
        waitFor(3);
        javascriptClick(By.cssSelector(createMomentBtn));
        log("Clicked on Create Moment Button");
        try {
            sendKeys(By.xpath(momentNameTxt), MomentName);
            log("Entered Moment Name '" + MomentName + "' in text Field");

            click(By.xpath(FilterBtn.replace("type", Global)));
            log("Clicked on '" + Global + "'Button");

            click(By.xpath(FilterBtn.replace("type", emotion)));
            log("Clicked on '" + emotion + "'Button");
        } catch (Exception e) {
            log("Error: An unexpected error occurred while Buttons");
        }
        try {
            click(By.xpath(selectConversationType));
            log("Clicked on Select Conversation Element ");

            click(By.xpath(conversationTypeDrp));
            log("Clicked on'Call' DropDown Options");

            click(By.xpath(callerType));
            log("Clicking on 'Select Dropdown'");

            click(By.xpath(selectAllCallerType));
            log("Clicking on 'Select All'");
        } catch (Exception e) {
            log("Error: An unexpected error occurred while Selecting filter values");
        }
        click(By.xpath(sideEle));
        clickNext();
    }

    public void createMomentWithTeamFilters(String MomentName, String emotion) {
        waitForElementToBeClickable(driver,By.cssSelector(createMomentBtn),5);
        click(By.cssSelector(createMomentBtn));
        log("Clicked on Create Moment Button");

        sendKeys(By.xpath(momentNameTxt), MomentName);
        log("Entered Moment Name '" + MomentName + "' in text Field");

        click(By.xpath(defaultSelectedBtn));
        log("Clicked on 'Filtered' Button");

        click(By.xpath(FilterBtn.replace("type", emotion)));
        log("Clicked on '" + emotion + "'Button");

        click(By.xpath(selectConversationType));
        log("Clicked on Select Conversation Element ");

        click(By.xpath(conversationTypeDrp));
        log("Clicked on'Call' DropDown Options");
    }

    public void selectTeamDetails(String scope, List<String> teamNames, List<String> campaignNames, List<String> processNames) {
        if (scope.equals("Team")) {
            try {
                click(By.xpath(FilterBtn.replace("type", scope)));
                log("Clicking on '" + scope + "'Button");
                click(By.xpath(selectTeamDrp));
                log("Clicking on 'Select Dropdown'");
                List<WebElement> teams = driver.findElements(By.xpath(teamList));
                for (String teamName : teamNames) {
                    for (WebElement team : teams) {
                        String name = team.getText();
                        if (name.equals(teamName)) {
                            team.click();
                            log("Selecting '" + teamName + "'Team");
                            break;
                        }
                    }
                }
                click(By.xpath(callerType));
                log("Clicking on 'Select Dropdown'");

                click(By.xpath(selectAllCallerType));
                log("Clicking on 'Select All'");
            } catch (Exception e) {
                log("Error: An Error occurred with Team Filters : " + e.getMessage());
            }

        } else if (scope.equals("Filter")) {
            try {
                log("Clicking on '" + scope + "'Button");
                click(By.xpath(FilterBtn.replace("type", scope)));
                click(By.xpath(callFilterDrp.replace("txt", "Select Campaign Name")));
                List<WebElement> list = driver.findElements(By.xpath(campaignNameList));
                for (String campaign : campaignNames) {
                    for (WebElement ele : list) {
                        String name = ele.getText();
                        if (campaign.equals(name)) {
                            ele.click();
                            log("Selecting '" + campaign + "'Team");
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                log("Error: An Error occurred in CampaignName Selection Filters : " + e.getMessage());
            }
            click(By.xpath(callFilterDrp.replace("txt", "Select Mode of calling")));
            log("Clicking on 'Select Mode of calling' Dropdown");
            click(By.id(selectAllCallingMode));
            log("Clicking on 'Select All'");
            try {
                click(By.xpath(callFilterDrp.replace("txt", "Select Process Name")));
                List<WebElement> processList = driver.findElements(By.xpath(processNameList));
                for (WebElement process : processList) {
                    String name = process.getText();
                    for (String prcName : processNames) {
                        if (prcName.equals(name)) {
                            process.click();
                            log("Selecting '" + prcName + "'Process");
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                log("Error: An Error occurred in  ProcessName Selection : " + e.getMessage());
            }
            click(By.xpath(callFilterDrp.replace("txt", "Select Caller Type")));
            log("Clicking on 'Select Mode of calling' Dropdown");

            click(By.id(selectAllCallingMode));
            log("Clicking on 'Select All'");
            click(By.xpath("//p[.='Campaign Name *']"));
        }
            conditionalMoment();
            clickNext();
    }


    public void callTeamsSearchSelectFilter(String teamName) {
        click(By.xpath(callsTeamFilterBtn));
        log("Clicking on'Select Dropdowns");

        sendKeys(By.xpath(searchFild), teamName);
        log("Entering  TeamName'" + teamName + "'In Searchbar");

        click(By.xpath(checkBox));
        log("Selecting Team By clicking on 'CheckBox'");
        click(By.xpath(sideClick));
    }

    public String teamValidation() {
        String seletedTeam = driver.findElement(By.xpath(selectedTeamloc)).getText();
        return seletedTeam;
    }

    public void login(String email, String pass) {
        driver.findElement(By.id(emaillocator)).sendKeys(email);
        log("User Entering  Email '" + email + "'");

        driver.findElement(By.id(password)).sendKeys(pass);
        log("User Entering  Password '" + pass + "'");

        click(By.xpath(signIn));
        log("Clicking on Sign In Button");
    }

    public String getDefaultAppliedFilters() {
        StringBuilder appliedFilters = new StringBuilder();
        List<WebElement> appliedFiltersList = driver.findElements(By.xpath(defalultCall));
        for (WebElement ele : appliedFiltersList) {
            appliedFilters.append(ele.getText()).append(", ");
        }
        String appliedFilterString = appliedFilters.toString().trim();
        return appliedFilterString.trim();
    }

    public String getTotalNumberOfTeam() {
        click(By.xpath(selectedTeamloc));
        By teamsLoc = By.xpath("//*[@class='nb__H8r3d']/p[contains(text(),'Teams')]");
        String teams = driver.findElement(teamsLoc).getText();
        System.out.println(teams);
        return teams;
    }

    public String getSelectedNumberOfTeam() {
        click(By.xpath(selectedTeamloc));
        By teamsLoc = By.xpath("//*[@class='nb__H8r3d']/p[contains(text(),'Selected')]");
        String selectedTeams = driver.findElement(teamsLoc).getText();
        System.out.println(selectedTeams);
        return selectedTeams;
    }


}
