package login;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import java.io.IOException;
import java.util.Map;

public class LoginNotApproved {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases;

    @BeforeMethod
    public void setUp() throws IOException {
        // Initialize WebDriver and login page
        driver = utils.WebDriverUtils.getDriver();
        loginPage = new LoginPage(driver);

        // Open the login page
        driver.get("https://practicesoftwaretesting.com/");

        // Load the test cases into a Map
        testCases = JsonReaderUtils.getTestCasesMap("src/test/resources/logintests/loginTestData.json", LoginMappingData.class);
    }

    @Test
    public void invalidLogin_MissingEmail() {
        LoginMappingData testData = testCases.get("invalidLogin_MissingEmail");

        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();
    }

    @Test
    public void invalidLogin_InvalidEmail() {
        LoginMappingData testData = testCases.get("invalidLogin_InvalidEmail");

        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();
    }

    @Test
    public void invalidLogin_InvalidPassword() {
        LoginMappingData testData = testCases.get("invalidLogin_InvalidPassword");

        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessagePassword();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
