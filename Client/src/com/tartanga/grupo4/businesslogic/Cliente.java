package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.exceptions.ClientSideException;
import com.tartanga.grupo4.model.Message;
import com.tartanga.grupo4.model.SignInSignUpEnum;
import com.tartanga.grupo4.model.Signable;
import com.tartanga.grupo4.model.User;
import com.tartanga.grupo4.exceptions.MaxConnectionsException;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.exceptions.UserPasswdException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client class that handles communication with the server for user authentication (sign in and sign up).
 * Implements the Signable interface, enabling client-side requests for signing in and signing up.
 * Handles server response and exceptions, managing both user verification and registration.
 */
public class Cliente implements Signable {

    /**
     * Port number for the server connection.
     */
    private final int PUERTO = 6000;

    /**
     * IP address of the server (change if necessary).
     */
    private final String IP = "127.0.0.1";

    /**
     * Socket for connecting to the server.
     */
    Socket cliente = null;

    /**
     * Stream for receiving objects from the server.
     */
    ObjectInputStream entrada = null;

    /**
     * Stream for sending objects to the server.
     */
    ObjectOutputStream salida = null;

    /**
     * Logger for tracking client actions and errors.
     */
    private static final Logger logger = Logger.getLogger("Client");

    /**
     * Message object to send requests and receive responses from the server.
     */
    Message message = new Message();

    /**
     * Enum to manage sign-in and sign-up responses.
     */
    SignInSignUpEnum sign;

    /**
     * Sends a message to the server. Currently empty.
     */
    public void mandarMessage() {
        // Empty method for sending a message
    }

    /**
     * Attempts to sign in a user by sending credentials to the server and processing the response.
     *
     * @param user the User object with credentials to be authenticated
     * @return the User object if sign-in is successful
     * @throws ServerErrorException if there is an internal server error
     * @throws UserPasswdException if the user/password combination is incorrect
     * @throws ClientSideException if there is an I/O or other client-side error
     * @throws MaxConnectionsException if the server's maximum connection limit is reached
     */
    @Override
    public User signIn(User user) throws ServerErrorException, UserPasswdException, ClientSideException, MaxConnectionsException {
        try {
            cliente = new Socket(IP, PUERTO);
            logger.log(Level.INFO, "Connecting to the server");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            message.setSignInSignUpEnum(SignInSignUpEnum.SIGN_IN_REQUEST);
            message.setUser(user);
            salida.writeObject(message);

            message = (Message) entrada.readObject();
            sign = (SignInSignUpEnum) message.getSignInSignUpEnum();
            logger.log(Level.INFO, "Answer from the server received");

            switch (sign) {
                case OK:
                    logger.log(Level.INFO, "User verified");
                    break;
                case USER_PASSWD_ERROR:
                    logger.log(Level.SEVERE, "ERROR: Incorrect password or username");
                    throw new UserPasswdException();
                case MAX_CONNECTIONS:
                    logger.log(Level.SEVERE, "ERROR: Maximum connections reached (5)");
                    throw new MaxConnectionsException();
                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "ERROR: Internal server error");
                    throw new ServerErrorException();
            }
        } catch (IOException error) {
            logger.log(Level.SEVERE, "IOException from ObjectOutputStream, ObjectInputStream");
            throw new ClientSideException();
        } catch (ClassNotFoundException error) {
            logger.log(Level.SEVERE, "ClassNotFoundException from readObject()");
            throw new ClientSideException();
        } finally {
            finalizar();
        }
        return user;
    }

    /**
     * Attempts to sign up a user by sending user information to the server and processing the response.
     *
     * @param user the User object to be registered
     * @return the User object if sign-up is successful
     * @throws UserExistInDatabaseException if the username already exists in the database
     * @throws ClientSideException if there is an I/O or other client-side error
     * @throws ServerErrorException if there is an internal server error
     * @throws MaxConnectionsException if the server's maximum connection limit is reached
     */
    @Override
    public User signUp(User user) throws UserExistInDatabaseException, ClientSideException, ServerErrorException, MaxConnectionsException {
        try {
            cliente = new Socket(IP, PUERTO);
            logger.log(Level.INFO, "Connecting to the server");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            message.setSignInSignUpEnum(SignInSignUpEnum.SIGN_UP_REQUEST);
            message.setUser(user);
            salida.writeObject(message);

            message = (Message) entrada.readObject();
            sign = (SignInSignUpEnum) message.getSignInSignUpEnum();
            logger.log(Level.INFO, "Answer from the server received");

            switch (sign) {
                case OK:
                    logger.log(Level.INFO, "User has been registered");
                    break;
                case USER_EXIST_IN_DB:
                    logger.log(Level.SEVERE, "ERROR: Username already exists");
                    throw new UserExistInDatabaseException();
                case MAX_CONNECTIONS:
                    logger.log(Level.SEVERE, "ERROR: Maximum connections reached (5)");
                    throw new MaxConnectionsException();
                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "ERROR: Internal server error");
                    throw new ServerErrorException();
            }
        } catch (IOException error) {
            logger.log(Level.SEVERE, "IOException from ObjectOutputStream, ObjectInputStream");
            throw new ClientSideException();
        } catch (ClassNotFoundException error) {
            logger.log(Level.SEVERE, "ClassNotFoundException from readObject()");
            throw new ClientSideException();
        } finally {
            finalizar();
        }
        return user;
    }

    /**
     * Closes the client socket and input/output streams to release resources.
     */
    public void finalizar() {
        try {
            if (cliente != null) {
                cliente.close();
            }
            if (entrada != null) {
                entrada.close();
            }
            if (salida != null) {
                salida.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
