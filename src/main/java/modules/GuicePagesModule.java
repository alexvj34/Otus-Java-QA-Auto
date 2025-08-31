package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;

public class GuicePagesModule extends AbstractModule {

    private WebDriver driver;

    public GuicePagesModule(WebDriver driver) {
        this.driver = driver;
    }

    @Provides
    public WebDriver getDriver() {
        return driver;
    }

    @Provides
    @Singleton
    public MainPage getMainPage() {
        return new MainPage(driver);
    }
    @Provides
    @Singleton
    public CoursesPage getCoursesPage() {
        return new CoursesPage(driver);
    }

    @Provides
    @Singleton
    public CoursePage getCoursePage() {
        return new CoursePage(driver);
    }

    @Provides
    @Singleton
    public MouseListener getMouseListener() {
        return new MouseListener();
    }
    //возможно лучше вместе MouseListener
    //@Provides
    //@Singleton
    //public WebDriver getDriver(MouseListener listener) {
    //    WebDriver baseDriver = new ChromeDriver();
    //    return new EventFiringDecorator<>(listener).decorate(baseDriver);
    //}
    //Ты в GuicePagesModule возвращаешь MouseListener, но он сам по себе бесполезен, если драйвер не обёрнут в EventFiringDecorator.
    //Обычно удобнее оборачивать драйвер прямо в модуле, где он создаётся:

}