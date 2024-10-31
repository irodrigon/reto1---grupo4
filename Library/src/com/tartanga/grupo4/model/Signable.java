package com.tartanga.grupo4.model;

import com.tartanga.grupo4.exceptions.ClientSideException;
import com.tartanga.grupo4.exceptions.MaxConnectionsException;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.exceptions.UserPasswdException;

/**
 * Interface with the methods {@code signIn} and {@code signUp} that are
 * implemented on the classes {@code Cliente} and {@code DAO}
 *
 * @author Aitor
 */
public interface Signable {

    /**
     * Authenticates a user based on the provided {@code User} object containing 
     * login credentials. If authentication is successful, returns a {@code User} 
     * object representing the authenticated user. Used as the main method in the 
     * logic operations in both the client and the server.
     * <p>
     * Throws relevant exceptions if authentication fails due to incorrect 
     * credentials, server errors, or connection limitations.
     * </p>
     *
     * @param user the {@code User} object containing the user's login credentials
     * @return a {@code User} object representing the authenticated user
     * @throws UserPasswdException if the username or password is incorrect
     * @throws ServerErrorException if a server-side error occurs during authentication
     * @throws MaxConnectionsException if the maximum number of allowed connections is reached
     * @throws ClientSideException if {@code Cliente} throws an unexpected exception
     */
    public User signIn(User user) throws UserPasswdException, ServerErrorException, MaxConnectionsException,ClientSideException;

    /**
     * Registers a new user based on the provided {@code User} object. If registration 
     * is successful, returns a {@code User} object representing the newly registered user.
     * Used as the main method in the logic operations in both the client and the server.
     * <p>
     * Throws relevant exceptions if registration fails due to server errors, 
     * username uniqueness violations, or connection limitations.
     * </p>
     *
     * @param user the {@code User} object containing the user's registration details
     * @return a {@code User} object representing the newly registered user
     * @throws ServerErrorException if a server-side error occurs during registration
     * @throws UserExistInDatabaseException if the username already exists in the database
     * @throws MaxConnectionsException if the maximum number of allowed connections is reached
     * @throws ClientSideException if {@code Cliente} throws an unexpected exception
     */
    public User signUp(User user) throws ServerErrorException, UserExistInDatabaseException, MaxConnectionsException,ClientSideException;
}
