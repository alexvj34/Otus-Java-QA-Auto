package exceptions;

import static java.lang.String.format;

public class BrowserNotSupportedException extends RuntimeException {
    public BrowserNotSupportedException(String browser) {
        super(format("Браузер '%s' не поддерживается", browser));
    }
}