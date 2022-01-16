package jason.javino.exception;

import jason.javino.constants.JavinoConstants;

/** Exception class for exceptions in the base conversion. */
public class BaseConversionException extends Exception {

    /** Default error message. */
    private static final String DEFAULT_MESSAGE = JavinoConstants.JAVINO_IDENTIFIER
            + "Error converting the value/element %s to the base %s";

    /**
     * Construtor.
     *
     * @param value    The value to be converted.
     * @param baseName Name of the base to be converted.
     */
    public BaseConversionException(Object value, String baseName) {
        super(String.format(DEFAULT_MESSAGE, value, baseName));
    }

}
