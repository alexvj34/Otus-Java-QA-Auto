package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import components.HeaderComponent;
import components.TrainingComponent;
import factory.WebDriverFactory;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import support.GuiceScoped;

import java.net.MalformedURLException;

public class CucumberModule extends AbstractModule {

  @Override
  protected void configure() {
    // Явно биндим все зависимости
    //bind(WebDriver.class).toProvider(GuicePagesModule.WebDriverProvider.class);
  }

  @Provides
  @ScenarioScoped
  public WebDriver provideWebDriver() throws MalformedURLException {
    System.out.println("Creating WebDriver...");
    return new WebDriverFactory().create();
  }

  @Provides
  public GuiceScoped provideGuiceScoped(WebDriver driver) {
    GuiceScoped scoped = new GuiceScoped();
    scoped.driver = driver;
    scoped.browserName = System.getProperty("browser", "chrome");
    return scoped;
  }

  // Страницы
  @Provides
  public MainPage provideMainPage(WebDriver driver) {
    return new MainPage(driver);
  }

  @Provides
  public CoursesPage provideCoursesPage(WebDriver driver) {
    return new CoursesPage(driver);
  }

  @Provides
  public CoursePage provideCoursePage(WebDriver driver) {
    return new CoursePage(driver);
  }

  // Компоненты
  @Provides
  public HeaderComponent provideHeaderComponent(WebDriver driver) {
    return new HeaderComponent(driver);
  }

  @Provides
  public TrainingComponent provideTrainingComponent(WebDriver driver) {
    return new TrainingComponent(driver);
  }
}
