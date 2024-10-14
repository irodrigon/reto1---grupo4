/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Example.Message;
import Example.SignInSignUp;
import Example.User;
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

    public Worker(Socket cliente, ApplicationS servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        
        try{
            entrada = new ObjectInputStream(cliente.getInputStream());
            salida = new ObjectOutputStream(cliente.getOutputStream());
            
            message = (Message) entrada.readObject();
            
            if(message.getSignInSignUp().equals(SignInSignUp.SIGN_IN)){
                User user = DAOFactory.getSignable().signIn();
                salida.writeObject("Sign in correcto");
            }else if(message.getSignInSignUp().equals(SignInSignUp.SIGN_UP)){
                User user = DAOFactory.getSignable().signUp();
                salida.writeObject("Sign up correcto");
            }else{
                //Deberiamos incluir nuestra excepcion aqui o asegurarnos que nos llega un enum
            }
            
            //Hay que decidir que se quiere devolver y cambiar esta parte
            
            
        }catch (IOException error){
            Logger.getLogger("SERVIDOR").log(Level.SEVERE, "Fallo al mandar/recivir message");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
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
