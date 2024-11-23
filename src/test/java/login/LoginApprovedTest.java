package login;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import org.openqa.selenium.WebDriver;
import utils.ScreenshotReportUtils;

import java.io.IOException;
import java.util.Map;

public class LoginApprovedTest {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases;

    private String classFolderPath;

    @BeforeMethod
    public void setUp() throws IOException {
        // Initialize WebDriver and LoginPage
        driver = utils.WebDriverUtils.getDriver();
        loginPage = new LoginPage(driver);

        // Open the login page
        driver.get("https://practicesoftwaretesting.com/");

        // Load test cases from JSON
        testCases = JsonReaderUtils.getTestCasesMap(
                "src/test/resources/logintests/loginTestData.json", LoginMappingData.class);

        // Create a folder for the test class
        String className = this.getClass().getSimpleName();
        classFolderPath = ScreenshotReportUtils.createClassFolder(className);
    }

    @Test
    public void invalidLogin_MissingEmail() {
        // Initialize the test in ExtentReports
        String testName = "invalidLogin_MissingEmail";
        ScreenshotReportUtils.startTest(driver, classFolderPath, this.getClass().getSimpleName(), testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for missing email.");
    }

    @Test
    public void invalidLogin_InvalidEmail() {
        // Initialize the test in ExtentReports
        String testName = "invalidLogin_InvalidEmail";
        ScreenshotReportUtils.startTest(driver, classFolderPath, this.getClass().getSimpleName(), testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for invalid email.");
    }

    @Test
    public void invalidLogin_InvalidPassword() {
        // Initialize the test in ExtentReports
        String testName = "invalidLogin_InvalidPassword";
        ScreenshotReportUtils.startTest(driver, classFolderPath, this.getClass().getSimpleName(), testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessagePassword();

        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for invalid password.");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log failure if the test fails
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotReportUtils.logFailureWithScreenshot(
                    driver, result.getName(), result.getThrowable());
        }

        // Finalize the ExtentReport for this test
        ScreenshotReportUtils.endTest();

        // Close the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }
}
