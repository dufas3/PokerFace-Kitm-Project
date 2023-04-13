import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Header {

    public static final By FESTO_LOGO = By.id("logolink");
    public static final By LOGO_RESULTS = By.className("headline--YLOqq");
    public static final By HEADER_RESULTS = By.cssSelector("#root > div.App > div > div > div.info > h2");

    public static void clickUserIconLogin(String text) {
        Setup.waitForElementToAppear(By.partialLinkText(text));
        WebElement userIconLogin = Setup.browser.findElement(By.partialLinkText(text));
        Setup.clickJavaScript(userIconLogin);
    }

    public static void clickFestoLogo() {
        Setup.waitForElementToAppear(FESTO_LOGO);
        WebElement festoLogo = Setup.browser.findElement(FESTO_LOGO);
        Assert.assertTrue("Festo logo is invisible", festoLogo.isDisplayed());
        Setup.clickJavaScript(festoLogo);
    }

    public static String getHeaderIconResults() {
        WebElement getHeaderResults = Setup.browser.findElement(HEADER_RESULTS);
        return getHeaderResults.getText();
    }

    public static String getFestoLogoResults() {
        WebElement getLogoResults = Setup.browser.findElement(LOGO_RESULTS);
        String resultsHeadlineText = getLogoResults.getText();
        return resultsHeadlineText;
    }
}
