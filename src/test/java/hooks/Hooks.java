package hooks;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import factory.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import modules.GuiceComponentsModule;
import modules.GuicePagesModule;
import org.openqa.selenium.WebDriver;
import support.GuiceScoped;

public class Hooks {
  @Inject
  private GuiceScoped guiceScoped;

  @Before
  public void setup() {
    // Guice автоматически внедрит зависимости
    // WebDriver уже создан через Guice модуль
    System.out.println("Starting test with browser: " + guiceScoped.browserName);
  }

  @After
  public void tearDown() {
    if (guiceScoped.driver != null) {
      guiceScoped.driver.quit();
      guiceScoped.driver = null;
    }
  }
}
