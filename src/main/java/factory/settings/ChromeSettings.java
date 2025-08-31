package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

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

        // Дополнительные аргументы
        String extraArgs = ConfigReader.get("browser.args");
        if (extraArgs != null && !extraArgs.isEmpty()) {
            for (String arg : extraArgs.split(",")) {
                options.addArguments(arg.trim());
            }
        }

        // Selenoid options
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", ConfigReader.getBoolean("browser.vnc"));
        selenoidOptions.put("enableVideo", ConfigReader.getBoolean("browser.video"));
        options.setCapability("selenoid:options", selenoidOptions);

        logOptions();

        return options;
    }

    private void logOptions() {
        System.out.println("[ChromeSettings] Headless = " + ConfigReader.getBoolean("browser.headless"));
        System.out.println("[ChromeSettings] StartMaximized = " + ConfigReader.getBoolean("browser.startMaximized"));
        System.out.println("[ChromeSettings] VNC = " + ConfigReader.getBoolean("browser.vnc"));
        System.out.println("[ChromeSettings] Video = " + ConfigReader.getBoolean("browser.video"));
    }
}

