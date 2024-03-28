package Pages.callzen;

public interface Locators {
    String emaillocator = "email";
    String password = "password";
    String signIn = "//button[@type='submit' and   text()='Sign In']";
    String defalultCall = "//*[@ID='cz-filter-chips']//div";
    String callsTeamFilterBtn = "//button[@type='button' and @class='nb__3-Zp-']";
    String searchFild = "//input[@placeholder='Search']";
    String checkBox = "//input[@type='checkbox']";
    String sideClick = "//*[@class='nb__1BVSZ']";
    String selectedTeamloc = "//*[@class='nb__ObcR9']//button[1]";
    //
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

    String createMomentBtn = ".nb__3RG9i ";
    String momentNameTxt = "//input[@placeholder='Moment Name']";
    String momentDescriptionTxt = "//input[@placeholder='Moment Description']";
    String FilterBtn = " //*[@class='nb__1CN7y']//span[text()='type']";
    String FiltersBtn = "//div[@class='nb__1CN7y']";
    String defaultSelectedBtn = "//div[@class='nb__29zAy']";
    String selectTeamDrp = "//span[text()='Select your team']";
    String teamDrpOptions = "//div[@class='nb__1reWz']//descendant::span[text()='team']";
    String callerType = "//span[.='Select Caller Type']";
    String conditionalCheckBox = "//input[@name='isFollowUp']";
    String selectParentMomentDrp = ".css-zpleb7-placeholder";
    String parentList = "//*[@class='css-11unzgr']//div[contains(@id,'react-select')]";
    String sideEle  ="//*[contains(@class,'nb__3TU_R') and contains(text(),'Moment Details')]";
    String selectAllCallerType = "//input[@id='selectAll']";
    String teamList = "//div[@class='nb__1reWz']//descendant::span";
    String selectConversationType = "//div[@class='css-1hwfws3']";
    String callFilterDrp = "//span[contains(.,'txt')]";
    String campaignNameList  = "//div[@class='nb__1reWz']//descendant::span";
    String processNameList = "//*[@class='nb__1reWz']//span";
    String selectAllCallingMode = "selectAll";
    String conversationTypeDrp = "(//*[@class='css-11unzgr']//div[contains(@id,'react-select')])[1]";
    String NextBtn = "//button[.='Next']";
    String saveAsDraftBtn = "//button[.='Save as Draft']";
    String submitBtn = "//button[.='Submit']";
    String finishBtn = "//button[.='Finish']";
    String momentType = "//*[@class='nb__2cBxG']//following-sibling :: div/span[text()='name']";
    String searchBar = ".nb__1sWR8";
    String searchTextBoxInstructional = ".nb__3I1_e";
    String additionalTextbox = ".nb__1v545";
    String  testAudioResponseBtn = ".nb__3t7nY";
    String recommendationBtn = ".nb__2bPBx";
    String urlInputField = "//input[@placeholder='Paste the Callzen URL link here']";
    String addMoreAudioBtn ="//button[.='Add more audio ']";
    String presentTxt ="//span[contains(@class,'nb__1f34Q') and contains(text(),'txt')]";
    String deleteIconInstructional="//button[@class='nb__3_6ba']//*[name()='svg']";
    String reloadInstructional = "//button[@class='nb__1F0PC']//*[name()='svg']";
    String notifyBtn =  ".nb__24TyP";




    String keywordSearchBar = ".nb__1jb_K";
    String keywordSamplePhraseBtn = "//button[.='View Sample Phrases']";
    String keywordSamplePhraseList = "//*[@class='nb__1GHeM']/div/span";
    String clearResultsBtn = ".nb__29i7Q";
    String refreshResultBtn = ".nb__2H0_r";
    String refreshResultIcon = "//button[@class='nb__2ti88 nb__2nIpP']//*[name()='svg']";

    String deepSearch = "//span[@class='nb__1Huv-']//span[@class='nb__2ZZ7J nb__1C4CE']";
    String suggetionsList = ".nb__ubuSu";
    String takeActionBtn = "//button[contains(@id, 'popover')]";
    String takeActionOptions = "//*[@class='nb__3Z6hf']//button";
    String suggestSimplerPhraseOptions = "//*[@class='nb__2PJM7']//div";
    String highlight ="//button[normalize-space()='Highlight' and @class='nb__eRfg7']";
    String nonHighlight ="//*[@class='nb__3yg6v']";
    String highlightSentence = ".nb__hNzpp";
        String clusterError ="//*[contains(@class,'nb__38VA4') and contains(text(),'Cluster Error .')]";
    String addAnywayBtn ="//*[contains(@class,'nb__2UUJ2') and contains(text(),'Add Anyway')]";
    String deleteBtn="//*[contains(@class,'nb__-Jnb7') and contains(text(),'Delete')]";
    String highlightCross ="//span[@class='nb__2avRm']//*[name()='svg']";
    String highlightText = "//*[@class='nb__2c7Hy']";
    String highlightSelectCross = "//*[@class='nb__1gqCK']";
    String momentCreatedSuccessfullyLLoc = "//div[@class='nb__1ztHS']";
    String momentTaggingFeedback = "//*[@class='nb__BsCcc']";
    String giveFeedBackBtn = ".nb__2DiTj";
    String feedbackSentences = ".nb__12p6U";
    String feedbackPositivePositive = "//*[@class='nb__1iXN6']//span[1]";
    String feedbackPositiveNegative = "(//*[@class='nb__1iXN6']//span[2])[3]";
    String feedbackSubmittedSuccessfully = ".nb__25gBr";

    String tagFromNowBtm = "//*[@class='nb__284LV']//*[@class='nb__2ZZ7J nb__1C4CE']";
    String bulkTagFromDateTime = "viewByfrom";
    String bulkTagToDateTime = "viewByto";
    String bulkTagFromDateTimeLoc = "//*[@id='viewByfrom']";
    String bulkTagToDateTimeLoc = "//*[@id='viewByto']";



    String totalCallToBeAffected = "//*[@class='nb__3jYg4']//p";
    String callLimitWarnings = "//*[@class='nb__1zGx4']";
    String momentsTable = "//tbody/tr[1]/td";
    String actionOnBtn = ".nb__2ZZ7J";
    String actionOffBtn = ".nb__1QqmO";

    String editAccordion = "//tbody/tr[1]/td[6]//button";
    String deleteMomentBt = ".nb__2ID8Y";
    String momentsNameText = "//div[@class='nb__2RQRc']//p/span";
    String textBox = ".nb__34FH4";
    String confirmBtn = "//button[text()='Confirm']";
    String deleteSuccessMsg = "//p[@class='nb__1qEe-']";
    String keywordSearchType = "//*[@class='css-11unzgr']//div[contains(@id,'react-select')]";



}
