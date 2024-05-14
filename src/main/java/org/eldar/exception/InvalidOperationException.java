package org.eldar.exception;

public class InvalidOperationException extends Exception{

    public InvalidOperationException(InvalidOperationCause cause){
        super("The operation is invalid! Caused by: " + cause.getMessage());
    }
}
