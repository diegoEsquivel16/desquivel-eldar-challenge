package org.eldar.exception;

public class CreditCardRepositoryException extends Exception{

    public CreditCardRepositoryException(String cause){
        super("There was a problem in the Credit Card Repository! Caused by: " + cause);
    }
}
