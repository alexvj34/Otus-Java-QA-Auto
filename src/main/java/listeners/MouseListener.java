package listeners;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class MouseListener implements WebDriverListener {

    @Override
    public void beforeClick(WebElement element) {
        WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red';",
                element);
    }

}

//Корректный вариант:
//@Override
//public void beforeClick(WebElement element, WebDriver driver) {
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//    js.executeScript("arguments[0].style.border='3px solid red';", element);
//}

//Снятие рамки после клика (опционально)
//Иначе элементы будут "красными" и дальше. Можно сделать в afterClick:
//@Override
//public void afterClick(WebElement element, WebDriver driver) {
//    ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='';", element);
//}

//Совместимость с EventFiringDecorator
//Ты уже правильно обернул драйвер через
//new EventFiringDecorator<>(new MouseListener()).decorate(driver);