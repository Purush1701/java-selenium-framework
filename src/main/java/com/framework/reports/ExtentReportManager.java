package com.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static final String REPORT_PATH = "test-output/reports/";

    public static void initializeReport() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportName = "TestReport_" + timestamp + ".html";
            
            // Create directory if it doesn't exist
            File reportDir = new File(REPORT_PATH);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH + reportName);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Configuration
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle(ConfigReader.getProperty("report.title", "Selenium Framework Test Report"));
            sparkReporter.config().setReportName(ConfigReader.getProperty("report.name", "Automation Test Results"));
            sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

            // System Information
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", ConfigReader.getProperty("browser.name", "Chrome"));
            extent.setSystemInfo("Environment", "Test");
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
    }

    // Alias method for backward compatibility
    public static void initReport() {
        initializeReport();
    }

    public static ExtentTest createTest(String testName, String description) {
        test = extent.createTest(testName, description);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public static void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
        }
    }

    public static void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
        }
    }

    public static void logSkip(String message) {
        if (test != null) {
            test.log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.YELLOW));
        }
    }

    public static void logWarning(String message) {
        if (test != null) {
            test.log(Status.WARNING, MarkupHelper.createLabel(message, ExtentColor.ORANGE));
        }
    }

    public static void addScreenshot(WebDriver driver, String screenshotName) {
        if (test != null && driver != null) {
            try {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, screenshotName);
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                logWarning("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void logTestResult(ITestResult result) {
        if (test != null) {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    logPass("Test passed: " + result.getMethod().getMethodName());
                    break;
                case ITestResult.FAILURE:
                    logFail("Test failed: " + result.getMethod().getMethodName());
                    if (result.getThrowable() != null) {
                        logFail("Error: " + result.getThrowable().getMessage());
                    }
                    break;
                case ITestResult.SKIP:
                    logSkip("Test skipped: " + result.getMethod().getMethodName());
                    break;
            }
        }
    }
}
