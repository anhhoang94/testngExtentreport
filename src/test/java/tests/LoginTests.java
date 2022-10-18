package tests;

import static org.testng.AssertJUnit.assertTrue;
import static utils.extentreports.ExtentTestManager.startTest;
import java.util.*;
import com.aventstack.extentreports.*;
import org.testng.*;
import org.testng.annotations.*;

public class LoginTests extends BaseTest {
    @DataProvider(name = "TC1")
    public Object[][] testData() {
        return new Object[][]{
                {"TC1","Case01"}
        };
    }

    @Test(priority = 0, enabled = false, dataProvider ="TC1", description = "Invalid Login Scenario with incorrect username and password.")
    public void invalidLoginTest_InvalidUserNameInvalidPassword(String sheetName, String testcase) throws Exception {
        //Read test date from Excel file
        excel.setExcelFile(excelPath, sheetName);
        Map<String, String> testData = excel.getTcData(testcase);

        //ExtentReports Description
        ExtentTest test = startTest("Partner and Associate login app by email", "Login app by: "+testData.get("Username")+"/"+testData.get("Password"));

        //Send data to listener
        ITestResult testResult = Reporter.getCurrentTestResult();
        testResult.setAttribute("excel", excel);
        testResult.setAttribute("testcase", testcase);

        //Reset app
        homePage.resetApp();

        //Login to app
        loginPage.loginToApp(testData.get("Username"), testData.get("Password"));
        assertTrue(homePage.settingElePresent());
        homePage.screenshot(test,"After trying to login app");
    }

    @DataProvider(name = "TC2")
    public Object[][] TC2() throws Exception {
        excel.setExcelFile(excelPath, "TC2");
        Object[][] data = excel.getParameterFromExcel("TC2");

        return data;
    }

    @Test(priority = 0, dataProvider = "TC2")
    public void Partner_and_Associate_login_app_by_email(String sheetName, String testcase) throws Exception {
        //Read test date from Excel file
        excel.setExcelFile(excelPath, sheetName);
        Map<String, String> testData = excel.getTcData(testcase);

//      //ExtentReports Description
        ExtentTest test = startTest("Partner and Associate login app by email", "Login app by: "+testData.get("Username")+"/"+testData.get("Password"));

        //Send data to listener
        ITestResult testResult = Reporter.getCurrentTestResult();
        testResult.setAttribute("excel", excel);
        testResult.setAttribute("testcase", testcase);
//
        try {
//          //Reset app
            homePage.resetApp();
            homePage.logout();
//
//          //Login to app
            loginPage.loginToApp(testData.get("Username"), testData.get("Password"));
            Assert.assertTrue(homePage.settingElePresent());
            homePage.screenshot(test,"After trying to login app");
        } catch (Exception e) {
            System.out.println("Exception:: "+e);
        }
    }
}
