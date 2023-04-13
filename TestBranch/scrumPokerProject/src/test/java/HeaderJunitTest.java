import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeaderJunitTest {

    @Before
    public void setup() {
        Setup.launchMainBrowser();
    }

    @Test
    public void pressUserIconLoginPositiveTest() {
        Setup.launchAlternativeBrowser();
        Header.clickUserIconLogin("Login");
        String expectedModeratorPageResults = "Welcome back";
        String actualModeratorPageResults = Header.getHeaderIconResults();
        Assert.assertEquals("User Icon/Login button does not redirect to Moderator login page", expectedModeratorPageResults, actualModeratorPageResults);
    }

    @Test
    public void pressUserIconNegativeTest() {
        Setup.launchAlternativeBrowser();
        Header.clickUserIconLogin("Login");
        String expectedResults = "Something";
        String actualResults = Header.getHeaderIconResults();
        Assert.assertNotEquals("Main headline of Moderator login page is Something", expectedResults, actualResults);
    }

    @Test
    public void pressFestoLogoPositiveTest() {
        Header.clickFestoLogo();
        Setup.acceptFestoCookies();
        String currentUrlFesto = Setup.browser.getCurrentUrl();
        String expectedUrlFesto = Setup.URL_FESTO;
        Assert.assertEquals("Festo logo does not redirect to Festo company main page", expectedUrlFesto, currentUrlFesto);
    }

    @Test
    public void pressFestoLogoNegativeTest() {
        Header.clickFestoLogo();
        Setup.acceptFestoCookies();
        String expectedResults = "Something";
        Assert.assertNotEquals("Main headline of Festo company main page is Something", expectedResults, Header.getFestoLogoResults());
    }

   @After
    public void finish() {
        Setup.closePage();
    }
}
