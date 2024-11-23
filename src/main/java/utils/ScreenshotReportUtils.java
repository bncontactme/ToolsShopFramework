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
    private static String currentTestCaseFolderPath;
    private static String screenshotFolderPath;

    public static String createClassFolder(String className) {
        String baseDir = System.getProperty("user.dir") + "/src/test/resources/testresults/";
        String classFolderPath = baseDir + className;

        File classFolder = new File(classFolderPath);
        if (!classFolder.exists()) {
            classFolder.mkdirs();
        }

        return classFolderPath;
    }

    public static String createTestCaseFolder(String classFolderPath, String testName) {
        String timestamp = new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date());
        String testCaseFolderPath = classFolderPath + "/" + testName + "_" + timestamp;

        File testCaseFolder = new File(testCaseFolderPath);
        if (!testCaseFolder.exists()) {
            testCaseFolder.mkdirs();
        }

        currentTestCaseFolderPath = testCaseFolderPath; // Set the current test folder
        return testCaseFolderPath;
    }

    public static String createScreenshotFolder(String testCaseFolderPath) {
        String screenshotFolderPath = testCaseFolderPath + "/screenshots";

        File screenshotFolder = new File(screenshotFolderPath);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }

        ScreenshotReportUtils.screenshotFolderPath = screenshotFolderPath; // Set the screenshot folder
        return screenshotFolderPath;
    }

    public static String takeScreenshot(WebDriver driver, String screenshotFolderPath, String className, String testName) {
        String timestamp = new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date());
        String screenshotPath = screenshotFolderPath + "/" + className + "_" + testName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshotPath;
    }

    public static void startTest(WebDriver driver, String classFolderPath, String className, String testName) {
        // Create test case and screenshot folders
        String testCaseFolder = createTestCaseFolder(classFolderPath, testName);
        createScreenshotFolder(testCaseFolder);

        // Set up Extent Report
        String reportPath = testCaseFolder + "/" + className + "_" + testName + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        currentTest = extent.createTest(testName);
    }

    public static void logSuccessWithScreenshot(WebDriver driver, String message) {
        String screenshotPath = takeScreenshot(driver, screenshotFolderPath, "Success", "Step");
        currentTest.pass(message).addScreenCaptureFromPath(screenshotPath);
    }

    public static void logFailureWithScreenshot(WebDriver driver, String testName, Throwable throwable) {
        String screenshotPath = takeScreenshot(driver, screenshotFolderPath, "Failure", testName);
        currentTest.fail("Test failed").addScreenCaptureFromPath(screenshotPath);
        currentTest.fail(throwable);
    }

    public static void endTest() {
        if (extent != null) {
            extent.flush();
        }
    }
}
