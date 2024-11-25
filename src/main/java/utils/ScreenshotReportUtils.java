package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotReportUtils {

    private static ExtentReports extent;
    private static ExtentTest currentTest;
    private static String runFolderPath;        // Single folder for the test run
    private static String screenshotFolderPath; // Folder for screenshots
    private static String runId;                // Unique run identifier (timestamp)

    // Initialize ExtentReports once per test run
    public static ExtentReports getExtentReportsInstance() {
        if (extent == null) {
            // Use the run_id as the report name
            String reportPath = runFolderPath + "/run_" + runId + ".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    // Create a single folder for the entire test run
    public static String createRunFolder() {
        if (runFolderPath == null) {
            String baseDir = System.getProperty("user.dir") + "/src/test/resources/testresults/";
            runId = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            runFolderPath = baseDir + "run_" + runId;

            File runFolder = new File(runFolderPath);
            if (!runFolder.exists()) {
                runFolder.mkdirs();
            }

            // Create the screenshots subfolder
            screenshotFolderPath = runFolderPath + "/screenshots";
            File screenshotsFolder = new File(screenshotFolderPath);
            if (!screenshotsFolder.exists()) {
                screenshotsFolder.mkdirs();
            }
        }
        return runFolderPath;
    }

    // Capture and save a screenshot
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = screenshotFolderPath + "/" + screenshotName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshotPath;
    }

    // Start a new test case in the Extent Report
    public static void startTest(String testName) {
        if (runFolderPath == null) {
            createRunFolder(); // Ensure the run folder is created
        }

        // Initialize ExtentReports if not already done
        extent = getExtentReportsInstance();

        // Create a new test in the report
        currentTest = extent.createTest(testName);
    }

    // Log success with a screenshot
    public static void logSuccessWithScreenshot(WebDriver driver, String message) {
        String screenshotPath = takeScreenshot(driver, "Success");
        currentTest.pass(message).addScreenCaptureFromPath(screenshotPath);
    }

    // Log failure with a screenshot
    public static void logFailureWithScreenshot(WebDriver driver, Throwable throwable) {
        String screenshotPath = takeScreenshot(driver, "Failure");
        currentTest.fail("Test failed").addScreenCaptureFromPath(screenshotPath);
        currentTest.fail(throwable);
    }

    // Flush the report at the end of the test run
    public static void endTestRun() {
        if (extent != null) {
            extent.flush();
        }
    }
}
