package extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import factory.WebDriverFactory;
import modules.GuiceComponentsModule;
import modules.GuicePagesModule;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class UIExtension  implements BeforeEachCallback, AfterEachCallback {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    @Override
    public void beforeEach(ExtensionContext context) throws MalformedURLException {
        WebDriver webDriver = new WebDriverFactory().create();
        DRIVER.set(webDriver);
        Injector injector = Guice.createInjector(new GuicePagesModule(webDriver), new GuiceComponentsModule(webDriver));
        injector.injectMembers(context.getTestInstance().get());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        WebDriver webDriver = DRIVER.get();
        if (webDriver != null) {
            if (context.getExecutionException().isPresent()) {
                Throwable throwable = context.getExecutionException().get();

                // Логируем потенциально сломанный локатор
                if (throwable instanceof NoSuchElementException || throwable instanceof IndexOutOfBoundsException) {
                    System.err.println("❌ Элемент не найден или список пуст! Возможно сломан локатор: " + throwable.getMessage());
                } else {
                    System.err.println("❌ Тест упал с исключением: " + throwable);
                }

                // Скриншот при любом падении
                String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9.-]", "_");
                takeScreenshot(webDriver, testName);
            }

            webDriver.quit();
            DRIVER.remove();
        }
    }

    private void takeScreenshot(WebDriver driver, String testName) {
        try {
            // Делаем скриншот
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Папка для сохранения
            String screenshotDir = "./target/screenshots/";
            new File(screenshotDir).mkdirs();

            // Имя файла: имя теста + timestamp
            String path = screenshotDir + testName + "_" + System.currentTimeMillis() + ".png";

            FileUtils.copyFile(source, new File(path));
            System.out.println("✅ Screenshot saved to: " + path);
        } catch (IOException e) {
            System.err.println("❌ Failed to capture screenshot: " + e.getMessage());
        }
    }
}
