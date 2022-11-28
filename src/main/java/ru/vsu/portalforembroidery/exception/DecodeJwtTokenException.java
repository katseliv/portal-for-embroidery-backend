package ru.vsu.portalforembroidery.exception;

public class DecodeJwtTokenException extends RuntimeException {

    public DecodeJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
