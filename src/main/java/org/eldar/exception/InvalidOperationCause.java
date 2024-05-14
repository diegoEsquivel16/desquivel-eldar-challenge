package org.eldar.exception;

public enum InvalidOperationCause {

    OUT_OF_RANGE_OPERATION("The import of the operation is out of range");

    private final String message;

    InvalidOperationCause(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
