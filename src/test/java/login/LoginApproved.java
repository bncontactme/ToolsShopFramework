package login;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonReaderUtils;
import login.LoginMappingData;
import org.openqa.selenium.WebDriver;
import utils.ScreenshotReportUtils;

import java.io.IOException;
import java.util.Map;

public class LoginApproved {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases;

    private ExtentReports extent;
    private ExtentTest test;
    private String testCaseFolderPath;
    private String screenshotFolderPath;
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
        String testName = "invalidLogin_MissingEmail";

        // Create test case and screenshot folders
        testCaseFolderPath = ScreenshotReportUtils.createTestCaseFolder(classFolderPath, testName);
        screenshotFolderPath = ScreenshotReportUtils.createScreenshotFolder(testCaseFolderPath);

        // Initialize Extent Report
        String reportPath = ScreenshotReportUtils.getExtentReportPath(testCaseFolderPath, this.getClass().getSimpleName(), testName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        test = extent.createTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Add a dummy screenshot for testing
        String screenshotPath = ScreenshotReportUtils.takeScreenshot(driver, screenshotFolderPath, this.getClass().getSimpleName(), testName);
        test.pass("Screenshot added").addScreenCaptureFromPath(screenshotPath);
    }

    @Test
    public void invalidLogin_InvalidEmail() {
        String testName = "invalidLogin_InvalidEmail";

        // Create test case and screenshot folders
        testCaseFolderPath = ScreenshotReportUtils.createTestCaseFolder(classFolderPath, testName);
        screenshotFolderPath = ScreenshotReportUtils.createScreenshotFolder(testCaseFolderPath);

        // Initialize Extent Report
        String reportPath = ScreenshotReportUtils.getExtentReportPath(testCaseFolderPath, this.getClass().getSimpleName(), testName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        test = extent.createTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessageEmail();

        // Add a dummy screenshot for testing
        String screenshotPath = ScreenshotReportUtils.takeScreenshot(driver, screenshotFolderPath, this.getClass().getSimpleName(), testName);
        test.pass("Screenshot added").addScreenCaptureFromPath(screenshotPath);
    }

    @Test
    public void invalidLogin_InvalidPassword() {
        String testName = "invalidLogin_InvalidPassword";

        // Create test case and screenshot folders
        testCaseFolderPath = ScreenshotReportUtils.createTestCaseFolder(classFolderPath, testName);
        screenshotFolderPath = ScreenshotReportUtils.createScreenshotFolder(testCaseFolderPath);

        // Initialize Extent Report
        String reportPath = ScreenshotReportUtils.getExtentReportPath(testCaseFolderPath, this.getClass().getSimpleName(), testName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        test = extent.createTest(testName);

        // Perform test logic
        LoginMappingData testData = testCases.get(testName);
        loginPage.performFullLogin(testData.getEmail(), testData.getPassword());
        loginPage.verifyErrorMessagePassword();

        // Add a dummy screenshot for testing
        String screenshotPath = ScreenshotReportUtils.takeScreenshot(driver, screenshotFolderPath, this.getClass().getSimpleName(), testName);
        test.pass("Screenshot added").addScreenCaptureFromPath(screenshotPath);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            String screenshotPath = ScreenshotReportUtils.takeScreenshot(
                    driver, screenshotFolderPath, this.getClass().getSimpleName(), result.getName());
            test.fail("Test failed").addScreenCaptureFromPath(screenshotPath);
            test.fail(result.getThrowable());
        }

        if (extent != null) {
            extent.flush(); // Save ExtentReport
        }

        if (driver != null) {
            driver.quit(); // Close WebDriver
        }
    }
}
