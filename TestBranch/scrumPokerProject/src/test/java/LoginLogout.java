import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginLogout {

    public static final String FIELD_NAME = "saule";
    public static final String FIELD_NAME_SECOND = "Justas";
    public static final String FIELD_NAME_THIRD = "Antanas";
    public static final String FIELD_NAME_MAX_SYMBOLS = "Wsnklksjeiwocms01#&laqoksm";
    public static final String FIELD_NAME_MIN_SYMBOLS = "Li";
    public static final String FIELD_NAME_EMPTY = "";

    public static final String EMAIL_CORRECT = "testemail@gmail.com";
    public static final String EMAIL_INCORRECT = "testemailgmail.com";
    public static final String SECOND_EMAIL = "testemail10@gmail.com";

    public static final String PASSWORD_CORRECT = "testpassword123";
    public static final String PASSWORD_INCORRECT = "testpass";

    public static final By ENTER_BUTTON = By.id("joinbutton");
    public static final By NAME_FIELD = By.id("loginname");
    public static final By EMAIL_FIELD = By.id("emailenter");
    public static final By PASSWORD_FIELD = By.id("passwordenter");
    public static final By LOGIN_BUTTON = By.id("loginbutton");
    public static final By LOGIN_NAME_RESULT = By.id("dropdown-basic");
    public static final By LOGIN_ERROR_MESSAGE = By.cssSelector(".error-text.text-danger");
    public static final By LOGOUT_BUTTON = By.id("logoutbutton");

    public static void loginPlayer(String name) {
        Setup.waitForElementToAppear(NAME_FIELD);
        WebElement nameField = Setup.browser.findElement(NAME_FIELD);
        Assert.assertTrue("Player name field is inactive and/or invisible", nameField.isEnabled() && nameField.isDisplayed());
        nameField.sendKeys(name);
    }

    public static void clickEnterPlayerButton() {
        Setup.waitForElementToAppear(ENTER_BUTTON);
        WebElement enterButton = Setup.browser.findElement(ENTER_BUTTON);
        Assert.assertTrue("Enter button is invisible", enterButton.isDisplayed());
        enterButton.click();
    }

    public static void enterEmail(String email) {
        Setup.waitForElementToAppear(EMAIL_FIELD);
        WebElement emailField = Setup.browser.findElement(EMAIL_FIELD);
        Assert.assertTrue("Email field is inactive and/or invisible", emailField.isEnabled() && emailField.isDisplayed());
        emailField.sendKeys(email);
    }

    public static void enterPassword(String password) {
        Setup.waitForElementToAppear(PASSWORD_FIELD);
        WebElement passwordField = Setup.browser.findElement(PASSWORD_FIELD);
        Assert.assertTrue("Password field is inactive and/or invisible", passwordField.isEnabled() && passwordField.isDisplayed());
        passwordField.sendKeys(password);
    }

    public static void loginModerator(String email, String password){
        enterEmail(email);
        enterPassword(password);
        submitLoginForm();
    }

    public static void submitLoginForm() {
        Setup.waitForElementToAppear(LOGIN_BUTTON);
        WebElement loginButton = Setup.browser.findElement(LOGIN_BUTTON);
        Assert.assertTrue("Login button is invisible", loginButton.isDisplayed());
        loginButton.click();
    }

    public static void waitForLoginResults() {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(LOGIN_NAME_RESULT));
    }

    public static void waitForNameResults(String text) {
        WebElement name = Setup.browser.findElement(LOGIN_NAME_RESULT);
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15)).until(ExpectedConditions.textToBePresentInElement(name,text));
    }

    public static String getLoginResults() {
        WebElement loginName = Setup.browser.findElement(LOGIN_NAME_RESULT);
        String resultsText = loginName.getText();
        return resultsText;
    }

    public static String getUnsuccessfulLoginMessage() {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(LOGIN_ERROR_MESSAGE));
        WebElement loginErrorMessage = Setup.browser.findElement(LOGIN_ERROR_MESSAGE);
        String errorMessageText = loginErrorMessage.getText();
        System.out.println(errorMessageText);
        return errorMessageText;
    }

    public static void pressLoginNameButton() {
        By loginResult = LOGIN_NAME_RESULT;
        new WebDriverWait(Setup.browser, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(loginResult));
        WebElement loginNameButton = Setup.browser.findElement(LOGIN_NAME_RESULT);
        Assert.assertTrue("Login User name button located in Header is invisible", loginNameButton.isDisplayed());
        loginNameButton.click();
    }

    public static void pressLogoutButton() {
        LoginLogout.waitForLoginResults();
        WebElement logoutButton = Setup.browser.findElement(LOGOUT_BUTTON);
        Assert.assertTrue("Logout button located in Header is invisible", logoutButton.isDisplayed());
        logoutButton.click();
    }

    public static String getButtonColor(By elementSelector){
        String buttonColor = Setup.browser.findElement(elementSelector).getCssValue("background");
        System.out.println("Button color: " + buttonColor);
        return buttonColor;
    }
}
