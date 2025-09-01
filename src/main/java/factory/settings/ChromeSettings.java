package factory.settings;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ChromeSettings implements IBrowserSettings {

    @Override
    public ChromeOptions getDefaultSettings() {
        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.getBoolean("browser.startMaximized")) {
            options.addArguments("start-maximized");
        }
        if (ConfigReader.getBoolean("browser.headless")) {
            options.addArguments("--headless=new");
        }

        String extraArgs = ConfigReader.get("browser.args");
        if (extraArgs != null && !extraArgs.isEmpty()) {
            for (String arg : extraArgs.split(",")) {
                options.addArguments(arg.trim());
            }
        }

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", ConfigReader.getBoolean("browser.vnc"));
        selenoidOptions.put("enableVideo", ConfigReader.getBoolean("browser.video"));
        options.setCapability("selenoid:options", selenoidOptions);

        logOptions();

        return options;
    }

    private void logOptions() {
        log.info("[ChromeSettings] Headless = " + ConfigReader.getBoolean("browser.headless"));
        log.info("[ChromeSettings] StartMaximized = " + ConfigReader.getBoolean("browser.startMaximized"));
        log.info("[ChromeSettings] VNC = " + ConfigReader.getBoolean("browser.vnc"));
        log.info("[ChromeSettings] Video = " + ConfigReader.getBoolean("browser.video"));
    }
}