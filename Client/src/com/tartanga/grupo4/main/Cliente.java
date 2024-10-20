package com.tartanga.grupo4.main;

import Example.Message;
import Example.SignInSignUpEnum;
import Example.Signable;
import Example.User;
import exceptions.MaxConnectionsException;
import exceptions.ServerErrorException;
import exceptions.UserExistInDatabaseException;
import exceptions.UserPasswdException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Signable {

    private final int PUERTO = 6000;
    private final String IP = "127.0.0.1";//Cambiar segun donde conectar
    Socket cliente = null;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;
    private static final Logger logger = Logger.getLogger("Client");
    Message message = new Message();
    SignInSignUpEnum sign;

    public void mandarMessage() {

    }

    /*public static void main(String[] args) {
		PruebaCliente c1 = new PruebaCliente();
		c1.iniciar();
	}*/
    @Override
    public User signIn(User user) throws ServerErrorException, UserExistInDatabaseException, Exception,MaxConnectionsException {
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
            return user;
        } finally {
            finalizar();
        }

    }

    @Override
    public User signUp(User user) throws UserPasswdException, ServerErrorException, Exception,MaxConnectionsException {
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
                    logger.log(Level.SEVERE, "Max conections (5) reached, refusing service");
                    throw new MaxConnectionsException();

                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "Internal server ERROR");
                    throw new ServerErrorException();
            }

            return user;
        } finally {
            finalizar();
        }
    }

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
