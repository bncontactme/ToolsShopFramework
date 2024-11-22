package login;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import login.LoginMappingData;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.util.Map;

public class LoginApproved {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases;

    @BeforeMethod
    public void setUp() throws IOException {
        driver = utils.WebDriverUtils.getDriver();
        loginPage = new LoginPage(driver);


        driver.get("https://practicesoftwaretesting.com/");


        testCases = JsonReaderUtils.getTestCasesMap("src/test/resources/logintests/loginTestData.json", LoginMappingData.class); // Read test cases
    }

    @Test
    public void Login_Approved() {
        LoginMappingData testData = testCases.get("validLogin");


        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
