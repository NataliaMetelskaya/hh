package ru.hh.price.utils;

/**
 * @author e3s team
 */
public class XmlParserException extends RuntimeException {

    public XmlParserException() {
    }

    public XmlParserException(String message) {
        super(message);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParserException(Throwable cause) {
        super(cause);
    }
}
