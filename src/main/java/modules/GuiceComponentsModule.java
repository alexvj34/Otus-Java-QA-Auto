package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import components.HeaderComponent;
import components.TrainingComponent;
import org.openqa.selenium.WebDriver;

public class GuiceComponentsModule extends AbstractModule {
  private WebDriver driver;

  public GuiceComponentsModule(WebDriver driver) {
    this.driver = driver;
  }

  @Override
  protected void configure() {
    // Конфигурация бинов
  }

  @Provides
  @Singleton
  public HeaderComponent getHeaderComponent(WebDriver driver) {
    return new HeaderComponent(driver);
  }

  @Provides
  @Singleton
  public TrainingComponent getTrainingComponent(WebDriver driver) {
    return new TrainingComponent(driver);
  }
}