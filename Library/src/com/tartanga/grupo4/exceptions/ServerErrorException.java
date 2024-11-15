package com.tartanga.grupo4.exceptions;

/**
 * Exception that is thrown when an error occurs on the server side of the application. 
 * This exception serves as a custom indication of server-specific issues.
 * 
 * @author Aitor
 * 
 */
public class ServerErrorException extends Exception {

    /**
     * Constructor for {@code ServerErrorException} with a default message 
     * indicating an error on the server side.
     */
    public ServerErrorException() {
        super("Server returned an error");
    }
}
