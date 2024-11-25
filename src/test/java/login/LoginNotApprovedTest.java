package login;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import utils.ScreenshotReportUtils;

import java.io.IOException;
import java.util.Map;

public class LoginNotApprovedTest {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases;

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

        // Initialize the single run folder at the start of the test run
        ScreenshotReportUtils.createRunFolder();
    }

    @Test
    public void invalidLogin_MissingEmail() {
        // Get the current test method name
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();

        // Initialize the test in ExtentReports
        ScreenshotReportUtils.startTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for missing email.");
    }

    @Test
    public void invalidLogin_InvalidEmail() {
        // Get the current test method name
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();

        // Initialize the test in ExtentReports
        ScreenshotReportUtils.startTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for invalid email.");
    }

    @Test
    public void invalidLogin_InvalidPassword() {
        // Get the current test method name
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();

        // Initialize the test in ExtentReports
        ScreenshotReportUtils.startTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessagePassword();

        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for invalid password.");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            ScreenshotReportUtils.logFailureWithScreenshot(driver, result.getThrowable());
        }

        if (driver != null) {
            driver.quit(); // Close WebDriver
        }
    }

    @AfterClass
    public void flushReports() {
        // Flush the ExtentReports once after all tests are finished
        ScreenshotReportUtils.endTestRun();
    }
}
