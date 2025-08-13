package components;

import annotations.Component;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Component("xpath://*[@id=\"__next\"]/div[2]/div[2]")
public class HeaderComponent extends AbsBaseComponent {

    @FindBy(css = "span[title='Обучение']")
    private WebElement trainingField;

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void hoverOnTrainingField() {
        verifyComponentLoaded(); // can not check in constructor ,causes the issue in injection,during creating object element can not find
        actionUtils.hoverOnElement(trainingField);// use Actions
    }
}