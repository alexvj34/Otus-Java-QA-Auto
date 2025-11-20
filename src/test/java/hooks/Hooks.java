package hooks;

import com.google.inject.Guice;
import com.google.inject.Injector;
import factory.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import modules.GuiceComponentsModule;
import modules.GuicePagesModule;
import org.openqa.selenium.WebDriver;

public class Hooks {
  public static WebDriver driver;
  public static Injector injector;

  @Before
  public void setup() throws Exception {
    driver = new WebDriverFactory().create();
    injector = Guice.createInjector(
        new GuicePagesModule(driver),
        new GuiceComponentsModule(driver)
    );
  }
  @After
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
