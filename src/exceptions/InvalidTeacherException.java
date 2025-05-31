package exceptions;

public class InvalidTeacherException extends IllegalArgumentException{
    public InvalidTeacherException(String message){
        super(message);
    }
}
