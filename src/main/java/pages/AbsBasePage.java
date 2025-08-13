package pages;

import annotations.Path;
import common.AbsCommon;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.AnnotationUtils;

import java.util.List;

public abstract class AbsBasePage extends AbsCommon {
    private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");
    //Это ок для простого фреймворка, но при тестировании с несколькими окружениями удобнее, если BASE_URL будет в конфиге (например, Owner), чтобы не зависеть от системных свойств.

    public AbsBasePage(WebDriver driver) {
        super(driver);
        waitUtils.waitTillPageLoaded();
        waitUtils.waitTillPageReady();
    }


    public void openPage() {
        driver.get(BASE_URL + getPath());
        this.addCookie();
        driver.navigate().refresh();
        waitUtils.waitTillPageLoaded();
        waitUtils.waitTillPageReady();
        //Ты добавляешь куки после driver.get(), а потом делаешь refresh().
        //Это рабочий способ, но лучше сначала открыть страницу, загрузить минимальный контент, потом добавить куки, и уже перейти на страницу повторно, чтобы избежать лишней перезагрузки.
        //public void openPage() {
        //    driver.get(BASE_URL); // только базовый домен
        //    this.addCookie();
        //    driver.get(BASE_URL + getPath());
        //    waitUtils.waitTillPageLoaded();
        //    waitUtils.waitTillPageReady();
        //}
    }

    private String getPath() {
        return new AnnotationUtils().getAnnotationInstance(this.getClass(), Path.class).value();
        //Ты каждый раз в getPath() делаешь: new AnnotationUtils().getAnnotationInstance(...)
        //Можно просто внедрить AnnotationUtils через конструктор или Guice, чтобы не плодить объекты.
    }

    protected void pageLoadedCondition(List<WebElement> element) {
        waitUtils.waitTillElementVisible(element.getFirst());
    }
    //Метод List.getFirst() появился только в Java 21. Если проект будет собираться под более ранние версии Java, он упадёт.
    //Ещё одна проблема — если список пустой, будет NoSuchElementException.
    //protected void pageLoadedCondition(List<WebElement> elements) {
    //    if (elements != null && !elements.isEmpty()) {
    //        waitUtils.waitTillElementVisible(elements.get(0));
    //    } else {
    //        throw new IllegalArgumentException("Список элементов пуст или null — нельзя проверить загрузку страницы");
    //    }
    //}
}