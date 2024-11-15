package com.tartanga.grupo4.exceptions;

/**
 * Exception that is thrown when the user trying to sign up already exist in the database. 
 * 
 * @author Aitor
 * 
 */
public class UserExistInDatabaseException extends Exception {

    /**
     * Constructor for {@code UserExistInDatabaseException} with a default message 
     * indicating an error on the server side.
     */
    public UserExistInDatabaseException() {
        super("The introduced login already exist in the DB");
    }

}
