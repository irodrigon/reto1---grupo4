/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Example.Message;
import Example.SignInSignUpEnum;
import Example.User;
import exceptions.UserExistInDatabaseException;
import exceptions.UserPasswdException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aitor
 */
public class Worker extends Thread {

    private static final Logger logger = Logger.getLogger("Client");
    Socket cliente;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;
    private ApplicationS servidor;
    private Message message;
    User user;

    public Worker(Socket cliente, ApplicationS servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    @Override
    public void run() {

        try {
            entrada = new ObjectInputStream(cliente.getInputStream());
            salida = new ObjectOutputStream(cliente.getOutputStream());

            message = (Message) entrada.readObject();

            if (message.getSignInSignUpEnum().equals(SignInSignUpEnum.SIGN_IN_REQUEST)) {
                user = DAOFactory.getSignable().signIn(message.getUser());
                message.setSignInSignUpEnum(SignInSignUpEnum.OK);
                logger.log(Level.INFO, "User has been verified");

            } else if (message.getSignInSignUpEnum().equals(SignInSignUpEnum.SIGN_UP_REQUEST)) {
                user = DAOFactory.getSignable().signUp(message.getUser());
                message.setSignInSignUpEnum(SignInSignUpEnum.OK);

            } else {
                message.setSignInSignUpEnum(SignInSignUpEnum.SERVER_ERROR);
            }

            mandarMessage(message);

        } catch (UserPasswdException error) {
            logger.log(Level.INFO, "Password/USer does not match");
            message.setSignInSignUpEnum(SignInSignUpEnum.USER_PASSWD_ERROR);
            mandarMessage(message);
            
        } catch (UserExistInDatabaseException error) {
            logger.log(Level.INFO, "User login already Exist in DB");
            message.setSignInSignUpEnum(SignInSignUpEnum.USER_EXIST_IN_DB);
            mandarMessage(message);

        } catch (IOException error) {
            logger.log(Level.INFO, "Failed to send message,ERROR CRITICAL", error);
            message.setSignInSignUpEnum(SignInSignUpEnum.SERVER_ERROR);
            mandarMessage(message);
            
        } catch (Exception error) {
            logger.log(Level.INFO, "ERROR SERVIDOR", error);
            message.setSignInSignUpEnum(SignInSignUpEnum.SERVER_ERROR);
            mandarMessage(message);
            
        } finally {
            servidor.liberarConexion();
            liberaRecursos();
        }

    }

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

    private void mandarMessage(Message message) {
        try {
            salida.writeObject(message);
        } catch (IOException error) {
            logger.log(Level.SEVERE, "Failed to send message,ERROR CRITICAL", error);
        }
    }
}
