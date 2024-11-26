package base;

import testscripts.logintest.LoginMappingData; //Update with you Data Map Import
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

public class BaseTemplate {
    protected WebDriver driver;
    private LoginPage loginPage;
    private Map<String, LoginMappingData> testCases; //Update with your Data Map Import

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        // Initialize WebDriver
        driver = utils.WebDriverUtils.getDriver();
        //Initialize LoginPage
        loginPage = new LoginPage(driver);
        driver.get("https://practicesoftwaretesting.com/");
        //Create Run Folder
        ExtentReportsUtils.createRunFolder();
        // Load test cases from JSON Update with you Data Map
        testCases = JsonReaderUtils.getTestCasesMap(
                "src/test/resources/testdata/logintestsdata/LoginTestData.json", LoginMappingData.class);
    }

    @Test(groups = {"dummyName"})
    public void dummyMethodName() {
        // Initialize the test in ExtentReports
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();
        ExtentReportsUtils.startTest(testName);

        // Perform test logic

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
