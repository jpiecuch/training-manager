package pl.jakubpiecuch.trainingmanager.web.exception.notfound;

/**
 * Created by Rico on 2014-12-21.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super();
    }
}
