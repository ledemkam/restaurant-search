package com.ledemkam.backend.exceptions;

public class ReviewNotAllowedException extends RuntimeException {

    public ReviewNotAllowedException() {
        super("Review not allowed for this restaurant.");
    }

    public ReviewNotAllowedException(String message) {
        super(message);
    }

    public ReviewNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewNotAllowedException(Throwable cause) {
        super(cause);
    }

    public ReviewNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
