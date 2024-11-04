package com.tartanga.grupo4.exceptions;

/**
 * Exception that is thrown when the user's login and password does not match up 
 * when trying to sign in. 
 * 
 * @author Aitor
 * 
 */
public class UserPasswdException extends Exception {
     /**
     * Constructor for {@code UserPasswdException} with a default message 
     * indicating that the login and password does not match up.
     */
    public UserPasswdException() {      
        super("The user/password is not correct");
    }
}
