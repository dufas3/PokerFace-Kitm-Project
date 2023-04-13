import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
public class Footer {

    public static final By FOOTER_RESULTS = By.className("main-headline");

    public static void clickFooterLink(String text) {
        Setup.waitForElementToAppear(By.partialLinkText(text));
        WebElement footerLink = Setup.browser.findElement(By.partialLinkText(text));
        Setup.clickJavaScript(footerLink);
    }

    public static String getFooterLinkResults() {
        WebElement getFooterResults = Setup.browser.findElement(FOOTER_RESULTS);
        String resultsHeadingText = getFooterResults.getText();
        return resultsHeadingText;
    }
}
