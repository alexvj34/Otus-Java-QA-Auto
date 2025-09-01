package common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ActionUtils;
import utils.WaitUtils;

public abstract class AbsCommon<T extends AbsCommon<T>> {
    protected ActionUtils actionUtils;
    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public AbsCommon(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver).getWaitDriver();
        this.actionUtils = new ActionUtils(driver);
        PageFactory.initElements(driver, this);//реализациа фабрики
    }

    protected void clickOnElement(WebElement element) {
        element.click();
    }

    protected String getText(WebElement element) {
        return element.getText();
    }

    protected String getElementAttribute(WebElement element, String text) {
        return element.getDomAttribute(text);
    }

    public void addCookie() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('cookieAccess', 'true');");
    }
}