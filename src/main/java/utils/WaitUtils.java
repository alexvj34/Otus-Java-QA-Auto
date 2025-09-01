package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private int duration = 20;
    private WebDriverWait wait;


    public WaitUtils(WebDriver driver) {
        this.driver = driver;
    }

    public WaitUtils getWaitDriver() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        return this;
    }

    public void waitTillElementBeClickable(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitTillElementVisible(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitTillElementVisible(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitTillPageReady() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void waitTillPageLoaded() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return typeof jQuery != 'function' || jQuery.active == 0")
                .equals(true));
    }
}