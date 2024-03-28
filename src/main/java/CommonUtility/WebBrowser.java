package CommonUtility;

import com.microsoft.playwright.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import Utility.PropertyFileReaders;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebBrowser {
    protected static WebDriver driver;
    public static ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();
    private static ThreadLocal<Browser> threadLocalBrowser = new ThreadLocal<>();

    public static Browser getBrowser() {
        return threadLocalBrowser.get();
    }

    private static ThreadLocal<BrowserContext> threadLocalBrowserContext = new ThreadLocal<>();

    public static BrowserContext getBrowserContext() {
        return threadLocalBrowserContext.get();
    }

    private static ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();

    public static Page getPage() {
        return threadLocalPage.get();
    }

    private static ThreadLocal<Playwright> threadLocalPlaywright = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return threadLocalPlaywright.get();
    }

    public PropertyFileReaders config = new PropertyFileReaders();
    public static String webBrowser;
    public static Page page;
    public boolean isHeadless;

    public WebDriver getSeleniumDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            isHeadless = Boolean.parseBoolean(config.callzenProp("Web").getProperty("headless"));
            if (isHeadless) {
                System.out.println("Headless Chrome Browser Started");
                options.addArguments("--headless");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("autofill.profile_enabled", false);
                prefs.put("profile.default_content_setting_values.notifications", 2);
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--ignore-ssl-errors=yes");
                options.addArguments("--ignore-certificate-errors");
                options.setExperimentalOption("prefs", prefs);
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            } else {
                options.addArguments("--force-device-scale-factor=0.8");
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            isHeadless = Boolean.parseBoolean(config.callzenProp("Web").getProperty("headless"));
            if (isHeadless) {
                System.out.println("Headless Firefox Browser Started");
                options.addArguments("-headless");
            } else {
                options.addArguments("--width=800", "--height=600");
            }
            driver = new FirefoxDriver(options);
        }
        setupDriver(driver);
        return localDriver.get();
    }

    private void setupDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        localDriver.set(driver);
    }

    public static synchronized WebDriver getDriver() {
        return localDriver.get();
    }

    public Page getPage(String webBrowser) {
        threadLocalPlaywright.set(Playwright.create());
        switch (webBrowser.toLowerCase()) {
            case "chrome":
                threadLocalBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                        .setChannel("chrome").setHeadless(isHeadless)));
                break;
            case "chromium":
                threadLocalBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless)));
                break;
            case "firefox":
                threadLocalBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless((isHeadless))));
                break;

            default:
                System.out.println("please pass the right browser name......");
                break;
        }
        threadLocalBrowserContext.set(getBrowser().newContext());
        threadLocalPage.set(getBrowserContext().newPage());
        return getPage();
    }

}

