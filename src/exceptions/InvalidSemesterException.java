package exceptions;

public class InvalidSemesterException extends IllegalArgumentException{
    public InvalidSemesterException(String message){
        super(message);
    }
}
