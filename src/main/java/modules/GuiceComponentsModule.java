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

    @Provides
    @Singleton
    public HeaderComponent getHeaderComponent() {
        return new HeaderComponent(driver);
    }

    @Provides
    @Singleton
    public TrainingComponent getTrainingComponent() {
        return new TrainingComponent(driver);
    }
}