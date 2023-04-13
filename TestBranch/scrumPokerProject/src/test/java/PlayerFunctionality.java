import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PlayerFunctionality {

    public static final By CARD_TAG = By.tagName("h2");
    public static final By PLAYER_TAG = By.tagName("h6");
    public static final By CHECKMARK_TAG = By.tagName("i");
    public static final By VOTING_RESULT = By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div[2]/div[2]/div[2]/h5");
    public static final By VOTE_ICON_RESULT = By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div[2]/div[2]/div[2]/img");

    public static void clickCardPlayer(String cardNumber) {
        WebElement playerCard = Setup.browser.findElement(ModeratorSettings.CARD_NUMBER);
        System.out.println("Player card is: " + playerCard);

        List<WebElement> playerCards = Setup.browser.findElements(ModeratorSettings.CARD);
        System.out.println("Total cards: " + playerCards.size());

        for (WebElement card : playerCards) {
            WebElement cardTag = card.findElement(CARD_TAG);
            if (cardTag.getText().equals(cardNumber)) {
                card.click();
                break;
            }
        }
    }

    public static String getCheckMarkElement(String playerName) {
        Setup.waitForElementToAppear(ModeratorSettings.USER_IN_LIST);
        List<WebElement> playersList = Setup.browser.findElements(ModeratorSettings.USER_IN_LIST);
        String checkMarkText = "";

        for (WebElement player : playersList) {
            WebElement playerTag = player.findElement(PLAYER_TAG);
            if (playerTag.getText().equals(playerName)) {
                checkMarkText = playerTag.findElement(CHECKMARK_TAG).getAttribute("class");
                System.out.println("text: " + checkMarkText);
            }
        }
        return checkMarkText;
    }

    public static String getVotingResultAfterFlipCards(String playerName) {
        Setup.waitForElementToAppear(VOTING_RESULT);
        List<WebElement> playersList = Setup.browser.findElements(ModeratorSettings.USER_IN_LIST);
        String votingResultText = "";

        for (WebElement player : playersList) {
            WebElement playerTag = player.findElement(PLAYER_TAG);
            if (playerTag.getText().equals(playerName)) {
                votingResultText = playerTag.findElement(VOTING_RESULT).getText();
            }
        }
        System.out.println("text: " + votingResultText);
        return votingResultText;
    }

    public static String getVoteIconNextToNameBeforeVotes(String playerName) {
        Setup.waitForElementToAppear(VOTE_ICON_RESULT);
        List<WebElement> playersList = Setup.browser.findElements(ModeratorSettings.USER_IN_LIST);
        String votingIconResult = "";

        for (WebElement player : playersList) {
            WebElement playerTag = player.findElement(PLAYER_TAG);
            if (playerTag.getText().equals(playerName)) {
                votingIconResult = playerTag.findElement(VOTE_ICON_RESULT).getAttribute("class");
            }
        }
        System.out.println("Voting icon class text: " + votingIconResult);
        return votingIconResult;
    }

    public static ArrayList<String> getPossiblePayerVotingAreaCardsList() {
        ArrayList<String> playerCards = new ArrayList<>();
        for (WebElement cardElement : Setup.browser.findElements(ModeratorSettings.CARD_NUMBER)) {
            playerCards.add(cardElement.getText());
        }
        return playerCards;
    }

    public static String getPlayerVisibleInTheList(String playerName) {
        WebElement playerInList = Setup.browser.findElement(By.xpath("//*[text()='" + playerName + "']"));
        String playerNameText = playerInList.getText();
        System.out.println(playerNameText);
        return playerNameText;
    }

    public static ArrayList<String> getAllUsersInTheList() {
        ArrayList<String> actual = new ArrayList<>();
        for (WebElement element : Setup.browser.findElements(ModeratorSettings.USER_IN_LIST)) {
            actual.add(element.getText());
        }
        return actual;
    }
}

