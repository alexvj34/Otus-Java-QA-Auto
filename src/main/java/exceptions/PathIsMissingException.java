package exceptions;

import static java.lang.String.format;

public class PathIsMissingException extends RuntimeException {
    public PathIsMissingException(String className) {
        super(format("Путь к классу %s отсутствует", className));
    }
}