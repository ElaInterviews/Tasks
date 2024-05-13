package com.mozzie.nbp.domain.exceptions;

public class PeselInvalidException extends RuntimeException {

    public PeselInvalidException(String message) {
        super(message);
    }
}
