/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Example.Message;
import Example.SignInSignUpEnum;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aitor
 */
public class ApplicationS {

    private final int PUERTO = 6000;
    ServerSocket servidor = null;
    Socket cliente = null;
    private static int conexiones = 0;
    private final int MAX_CONEXIONES = 5;
    private static final Logger logger = Logger.getLogger("ApplicationS");
    ObjectOutputStream salida = null;

    /**
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
