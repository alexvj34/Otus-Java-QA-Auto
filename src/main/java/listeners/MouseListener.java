package listeners;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class MouseListener implements WebDriverListener {

    @Override
    public void beforeClick(WebElement element) {
        WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
        highlight(element, driver, "3px solid red");
    }

    @Override
    public void afterClick(WebElement element) {
        WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
        highlight(element, driver, ""); // снимаем подсветку
    }

    private void highlight(WebElement element, WebDriver driver, String borderStyle) {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].style.border='" + borderStyle + "';", element);
        } catch (Exception e) {
            System.err.println("⚠️ Could not highlight element: " + e.getMessage());
        }
    }
}