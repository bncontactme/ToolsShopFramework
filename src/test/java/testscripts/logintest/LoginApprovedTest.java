package testscripts.logintest;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import utils.ExtentReportsUtils;
import java.io.IOException;
import java.util.Map;

public class LoginApprovedTest {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {

        // Initialize WebDriver, LoginPage, Run Folder
        driver = utils.WebDriverUtils.getDriver();
        loginPage = new LoginPage(driver);
        driver.get("https://practicesoftwaretesting.com/");
        ExtentReportsUtils.createRunFolder();

        // Load test cases from JSON
        testCases = JsonReaderUtils.getTestCasesMap(
                "src/test/resources/testdata/logintestsdata/LoginTestData.json", LoginMappingData.class);

    }

    @Test(groups = {"login"})
    public void validLogin() {
        // Get the current test method name & Initialize the test in ExtentReports
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();
        ExtentReportsUtils.startTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Log success and take a screenshot (Debug)
        ExtentReportsUtils.logSuccessWithScreenshot(driver, "Verified error message for missing email.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            ExtentReportsUtils.logFailureWithScreenshot(driver, result.getThrowable());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass(alwaysRun = true)
    public void flushReports() {
        // Flush the ExtentReports once after all tests are finished
        ExtentReportsUtils.endTestRun();
    }
}
