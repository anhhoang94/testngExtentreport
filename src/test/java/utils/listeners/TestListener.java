package utils.listeners;

import static utils.extentreports.ExtentTestManager.getTest;
import com.aventstack.extentreports.*;
import java.io.*;
import java.text.*;
import java.util.*;
import common.helpers.*;
import io.appium.java_client.android.*;
import org.apache.commons.io.*;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.extentreports.ExtentManager;
import utils.logs.Log;

public class TestListener extends BaseTest implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for ExtentReports reporting!
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is succeed.");
        ExcelHelpers excel = (ExcelHelpers) iTestResult.getAttribute("excel");

        if (excel != null) {
            try {
                String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date());
                excel.setCellData(timeStamp, (String) iTestResult.getAttribute("testcase"), "ExecuteTime");
                excel.setCellData("PASS", (String) iTestResult.getAttribute("testcase"), "Status");
            } catch (Exception e) {
                Log.info("onTestSuccess exception: "+e);
                throw new RuntimeException(e);
            }}

        screenshot(iTestResult, Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is failed.");
        ExcelHelpers excel = (ExcelHelpers) iTestResult.getAttribute("excel");

        if (excel != null) {
            try {
                String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date());
                excel.setCellData(timeStamp, (String) iTestResult.getAttribute("testcase"), "ExecuteTime");
                excel.setCellData("FAIL", (String) iTestResult.getAttribute("testcase"), "Status");
            } catch (Exception e) {
                Log.info("onTestFailure exception:: "+e);
                throw new RuntimeException(e);
            }
        }

        screenshot(iTestResult, Status.FAIL, "Test Failed");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    private void screenshot(ITestResult iTestResult, Status status, String details) {
        try {
            Object testClass = iTestResult.getInstance();
            AndroidDriver driver = ((BaseTest) testClass).getDriver();

            File srcFile=driver.getScreenshotAs(OutputType.FILE);
            String filename= UUID.randomUUID().toString();
            File targetFile=new File("extent-reports/"+ details + filename +".jpg");
            FileUtils.copyFile(srcFile,targetFile);
            String filePath = targetFile.getAbsolutePath();
            getTest().log(status, details, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
        } catch (Exception e) {
            Log.info("screenshot exception:: "+details + " :: "+ e);
        }
    }
}
