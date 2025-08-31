package exceptions;

public class OpenedCourseNotFoundException extends RuntimeException {
    public OpenedCourseNotFoundException(String courseName) {
        super("❌ Открытый курс не найден: " + courseName);
    }
}
