import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerFunctionalityJunitTest {

    ArrayList<String>expectedUsersList;

    @Before
    public void setup() {
        Setup.launchMainBrowser();
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();

        expectedUsersList = new ArrayList<>();
        expectedUsersList.add("testemail@gmail.com");
        expectedUsersList.add("saule");
        expectedUsersList.add("Justas");
    }

    @Test
    public void getCheckMarkPlayerVotedTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("0");
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("Coffee");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("20");
        PlayerFunctionality.getCheckMarkElement("saule");
        String expectedResults = "fa-regular fa-circle-check text-primary";
        Assert.assertEquals("Checkmark did not appear next to the Player's name after the vote", PlayerFunctionality.getCheckMarkElement("saule"), expectedResults);
    }

    @Test
    public void comparePlayerCardsWithModeratorCardsPositiveTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("0");
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("Coffee");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        List<String> actualListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.getPossiblePayerVotingAreaCardsList();
        System.out.println("Moderator cards list: " + ModeratorSettings.getPossibleModeratorVotingAreaCardsList());
        System.out.println("Player cards list: " + PlayerFunctionality.getPossiblePayerVotingAreaCardsList());
        ArrayList<String> actualListPlayer = PlayerFunctionality.getPossiblePayerVotingAreaCardsList();
        Assert.assertEquals("Moderator cards list is different from Player cards list", actualListOfModeratorCards, actualListPlayer);
    }

    @Test
    public void getPlayerNameInTheListTest() {
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForNameResults(LoginLogout.FIELD_NAME);
        PlayerFunctionality.getPlayerVisibleInTheList(LoginLogout.FIELD_NAME);
        String expectedPlayerName = "saule";
        String actualPlayerName = PlayerFunctionality.getPlayerVisibleInTheList(LoginLogout.FIELD_NAME);
        Assert.assertEquals("Player is not shown in the list", expectedPlayerName, actualPlayerName);
    }

    @Test
    public void getAllUsersInTheListTest() {
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        PlayerFunctionality.getAllUsersInTheList();
        ArrayList<String> actualUsersList = PlayerFunctionality.getAllUsersInTheList();
        Assert.assertEquals("Acquirement of All Users List was unsuccessful", expectedUsersList, actualUsersList);
    }

    @Test
    public void getQuestionMarkPlayerNotVotedTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("0");
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("Coffee");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("20");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFlipCardsButton();
        PlayerFunctionality.getVotingResultAfterFlipCards("Justas");
        String expectedQuestionMarkResult = "?";
        String actualQuestionMarkResult = PlayerFunctionality.getVotingResultAfterFlipCards("Justas");
        Assert.assertEquals("Question mark is invisible", actualQuestionMarkResult, expectedQuestionMarkResult);
    }

    @Test
    public void getVotingResultNextToPlayerNameTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("0");
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("Coffee");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("8");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFlipCardsButton();
        PlayerFunctionality.getVotingResultAfterFlipCards("Justas");
        String expectedVotingResult = "8";
        String actualVotingResult = PlayerFunctionality.getVotingResultAfterFlipCards("Justas");
        Assert.assertEquals("Voting result is invisible", actualVotingResult, expectedVotingResult);
    }

    @Test
    public void getVoteIconBeforePlayerVotedTest() {
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        PlayerFunctionality.getVoteIconNextToNameBeforeVotes("Justas");
        String expectedSymbolResult = "vote-icon";
        String actualSymbolResult = PlayerFunctionality.getVoteIconNextToNameBeforeVotes("Justas");
        Assert.assertEquals("Vote icon is invisible", actualSymbolResult, expectedSymbolResult);
    }

    @After
    public void finish() {
        ModeratorSettings.resetPreviouslySavedValuesJavaScript();
        ModeratorSettings.resetAutoRevealJavaScript();
        Setup.closePage();
    }
}
