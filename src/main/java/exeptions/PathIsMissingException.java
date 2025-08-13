package exeptions;

import static java.lang.String.format;

public class PathIsMissingException extends RuntimeException{
    public PathIsMissingException(String className){
        super(format("Path on class %s is absent", className));

        //возможно тут тоже нужно заменить на этот аналог
        //super(format("Browser '%s' is not supported", browser));
    }
}
