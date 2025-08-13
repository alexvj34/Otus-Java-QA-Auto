package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeSettings implements IBrowserSettings {

    public ChromeOptions settingsAmd64() //getDefaultSettings()
    // Переименовать метод settingsAmd64() во что-то более универсальное (getDefaultSettings() или build()), т.к. в нём нет ничего архитектурно специфичного под AMD64.
    //Сделать возможность читать аргументы и capabilities из конфигурационного файла (.properties или YAML), чтобы не хардкодить.
    //Добавить поддержку headless-режима через аргумент "--headless=new" для CI/CD.
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        options.setCapability("selenoid:options", selenoidOptions);
        return options;
    }
}




//Сделаем так, чтобы оба класса реализовывали один и тот же интерфейс:
//package factory.settings;
//
//import org.openqa.selenium.chrome.ChromeOptions;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ChromeSettings implements IBrowserSettings {
//    @Override
//    public ChromeOptions settingsAmd64() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");
//        Map<String, Object> selenoidOptions = new HashMap<>();
//        selenoidOptions.put("enableVNC", true);
//        options.setCapability("selenoid:options", selenoidOptions);
//        return options;
//    }
//}
