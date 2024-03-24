package Utility;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    public static ExtentReports extentReports;

    public static ExtentReports createInstance(String fileName, String reportName, String documentTitle){
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
        extentSparkReporter.config().setReportName(reportName);
        extentSparkReporter.config().setDocumentTitle(documentTitle);
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setEncoding("utf-8");
        extentSparkReporter.config().setReportName("AUTOMATION REPORT");

        extentReports = new ExtentReports();

        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("UserName", "Milind G");
        extentReports.setSystemInfo("OS", "Ubuntu/Linux");
        extentReports.setSystemInfo("Os Vesrion", "22.04");
        extentReports.setSystemInfo("Java Version", "11.0.20");
        extentReports.setSystemInfo("Time", "Asia/Calcuta");
        return extentReports;
    }

}