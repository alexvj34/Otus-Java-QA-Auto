package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;

public class ActionUtils {
    private Actions actions;

    public ActionUtils(WebDriver driver) {
        this.actions = new Actions(driver);
    }

    public void hoverOnElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }
    //если добавить ожидание (через твой waitUtils или WebDriverWait), то это сделает ховер стабильнее:
    //public void hoverOnElement(WebElement element) {
    //    // Ждём, чтобы элемент был виден
    //    waitUtils.waitTillElementVisible(element);
    //    actions.moveToElement(element).perform();
    //}
}
