package CommonUtility.playwrightUtils;
import com.microsoft.playwright.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DriverFactory {

    private static final ThreadLocal<Map<String, Browser>> browserMap = new ThreadLocal<>();

    private static final Map<String, Supplier<Page>> driverMap = new HashMap<>();

    private static final Supplier<Page> chromium = () -> {
        Map<String, Browser> threadBrowsers = browserMap.get();
        if (threadBrowsers == null) {
            threadBrowsers = new ConcurrentHashMap<>();
            browserMap.set(threadBrowsers);
        }

        Browser browser = threadBrowsers.get("CHROME");
        if (browser == null) {
            browser = Playwright.create().chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            threadBrowsers.put("CHROME", browser);
        }

        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        page.navigate("https://beta-new.callzen.ai/"); // Navigate to the desired URL
        return page;
    };

    private static final Supplier<Page> chromiumHeadless = () -> {
        Map<String, Browser> threadBrowsers = browserMap.get();
        if (threadBrowsers == null) {
            threadBrowsers = new ConcurrentHashMap<>();
            browserMap.set(threadBrowsers);
        }
        Browser browser = threadBrowsers.get("CHROME_HEADLESS");
        if (browser == null) {
            browser = Playwright.create().chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(true));
            threadBrowsers.put("CHROME_HEADLESS", browser);
        }
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        return page;
    };

    private static final Supplier<Page> firefoxDriver = () -> {
        Map<String, Browser> threadBrowsers = browserMap.get();
        if (threadBrowsers == null) {
            threadBrowsers = new ConcurrentHashMap<>();
            browserMap.set(threadBrowsers);
        }
        Browser browser = threadBrowsers.get("FIREFOX");
        if (browser == null) {
            browser = Playwright.create().firefox()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            threadBrowsers.put("FIREFOX", browser);
        }
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        return page;
    };

    static {
        driverMap.put("CHROME", chromium);
        driverMap.put("FIREFOX", firefoxDriver);
        driverMap.put("CHROME_HEADLESS", chromiumHeadless);
    }
    public static final Page getDriver(String type) {
        return driverMap.get(type).get();
    }
}
