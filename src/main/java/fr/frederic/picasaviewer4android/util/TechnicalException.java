package fr.frederic.picasaviewer4android.util;

/**
 * Created by Frederic on 05/03/14.
 */
public class TechnicalException extends Exception {

    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     */
    public TechnicalException() {
        super();
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public TechnicalException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code TechnicalException} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable     the cause of this exception.
     */
    public TechnicalException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public TechnicalException(Throwable throwable) {
        super(throwable);
    }
}
