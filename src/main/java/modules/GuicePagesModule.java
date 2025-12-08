package modules;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import factory.WebDriverFactory;
import jakarta.inject.Provider;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import support.GuiceScoped;

import java.net.MalformedURLException;

public class GuicePagesModule extends AbstractModule {

  @Override
  protected void configure() {
    // Биндим WebDriver через провайдер
    bind(WebDriver.class).toProvider((Class<? extends Provider<? extends WebDriver>>) WebDriverProvider.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  public MainPage provideMainPage(WebDriver driver) {
    return new MainPage(driver);
  }

  @Provides
  @Singleton
  public CoursesPage provideCoursesPage(WebDriver driver) {
    return new CoursesPage(driver);
  }

  @Provides
  @Singleton
  public CoursePage provideCoursePage(WebDriver driver) {
    return new CoursePage(driver);
  }

  @Provides
  @Singleton
  public MouseListener provideMouseListener() {
    return new MouseListener();
  }

  public static class WebDriverProvider implements Provider<WebDriver> {

    private final GuiceScoped guiceScoped;
    private final WebDriverFactory driverFactory;

    // Внедряем зависимости через конструктор
    public WebDriverProvider(GuiceScoped guiceScoped, WebDriverFactory driverFactory) {
      this.guiceScoped = guiceScoped;
      this.driverFactory = driverFactory;
    }

    @Override
    public WebDriver get() {
      if (guiceScoped.driver == null) {
        try {
          guiceScoped.driver = driverFactory.create();
        } catch (MalformedURLException e) {
          throw new RuntimeException(e);
        }
      }
      return guiceScoped.driver;
    }
  }
}