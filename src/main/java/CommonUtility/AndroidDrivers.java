package CommonUtility;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import Utility.PropertyFileReaders;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AndroidDrivers extends WebBrowser{
	public static int PORT;
	public static String NODE_PATH;  
	public static String MAIN_JS;
	public static String IP;
	public static String URL;
	public static String DEVICE_NAME;
	public static String APPLICATION_PATH;
	public static String CHROME_DRIVER_PATH;
	public static String BROWSER_NAME;
	public static String AUTOMATION_NAME;
	public static String PLATFORM_NAME;
	public static String NEW_COMMAND_TIMEOUT;
	public static String PLATFORM_VERSION;
	public static String DEVICE_READY_TIMEOUT;
	public static String BASE_PKG;
	public static String APP_ACTIVITY;
	public static String EMAIL;
	public static String PASSWORD;
	public static String WEB_URL;
	private static DesiredCapabilities capabilities = new DesiredCapabilities();
	public static UiAutomator2Options options = new UiAutomator2Options();
	public static PropertyFileReaders config = new PropertyFileReaders();
	public static String propPath = "App";
	public static AndroidDriver androidDriver;
	public static Driver currentDriver;
	
	@BeforeSuite
	public void loadConfigProp() throws IOException {
		// SERVER CONFIGURATION
		PORT = Integer.parseInt(config.callzenProp(propPath).getProperty("appium.server.port"));
		NODE_PATH = config.callzenProp(propPath).getProperty("nodePath");
		MAIN_JS = config.callzenProp(propPath).getProperty("mainJs");
		IP = config.callzenProp(propPath).getProperty("ip");
		URL = config.callzenProp(propPath).getProperty("URL");
		// APPLICATION CONFIGURATION
		APPLICATION_PATH = config.callzenProp(propPath).getProperty("application.path");
		CHROME_DRIVER_PATH = config.callzenProp(propPath).getProperty("chromedriver.path");
		BASE_PKG = config.callzenProp(propPath).getProperty("base.pkg");
		APP_ACTIVITY = config.callzenProp(propPath).getProperty("application.activity");
		AUTOMATION_NAME = config.callzenProp(propPath).getProperty("automation.name");
		DEVICE_NAME = config.callzenProp(propPath).getProperty("device.name");
		BROWSER_NAME = config.callzenProp(propPath).getProperty("browser.name");
		PLATFORM_NAME = config.callzenProp(propPath).getProperty("platform.name");
		PLATFORM_VERSION = config.callzenProp(propPath).getProperty("platform.version");
		NEW_COMMAND_TIMEOUT = config.callzenProp(propPath).getProperty("new.command.timeout");
		DEVICE_READY_TIMEOUT = config.callzenProp(propPath).getProperty("device.ready.timeout");
		EMAIL = config.callzenProp(propPath).getProperty("user.email");
		PASSWORD = config.callzenProp(propPath).getProperty("user.password");
		WEB_URL=config.callzenProp("Web").getProperty("url");
	}

	public static UiAutomator2Options mobileBroserOptions() {
		options.setDeviceName(AndroidDrivers.DEVICE_NAME);
		options.setChromedriverExecutable(AndroidDrivers.CHROME_DRIVER_PATH);
		options.setCapability("browserName", AndroidDrivers.BROWSER_NAME);
		options.setPlatformName(AndroidDrivers.PLATFORM_NAME);
		options.setPlatformVersion(AndroidDrivers.PLATFORM_VERSION);
		options.setAutomationName(AndroidDrivers.AUTOMATION_NAME);
		return options;
	}

	public static DesiredCapabilities setCapabilities() {
		capabilities.setCapability("automationName", AndroidDrivers.AUTOMATION_NAME);
		capabilities.setCapability("platformName", AndroidDrivers.PLATFORM_NAME);
		capabilities.setCapability("platformVersion", AndroidDrivers.PLATFORM_VERSION);
		capabilities.setCapability("deviceName", AndroidDrivers.DEVICE_NAME);
		capabilities.setCapability("app", AndroidDrivers.APPLICATION_PATH);
		capabilities.setCapability("newCommandTimeout", AndroidDrivers.NEW_COMMAND_TIMEOUT);
		capabilities.setCapability("deviceReadyTimeout", AndroidDrivers.DEVICE_READY_TIMEOUT);
		capabilities.setCapability("appActivity", AndroidDrivers.APP_ACTIVITY);
		capabilities.setCapability("appPackage", AndroidDrivers.BASE_PKG);
		return capabilities;
	}

	public static UiAutomator2Options setUiAutomator2Options() {
		options.setDeviceName(AndroidDrivers.DEVICE_NAME);
		options.setApp(AndroidDrivers.APPLICATION_PATH);
		options.setPlatformName(AndroidDrivers.PLATFORM_NAME);
		options.setPlatformVersion(AndroidDrivers.PLATFORM_VERSION);
		options.setAutomationName(AndroidDrivers.AUTOMATION_NAME);
		options.setAppActivity(AndroidDrivers.APP_ACTIVITY);
		options.setAppPackage(AndroidDrivers.BASE_PKG);
		options.setChromedriverExecutable(AndroidDrivers.CHROME_DRIVER_PATH);
		options.setAutoGrantPermissions(true);
		return options; 
	}

	public static AppiumDriverLocalService appiumServer() {
		AppiumDriverLocalService server = new AppiumServiceBuilder().usingDriverExecutable(new File(NODE_PATH))
				.withAppiumJS(new File(MAIN_JS)).withIPAddress(IP).usingPort(PORT).build();
		return server;
	}
	
	public enum Driver {
		Android, WebDriver,Playwright
	}

}