package com.test_task_tn.shared.exceptions;

import lombok.Getter;

@Getter
public class BadRequestImageException extends RuntimeException {

    private TypeException typeException;

    public BadRequestImageException(String message, TypeException typeException) {
        super(message);
        this.typeException = typeException;
    }
}
