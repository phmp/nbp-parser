package pl.parser.nbp.utils.exceptions;


public abstract class ExpectedException extends Exception{

    public ExpectedException(String message) {
        super(message);
    }

    public ExpectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
