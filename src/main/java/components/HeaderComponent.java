package components;

import annotations.Component;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Component("xpath://div[@id='__next']/div[1]/div[2]")
public class HeaderComponent extends AbsBaseComponent {

  @FindBy(css = "span[title='Обучение']")
  private WebElement trainingField;

  @FindBy(xpath = "//header//a[contains(text(),'Курсы')]")
  private WebElement trainingMenu;

  public HeaderComponent(WebDriver driver) {
    super(driver);
  }

  public void hoverOnTrainingField() {
    verifyComponentLoaded();
    actionUtils.hoverOnElement(trainingField);
  }
}