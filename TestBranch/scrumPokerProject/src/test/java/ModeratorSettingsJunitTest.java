import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ModeratorSettingsJunitTest {

    ArrayList<String> cardCheckBoxValuesExpected;
    ArrayList<String> cardOptionsExpected;
    ArrayList<String> specificCardOptionsExpected;

    @Before
    public void setup() {
        Setup.launchMainBrowser();
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();

        cardCheckBoxValuesExpected = new ArrayList<>();
        cardCheckBoxValuesExpected.add("Use all cards");
        cardCheckBoxValuesExpected.add("0");
        cardCheckBoxValuesExpected.add("1/2");
        cardCheckBoxValuesExpected.add("1");
        cardCheckBoxValuesExpected.add("2");
        cardCheckBoxValuesExpected.add("3");
        cardCheckBoxValuesExpected.add("5");
        cardCheckBoxValuesExpected.add("8");
        cardCheckBoxValuesExpected.add("13");
        cardCheckBoxValuesExpected.add("20");
        cardCheckBoxValuesExpected.add("40");
        cardCheckBoxValuesExpected.add("100");
        cardCheckBoxValuesExpected.add("?");
        cardCheckBoxValuesExpected.add("Coffee");
        cardCheckBoxValuesExpected.add("Auto Reveal");

        cardOptionsExpected = new ArrayList<>();
        cardOptionsExpected.add("0");
        cardOptionsExpected.add("1/2");
        cardOptionsExpected.add("1");
        cardOptionsExpected.add("2");
        cardOptionsExpected.add("3");
        cardOptionsExpected.add("5");
        cardOptionsExpected.add("8");
        cardOptionsExpected.add("13");
        cardOptionsExpected.add("20");
        cardOptionsExpected.add("40");
        cardOptionsExpected.add("100");
        cardOptionsExpected.add("?");
        cardOptionsExpected.add("Coffee");

        specificCardOptionsExpected = new ArrayList<>();
        specificCardOptionsExpected.add("3");
        specificCardOptionsExpected.add("8");
        specificCardOptionsExpected.add("20");
        specificCardOptionsExpected.add("Coffee");
    }

    @Test
    public void getPossibleCardCheckboxValuesTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.getPossibleCardCheckboxValuesList();
        ArrayList<String> actualCardCheckboxValuesList = ModeratorSettings.getPossibleCardCheckboxValuesList();
        System.out.println("actual list: " + ModeratorSettings.getPossibleCardCheckboxValuesList());
        Assert.assertEquals("Actual Card Checkbox values list does not meet the requirements", cardCheckBoxValuesExpected, actualCardCheckboxValuesList);
    }

    @Test
    public void selectAllCardsTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("Use all cards");
        ModeratorSettings.checkIfCardCheckboxIsMarked("Use all cards");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        List<String> actualListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Assert.assertEquals("All cards are not selected", cardOptionsExpected, actualListOfModeratorCards);
    }

    @Test
    public void selectSpecificCardsTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickAndValidateCardCheckbox("3");
        ModeratorSettings.clickAndValidateCardCheckbox("8");
        ModeratorSettings.clickAndValidateCardCheckbox("20");
        ModeratorSettings.clickAndValidateCardCheckbox("Coffee");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        List<String> actualListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Assert.assertEquals("Specific cards are not selected", specificCardOptionsExpected, actualListOfModeratorCards);
    }

    @Test
    public void checkIfActiveVotingSessionResetAfterNewValuesSelectedTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickAndValidateCardCheckbox("3");
        ModeratorSettings.clickAndValidateCardCheckbox("5");
        ModeratorSettings.clickAndValidateCardCheckbox("8");
        ModeratorSettings.clickAndValidateCardCheckbox("13");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        List<String> firstListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("8");
        PlayerFunctionality.getCheckMarkElement("saule");
        ModeratorSettings.waitForNumberOfCheckmarksToBe(1);
        Setup.launchMainBrowser();
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickAndValidateCardCheckbox("20");
        ModeratorSettings.clickAndValidateCardCheckbox("40");
        ModeratorSettings.clickAndValidateCardCheckbox("100");
        ModeratorSettings.clickAndValidateCardCheckbox("Coffee");
        ModeratorSettings.clickAndValidateCardCheckbox("?");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptionsVotingArea();
        ModeratorSettings.waitForNumberOfCheckmarksToBe(0);
        List<String> secondListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Assert.assertNotEquals("New Card values were not applied", firstListOfModeratorCards, secondListOfModeratorCards);
        Assert.assertTrue("Checkmark did not disappear/ Voting session did not reset after value changes", ModeratorSettings.waitForNumberOfCheckmarksToBe(0));
    }

    @Test
    public void flipCardsTest() {
        ModeratorSettings.clickFlipCardsButton();
        Setup.waitForElementToAppear(ModeratorSettings.VOTING_SUMMARY_CIRCLE);
        String actualCircleText = ModeratorSettings.getCircleText();
        String expectedCircleText = "0 Players\nvoted";
        Assert.assertEquals("Cards are not flipped/ Flip Cards button does not work", expectedCircleText, actualCircleText);
    }

    @Test
    public void clearVotesTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickAndValidateCardCheckbox("3");
        ModeratorSettings.clickAndValidateCardCheckbox("8");
        ModeratorSettings.clickAndValidateCardCheckbox("20");
        ModeratorSettings.clickAndValidateCardCheckbox("Coffee");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        List<String> expectedListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        ModeratorSettings.clickFlipCardsButton();
        Setup.waitForElementToAppear(ModeratorSettings.VOTING_SUMMARY_CIRCLE);
        ModeratorSettings.clickClearVotesButton();
        ModeratorSettings.waitForCardsOptions();
        List<String> actualListOfModeratorCards = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Assert.assertEquals("Votes are not cleared/ Clear votes button does not work", expectedListOfModeratorCards, actualListOfModeratorCards);
    }

    @Test
    public void finishVotingTest() {
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
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_THIRD);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("20");
        PlayerFunctionality.getCheckMarkElement("saule");
        ModeratorSettings.waitForNumberOfCheckmarksToBe(1);
        Setup.launchMainBrowser();
        ModeratorSettings.clickFinishVotingButton();
        ModeratorSettings.waitForNumberOfCheckmarksToBe(0);
        Assert.assertTrue("Checkmark did not disappear/ Voting is not finished/ Voting button does not work", ModeratorSettings.waitForNumberOfCheckmarksToBe(0));
    }

    @Test
    public void checkCancelButtonFunctionality() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("13");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("100");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.waitForCardsOptions();
        List <String> expectedList = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("Use all cards");
        ModeratorSettings.clickCancelButton();
        List <String> actualList = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Assert.assertEquals("Cancel button does not work properly", expectedList, actualList);
    }

    @Test
    public void getFirstUserInTheListTest() {
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForNameResults(LoginLogout.FIELD_NAME);
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForNameResults("Justas");
        ModeratorSettings.getFirstUserInTheList();
        String expectedUserInTheList = "testemail@gmail.com";
        String actualUserInTheList = ModeratorSettings.getFirstUserInTheList();
        Assert.assertEquals("First user in the list is not Moderator", expectedUserInTheList, actualUserInTheList);
    }

    @Test
    public void getPhaseMessageTopOffListAfterCardsFlippedTest() {
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
        PlayerFunctionality.getCheckMarkElement("saule");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFlipCardsButton();
        ModeratorSettings.waitPhaseMessageResults("Waiting for moderator to finalize votes");
        ModeratorSettings.getPhaseMessageTopOffList();
        String expectedPhaseMessage = "Waiting for moderator to finalize votes";
        String actualPhaseMessage = ModeratorSettings.getPhaseMessageTopOffList();
        Assert.assertEquals("Phase Message shown after Cards have been flipped does not meet the requirements", expectedPhaseMessage, actualPhaseMessage);
    }

    @Test
    public void getMessageTopOffListVotingPhaseTest() {
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
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_THIRD);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("20");
        PlayerFunctionality.getCheckMarkElement("saule");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFinishVotingButton();
        ModeratorSettings.waitPhaseMessageResults("Waiting for 2 players to vote");
        ModeratorSettings.getPhaseMessageTopOffList();
        String expectedPhaseMessage = "Waiting for 2 players to vote";
        String actualPhaseMessage = ModeratorSettings.getPhaseMessageTopOffList();
        Assert.assertEquals("Message shown during Voting Phase does not meet the requirements", expectedPhaseMessage, actualPhaseMessage);
    }

    @Test
    public void getSumOfPlayersVotedInVotingSummaryTest() {
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
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("20");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFlipCardsButton();
        Setup.waitForElementToAppear(ModeratorSettings.VOTING_SUMMARY_CIRCLE);
        String actualSumOfPlayersText = ModeratorSettings.getCircleText();
        String expectedSumOfPlayersText = "2 Players\nvoted";
        Assert.assertEquals("Sum of Players voted is not shown in the Voting summary", expectedSumOfPlayersText, actualSumOfPlayersText);
    }

    @Test
    public void getVoteValueInSplitOfVotesVotingSummaryTest() {
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
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_THIRD);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("8");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFlipCardsButton();
        Setup.waitForElementToAppear(ModeratorSettings.VOTING_SUMMARY_CIRCLE);
        ModeratorSettings.getSplitOfVotesInVotingSummary(ModeratorSettings.VOTE_VALUE);
        String expectedVoteValue = "8";
        String actualVoteValue = ModeratorSettings.getSplitOfVotesInVotingSummary(ModeratorSettings.VOTE_VALUE);
        Assert.assertEquals("Voting Value is not shown in the Voting summary", expectedVoteValue, actualVoteValue);
    }

    @Test
    public void getPercentageOfPlayersForEachVoteVotingSummaryTest() {
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
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_THIRD);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("8");
        Setup.launchMainBrowser();
        ModeratorSettings.clickFlipCardsButton();
        Setup.waitForElementToAppear(ModeratorSettings.VOTING_SUMMARY_CIRCLE);
        ModeratorSettings.getSplitOfVotesInVotingSummary(ModeratorSettings.VOTE_PERCENTAGE);
        String expectedVotePercentage = "100.0% (1 players)";
        String actualVotePercentage = ModeratorSettings.getSplitOfVotesInVotingSummary(ModeratorSettings.VOTE_PERCENTAGE);
        Assert.assertEquals("Percentage of players who voted for each different vote is visible in the Voting results summary", expectedVotePercentage, actualVotePercentage);
    }

    @Test
    public void selectAutoRevealCheckboxTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("Auto Reveal");
        ModeratorSettings.checkIfCardCheckboxIsMarked("Auto Reveal");
        ModeratorSettings.clickSaveButton();
        ModeratorSettings.clickVotingConfigurationButton();
        Assert.assertTrue("Auto Reveal is not selected", ModeratorSettings.checkIfCardCheckboxIsMarked("Auto Reveal"));
    }

    @Test
    public void checkIfVotesAreRevealedWhenAllPlayersVotedAutoRevealCheckboxMarkedTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("0");
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("Coffee");
        ModeratorSettings.clickCardCheckbox("Auto Reveal");
        ModeratorSettings.checkIfCardCheckboxIsMarked("Auto Reveal");
        ModeratorSettings.clickSaveButton();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        Setup.launchThirdBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("3");
        Setup.launchAlternativeBrowser();
        PlayerFunctionality.clickCardPlayer("8");
        Setup.waitForElementToAppear(ModeratorSettings.VOTING_SUMMARY_CIRCLE);
        String actualCircleText = ModeratorSettings.getCircleText();
        String expectedCircleText = "2 Players\nvoted";
        Assert.assertEquals("Votes are not revealed automatically after all Players have voted, even though AutoReveal had been marked", expectedCircleText, actualCircleText);
    }

    @Test
    public void checkIfVotesAreNotRevealedWhenAllPlayersVotedAutoRevealCheckboxUnmarkedTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("0");
        ModeratorSettings.clickCardCheckbox("3");
        ModeratorSettings.clickCardCheckbox("8");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("Coffee");
        ModeratorSettings.clickCardCheckbox("Auto Reveal");
        ModeratorSettings.checkIfCardCheckboxIsMarked("Auto Reveal");
        ModeratorSettings.clickCardCheckbox("Auto Reveal");
        ModeratorSettings.clickSaveButton();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        ModeratorSettings.waitForCardsOptions();
        PlayerFunctionality.clickCardPlayer("3");
        PlayerFunctionality.getVoteIconNextToNameBeforeVotes("Justas");
        String expectedSymbolResult = "vote-icon";
        String actualSymbolResult = PlayerFunctionality.getVoteIconNextToNameBeforeVotes("Justas");
        Assert.assertEquals("Votes are revealed automatically after all Players have voted, however AutoReveal had been unmarked", actualSymbolResult, expectedSymbolResult);
    }

    @Test
    public void getModeratorGeneratedRoomLinkTest() {
        Setup.browser.getCurrentUrl().contains("https://pokerface-dev.azurewebsites.net/Poker?roomId");
        Assert.assertTrue("Current room link is not uniquely generated", Setup.browser.getCurrentUrl().contains("https://pokerface-dev.azurewebsites.net/Poker?roomId"));
        Assert.assertNotEquals("General link and uniquely generated link are the same", Setup.POKER_URL, Setup.browser.getCurrentUrl());
    }

    @Test
    public void compareModeratorAndPlayerRoomLinksTest() {
        String moderatorRoomLink = Setup.browser.getCurrentUrl();
        Setup.launchAlternativeBrowser();
        LoginLogout.loginPlayer(LoginLogout.FIELD_NAME_SECOND);
        LoginLogout.clickEnterPlayerButton();
        LoginLogout.waitForLoginResults();
        String playerRoomLink = Setup.browser.getCurrentUrl();
        Assert.assertEquals("Player and Moderator are in different rooms", moderatorRoomLink, playerRoomLink);
    }

    @Test
    public void compareGeneratedRoomLinksFromTwoDifferentModeratorsTest() {
        String firstModeratorRoomLink = Setup.browser.getCurrentUrl();
        Setup.launchFourthBrowser();
        LoginLogout.loginModerator(LoginLogout.SECOND_EMAIL, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        String secondModeratorRoomLink = Setup.browser.getCurrentUrl();
        Assert.assertNotEquals("Both newly generated room links from each different Moderator are the same", firstModeratorRoomLink, secondModeratorRoomLink);
    }

    @Test
    public void checkIfTimerIsVisibleInPlayersSectionTest() {
        LoginLogout.waitForNameResults("testemail@gmail.com");
        ModeratorSettings.getTimerVisibleInPlayersSection();
        Assert.assertTrue("Timer is not displayed", ModeratorSettings.getTimerVisibleInPlayersSection());
    }

    @Test
    public void checkIfReEnteredRoomHasPreviouslySavedCardOptionsTest() {
        ModeratorSettings.clickVotingConfigurationButton();
        ModeratorSettings.waitForCardCheckboxOptions();
        ModeratorSettings.clickCardCheckbox("13");
        ModeratorSettings.clickCardCheckbox("20");
        ModeratorSettings.clickCardCheckbox("100");
        ModeratorSettings.clickSaveButton();
        List <String> expectedListBeforeLogout = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        LoginLogout.pressLoginNameButton();
        LoginLogout.pressLogoutButton();
        LoginLogout.loginModerator(LoginLogout.EMAIL_CORRECT, LoginLogout.PASSWORD_CORRECT);
        LoginLogout.waitForLoginResults();
        ModeratorSettings.waitForCardsOptions();
        List <String> actualListAfterLogoutRoomReEntered = ModeratorSettings.getPossibleModeratorVotingAreaCardsList();
        Assert.assertEquals("Previously saved card options are not visible",expectedListBeforeLogout, actualListAfterLogoutRoomReEntered);
    }

    @After
    public void finish() {
        ModeratorSettings.resetPreviouslySavedValuesJavaScript();
        ModeratorSettings.resetAutoRevealJavaScript();
        Setup.closePage();
    }
}
