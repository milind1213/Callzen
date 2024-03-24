package CommonUtility;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Map;

import static Utility.TestListeners.extentTest;

public class CommonPlaywright {
        public Page page;
        public CommonPlaywright(Page page) {
            this.page = page;
        }



    public static boolean isLocatorPresent(Page page, String selector, int timeoutSeconds) {
        try {
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutSeconds * 1000));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
        public void type(String loc, String text) {
            Locator locator = page.locator(loc);
            locator.evaluate("element => element.style.border = '2px solid red'");
            locator.type(text);
        }
        public void click(String loc) {
            Locator locator = page.locator(loc);
            locator.evaluate("element => element.style.border = '2px solid red'");
            locator.click();
        }
        public void fill(String loc, String text) {
            Locator locator = page.locator(loc);
            locator.evaluate("element => element.style.border = '2px solid red'");
            locator.fill(text);
        }

        public void pressEnter() {
            page.keyboard().press("Enter");
        }
        public boolean elementExists(String locatorString) {
            return page.locator(locatorString).first() != null;
        }

        public String getText(String locatorString) {
            return page.locator(locatorString).innerText();
        }

        public void waitFor(int seconds) {
            try {
                Thread.sleep(1000* seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public static void waitForElement(Page page, String selector, int seconds) throws InterruptedException{
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(seconds * 1000));
        }

    protected static void addLogsExtentReport(String label, Map<String,Object> log) {
        extentTest.get().info(MarkupHelper.createLabel(label, ExtentColor.PURPLE));
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : log.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        extentTest.get().info(MarkupHelper.createLabel(stringBuilder.toString(), ExtentColor.TRANSPARENT));
    }


    }
