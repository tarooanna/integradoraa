package exceptions;

public class InvalidAssignmentException extends IllegalArgumentException{
    public InvalidAssignmentException(String message){
        super(message);
    }
}
