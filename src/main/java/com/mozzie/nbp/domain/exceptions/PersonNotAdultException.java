package com.mozzie.nbp.domain.exceptions;

public class PersonNotAdultException extends RuntimeException {

    public PersonNotAdultException(String message) {
        super(message);
    }
}
