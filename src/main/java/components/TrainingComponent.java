package components;

import annotations.Component;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

@Component("xpath://nav/div[3]/div")
public class TrainingComponent extends AbsBaseComponent {

  @FindBy(xpath = "//nav//div[3]//div//div//div[1]//div//div//a")
  public List<WebElement> courcesList;

  // XPath пример — подставишь свой из меню
  @FindBy(xpath = "//a[contains(text(), 'Подготовительные курсы')]")
  private WebElement prepCoursesLink;

  @Inject
  public TrainingComponent(WebDriver driver) {
    super(driver);
  }

  public String clickOnRandomCourseAndReturnName() {
    verifyComponentLoaded();
    int index = (int) (Math.random() * courcesList.size());
    String name = getText(courcesList.get(index)).split(" \\(")[0];
    clickOnElement(courcesList.get(index));
    return name;
  }

  public void openPrepCourses() {
    prepCoursesLink.click();
  }
}