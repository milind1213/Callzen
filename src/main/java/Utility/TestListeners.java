package Utility;

import CommonUtility.AndroidDrivers;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestListeners extends AndroidDrivers implements ITestListener {
    public static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    public static String screenshotName;
    @Override
    public synchronized void onStart(ITestContext context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        String reportName = "Automation_Report_" + currentTime + ".html";
        String fullReportPath = System.getProperty("user.dir") + "//reports//" + reportName;
        extentReports = ExtentReportManager.createInstance(fullReportPath, "Automation Test Report", "Test Execution Report");
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = enhancedMethodName(result.getMethod().getMethodName());
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);
        System.out.println("[=========== Started Test method [" + methodName + "] ======]");
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        test.assignCategory(result.getTestContext().getSuite().getName());
        test.assignCategory(className);
        extentTest.set(test);
        extentTest.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String methodName = enhancedMethodName(result.getMethod().getMethodName());
        String className = result.getMethod().getRealClass().getSimpleName();
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        if (className.toLowerCase().contains("api")) {
            extentTest.get().fail(MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
            String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
            stackTrace.replaceAll(",", "<br>");
            String formattedTrace = "<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occurred: Click to View"
                    + "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>"
                    + " \n";
            extentTest.get().fail(formattedTrace);
        } else {
            try {
                captureScreenshot(methodName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            extentTest.get()
                    .fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occurred: Click to View"
                            + "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>"
                            + " \n");
            extentTest.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of Failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotName).build());
        }
        String failureLog =  "<b>" + "Test Case: '" + methodName + "' Failed \uD83D\uDE12\uD83D\uDE12\uD83D\uDE12 " + "</b>";
        Markup m = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, m);

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        String methodName = enhancedMethodName(result.getMethod().getMethodName());
        String logText = "<b>" + "Test Case: '" + methodName + "' Passed \uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE0A " + "</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().pass(m);

        // extentTest.get().log(Status.PASS, "Test Passed");
    }
    @Override
    public synchronized  void onTestSkipped(ITestResult result) {
        String methodName = enhancedMethodName(result.getMethod().getMethodName());
        String logText = "<b>" + "Test Case: '" + methodName + " Skipped" + "</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.INDIGO);
        extentTest.get().skip(m);

    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("[=========== Finish Test method [" + context.getName() + "] ======]");
        if (extentReports != null)
            extentReports.flush();


      }

    public synchronized static void captureScreenshot(String methodName) throws IOException {
        File scrFile;
        screenshotName = "Failed_"+methodName + ".jpg";
        switch (currentDriver) {
            case Android:
                scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//reports//" + screenshotName));
                FileUtils.copyFile(scrFile, new File(".\\reports\\" + screenshotName));
                break;
            case WebDriver:
                scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//reports//" + screenshotName));
                FileUtils.copyFile(scrFile, new File(".\\reports\\" + screenshotName));
                break;
            case Playwright:
               getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get("reports", screenshotName)));
                break;
            default:
                throw new IllegalArgumentException("Invalid driver type");
          }
       }

    private String enhancedMethodName(String str) {
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = true; // Capitalize the first letter
        for (char c : str.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(capitalizeNext ? Character.toUpperCase(c) : Character.toLowerCase(c));
                capitalizeNext = false;
            } else if (sb.length() > 0) { // Add a space before the next character if not the first
                sb.append(' ');
                capitalizeNext = true; // Capitalize the letter after the space
            }
        }
        return sb.toString().trim();
    }





}
