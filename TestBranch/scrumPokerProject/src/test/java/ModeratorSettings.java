import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class ModeratorSettings {

    public static final By FLIP_CARDS_BUTTON = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/div/div/div/button[1]/h6");
    public static final By CLEAR_VOTES_BUTTON = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/div/div/div/button[2]/h6");
    public static final By FINISH_VOTING_BUTTON = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/div/div/button/h6");
    public static final By VOTING_CONFIGURATION_BUTTON = By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div[2]/button/i");
    public static final By CARD_CHECKBOX = By.className("form-check-input");
    public static final By CARD_CHECKBOX_VALUE = By.className("form-check-label");
    public static final By CARD = By.className("card-css");
    public static final By CARD_NUMBER = By.className("number");
    public static final By SAVE_BUTTON = By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div[1]/div[2]/div[3]/button[1]/h6");
    public static final By CANCEL_BUTTON = By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div[1]/div[2]/div[3]/button[2]/h6");
    public static final By VOTING_SUMMARY_CIRCLE = By.className("circle");
    public static final By VOTE_PERCENTAGE = By.className("vote-percentage");
    public static final By VOTE_VALUE = By.className("vote-value");
    public static final By CHECKMARK = By.xpath("//i[@class='fa-regular fa-circle-check text-primary']");
    public static final By USER_IN_LIST = By.className("align-center-start");
    public static final By PHASE_MESSAGE = By.cssSelector("#root > div.App > div.poker > div.player-list > div.player-list-top > h6");
    public static final By TIMER = By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div[2]/div[1]/div/h6[2]");


    public static void clickFlipCardsButton() {
        Setup.waitForElementToAppear(FLIP_CARDS_BUTTON);
        WebElement flipCardsButton = Setup.browser.findElement(FLIP_CARDS_BUTTON);
        Assert.assertTrue("Clear votes button is invisible", flipCardsButton.isDisplayed());
        flipCardsButton.click();
    }

    public static void clickClearVotesButton() {
        Setup.waitForElementToAppear(CLEAR_VOTES_BUTTON);
        WebElement clearVotesButton = Setup.browser.findElement(CLEAR_VOTES_BUTTON);
        Assert.assertTrue("Clear votes button is invisible", clearVotesButton.isDisplayed());
        clearVotesButton.click();
    }

    public static void clickFinishVotingButton() {
        Setup.waitForElementToAppear(FINISH_VOTING_BUTTON);
        WebElement finishVotingButton = Setup.browser.findElement(FINISH_VOTING_BUTTON);
        Assert.assertTrue("Finish voting button is invisible", finishVotingButton.isDisplayed());
        finishVotingButton.click();
    }

    public static void clickVotingConfigurationButton() {
        Setup.waitForElementToAppear(VOTING_CONFIGURATION_BUTTON);
        WebElement votingConfigurationButton = Setup.browser.findElement(VOTING_CONFIGURATION_BUTTON);
        Assert.assertTrue("Voting configuration button is invisible", votingConfigurationButton.isDisplayed());
        votingConfigurationButton.click();
    }

    public static void waitForCardCheckboxOptions() {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15)).until(ExpectedConditions.numberOfElementsToBeMoreThan(CARD_CHECKBOX, 2));
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15),Duration.ofMillis(50)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CARD_CHECKBOX));
    }

    public static void waitForCardsOptions() {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15)).until(ExpectedConditions.numberOfElementsToBeMoreThan(CARD, 1));
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CARD));
    }

    public static void resetPreviouslySavedValuesJavaScript() {
        ((JavascriptExecutor) Setup.browser).executeAsyncScript("fetch('https://pokerface-devbackend.azurewebsites.net/api/session/setActiveCards', {\n" +
                "  headers: {\n" +
                "    'content-type': 'application/json',\n" +
                "  },\n" +
                "  body: `{\\\"roomId\\\":\"${new URLSearchParams(location.search).get('roomId')}\",\\\"cardIds\\\":[]}`,\n" +
                "  method: 'POST',\n" +
                "  mode: 'cors',\n" +
                "  credentials: 'omit'\n" +
                "}).then(()=>arguments[0]());");
    }

    public static void resetAutoRevealJavaScript() {
        ((JavascriptExecutor) Setup.browser).executeAsyncScript("fetch('https://pokerface-devbackend.azurewebsites.net/api/settings/setSettings', {\n" +
                "  headers: {\n" +
                "    'content-type': 'application/json',\n" +
                "  },\n" +
                "  body: `{\\\"roomId\\\":\"${new URLSearchParams(location.search).get('roomId')}\",\\\"ids\\\":[]}`,\n" +
                "  method: 'POST',\n" +
                "  mode: 'cors',\n" +
                "  credentials: 'omit'\n" +
                "}).then(()=>arguments[0]());");
    }

    public static void waitForCardsOptionsVotingArea() {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(15)).until(ExpectedConditions.numberOfElementsToBeMoreThan(CARD, 4));
    }

    public static void clickCardCheckbox(String text) {
        Setup.waitForElementToAppear(By.xpath("//label[contains(.,'" + text + "')]"));
        WebElement cardBoxOption = Setup.browser.findElement(By.xpath("//label[contains(.,'" + text + "')]"));
        cardBoxOption.click();
    }

    public static Boolean checkIfCardCheckboxIsMarked(String text) {
        Setup.waitForElementToAppear(By.xpath("//label[contains(.,'" + text + "')]"));
        WebElement checkbox = Setup.browser.findElement(By.xpath("//label[contains(.,'" + text + "')]"));
        if(!checkbox.isSelected()) {
            System.out.println("Checkbox is not selected");
        }
        else {
            checkbox.click();
        }
        return true;
    }

    public static void clickAndValidateCardCheckbox(String text) {
        Setup.waitForElementToAppear(By.xpath("//label[contains(.,'" + text + "')]"));
        WebElement cardBoxOption = Setup.browser.findElement(By.xpath("//label[contains(.,'" + text + "')]"));
        cardBoxOption.click();
        if(!cardBoxOption.isSelected()) {
            System.out.println("Checkbox is not selected");
        }
        else {
            cardBoxOption.click();
        }
    }

    public static void clickSaveButton() {
        Setup.waitForElementToAppear(SAVE_BUTTON);
        WebElement saveButton = Setup.browser.findElement(SAVE_BUTTON);
        Assert.assertTrue("Save button is invisible", saveButton.isDisplayed());
        saveButton.click();
    }

    public static void clickCancelButton() {
        Setup.waitForElementToAppear(CANCEL_BUTTON);
        WebElement cancelButton = Setup.browser.findElement(CANCEL_BUTTON);
        Assert.assertTrue("Cancel button is invisible", cancelButton.isDisplayed());
        cancelButton.click();
    }

    public static ArrayList<String> getPossibleCardCheckboxValuesList() {
        ArrayList<String> actual = new ArrayList<>();
        for (WebElement element : Setup.browser.findElements(CARD_CHECKBOX_VALUE)) {
            actual.add(element.getText());
        }
        return actual;
    }

    public static ArrayList<String> getPossibleModeratorVotingAreaCardsList() {
        Setup.waitForElementToAppear(CARD_NUMBER);
        ArrayList<String> moderatorCards = new ArrayList<>();
        for (WebElement cardsElement : Setup.browser.findElements(CARD_NUMBER)) {
            moderatorCards.add(cardsElement.getText());
        }
        System.out.println(moderatorCards);
        return moderatorCards;
    }

    public static String getCircleText() {
        WebElement circleText = Setup.browser.findElement(VOTING_SUMMARY_CIRCLE);
        String resultsText = circleText.getText();
        System.out.println(resultsText);
        return resultsText;
    }

    public static String getSplitOfVotesInVotingSummary(By elementSelector) {
        Setup.waitForElementToAppear(VOTE_PERCENTAGE);
        Setup.waitForElementToAppear(VOTE_VALUE);
        WebElement voteValue = Setup.browser.findElement(elementSelector);
        String resultsText = voteValue.getText();
        System.out.println(resultsText);

        return resultsText;
    }

    public static boolean waitForNumberOfCheckmarksToBe(int number) {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(6)).until(ExpectedConditions.numberOfElementsToBe(CHECKMARK, number));
        return true;
    }

    public static String getFirstUserInTheList() {
        ArrayList<String> actualUsers = new ArrayList<>();
        String firstElement = null;
        for (WebElement element : Setup.browser.findElements(USER_IN_LIST)) {
            actualUsers.add(element.getText());
            firstElement = actualUsers.get(0);
            if (firstElement.equals("testemail@gmail.com")) {
                break;
            }
        }
        System.out.println(firstElement);
        return firstElement;
    }

    public static String getPhaseMessageTopOffList() {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(PHASE_MESSAGE));
        WebElement phaseMessage = Setup.browser.findElement(PHASE_MESSAGE);
        String phaseMessageText = phaseMessage.getText();
        System.out.println(phaseMessageText);
        return phaseMessageText;
    }

    public static void waitPhaseMessageResults(String text) {
        new WebDriverWait(Setup.browser, Duration.ofSeconds(20), Duration.ofMillis(50)).until(ExpectedConditions.textToBePresentInElementLocated(PHASE_MESSAGE, text));
    }

    public static Boolean getTimerVisibleInPlayersSection() {
        WebElement timer = Setup.browser.findElement(TIMER);
        if(!timer.isDisplayed()) {
            System.out.println("Timer is not displayed");
        }
        else {
            System.out.println("Timer is displayed");
        }
        return true;
    }
}