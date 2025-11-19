package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Path("/online/")
public class CoursesPreparePage {
  // Локатор для цен подготовительных курсов
  @FindBy(xpath = "//div[@class='lessons']//div[@class='lessons__new-item-price']")
  public List<WebElement> coursePrices;
  public List<WebElement> courseNames;



}
