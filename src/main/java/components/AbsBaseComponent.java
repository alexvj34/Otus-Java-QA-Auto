package components;

import annotations.Component;
import common.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.AnnotationUtils;

public abstract class AbsBaseComponent extends AbsCommon {

    public AbsBaseComponent(WebDriver driver) {
        super(driver);
    }

    public void verifyComponentLoaded() {
        waitUtils.waitTillElementVisible(driver.findElement(getComponentSelector()));
    }

    public By getComponentSelector() {
        String[] selector = new AnnotationUtils().getAnnotationInstance(this.getClass(), Component.class).value().split(":");
        return switch (selector[0].trim()) {
            case "css" -> By.cssSelector(selector[1].trim());
            case "xpath" -> By.xpath(selector[1].trim());
            default -> null;
        };
    }
}
