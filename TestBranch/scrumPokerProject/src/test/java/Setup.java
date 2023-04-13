import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Setup {

    public static WebDriver browser;
    public static WebDriver mainBrowser;
    public static WebDriver alternativeBrowser;
    public static WebDriver thirdBrowser;
    public static WebDriver fourthBrowser;
    public static final String POKER_URL = "https://pokerface-dev.azurewebsites.net/Login";
    public static final String URL_ROOM_PLAYER = "https://pokerface-dev.azurewebsites.net/Poker?roomId=92aabb76-af53-4652-a2cc-16d7ce4dfe76";
    public static final String URL_FESTO = "https://www.festo.com/us/en/";

    public static WebDriver setup() {
        String browserType = System.getenv("BROWSER");

        WebDriver browser;
        if ("edge".equals(browserType)) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            browser = new EdgeDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            browser = new ChromeDriver(options);
        }
        browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return browser;
    }

    public static void waitForElementToAppear(By elementSelector) {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(20), Duration.ofMillis(50)).until(e -> e.findElement(elementSelector).isDisplayed());
    }

    public static void clickJavaScript(WebElement element) {
        ((JavascriptExecutor) browser).executeScript("arguments[0].click()", element);
    }

    public static void acceptFestoCookies() {
        By buttonSelector = By.xpath("//button[contains(text(),'Accept All Cookies')]");
        new WebDriverWait(Setup.browser, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(buttonSelector));
        Setup.browser.findElement(buttonSelector).click();
    }

    public static void launchMainBrowser() {
        if (mainBrowser == null) {
            mainBrowser = setup();
            mainBrowser.get(POKER_URL);
        }
        browser = mainBrowser;
    }

    public static void launchAlternativeBrowser() {
        if (alternativeBrowser == null) {
            alternativeBrowser = setup();
            alternativeBrowser.get(URL_ROOM_PLAYER);
        }
        browser = alternativeBrowser;
    }

    public static void launchThirdBrowser() {
        if (thirdBrowser == null) {
            thirdBrowser = setup();
            thirdBrowser.get(URL_ROOM_PLAYER);
        }
        browser = thirdBrowser;
    }

    public static void launchFourthBrowser() {
        if (fourthBrowser == null) {
            fourthBrowser = setup();
            fourthBrowser.get(POKER_URL);
        }
        browser = fourthBrowser;
    }

    public static void closePage() {
        if (mainBrowser != null) mainBrowser.close();
        if (alternativeBrowser != null) alternativeBrowser.close();
        if (thirdBrowser != null) thirdBrowser.close();
        if (fourthBrowser != null) fourthBrowser.close();
        mainBrowser = null;
        alternativeBrowser = null;
        thirdBrowser = null;
        fourthBrowser = null;
    }
}




