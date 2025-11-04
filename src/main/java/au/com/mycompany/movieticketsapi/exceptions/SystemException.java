package au.com.mycompany.movieticketsapi.exceptions;

public class SystemException extends RuntimeException {
    public SystemException(String message) {
        super(message);
    }
}
