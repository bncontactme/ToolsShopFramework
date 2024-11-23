package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotReportUtils {

    public static String createClassFolder(String className) {
        // Base directory for storing test results
        String baseDir = System.getProperty("user.dir") + "/src/test/resources/testresults/";

        // Folder structure: /testresults/className/
        String classFolderPath = baseDir + className;

        // Check if the folder exists, if not, create it
        File classFolder = new File(classFolderPath);
        if (!classFolder.exists()) {
            classFolder.mkdirs();  // Create the folder if it doesn't exist
        }

        return classFolderPath;
    }

    public static String createTestCaseFolder(String classFolderPath, String testName) {
        // Generate timestamp for unique folder and file names
        String timestamp = new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date());

        // Folder structure: /testresults/className/testName_timestamp/
        String testCaseFolderPath = classFolderPath + "/" + testName + "_" + timestamp;

        // Create the folder if it doesn't exist
        File testCaseFolder = new File(testCaseFolderPath);
        if (!testCaseFolder.exists()) {
            testCaseFolder.mkdirs();
        }

        return testCaseFolderPath;
    }

    public static String createScreenshotFolder(String testCaseFolderPath) {
        // Create a "screenshots" subfolder inside the test case folder
        String screenshotFolderPath = testCaseFolderPath + "/screenshots";
        File screenshotFolder = new File(screenshotFolderPath);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();  // Create the folder if it doesn't exist
        }

        return screenshotFolderPath;
    }

    public static String takeScreenshot(WebDriver driver, String screenshotFolderPath, String className, String testName) {
        // Generate timestamp for unique screenshot file names
        String timestamp = new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date());

        // Screenshot file name
        String screenshotPath = screenshotFolderPath + "/" + className + "_" + testName + "_" + timestamp + ".png";

        // Take the screenshot and save it
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshotPath;  // Return the screenshot path for use in ExtentReport
    }

    public static String getExtentReportPath(String classFolderPath, String className, String testName) {
        // Generate timestamp for unique report file names
        String timestamp = new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date());

        // Report file path
        return classFolderPath + "/" + className + "_" + testName + "_" + timestamp + ".html";
    }
}
