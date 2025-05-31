package exceptions;

public class InvalidCourseException extends IllegalArgumentException{
    public InvalidCourseException(String message){
        super(message);
    }
}
