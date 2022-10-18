package pages;

import java.io.*;
import java.time.Duration;
import java.util.*;

import com.aventstack.extentreports.*;
import io.appium.java_client.android.*;
import org.apache.commons.io.*;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public AndroidDriver driver;
    public WebDriverWait wait;

    //Constructor
    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    //Click Method
    public void click(By by) {
        waitVisibility(by).click();
    }

    //Write Text
    public void writeText(By by, String text) {
        waitVisibility(by).sendKeys(text);
    }

    //Replace Text
    public void replaceText(By by, String text) {
        waitVisibility(by).clear();
        waitVisibility(by).sendKeys(text);
    }

    //Read Text
    public String readText(By by) {
        return waitVisibility(by).getText();
    }

    //Wait
    public WebElement waitVisibility(By by) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException | NoSuchElementException ex) {
            return null;
        }
    }

    //Is An Element Displayed
    public boolean isDisplayed(By by, int timeOutInSeconds) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException | NoSuchElementException ex) {
            return false;
        }
        return true;
    }

    public void screenshot(ExtentTest test, String path_screenshot) {
        File srcFile=driver.getScreenshotAs(OutputType.FILE);
        String filename= UUID.randomUUID().toString();
        File targetFile=new File("extent-reports/"+ path_screenshot + filename +".jpg");
        try {
            FileUtils.copyFile(srcFile,targetFile);
        } catch (Exception e) {
            System.out.println("screenshot exception "+path_screenshot + " :: "+ e);
        }
        String filePath = targetFile.getAbsolutePath();

        test.info(path_screenshot, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
    }
}