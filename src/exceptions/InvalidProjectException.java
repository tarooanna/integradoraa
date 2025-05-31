package exceptions;

public class InvalidProjectException extends IllegalArgumentException{
    public InvalidProjectException(String message){
        super(message);
    }
}
