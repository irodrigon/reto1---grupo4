/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.dataaccess.DAOFactory;
import com.tartanga.grupo4.main.ApplicationS;
import com.tartanga.grupo4.model.Message;
import com.tartanga.grupo4.model.SignInSignUpEnum;
import com.tartanga.grupo4.model.User;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.exceptions.UserPasswdException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread Class that processes client requests. Depending on the request 
 * it uses either <code>signIn</code> or <code>signUp</code> to attack the database. 
 * It responds with a <code>Message</code> that contains the answer of the server.
 * 
 * This class is responsible for:
 * <ul>
 *   <li>Reading the client's request message</li>
 *   <li>Authenticating a user for sign-in</li> 
 *   <li>Registering a new user for sign-up</li>
 *   <li>Sending responses to the client</li>
 *   <li>Releasing resources when the client is finished</li>
 *   <li>Updating <code>conexiones</code> lowering the counts when the client has finished</li>
 * </ul>
 * 
 * @author Aitor
 */
public class Worker extends Thread {
    private static final Logger logger = Logger.getLogger("Worker");
    Socket cliente;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;
    private ApplicationS servidor;
    private Message message;
    User user;

    
    /**
     * Constructor for Worker class.
     * 
     * @param cliente  The client socket
     * @param servidor The main server instance that manages <code>conexiones</code>
     */
    public Worker(Socket cliente, ApplicationS servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    
    /**
     * Runs the main logic for handling the client's request.
     * 
     * <p>It calls <code>DAOFactory</code> to get the methods needed to process the clients request</p>
     * 
     * <p>Exceptions are used to handle different errors and logical errors and send an appropriate respond
     * to client</p>
     */
    @Override
    public void run() {

        try {
            entrada = new ObjectInputStream(cliente.getInputStream());
            salida = new ObjectOutputStream(cliente.getOutputStream());

            message = (Message) entrada.readObject();

            if (message.getSignInSignUpEnum().equals(SignInSignUpEnum.SIGN_IN_REQUEST)) {
                user = DAOFactory.getInstance().getSignable().signIn(message.getUser());
                message.setSignInSignUpEnum(SignInSignUpEnum.OK);
                logger.log(Level.INFO, "User has been verified");

            } else if (message.getSignInSignUpEnum().equals(SignInSignUpEnum.SIGN_UP_REQUEST)) {
                user = DAOFactory.getInstance().getSignable().signUp(message.getUser());
                message.setSignInSignUpEnum(SignInSignUpEnum.OK);
                logger.log(Level.INFO, "User has been created");
            } else {
                message.setSignInSignUpEnum(SignInSignUpEnum.SERVER_ERROR);
                logger.log(Level.INFO, "Client did not send a request. ERROR");
            }

            mandarMessage(message);

        } catch (UserPasswdException error) {
            logger.log(Level.INFO, "Password/USer does not match", error.toString());
            message.setSignInSignUpEnum(SignInSignUpEnum.USER_PASSWD_ERROR);
            mandarMessage(message);
            
        } catch (UserExistInDatabaseException error) {
            logger.log(Level.INFO, "User login already Exist in DB", error.toString());
            message.setSignInSignUpEnum(SignInSignUpEnum.USER_EXIST_IN_DB);
            mandarMessage(message);

        } catch (IOException error) {
            logger.log(Level.INFO, "Failed to send message,ERROR CRITICAL", error.toString());
            message.setSignInSignUpEnum(SignInSignUpEnum.SERVER_ERROR);
            mandarMessage(message);
            
        } catch (Exception error) {
            logger.log(Level.INFO, "ERROR SERVIDOR", error.toString());
            message.setSignInSignUpEnum(SignInSignUpEnum.SERVER_ERROR);
            mandarMessage(message);
            
        } finally {
            liberaRecursos();
            servidor.controlarConexion(0);
        }

    }
    
    /**
     * Closes all resources used in the thread.
     */
    public void liberaRecursos() {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Sends the the message to the client
     * 
     * @param message the message that want to be sent to the client
     */
    private void mandarMessage(Message message) {
        try {
            salida.writeObject(message);
        } catch (IOException error) {
            logger.log(Level.SEVERE, "Failed to send message,CRITICAL ERROR", error);
        }
    }
}
