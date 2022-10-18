package pages;

import io.appium.java_client.android.*;
import org.openqa.selenium.By;
import utils.logs.Log;

public class LoginPage extends BasePage {
    /**
     * Constructor
     */
    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mobile Elements
     */

    By usernameE = By.xpath("//android.widget.EditText[@text = 'Email']");
    By passwordE = By.xpath("//android.widget.EditText[@text = 'Password']");
    By usernameBoxE = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.EditText");
    By passwordBoxE = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.EditText");
    By scrollViewE = By.xpath("//android.widget.ScrollView");
    By buttonSignInE = By.xpath("//android.widget.TextView[@text = 'Sign in']");

    /**
     * Page Methods
     */
    public void loginToApp(String username, String password) {
        Log.info("Trying to login the Lydecker mobile app.");
        waitVisibility(usernameBoxE);
        replaceText(usernameBoxE, username);
        replaceText(passwordBoxE, password);
        click(scrollViewE);
        click(buttonSignInE);
    }

    public void clickUsername() {
        click(usernameBoxE);
        click(passwordBoxE);
        click(usernameBoxE);
    }
}
