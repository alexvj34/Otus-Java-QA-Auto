package pages;

import annotations.Path;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Path("/online/")
public class CoursesPreparePage {
  @FindBy(xpath = "//div[@class='lessons']//div[@class='lessons__new-item-price']")
  public List<WebElement> coursePrices;
  public List<WebElement> courseNames;
}