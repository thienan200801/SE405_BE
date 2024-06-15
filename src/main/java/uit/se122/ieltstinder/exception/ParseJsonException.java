package uit.se122.ieltstinder.exception;

public class ParseJsonException extends RuntimeException {
    public ParseJsonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
