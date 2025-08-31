package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {
    private Actions actions;

    public ActionUtils(WebDriver driver) {
        this.actions = new Actions(driver);
    }

    public void hoverOnElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }
}