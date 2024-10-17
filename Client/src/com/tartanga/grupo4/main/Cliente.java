package com.tartanga.grupo4.main;

import Example.Message;
import Example.SignInSignUpEnum;
import Example.Signable;
import Example.User;
import exceptions.ServerErrorException;
import exceptions.UserExistInDatabaseException;
import exceptions.UserPasswdException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
IMPORTANTE
Esta clase solo esta creada para testear el server
Esta clase hay que borrarla y reemplazarla con la clase 
cliente una vez la tengamos desarrollada
 */
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
    public User signIn(User user) {
        try {
            cliente = new Socket(IP, PUERTO);
            logger.log(Level.INFO, "Conexion realizada con el servidor");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            message.setSignInSignUpEnum(SignInSignUpEnum.SIGN_IN_REQUEST);
            message.setUser(user);
            salida.writeObject(message);

            message = (Message) entrada.readObject();
            sign = (SignInSignUpEnum) message.getSignInSignUpEnum();
            logger.log(Level.INFO, "Respuesta del servidor recivida");
            
            switch(sign){
                case OK:
                    logger.log(Level.INFO, "Usuario verificado");
                    break;
                    
                case USER_PASSWD_ERROR:
                    logger.log(Level.SEVERE, "ERROR, contrasena y/o usuario incorrecto");
                    throw new UserPasswdException();
                
                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "ERROR interno del server");
                    throw new ServerErrorException();
            }
            

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }finally{
            logger.log(Level.INFO, "Conexion cliente cerrada");
            finalizar();
        }
        
        return user;

    }

    @Override
    public User signUp(User user) {
        try {
            cliente = new Socket(IP, PUERTO);
            logger.log(Level.INFO, "Conexion realizada con el servidor");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            message.setSignInSignUpEnum(SignInSignUpEnum.SIGN_UP_REQUEST);
            message.setUser(user);
            salida.writeObject(message);

            message = (Message) entrada.readObject();
            sign = (SignInSignUpEnum) message.getSignInSignUpEnum();
            logger.log(Level.INFO, "Respuesta del servidor recivida");
            
            switch(sign){
                case OK:
                    logger.log(Level.INFO, "Usuario registrado");
                    break;
                    
                case USER_EXIST_IN_DB:
                    logger.log(Level.SEVERE, "ERROR, el login introducido ya existe");
                    throw new UserExistInDatabaseException();
                
                case SERVER_ERROR:
                    logger.log(Level.SEVERE, "ERROR interno del server");
                    throw new ServerErrorException();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }finally{
            finalizar();
            logger.log(Level.INFO, "Conexion cliente cerrada");
        }
        return user;
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
