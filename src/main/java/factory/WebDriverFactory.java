package factory;

import exeptions.BrowserNotSupportedException;
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
/*
public class WebDriverFactory {

    private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
    private final String runMode = System.getProperty("mode", "local").toLowerCase();
    private final String vm = System.getProperty("url","http://192.168.18.52:4444/wd/hub");

    public WebDriver create() throws MalformedURLException {
        WebDriver driver;

        switch (browserName) {
            case "chrome":
                if ("remote".equals(runMode)) {
                    driver = new RemoteWebDriver(new URL(vm), new ChromeSettings().getDefaultSettings());
                } else {
                    driver = new ChromeDriver(new ChromeSettings().getDefaultSettings());
                }
                break;
            case "firefox":
                if ("remote".equals(runMode)) {
                    driver = new RemoteWebDriver(new URL(vm), new FirefoxSettings().settings());
                } else {
                    driver = new FirefoxDriver(new FirefoxSettings().settings());
                }
                break;
            default:
                throw new BrowserNotSupportedException(browserName);
        }
        return new EventFiringDecorator<>(new MouseListener()).decorate(driver);
    }
}
*/

//Сделаем так, чтобы оба класса реализовывали один и тот же интерфейс:
//Теперь фабрику можно упростить:
//package factory;
//
//import exeptions.BrowserNotSupportedException;
//import factory.settings.ChromeSettings;
//import factory.settings.FirefoxSettings;
//import factory.settings.IBrowserSettings;
//import listeners.MouseListener;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.events.EventFiringDecorator;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
public class WebDriverFactory {
    private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
    private final String runMode = System.getProperty("mode", "local").toLowerCase();
    private final String vm = System.getProperty("url","http://192.168.18.52:4444/wd/hub");
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

//✅ Плюсы такого подхода:
//
//Оба браузера работают через один интерфейс.
//
//В фабрике нет лишних .settings() vs .settingsAmd64() различий.
//
//Добавить новый браузер теперь — это просто сделать ещё одну реализацию IBrowserSettings и дописать один case в switch.