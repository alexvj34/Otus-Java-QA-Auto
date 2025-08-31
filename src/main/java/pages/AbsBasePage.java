package pages;

import annotations.Path;
import common.AbsCommon;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.AnnotationUtils;

import java.util.List;

public abstract class AbsBasePage extends AbsCommon {

    private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");

    private final AnnotationUtils annotationUtils;

    public AbsBasePage(WebDriver driver) {
        super(driver);
        this.annotationUtils = new AnnotationUtils();
        waitUtils.waitTillPageLoaded();
        waitUtils.waitTillPageReady();
    }

    public void openPage() {
        driver.get(BASE_URL + getPath());
        waitUtils.waitTillPageLoaded();
        waitUtils.waitTillPageReady();
    }

    public void openPageWithCookies() {
        driver.get(BASE_URL);
        this.addCookie();
        driver.get(BASE_URL + getPath());
        waitUtils.waitTillPageLoaded();
        waitUtils.waitTillPageReady();
    }

    private String getPath() {
        return annotationUtils.getAnnotationInstance(this.getClass(), Path.class).value();
    }

    protected void pageLoadedCondition(List<WebElement> elements) {
        if (elements != null && !elements.isEmpty()) {
            waitUtils.waitTillElementVisible(elements.get(0));
        } else {
            throw new IllegalArgumentException(
                    "Список элементов пуст или null — нельзя проверить загрузку страницы"
            );
        }
    }
}