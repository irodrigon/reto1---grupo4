/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.model;

import java.io.Serializable;

/**
 * Represents a message that can be sent between users during the sign-in or sign-up processes.
 * <p>
 * This class is designed to encapsulate user information and an enumeration indicating
 * the type of operation (sign-in or sign-up) being performed. It implements the
 * Serializable interface, allowing instances of this class to be serialized for
 * transmission over a network or for saving to a file.
 * </p>
 * <p>
 * The class contains the following attributes:
 * <ul>
 *     <li><strong>User user:</strong> The user associated with this message.</li>
 *     <li><strong>Enum SignInSignUpEnum:</strong> An enumeration that indicates whether
 *     this message pertains to a sign-in or sign-up operation.</li>
 * </ul>
 * </p>
 * <p>
 * The Message class provides getter and setter methods for its attributes, enabling
 * the retrieval and modification of user data and operation types.
 * </p>
 *
 * @author rabio
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization
    private User user; // The user related to this message
    private Enum SignInSignUpEnum; // Indicates the operation type (sign-in or sign-up)
    
    /**
     * Default constructor for the Message class.
     * <p>
     * This constructor initializes a new instance of the Message class.
     * The user and SignInSignUpEnum attributes are set to null initially.
     * </p>
     */
    public Message() {
        // Default constructor
    }

    /**
     * Gets the user associated with this message.
     * 
     * @return The user linked to this message, or null if not set.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this message.
     *
     * @param user The User object to be associated with this message.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the enumeration indicating the operation type for this message.
     * 
     * @return The enumeration representing the sign-in or sign-up operation,
     *         or null if not set.
     */
    public Enum getSignInSignUpEnum() {
        return SignInSignUpEnum;
    }

    /**
     * Sets the enumeration indicating the operation type for this message.
     *
     * @param SignInSignUpEnum The enumeration to set for the operation type.
     */
    public void setSignInSignUpEnum(Enum SignInSignUpEnum) {
        this.SignInSignUpEnum = SignInSignUpEnum;
    }
}
