package ru.hh.test.ui;

/**
 * @author dzianis_shkindzerau
 */
public class WebDriverPageException extends RuntimeException {

    public WebDriverPageException() {
    }

    public WebDriverPageException(String message) {
        super(message);
    }

    public WebDriverPageException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebDriverPageException(Throwable cause) {
        super(cause);
    }
}
