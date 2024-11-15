package com.tartanga.grupo4.exceptions;

/**
 * Exception that is thrown when an error occurs on the client side of the application.
 * This exception serves as a custom indication of client-specific issues.
 * 
 * @author Aitor
 * 
 */
public class ClientSideException extends Exception {

    /**
     * Constructor for {@code ClientSideException} with a default message 
     * indicating a client-side error has occurred.
     */
    public ClientSideException() {
        super("An error in the client side just happened, please contact support service or try later");
    }

}
