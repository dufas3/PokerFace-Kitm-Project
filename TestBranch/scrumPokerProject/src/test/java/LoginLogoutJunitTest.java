import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginLogoutJunitTest {

    @Before
    public void setup() {
        Setup.launchMainBrowser();
    }

    @Test
    public void loginPlayerPositiveTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForNameResults(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.getLoginResults();
        String actualPlayerName = LoginLogout.getLoginResults();
        System.out.println("result" + LoginLogout.getLoginResults());
        String expectedPlayerName = "Justas";
        Assert.assertEquals("Player did not login", expectedPlayerName, actualPlayerName);
    }

    @Test
    public void loginPlayerNegativeTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        String expectedResults = "Business";
        String actualResults = LoginLogout.getLoginResults();
        Assert.assertNotEquals("Player's name is Business, please check", expectedResults, actualResults);
    }

    @Test
    public void loginPlayerEmptyNameUnsuccessfulMessageTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_EMPTY);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.getUnsuccessfulLoginMessage();
        String expectedMessageResults = "Please enter username!";
        String actualMessageResults = LoginLogout.getUnsuccessfulLoginMessage();
        Assert.assertEquals("Player with empty name logged in successfully or Error message is different", expectedMessageResults, actualMessageResults);
    }

    @Test
    public void loginPlayerNameMaxSymbolsTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_MAX_SYMBOLS);
        LoginLogout.clickEnterPlayerButton();
        String expectedNameResult = "Wsnklksjeiwocms01#&laqoksm";
        String actualNameResult = LoginLogout.getLoginResults();
        Assert.assertNotEquals("Player with name 26 symbols long logged in successfully", expectedNameResult, actualNameResult);
    }

    @Test
    public void loginPlayerNameMinSymbolsTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_MIN_SYMBOLS);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.getUnsuccessfulLoginMessage();
        String expectedMessageResults = "Username is too short!";
        String actualMessageResults = LoginLogout.getUnsuccessfulLoginMessage();
        Assert.assertEquals("Player with name 2 symbols long logged in successfully or Error message is different", expectedMessageResults, actualMessageResults);
    }

    @Test
    public void loginPlayerSameNameTwiceTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.getUnsuccessfulLoginMessage();
        String actualMessage = LoginLogout.getUnsuccessfulLoginMessage();
        String expectedMessage = "This username is taken!";
        Assert.assertEquals("Player with already taken name logged in successfully or Error message is different", expectedMessage, actualMessage);
    }

    @Test
    public void loginModeratorPositiveTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        LoginLogout.waitForNameResults("testemail@gmail.com");
        String actualModeratorResults = LoginLogout.getLoginResults();
        String expectedModeratorResults = "testemail@gmail.com";
        Assert.assertEquals("Moderator did not login", expectedModeratorResults, actualModeratorResults);
    }

    @Test
    public void loginModeratorNegativeTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        LoginLogout.getLoginResults();
        String expectedResults = "Business";
        Assert.assertNotEquals("Moderator's name is Business, please check", expectedResults, LoginLogout.getLoginResults());
    }

    @Test
    public void loginModeratorIncorrectPasswordTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_INCORRECT);
        LoginLogout.getUnsuccessfulLoginMessage();
        String actualErrorMessage = LoginLogout.getUnsuccessfulLoginMessage();
        String expectedErrorMessage = "Wrong username or password!";
        Assert.assertEquals("Moderator with incorrect password logged in successfully or Error message is different", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void loginModeratorIncorrectEmailTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_INCORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.getUnsuccessfulLoginMessage();
        String actualErrorMessage = LoginLogout.getUnsuccessfulLoginMessage();
        String expectedErrorMessage = "Wrong username or password!";
        Assert.assertEquals("Moderator with incorrect email logged in successfully or Error message is different", expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void logoutUserPlayerPositiveTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.pressLoginNameButton();
        LoginLogout.pressLogoutButton();
        String currentUrl = Setup.browser.getCurrentUrl();
        Assert.assertEquals("Player did not log out", Setup.POKER_URL, currentUrl);
    }

    @Test
    public void logoutUserPlayerNegativeTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        String currentUrl = Setup.browser.getCurrentUrl();
        LoginLogout.pressLoginNameButton();
        LoginLogout.pressLogoutButton();
        Assert.assertNotEquals("Player logged out", Setup.POKER_URL, currentUrl);
    }

    @Test
    public void logoutUserModeratorPositiveTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        Setup.waitForElementToAppear(LoginLogout.LOGIN_NAME_RESULT);
        LoginLogout.pressLoginNameButton();
        LoginLogout.pressLogoutButton();
        String currentUrl = Setup.browser.getCurrentUrl();
        Assert.assertEquals("Moderator did not log out", Setup.POKER_URL, currentUrl);
    }

    @Test
    public void logoutUserModeratorNegativeTest() {
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        String currentUrlLoggedIn = Setup.browser.getCurrentUrl();
        LoginLogout.pressLoginNameButton();
        LoginLogout.pressLogoutButton();
        Assert.assertNotEquals("Moderator logged out", Setup.POKER_URL, currentUrlLoggedIn);
    }

    @Test
    public void getLoginButtonColorBlueTest() {
        String actualColor = LoginLogout.getButtonColor(LoginLogout.LOGIN_BUTTON);
        String expectedColor = "rgb(240, 240, 240) none repeat scroll 0% 0% / auto padding-box border-box";
        Assert.assertEquals("Login button color is not Blue", expectedColor, actualColor);
    }

    @Test
    public void getEnterButtonColorBlueTest() {
        Setup.launchAlternativeBrowser();
        String actualColor = LoginLogout.getButtonColor(LoginLogout.ENTER_BUTTON);
        String expectedColor = "rgb(240, 240, 240) none repeat scroll 0% 0% / auto padding-box border-box";
        Assert.assertEquals("Enter button color is not Blue", expectedColor, actualColor);
    }

    @After
    public void finish() {
        Setup.closePage();
    }
}