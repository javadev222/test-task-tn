package com.test_task_tn.shared.exceptions;

import lombok.Getter;

public enum TypeException {
    NO_IMAGE("Images expected, but not ");

    @Getter
    private String text;

    TypeException(String text) {
        this.text = text;
    }
}
