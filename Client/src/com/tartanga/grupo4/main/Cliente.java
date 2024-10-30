package com.tartanga.grupo4.main;

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
 * The {@code Cliente} class represents a client application responsible for handling
 * user sign-in and sign-up requests via a socket connection to a server.
 * <p>
 * This class implements the {@code Signable} interface, defining methods for user authentication and registration.
 * It manages socket connections, and handles error cases related to server communication and specific business logic errors.
 * It uses exceptions to communicate with the user interface windows.
 * </p>
 * 
 * @author Aitor
 * @see Signable
 */
public class Cliente implements Signable {
     /**
     * Port number for connecting to the server.
     */
    private final int PUERTO = 6000;
    /**
     * IP address of the server to connect to.
     */
    private final String IP = "127.0.0.1";//Cambiar segun donde conectar
    /**
     * The client socket used for establishing a connection with the server.
     */
    private Socket cliente = null;
    /**
     * Object input stream to receive objects from the server.
     */
    private ObjectInputStream entrada = null;
    /**
     * Object output stream to send objects to the server.
     */
    private ObjectOutputStream salida = null;
    /**
     * Logger instance for logging client operations and error messages.
     */
    private static final Logger logger = Logger.getLogger("Client");
    /**
     * Message object used for sending and receiving communication data.
     */
    private Message message = new Message();
    /**
     * Enum to handle request and responses with the server.
     */
    private SignInSignUpEnum sign;
    
    
    
     /**
     * Handles the sign-in process for the user by sending the login request to the server.
     * <p>
     * Establishes a socket connection to the server and transmits a {@code Message} containing
     * the user information and sign in request. Based on the server's response, it either returns
     * the user or throws specific exceptions if anything else.
     * </p>
     *
     * @param  user The {@code User} object containing the user's information.
     * @return The authenticated {@code User} object.
     * @throws ServerErrorException if a server error is encountered during sign-in.
     * @throws UserPasswdException if the provided user login or password is not correct.
     * @throws ClientSideException if connection or communication with the server fails.
     * @throws MaxConnectionsException if the maximum number of server connections is reached.
     */
    @Override
    public User signIn(User user) throws ServerErrorException, UserPasswdException, ClientSideException, MaxConnectionsException {
        try {
            cliente = new Socket(IP, PUERTO);
            logger.log(Level.INFO, "Conecting to the server");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            message.setSignInSignUpEnum(SignInSignUpEnum.SIGN_IN_REQUEST);
            message.setUser(user);
            salida.writeObject(message);

            message = (Message) entrada.readObject();
            //user = message.getUser();//Esta linea esta para probar el mensaje, borrarla cuando acabe las pruebas
            sign = (SignInSignUpEnum) message.getSignInSignUpEnum();
            logger.log(Level.INFO, "Answer from the server recived");

            switch (sign) {
                case OK:
                    logger.log(Level.INFO, "User verified");
                    break;

                case USER_PASSWD_ERROR:
                    logger.log(Level.SEVERE, "ERROR, password or user incorrect");
                    throw new UserPasswdException();

                case MAX_CONNECTIONS:
                    logger.log(Level.SEVERE, "Max conections (5) reached, refusing service");
                    throw new MaxConnectionsException();

                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "Internal server ERROR");
                    throw new ServerErrorException();
            }

        } catch (IOException error) {
            logger.log(Level.SEVERE, "IOException from ObjectOutputStream,ObjectInputStream");
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
     * Handles the sign-up process for the user by sending the registration request to the server.
     * <p>
     * Establishes a socket connection to the server and transmits a {@code Message} containing
     * the user information and sign up request. Based on the server's response, it either returns
     * the user or throws specific exceptions if anything else.
     * </p>
     *
     * @param  user The {@code User} object containing the user's information.
     * @return The just registered {@code User} object.
     * @throws ServerErrorException if a server error is encountered during sign-up.
     * @throws UserExistInDatabaseException if the user already exist in the database.
     * @throws ClientSideException if connection or communication with the server fails.
     * @throws MaxConnectionsException if the maximum number of server connections is reached.
     */
    @Override
    public User signUp(User user) throws UserExistInDatabaseException, ClientSideException, ServerErrorException, MaxConnectionsException {
        try {
            cliente = new Socket(IP, PUERTO);
            logger.log(Level.INFO, "Conecting to the server");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            message.setSignInSignUpEnum(SignInSignUpEnum.SIGN_UP_REQUEST);
            message.setUser(user);
            salida.writeObject(message);

            message = (Message) entrada.readObject();
            sign = (SignInSignUpEnum) message.getSignInSignUpEnum();
            logger.log(Level.INFO, "Answer from the server recived");

            switch (sign) {
                case OK:
                    logger.log(Level.INFO, "User has been registered");
                    break;

                case USER_EXIST_IN_DB:
                    logger.log(Level.SEVERE, "ERROR, chosen login already exist");
                    throw new UserExistInDatabaseException();

                case MAX_CONNECTIONS:
                    logger.log(Level.SEVERE, "Max conections reached, refusing service");
                    throw new MaxConnectionsException();

                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "Internal server ERROR");
                    throw new ServerErrorException();
            }

        } catch (IOException error) {
            logger.log(Level.SEVERE, "IOException from ObjectOutputStream,ObjectInputStream");
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
     * Closes the socket connection and input/output streams.
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
