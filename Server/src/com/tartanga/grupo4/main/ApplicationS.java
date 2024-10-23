/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.main;

import com.tartanga.grupo4.model.Message;
import com.tartanga.grupo4.model.SignInSignUpEnum;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.tartanga.grupo4.businesslogic.Worker;

/**
 * Main Class that starts the server. In a loop accepting client sockets until
 * an admin breaks the loop when it wants to shut it down. At which point the
 * program will run its course only after making sure that there are no more
 * active connections.
 *
 * @author Aitor
 */
public class ApplicationS {

    private final int PUERTO = 6000;
    ServerSocket servidor = null;
    Socket cliente = null;
    private static int conexiones = 0;
    private final int MAX_CONEXIONES = 5;

    //private boolean running = true;


    private static final Logger logger = Logger.getLogger("ApplicationS");
    ObjectOutputStream salida = null;

    /**
     * The main method that launches the server. It initializes the server,
     * starts listening for client connections, and handles incoming requests.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationS application = new ApplicationS();
        try {
            logger.log(Level.INFO, "Starting server...");
            application.iniciar();
        } catch (IOException error) {
            logger.log(Level.INFO, "Critical error when starting the server", error.toString());
        }

    }

    /**
     * Starts the server and listens for client connections in a loop. When a
     * client connects, the server either allocates a new thread for the
     * connection if the maximum number of connections has not been reached, or
     * refuses the connection if the limit is exceeded.
     *
     * @throws IOException if there is with the connection or
     *                     <code>ObjectOutputStream</code>.
     */
    public void iniciar() throws IOException {

        try {
            servidor = new ServerSocket(PUERTO);

            while (true) {
                logger.log(Level.INFO, "Wating conexion from client");
                cliente = servidor.accept();

                if (conexiones < MAX_CONEXIONES) {
                    controlarConexion(1);
                    Worker hilo = new Worker(cliente, this);
                    hilo.start();

                } else {
                    //Esto no esta testeado porque no tengo manera de mantener 5 conexiones a la vez. Probar con Junit
                    logger.log(Level.INFO, "Max conections (5) reached, refusing service");
                    salida = new ObjectOutputStream(cliente.getOutputStream());
                    Message message = new Message();
                    message.setSignInSignUpEnum(SignInSignUpEnum.MAX_CONNECTIONS);
                    salida.writeObject(message);
                    if (cliente != null) {
                        cliente.close();
                    }
                    if (salida != null) {
                        salida.close();
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            finalizar();

        }
    }
          
    /*public void finishServer() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        running = false;
        System.out.println("Cerrando servidor...");
        System.exit(0);
    }*/


    /**
     * Closed the server and client sockets. This method is called when the
     * server has been ordered to shut down.
     */
    public void finalizar() {
        try {
            if (servidor != null) {
                servidor.close();
            }

            if (cliente != null) {
                cliente.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Synchronizes and controls the number of active client connections.
     *
     * @param tipo an integer value to tell the method to increase or decrease <code>conexiones</code>.
     *             Pass 1 increase, any other value to decrease.
     */
    public synchronized void controlarConexion(int tipo) {
        if (tipo == 1) {
            conexiones++;
            Logger.getLogger("SERVIDOR").log(Level.INFO, "Clients connected: {0}", conexiones);
        } else {
            conexiones--;
            Logger.getLogger("SERVIDOR").log(Level.INFO, "Clients connected: {0}", conexiones);
        }

    }

}
