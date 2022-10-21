package tests;

import static org.testng.AssertJUnit.assertTrue;
import static utils.extentreports.ExtentTestManager.getTest;
import static utils.extentreports.ExtentTestManager.startTest;

import java.util.*;
import com.aventstack.extentreports.*;
import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import utils.logs.*;

public class LoginTests extends BaseTest {
    @DataProvider(name = "TC1")
    public Object[][] testData() throws Exception {
        excel.setExcelFile(excelPath, "TC1");
        Object[][] data = excel.getParameterFromExcel("TC1");

        return data;
    }

    @Test(priority = 0, enabled = false, dataProvider ="TC1", description = "The appication detect the devide's language")
    public void WelcomeScreen_TheApplicationDetectTheDeviceLanguage(String sheetName, String testcase) throws Exception {
        //Read test date from Excel file
        excel.setExcelFile(excelPath, sheetName);
        Map<String, String> testData = excel.getTcData(testcase);

        //ExtentReports Description
        ExtentTest test = startTest("The application detect the device's language", "Set device's language is: "+testData.get("Language"));

        //Send data to listener
        ITestResult testResult = Reporter.getCurrentTestResult();
        testResult.setAttribute("excel", excel);
        testResult.setAttribute("testcase", testcase);

        try {
        //Set language and locale for device
        setLanguage(testData.get("Language"), testData.get("Locale"));
        getTest().log(Status.INFO,"Set Language/Locale:: " + testData.get("Language")+"/"+testData.get("Locale"));

        //Reset app
        homePage.resetApp();

        //Verify the device is set language and locale successfully
        Capabilities capabilities = driver.getCapabilities();
        Assert.assertTrue(testData.get("Language").contains(capabilities.getCapability("language").toString()));
        getTest().log(Status.INFO,"Get language from device:: " + capabilities.getCapability("language"));

        //Open Sign-up screen
        homePage.screenshot(test,"launch app");
        loginPage.clickSignUpNow();
        String appLanguage = loginPage.getLanguage();
        getTest().log(Status.INFO,"Get default language on the app:: " + appLanguage);
        Assert.assertTrue(testData.get("AppLanguage").contains(appLanguage));
        homePage.screenshot(test,"Get default language");
        } catch (Exception e) {
            Log.info("Exception:: "+e);
        }
    }

    @DataProvider(name = "TC2")
    public Object[][] TC2() throws Exception {
        excel.setExcelFile(excelPath, "TC2");
        Object[][] data = excel.getParameterFromExcel("TC2");

        return data;
    }

    @Test(priority = 0, enabled = false, dataProvider = "TC2")
    public void WelcomeScreen_UserSelectsSupportedLanguage(String sheetName, String testcase) throws Exception {
        //Read test date from Excel file
        excel.setExcelFile(excelPath, sheetName);
        Map<String, String> testData = excel.getTcData(testcase);

        //ExtentReports Description
        ExtentTest test = startTest("User is able to select one of the supported languages from Welcome screen:: ",
                "Default language: "+testData.get("AppLanguage")+
                        "New language: "+testData.get("Password"));

        //Send data to listener
        ITestResult testResult = Reporter.getCurrentTestResult();
        testResult.setAttribute("excel", excel);
        testResult.setAttribute("testcase", testcase);

        try {
            //Set language and locale for device
            setLanguage(testData.get("Language"), testData.get("Locale"));
            getTest().log(Status.INFO,"Set Language/Locale:: " + testData.get("Language")+"/"+testData.get("Locale"));

            //Reset app
            homePage.resetApp();

            //Open Sign-up screen
            homePage.screenshot(test,"launch app");
            loginPage.clickSignUpNow();
            getTest().log(Status.INFO,"Get default language on the app:: " + loginPage.getLanguage());
            homePage.screenshot(test,"Get default language");

            loginPage.selectLanguage(testData.get("NewLanguage"));
            String appLanguage =loginPage.getLanguage();
            getTest().log(Status.INFO,"Get the new language on the app:: " + appLanguage);
            Assert.assertTrue(testData.get("NewLanguage").contains(appLanguage));
            homePage.screenshot(test,"Set new language");

            //Go to Sign-up screen
            loginPage.tapNextButton();
            loginPage.waitTermsAndConditions();
            loginPage.swipeToAgree();
            loginPage.waitPolicy();
            loginPage.swipeToAgree();
            String languageSetOnApp = loginPage.getLanguageSetOnApp();
            Assert.assertTrue(testData.get("NewLanguage").contains(languageSetOnApp));
            homePage.screenshot(test,"The language is set on app");
        } catch (Exception e) {
            Log.info("Exception:: "+e);
        }
    }

    @Test(priority = 0)
    public void WelcomeScreen_TapXIconToBackToTheWelcome() throws Exception {
        //ExtentReports Description
        ExtentTest test = startTest("Tap x icon to back to the Welcome screen", "Tap x icon to back to the Welcome screen");

        try {
            //Reset app
            homePage.resetApp();

            //Open Sign-up screen
            homePage.screenshot(test,"launch app");
            loginPage.clickSignUpNow();

            //Close Terms and Conditions
            loginPage.tapNextButton();
            loginPage.waitTermsAndConditions();
            homePage.screenshot(test,"waitTermsAndConditions");
            loginPage.closeTermsAndConditions();
            Assert.assertTrue(loginPage.visibilityWelcomeScreen());
            homePage.screenshot(test,"visibilityWelcomeScreen");

            //Close Privacy Policy
            loginPage.tapNextButton();
            loginPage.waitTermsAndConditions();
            loginPage.swipeToAgree();
            loginPage.waitPolicy();
            homePage.screenshot(test,"waitPolicy");
            loginPage.closePrivacyPolicy();
            Assert.assertTrue(loginPage.waitTermsAndConditions());
            homePage.screenshot(test,"visibilityTermsAndConditions");

            loginPage.closeTermsAndConditions();
            Assert.assertTrue(loginPage.visibilityWelcomeScreen());
            homePage.screenshot(test,"visibilityWelcomeScreen");
        } catch (Exception e) {
            Log.info("Exception:: "+e);
        }
    }
}
