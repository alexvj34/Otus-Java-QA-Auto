package exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String courseName) {
        super("❌ Курс не найден: " + courseName);
    }
}
