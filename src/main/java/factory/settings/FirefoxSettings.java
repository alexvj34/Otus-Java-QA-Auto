package factory.settings;

import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class FirefoxSettings {

    public FirefoxOptions settings() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        options.setCapability("selenoid:options", selenoidOptions);
        return options;
    }

}

//Унифицировать подходы: в Chrome используешь start-maximized, а в Firefox фиксированные размеры — лучше выбрать что-то одно.
//
//Чтение настроек из конфигов: чтобы можно было менять размер окна или включать headless без правок кода.
//
//Реализовать общий интерфейс (у тебя он уже есть IBrowserSettings) и сделать FirefoxSettings его реализацией — сейчас класс его не реализует.
//
//Добавить --headless для CI/CD или при необходимости.





//Сделаем так, чтобы оба класса реализовывали один и тот же интерфейс:
//// FirefoxSettings.java
//package factory.settings;
//
//import org.openqa.selenium.firefox.FirefoxOptions;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class FirefoxSettings implements IBrowserSettings {
//    @Override
//    public FirefoxOptions settingsAmd64() {
//        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--width=1920");
//        options.addArguments("--height=1080");
//        Map<String, Object> selenoidOptions = new HashMap<>();
//        selenoidOptions.put("enableVNC", true);
//        options.setCapability("selenoid:options", selenoidOptions);
//        return options;
//    }
//}