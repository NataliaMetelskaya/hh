package ru.hh.test.utils;

/**
 * @author dzianis_shkindzerau
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
