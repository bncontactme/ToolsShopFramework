package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportsUtils {

    private static ExtentReports extent;
    private static ExtentTest test;

    // This method will create and return a singleton instance of ExtentReports
    public static ExtentReports getInstance(String folderPath) {
        if (extent == null) {
            // Create the folder for the report if it doesn't exist
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Set the path for the Extent Report HTML file
            String reportPath = folderPath + "/ExtentReport.html";

            // Create the ExtentSparkReporter and attach it to the ExtentReports instance
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("Automation Test Report");
            sparkReporter.config().setDocumentTitle("Test Results");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Tester", "Your Name");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    // This method creates a test within the report
    public static ExtentTest createTest(String testName, String description, String folderPath) {
        test = getInstance(folderPath).createTest(testName, description);
        return test;
    }

    // This method is used to flush the report and ensure it is written
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
