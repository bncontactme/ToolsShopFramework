package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import utils.ScreenshotReportUtils;
import java.io.IOException;
import java.util.Map;
import login.LoginMappingData; //Update with your

public class BaseTemplate {

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
    public void exampleTemplateTest() {
        // Automatically extract the test name from the method name (METHOD NAME SHOULD BE NAMED THE SAME AS TESTCASE NAME IN JSON)
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        // Initialize the test in ExtentReports
        ScreenshotReportUtils.startTest(driver, classFolderPath, this.getClass().getSimpleName(), testName);
        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();
        // Log success and take a screenshot
        ScreenshotReportUtils.logSuccessWithScreenshot(driver, "Verified error message for invalid email.");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            ScreenshotReportUtils.logFailureWithScreenshot(driver, result.getName(), result.getThrowable());
        }

        // End the test and flush the ExtentReport
        ScreenshotReportUtils.endTest();

        if (driver != null) {
            driver.quit(); // Close WebDriver
        }
    }
}
