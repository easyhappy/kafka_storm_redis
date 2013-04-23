package contrib;

@SuppressWarnings("serial")
public class FailedFetchException extends RuntimeException {
    public FailedFetchException(Exception e) {
        super(e);
    } 
}
 