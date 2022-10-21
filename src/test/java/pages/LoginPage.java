package pages;

import io.appium.java_client.android.*;
import org.openqa.selenium.By;
import utils.logs.Log;
import common.helpers.*;
import org.openqa.selenium.Point;

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

    By welcomeE = By.xpath("//android.widget.TextView[@text = 'Welcome']");
    By welcomeMessageE = By.xpath("//android.widget.TextView[@text = 'Please select your language']");
    By languageDropE = By.xpath("//android.widget.EditText[@resource-id = 'text_input']");
    By signUpNowE = By.xpath("//android.widget.TextView[@text = 'No account yet? Sign up now']");
    By nextButtonE = By.xpath("//android.widget.TextView[@text = 'Next']");
    By termsAndConditionsE = By.xpath("//android.widget.TextView[@text = 'Terms and Conditions']");
    By privacyPolicyE = By.xpath("//android.widget.TextView[@text = 'Privacy Policy']");
    By swipeToAgreeE = By.xpath("//android.view.ViewGroup[@resource-id = 'TitleContainer']");
    By swipeButtonE = By.xpath("//android.view.ViewGroup[@resource-id = 'IconContainer']");
    By languageSetOnAppE = By.xpath("//android.widget.TextView[@text = 'Sign Up']//parent::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView");
    By closeTermsAndConditionsE = By.xpath("//android.widget.TextView[@text = 'Terms and Conditions']/following-sibling::android.view.ViewGroup");
    By closePrivacyPolicyE = By.xpath("//android.widget.TextView[@text = 'Privacy Policy']/following-sibling::android.view.ViewGroup");
    By signUpE = By.xpath("//android.widget.TextView[@text = 'Sign Up']");
    By mobileNumberE = By.xpath("//android.widget.TextView[@text = 'Mobile Number']");
    By verifyAreaCodeE = By.xpath("//android.widget.TextView[@text = 'Mobile Number']/following-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView");
    By enterPhoneNumberE = By.xpath("//android.widget.TextView[@text = '+65']//parent::android.view.ViewGroup/android.widget.EditText");
    By alreadyHaveAccountE = By.xpath("//android.widget.TextView[@text = 'Already have account? Sign In']");
    By passwordBoxE = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.EditText");
    By scrollViewE = By.xpath("//android.widget.ScrollView");
    By buttonSignInE = By.xpath("//android.widget.TextView[@text = 'Sign in']");

    /**
     * Page Methods
     */
    public void tapNextButton() {
        Log.info("Tap the Next button");
        click(nextButtonE);
    }

    public void swipeToAgree() {
        int startX = driver.findElement(swipeButtonE).getLocation().getX()
                + driver.findElement(swipeButtonE).getSize().getWidth()/2;
        int startY = driver.findElement(swipeButtonE).getLocation().getY()
                + driver.findElement(swipeButtonE).getSize().getHeight()/2;
        Point startSwipePoint = new Point((int)startX, (int)startY);

        int endX = driver.findElement(swipeToAgreeE).getLocation().getX()
                + driver.findElement(swipeToAgreeE).getSize().getWidth()
                - driver.findElement(swipeButtonE).getSize().getWidth()/2;
        int endY = driver.findElement(swipeToAgreeE).getLocation().getY()
                + driver.findElement(swipeToAgreeE).getSize().getHeight()/2;

        Log.info("swipeToAgree:: "+"startX: "+startX+" endX: "+endX+" startY: "+startY+" endY: "+endY);
        Point endSwipePoint = new Point((int)endX, (int)endY);
        W3cActions.doSwipe(driver, startSwipePoint, endSwipePoint, 1000);
    }

    public String getLanguage() {
        Log.info("getLanguage:: "+driver.findElement(languageDropE).getText());
        return driver.findElement(languageDropE).getText();
    }

    public void clickSignUpNow() {
        if (isDisplayed(signUpNowE, 5)) {
            int startX = driver.findElement(signUpNowE).getLocation().getX();
            int endX = driver.findElement(signUpNowE).getSize().getWidth();

            int startY = driver.findElement(signUpNowE).getLocation().getY();
            int endY = driver.findElement(signUpNowE).getSize().getHeight();

            Log.info("clickSignUpNow:: "+"startX: "+startX+" endX: "+endX+" startY: "+startY+" endY: "+endY);
            Point forTap = new Point((int)(startX + endX/2), (int)(startY + endY/2));
            W3cActions.doTap(driver, forTap, 200);
        }
    }

    public void selectLanguage(String language) {
        Log.info("selectLanguage");
        click(languageDropE);
        Log.info("visibleLanguage"+isDisplayed(By.xpath("//android.widget.CheckedTextView[@text = '"+language+"']"), 10));
        click(By.xpath("//android.widget.CheckedTextView[@text = '"+language+"']"));
    }

    public boolean waitTermsAndConditions() {
        Log.info("waitTermsAndConditions");
        return isDisplayed(termsAndConditionsE, 10);
    }

    public boolean waitPolicy() {
        Log.info("waitPolicy");
        return isDisplayed(privacyPolicyE, 10);
    }

    public String getLanguageSetOnApp() {
        String getLanguageSetOnApp = driver.findElement(languageSetOnAppE).getText();
        Log.info("getLanguageSetOnApp:: "+getLanguageSetOnApp);
        return getLanguageSetOnApp;
    }

    public void closeTermsAndConditions() {
        click(closeTermsAndConditionsE);
    }

    public void closePrivacyPolicy() {
        click(closePrivacyPolicyE);
    }

    public boolean visibilityWelcomeScreen() {
        return (isDisplayed(welcomeE, 10) && isDisplayed(welcomeMessageE, 10));
    }
}
