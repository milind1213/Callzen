
package TestBase;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import Utility.TestListeners;
import com.microsoft.playwright.Page;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import CommonUtility.AndroidDrivers;
import CommonUtility.WebBrowser;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterMethod;

public class BaseTest extends AndroidDrivers {
    public Logger logger = LogManager.getLogger(this.getClass());

    public void log(String message) {
        logger.info(message);
    }

    public AndroidDriver getAndroidDriver() throws Exception {
        if (!appiumServer().isRunning()) {
            appiumServer().start();
            log("Appium Server Started");
        }
        androidDriver = new AndroidDriver(new URL(URL), setUiAutomator2Options());
        log("Launched Application");
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        currentDriver = Driver.Android;
        return androidDriver;
    }

    public WebDriver getWebDriver() {
        webBrowser = config.callzenProp("Web").getProperty("browser");
        driver = new WebBrowser().getSeleniumDriver(webBrowser);
        if (webBrowser.equals("firefox")) {
            log("Firefox Browser Launched");
        } else {
            log("Chrome Browser Launched");
        }
        currentDriver = Driver.WebDriver;
        driver.get(WEB_URL);
        log("Navigated to: " + WEB_URL);
        return driver;
    }

    public Page getPlaywrightPage() {
        webBrowser = config.callzenProp("Web").getProperty("browser");
        try {
            page = new WebBrowser().getPage(webBrowser);
            log("Successfully Launched '" + webBrowser + "' Browser");
            currentDriver = Driver.Playwright;
            page.setViewportSize(1366,768);
            page.evaluate("document.body.style.zoom = 0.8;");
            page.navigate(WEB_URL);
            log("Navigated to: " + WEB_URL);
        } catch (Exception e) {
            log("Failed to launch Browser: " + e.getMessage());
        }
        return page;
    }


    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        try {
            if (page != null) {
                page.close();
                log("Successfully '" + webBrowser + "' Browser closed");
            }
        } catch (Exception e) {
            log("Failed to close'" + webBrowser + "'browser");
        }
    }

   // @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        if (androidDriver != null) {
            androidDriver.quit();
            log("Application Closed");
        }
        if (driver != null) {
            driver.quit();
            log("WebBrowser Closed");
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitServer() {
        if (appiumServer() != null && appiumServer().isRunning()) {
            appiumServer().stop();
            log("Appium Server Stopped");
        }
    }


    public void logs(String message) {
        String timestamp = new SimpleDateFormat("h:mm:ss a").format(new Date());
        ExtentTest extentTest = TestListeners.extentTest.get();
        if (extentTest != null) {
            extentTest.log(Status.PASS, message);
        }
        System.out.println("[" + timestamp + "] " + "INFO: " + message);
    }


    public static String randomMobileNumber() {
        return "665"+RandomStringUtils.randomNumeric(4)+"321";
    }

    public static String randomEmail() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase()+"@g.com";
    }

    public static String randomName() {
        return "Test_Moments_"+RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }


}
