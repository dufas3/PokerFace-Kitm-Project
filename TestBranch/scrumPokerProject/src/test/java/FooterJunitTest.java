import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FooterJunitTest {

    @Before
    public void setup() {
        Setup.launchMainBrowser();
    }

    @Test
    public void pressImprintLinkPositiveTest() {
        Footer.clickFooterLink("Imprint");
        Setup.acceptFestoCookies();
        String expectedResults = "Imprint";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertEquals("Imprint link does not redirect to Festo Imprint page", expectedResults, actualResults);
    }

    @Test
    public void pressImprintLinkNegativeTest() {
        Footer.clickFooterLink("Imprint");
        Setup.acceptFestoCookies();
        String expectedResults = "Something";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertNotEquals("Main headline of Festo Imprint page is Something",expectedResults, actualResults);
    }

    @Test
    public void pressPrivacyLinkPositiveTest() {
        Footer.clickFooterLink("privacy");
        Setup.acceptFestoCookies();
        String expectedResults = "Festo Data Privacy Statement";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertEquals("Data Privacy link does not redirect to Festo Data Privacy page", expectedResults, actualResults);
    }

    @Test
    public void pressPrivacyLinkNegativeTest() {
        Footer.clickFooterLink("privacy");
        Setup.acceptFestoCookies();
        String expectedResults = "Something";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertNotEquals("Main headline of Festo Data Privacy page is Something", expectedResults, actualResults);
    }

    @Test
    public void pressTermsLinkPositiveTest() {
        Footer.clickFooterLink("Terms");
        Setup.acceptFestoCookies();
        String expectedResults = "Terms and Conditions of Sale";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertEquals("Terms and Conditions link does not redirect to Festo Terms and Conditions of Sale page", expectedResults, actualResults);
    }

    @Test
    public void pressTermsLinkNegativeTest() {
        Footer.clickFooterLink("Terms");
        Setup.acceptFestoCookies();
        String expectedResults = "Something";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertNotEquals("Main headline of Festo Terms and Conditions of Sale page is Something", expectedResults, actualResults);
    }

    @Test
    public void pressCloudLinkPositiveTest() {
        Footer.clickFooterLink("Cloud");
        Setup.acceptFestoCookies();
        String expectedResults = "Cloud Services";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertEquals("Cloud Services link does not redirect to Festo Cloud Services page", expectedResults, actualResults);
    }

    @Test
    public void pressCloudLinkNegativeTest() {
        Footer.clickFooterLink("Cloud");
        Setup.acceptFestoCookies();
        String expectedResults = "Something";
        String actualResults = Footer.getFooterLinkResults();
        Assert.assertNotEquals("Main headline of Festo Cloud Services page is Something", expectedResults, actualResults);
    }

    @After
    public void finish() {
        Setup.closePage();
    }
}
