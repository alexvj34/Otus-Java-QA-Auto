package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import factory.settings.FirefoxSettings;
import factory.settings.IBrowserSettings;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {
    private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
    private final String runMode = System.getProperty("mode", "local").toLowerCase();
    private final String vm = System.getProperty("url", "http://192.168.18.52:4444/wd/hub");

    public WebDriver create() throws MalformedURLException {
        IBrowserSettings settings;

        switch (browserName) {
            case "chrome":
                settings = new ChromeSettings();
                break;
            case "firefox":
                settings = (IBrowserSettings) new FirefoxSettings();
                break;
            default:
                throw new BrowserNotSupportedException(browserName);
        }

        WebDriver driver = "remote".equals(runMode)
                ? new RemoteWebDriver(new URL(vm), settings.getDefaultSettings())
                : createLocalDriver(settings);

        return new EventFiringDecorator<>(new MouseListener()).decorate(driver);
    }

    private WebDriver createLocalDriver(IBrowserSettings settings) {
        switch (browserName) {
            case "chrome":
                return new ChromeDriver((ChromeOptions) settings.getDefaultSettings());
            case "firefox":
                return new FirefoxDriver((FirefoxOptions) settings.getDefaultSettings());
            default:
                throw new BrowserNotSupportedException(browserName);
        }
    }

}