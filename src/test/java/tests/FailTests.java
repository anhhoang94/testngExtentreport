package tests;

import com.aventstack.extentreports.*;
import org.testng.annotations.*;
import utils.logs.*;

import java.lang.reflect.*;

import static org.testng.AssertJUnit.assertTrue;
import static utils.extentreports.ExtentTestManager.startTest;

public class FailTests extends BaseTest {
    @Test(priority = 0, description = "failTest")
    public void failTest(Method method) {
        //ExtentReports Description
        ExtentTest test = startTest(method.getName(), "failTest");
        homePage.screenshot(test,"start app failTest");
        loginPage.loginToApp("123", "12345678x@X");
        homePage.screenshot(test,"login failTest");
        assertTrue(homePage.settingElePresent());
//        String query1 = "update invoice set status = \"New\" where mat_code = \"10014\"";
//        stmt.executeUpdate(query1);

//        excel.setCellData("Failed",1,5);
    }
}
