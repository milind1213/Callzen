package Pages.callzen;

public interface Locators {
    String emailField = "#email";
    String passwordField = "#password";
    String loginBtn = "//button[@type='submit' and text()='Sign In']";
    String headerLocator = "//*[@class='nb__3eMzz']//following-sibling::span[text()='menu']";
    String columnFilterBtn = "(//*[@class='nb__1hXpJ']//button)[1]";
    String SearchBar = "//input[@placeholder='Search']";
    String AgentTableColumn = "//tr/th[1]//p";
    String checkListCoverageColumn = "//div[@class='nb__1w2q6']";
    String actionsColumn = "//div[@class='nb__1HF0I']";
    String checkedColumns = "//input[@type='checkbox' and @checked]/ancestor::label";
    String clearTextIcon = "//button[@class='nb__2ti88 nb__2nIpP']//*[name()='svg']";

    //  Moments  Locators
    String createMomentBtn = "//*[text()='Create Moment']";
    String momentNameTxt = "//input[@placeholder='Moment Name']";
    String momentDescriptionTxt = "//input[@placeholder='Moment Description']";
    String FilterBtn = " //*[@class='nb__1CN7y']//span[text()='type']";
    String FiltersBtn = "//div[@class='nb__1CN7y']";
    String defaultSelectedBtn = "//div[@class='nb__29zAy']";
    String selectConversationType = "//div[@class='css-1hwfws3']";
    String conversationTypeDrp = "(//*[@class='css-11unzgr']//div[contains(@id,'react-select')])[1]";
    String NextBtn = "//button[.='Next']";
    String saveAsDraftBtn = "//button[.='Save as Draft']";
    String submitBtn = "//button[.='Submit']";
    String finishBtn = "//button[.='Finish']";
    String momentType = "//*[@class='nb__2cBxG']//following-sibling :: div/span[text()='name']";
    String searchBar = ".nb__1sWR8";
    String deepSearch = "//span[@class='nb__1Huv-']//span[@class='nb__2ZZ7J nb__1C4CE']";
    String suggetionsList = ".nb__ubuSu";
    String takeActionBtn = "//*[contains(@class,'nb__pfbMZ') or contains(text(),'Take Action')]";
    String takeActionOptions = "//*[@class='nb__3Z6hf']//button";
    String suggestSimplerPhraseOptions = "//*[@class='nb__2PJM7']//div";
    String highlight ="//button[normalize-space()='Highlight' and @class='nb__eRfg7']";
    String nonHighlight ="//*[@class='nb__3yg6v']";
    String highlightSentece = ".nb__hNzpp";
    String highlightCross ="//span[@class='nb__2avRm']";
    String highlightText = "//*[@class='nb__2c7Hy']";
    String highlightSelectCross = "//*[@class='nb__1gqCK']";
    String momentCreatedSuccessfullyLLoc = "//div[@class='nb__1ztHS']";
    String momentTaggingFeedback = "//*[@class='nb__BsCcc']";
    String giveFeedBackBtn = ".nb__2DiTj";
    String feedbackSentences = ".nb__12p6U";
    String feedbackPositivePositive = "(//*[@class='nb__1iXN6']//span[1])[3]";
    String feedbackPositiveNegative = "(//*[@class='nb__1iXN6']//span[2])[3]";
    String feedbackSubmittedSuccessfully = ".nb__25gBr";
    String tagFromNowBtm = "//*[@class='nb__284LV']//*[@class='nb__2ZZ7J nb__1C4CE']";
    String bulkTagFromDateTime = "#viewByfrom";
    String bulkTagToDateTime = "#viewByto";
    String momentsTable = "//tbody/tr[1]/td[3]";
    String actionOnBtn = ".nb__2ZZ7J";
    String actionOffBtn = ".nb__1QqmO";

}
