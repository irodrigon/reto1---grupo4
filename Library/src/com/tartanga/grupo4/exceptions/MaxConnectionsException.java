package com.tartanga.grupo4.exceptions;

/**
 * Exception that is thrown when the server maximum connections has been reached. 
 * 
 * @author Aitor
 * 
 */
public class MaxConnectionsException extends Exception {

    /**
     * Constructor for {@code MaxConnectionsException} with a default message 
     * indicating a connection refusal due to reaching maximum connections in the server.
     */
    public MaxConnectionsException() {
        super("Max conections reached, refusing service");
    }

   
}
