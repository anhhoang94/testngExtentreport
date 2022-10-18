package pages;

import io.appium.java_client.android.*;
import org.openqa.selenium.By;
import utils.logs.Log;

public class HomePage extends BasePage {
    /**
     * Constructor
     */
    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Variables
     */
    String appPackage = "com.lydecker.timetracking.dev";

    /**
     * Mobile Elements
     */
    By timeEntryE = By.xpath("//android.widget.FrameLayout/android.widget.TextView[@text = 'Time Entry']");
    By notificationE = By.xpath("//android.widget.TextView[@text = 'Notification']");
    By newTimeEntryE = By.xpath("//android.widget.FrameLayout[3]/android.widget.ImageView");
    By reportE = By.xpath("//android.widget.TextView[@text = 'Report']");
    By settingsE = By.xpath("//android.widget.TextView[@text = 'Settings']");
    By signOutE = By.xpath("//android.widget.TextView[@text = 'Settings']/following-sibling::android.view.ViewGroup/android.widget.ImageView");
    By offlineE = By.xpath("//android.widget.TextView[@text = 'Working offline, pending to sync. Learn more']");
    By backE = By.xpath("//android.widget.ImageView");
    By updateAvailableE = By.xpath("//android.widget.TextView[@text = 'Update available']");
    By ignoreE = By.xpath("//android.widget.Button[@text = 'IGNORE']");

    /**
     * Page Methods
     */
    //Ignore update
    public void ignoreUpdate() {
        if (isDisplayed(updateAvailableE,5)) {
            Log.info("Ignore Update");
            click(ignoreE);
        }
    }

    //Go to LoginPage
    public void resetApp() {
        Log.info("Reset App");
        driver.terminateApp(appPackage);
        driver.activateApp(appPackage);
        ignoreUpdate();
    }

    public boolean settingElePresent() {
        return isDisplayed(settingsE, 10);
    }

    public void logout() {
        if (settingElePresent()) {
            click(settingsE);
            click(signOutE);
        }
    }

}
