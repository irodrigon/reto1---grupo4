/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.dataaccess.DAOFactory;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.exceptions.UserPasswdException;
import com.tartanga.grupo4.main.ApplicationS;
import com.tartanga.grupo4.model.Message;
import com.tartanga.grupo4.model.SignInSignUpEnum;
import com.tartanga.grupo4.model.User;
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

            } else if (message.getSignInSignUpEnum().equals(SignInSignUpEnum.SIGN_UP_REQUEST)) {
                try {
                    user = DAOFactory.getSignable().signUp(message.getUser());
                } catch (UserExistInDatabaseException ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                //Deberiamos incluir nuestra excepcion aqui o asegurarnos que nos llega un enum
            }
            
            
            //Hay que decidir que se quiere devolver y cambiar esta parte
            salida.writeObject(user);

        } catch (IOException error) {
            Logger.getLogger("SERVIDOR").log(Level.SEVERE, "Fallo al mandar/recibir message");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserPasswdException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServerErrorException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
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
}
