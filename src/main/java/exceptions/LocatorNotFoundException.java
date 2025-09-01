package exceptions;

public class LocatorNotFoundException extends RuntimeException {
    public LocatorNotFoundException(String locatorInfo) {
        super("❌ Локатор не нашёл элементов! Проверьте: " + locatorInfo);
    }
}