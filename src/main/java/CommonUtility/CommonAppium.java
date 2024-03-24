package CommonUtility;

import org.openqa.selenium.By;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class CommonAppium {
	public Logger logger = LogManager.getLogger(this.getClass());
	AndroidDriver driver;
	public CommonAppium(AndroidDriver driver) {
		this.driver = driver;
	}
	public void log(String message) {
		logger.info(message);
	}

	public void doSwipe(AppiumDriver driver, Point start, Point end, int duration) {
		PointerInput FINGER = new PointerInput(PointerInput.Kind.TOUCH, "FINGER");
		Sequence swipe = new Sequence(FINGER, 1)
				.addAction(FINGER.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.getX(),
						start.getY()))
				.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
				.addAction(new Pause(FINGER, Duration.ofMillis(200)))
				.addAction(FINGER.createPointerMove(Duration.ofMillis(duration), PointerInput.Origin.viewport(),
						end.getX(), end.getY()))
				.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(Collections.singletonList(swipe));
	}

	public void swipeDown(int numberOfTimes, AppiumDriver driver) {
		for (int i = 0; i < numberOfTimes; i++) {
			Dimension size = driver.manage().window().getSize();
			int startX = size.getWidth() / 2, endX = startX;
			int startY = size.getHeight() / 2, endY = (int) (size.getHeight() * 0.25);
			Point startPoint = new Point(startX, startY);
			Point endPoint = new Point(endX, endY);
			doSwipe(driver, startPoint, endPoint, 1000);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void switchToContext(String contextName) {
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			if (context.startsWith(contextName)) {
				driver.context(context);
				System.out.println(driver.context(context));
				break;
			}
		}
	}

	public void scrollToElement(AndroidDriver driver, By elementLocator) {
		HashMap<String, Object> scrollObject = new HashMap<>();
		scrollObject.put("strategy", "xpath");
		scrollObject.put("locator", driver.findElement(elementLocator));
		((JavascriptExecutor) driver).executeScript("mobile: scrollTo", scrollObject);
	}

	public void scrollIntoViewByText(String text) {
		String scroll = String.format(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"%s\"));",
				text);
		driver.findElement(AppiumBy.androidUIAutomator(scroll));
	}

	public void longPressAction(WebElement ele) {
		try {
			((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
					ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to perform long press action on element: [" + ele + "]");
		}
	}

	public void swipeAction(WebElement ele, String direction) {
		try {
			((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("elementId",
					((RemoteWebElement) ele).getId(), "direction", direction, "percent", 0.75));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to perform swipe action on element: [" + ele + "] with direction: [" + direction + "]");
		}
	}

	public void rotateDevice(DeviceRotation deviceRotation) {
		try {
			driver.rotate(deviceRotation);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to rotate device with rotation: [" + deviceRotation + "]");
		}
	}

	public void rotateToPortrait() {
		try {
			DeviceRotation portrait = new DeviceRotation(0, 0, 0);
			driver.rotate(portrait);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to rotate device to portrait mode");
		}
	}

	public void waitForWebElementToBeClickable(WebElement element, Duration seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click: [" + element + "]");
		}
	} 

	public WebElement waitForElementToBeVisible(WebElement element, Duration seconds) {
		return new WebDriverWait(driver, seconds).pollingEvery(Duration.ofMillis(250))
				.until(ExpectedConditions.visibilityOf(element));
	} 

	public void waitForElementToAppear(WebElement ele, AppiumDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains((ele), "text", "Cart"));
	}

	public int getElementSize(List<WebElement> webElement) {
		wait(1);
		return webElement.size();
	}

	public void clickWithtWait(WebElement element) {
		try {
			wait(1);
			element.click();
			wait(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click: [" + element + "]");
		}
	}

	public void click(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click : [" + element + "]");
		}
	}

	public void sendKeys(WebElement locators, String text) {
		try {
			wait(1);
			locators.sendKeys(text);
			wait(1);
			hideKeyboard();
		} catch (Exception e) {
			e.printStackTrace();
			hideKeyboard();
			Assert.fail("Failed to send text: [" + text + "]");
		}

	}

	public void wait(int seconds) {
		waitForProgressBarDisappear();
		int milliseconds = seconds * 1000;
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			Assert.fail("Failed to wait for [" + seconds + "] seconds... ");
		}
	}

	public boolean isTextDisplayed(String text) {
		wait(2);
		boolean result = false;
		try {
			WebElement element = driver.findElement(By.xpath("//*[contains(text(), '" + text + "')]"));
			if (element.isDisplayed()) {
				wait(1);
				result = true;
			}
		} catch (NoSuchElementException | TimeoutException ex) {
			wait(1);
			result = false;
		}
		return result;
	}

	public boolean isElementDisplayed(WebElement webelement) {
		wait(2);
		boolean result = false;
		try {
			if (webelement.isDisplayed()) {
				wait(1);
				result = true;
			}
		} catch (Exception ex) {
			wait(1);
			result = false;
		}
		return result;
	}

	public boolean isElementPresent(WebElement webElement) {
		try {
			return webElement.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
			return false;
		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void waitForWebElement(WebElement webElement, Duration i) {
		try {
			WebDriverWait webDriverWait = new WebDriverWait(driver, i);
			webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Assert.fail("Failed to find element [" + webElement + "] even after waiting for [" + i + "] seconds.");
		}
	}

	public String waitForProgressBarDisappear() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.app.nobrokerhood:id/progressBar")));
			Thread.sleep(2);
			return "Done";
		} catch (Exception e) {
			return "Progress bar/loading icon is still appearing even after waiting for 120 sec.";
		}
	}

	public void pressDeviceHomeButton() {
		try {
			wait(1);
			KeyEvent keyEvent = new KeyEvent();
			driver.pressKey(keyEvent.withKey(AndroidKey.HOME));
			wait(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to press home button of device.");
		}
	}

	public void pressBackKey(int times) {
		try {
			for (int i = 1; i <= times; i++) {
				KeyEvent keyEvent = new KeyEvent();
				wait(1);
				driver.pressKey(keyEvent.withKey(AndroidKey.BACK));
				wait(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to back..");
		}
	}

	public void hideKeyboard() {
		wait(1);
		try {
			if (driver.isKeyboardShown()) {
				driver.hideKeyboard();
				wait(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to hide keyboard..");
		}
	}

	public String getText(WebElement element) {
		try {
			wait(1);
			return element.getText();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to get text from element: [" + element + "]");
			throw e;
		}
	}

}
