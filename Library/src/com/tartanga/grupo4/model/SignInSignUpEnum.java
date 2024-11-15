package com.tartanga.grupo4.model;

/**
 * The {@code SignInSignUpEnum} enum defines the different status and request types
 * related to user sign-in and sign-up processes in the server.
 * <p>
 * This enumeration is used to categorize various responses from the server during 
 * user authentication and registration. Each constant represents a specific outcome 
 * or request type that can occur during these processes.
 * </p>
 * 
 * @author Aratz
 */
public enum SignInSignUpEnum {
    /**
     * Represents a request for user sign-in.
     * This value indicates that a user is attempting to log into the system.
     */
    SIGN_IN_REQUEST,

    /**
     * Represents a request for user sign-up.
     * This value indicates that a new user is attempting to register an account in the system.
     */
    SIGN_UP_REQUEST,

    /**
     * Represents a successful operation.
     * This value indicates that the previous sign-in or sign-up request was processed successfully.
     */
    OK,

    /**
     * Indicates an error with the username or password.
     * This value signifies that the provided credentials do not match any existing account.
     */
    USER_PASSWD_ERROR,

    /**
     * Indicates that the user already exists in the database.
     * This value signifies that a sign-up request failed because the username is already taken.
     */
    USER_EXIST_IN_DB,

    /**
     * Indicates a general server error.
     * This value signifies that an unexpected error occurred on the server while processing the request.
     */
    SERVER_ERROR,

    /**
     * Indicates that the maximum number of connections has been reached.
     * This value signifies that no additional connections can be accepted at this time.
     */
    MAX_CONNECTIONS;
}
