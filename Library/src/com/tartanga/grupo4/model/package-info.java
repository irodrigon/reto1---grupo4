/**
 * <h1>Model Package.</h1>
 * 
 * <p>
 * This package contains core data models and enumerations essential for the user 
 * authentication and management system. The classes and interfaces defined here 
 * facilitate seamless communication between the client and server, particularly 
 * in handling sign-in and sign-up requests, as well as representing user information.
 * </p>
 * 
 * <p>
 * The package includes several key components:
 * </p>
 * 
 * <ul>
 *   <li><b>{@link com.tartanga.grupo4.model.Message}</b>: 
 *       Represents a message object that encapsulates user details and request 
 *       types for communication between the client and server. This class ensures 
 *       that all necessary information is correctly packaged for efficient processing.</li>
 *   
 *   <li><b>{@link com.tartanga.grupo4.model.SignInSignUpEnum}</b>: 
 *       Defines the various status codes and request types associated with sign-in 
 *       and sign-up operations, such as success, user errors, and server-related issues. 
 *       It provides a robust way to handle and interpret request outcomes.</li>
 *   
 *   <li><b>{@link com.tartanga.grupo4.model.Signable}</b>: 
 *       An interface that defines the required methods for implementing sign-in 
 *       and sign-up functionalities. This ensures a consistent approach across 
 *       different classes that manage authentication.</li>
 *   
 *   <li><b>{@link com.tartanga.grupo4.model.User}</b>: 
 *       A class that encapsulates user-related information, such as username, 
 *       password, name, and address details. It includes methods for serialization, 
 *       making it suitable for transferring user data securely and efficiently.</li>
 * </ul>
 * 
 * <p>
 * This package plays a critical role in structuring the application's 
 * user authentication logic and handling user data effectively. It lays the 
 * foundation for managing client-server interactions with clear and organized 
 * data structures.
 * </p>
 * 
 * @see <a href="file:///C:/Users/2dami/Documents/NetBeansProjects/reto1---grupo4/Server/dist/javadoc/index-files/index-12.html">Worker</a>
 */
package com.tartanga.grupo4.model;
