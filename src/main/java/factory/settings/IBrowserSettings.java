package factory.settings;

import org.openqa.selenium.remote.AbstractDriverOptions;

public interface IBrowserSettings {

    AbstractDriverOptions settingsAmd64();

}
//Сделаем так, чтобы оба класса реализовывали один и тот же интерфейс:
//// IBrowserSettings.java
//package factory.settings;
//
//import org.openqa.selenium.remote.AbstractDriverOptions;
//
//public interface IBrowserSettings {
//    AbstractDriverOptions<?> settingsAmd64();
//}