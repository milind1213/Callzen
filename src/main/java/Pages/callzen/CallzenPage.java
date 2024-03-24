package Pages.callzen;

import java.util.List;

import com.microsoft.playwright.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import CommonUtility.CommonSelenium;

import static Pages.callzen.Locators.*;

public class CallzenPage extends CommonSelenium {
    WebDriver driver;

    public CallzenPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    By emaillocator = By.id("email");
    By password = By.id("password");
    By signIn = By.xpath("//button[@type='submit' and   text()='Sign In'] ");
    By defalultCall = By.xpath("//*[@ID='cz-filter-chips']//div");
    By callsTeamFilterBtn = By.xpath("//button[@type='button' and @class='nb__3-Zp-']");
    By searchFild = By.xpath("//input[@placeholder='Search']");
    By checkBox = By.xpath("//input[@type='checkbox']");
    By sideClick = By.xpath("//*[@class='nb__1BVSZ']");
    By selectedTeamloc = By.xpath("//*[@class='nb__ObcR9']//button[1]");


    public void submitFeedback() {
        click(By.cssSelector(giveFeedBackBtn));
        log("Clicked on feedback button");
    }

    public String getMomentCreatedText() {
        try {
            waitForElementDisplay(driver, By.xpath(momentCreatedSuccessfullyLLoc), 3);
            String msg = driver.findElement(By.xpath(momentCreatedSuccessfullyLLoc)).getText();
            return msg;
        } catch (Exception e) {
            System.out.println("Failed to Extract text");
        }
    }

    public void phraseAction(String action) {
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

    public void highlightAction() {
        if (isElementPresent(By.xpath(highlight))) {
            List<WebElement> list = driver.findElements(By.xpath(highlight));
            for (int i = 0; i < list.size(); i++) {
                click(By.xpath(highlightCross));
                log("Clicked on the 'Highlight Cross' Button");
            }
        } else {
            log("Highlight Button Not not present");
        }
    }

    public void selectPhrases(String types, String sentence, int selectCount, String action) throws InterruptedException {
        click(By.xpath(momentType.replace("name", types)));

        WebElement searchBarElement = driver.findElement(By.cssSelector(searchBar));
        log(" Clicked on '" + types + "' and Entered the search " + sentence + "");
        searchBarElement.sendKeys(sentence);

        searchBarElement.sendKeys(Keys.ENTER);
        log("Pressed the 'Enter' Key ");

        waitForElementToBeClickable(By.cssSelector(suggetionsList), 120);
        List<WebElement> list = driver.findElements(By.cssSelector(suggetionsList));
        for (int i = 0; i < selectCount; i++) {
            list.get(i).click();
        }
        highlightAction();
        click(By.xpath(NextBtn));

        phraseAction(action);
        click(By.xpath(NextBtn));
    }


    public String selectHeaderOption(String header) {
        String headerLocators = headerLocator.replace("menu", header);
        try {
            click(driver.findElement(By.xpath(headerLocators)));
            return "Header option '" + header + "' selected successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to select header option '" + header + "'.";
        }
    }


    public void createMoment(String MomentName, String Global, String emotion) {
        click(By.xpath(createMomentBtn));
        log("Clicked on Create Moment Button");

        sendKeys(By.xpath(momentNameTxt), MomentName);
        log("Entered Moment Name '" + MomentName + "' in text Field");

        click(By.xpath(FilterBtn.replace("type", Global)));
        log("Clicked on '" + Global + "'Button");

        click(By.xpath(FilterBtn.replace("type", emotion)));
        log("Clicked on '" + emotion + "'Button");

        click(By.xpath(selectConversationType));
        log("Clicked on Select Conversation Element ");

        click(By.xpath(conversationTypeDrp));
        log("Clicked on'Call' DropDown Options");

        click(By.xpath(NextBtn));
        log("Clicked on'Next' Button");
    }


    public void callTeamsSearchSelectFilter(String teamName) {
        click(callsTeamFilterBtn);
        log("Clicking on'Select Dropdowns");

        sendKeys(searchFild, teamName);
        log("Entering  TeamName'" + teamName + "'In Searchbar");

        click(checkBox);
        log("Selecting Team By clicking on 'CheckBox'");
        click(sideClick);
    }

    public String teamValidation() {
        try {
            String seletedTeam = driver.findElement(selectedTeamloc).getText();
            return seletedTeam;
        } catch (Exception e) {
            System.out.println("Failed to Extract text");
        }
    }

    public void login(String email, String pass) {
        driver.findElement(emaillocator).sendKeys(email);
        log("User Entering  Email '" + email + "'");

        driver.findElement(password).sendKeys(pass);
        log("User Entering  Password '" + pass + "'");

        click(signIn);
        log("Clicking on Sign In Button");
    }

    public String getDefaultAppliedFilters() {
        StringBuilder appliedFilters = new StringBuilder();
        List<WebElement> appliedFiltersList = driver.findElements(defalultCall);
        for (WebElement ele : appliedFiltersList) {
            appliedFilters.append(ele.getText()).append(", ");
        }
        String appliedFilterString = appliedFilters.toString().trim();
        return appliedFilterString.trim();
    }

    public String getTotalNumberOfTeam() {
        click(selectedTeamloc);
        By teamsLoc = By.xpath("//*[@class='nb__H8r3d']/p[contains(text(),'Teams')]");
        String teams = driver.findElement(teamsLoc).getText();
        System.out.println(teams);
        return teams;
    }

    public String getSelectedNumberOfTeam() {
        click(selectedTeamloc);
        By teamsLoc = By.xpath("//*[@class='nb__H8r3d']/p[contains(text(),'Selected')]");
        String selectedTeams = driver.findElement(teamsLoc).getText();
        System.out.println(selectedTeams);
        return selectedTeams;
    }


}
