package tests;

import common.helpers.*;
import io.appium.java_client.android.*;
import io.appium.java_client.android.options.*;
import org.testng.annotations.*;
import pages.*;
import utils.logs.Log;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.*;

public class BaseTest {
    public AndroidDriver driver;
    public HomePage homePage;
    public LoginPage loginPage;
    public ExcelHelpers excel;
    public static Connection conn;
    public static Statement stmt;
    public static String excelPath;
    public AndroidDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void cleanReport() throws Exception {
        // Set the test data file
        excel = new ExcelHelpers();
        File excelFile = new File("./src/test/resources/TestData.xlsx");
        excelPath = excelFile.getAbsolutePath();
        Log.info("Excel Path: "+excelPath);

        // Clean up report folder
        Log.info("Clean up Report!");
        File dir = new File("./extent-reports");
        for (File file:dir.listFiles()) {
            file.delete();
        }
        dir.delete();

        // Get the Db connection
//        conn = ConnectionDB.getConnection();
//        stmt = conn.createStatement();
    }

    @BeforeClass
    public void classLevelSetup() throws Exception {
        Log.info("Tests is starting!");
        File file = new File("./src/test/resources/app-development-release.apk");
        System.out.println("apk File Path: "+file.getAbsolutePath());
        UiAutomator2Options caps = new UiAutomator2Options()
                .setDeviceName("sdk_gphone64_x86_64")
                .setPlatformVersion("12")
                .setApp(file.getAbsolutePath())
                .setFullReset(false)
                .setNoReset(true)
                .eventTimings();

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);

        // Enable wifi connection
        if (!driver.getConnection().isWiFiEnabled()) {
            driver.toggleWifi();
        }
    }

    @BeforeMethod
    public void methodLevelSetup() {
        System.out.println("BeforeMethod: ");
    }

    @AfterMethod
    public void methodLevelTeardown() {
        System.out.println("AfterMethod: ");
    }

    @AfterClass
    public void teardown() throws SQLException {
        Log.info("Tests are ending!");
        if (conn != null) {
            Log.info("Close connection!");
            conn.close();
        }

        if (driver != null) {
            Log.info("Quit driver!");
            driver.quit();
        }
    }
}
