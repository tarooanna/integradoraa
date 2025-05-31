package exceptions;

public class InvalidResultIndexException extends IllegalArgumentException{
    public InvalidResultIndexException(String message){
        super(message);
    }
}
