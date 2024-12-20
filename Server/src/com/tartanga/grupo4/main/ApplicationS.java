package com.tartanga.grupo4.main;

import com.tartanga.grupo4.businesslogic.Listener;
import com.tartanga.grupo4.model.Message;
import com.tartanga.grupo4.model.SignInSignUpEnum;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.tartanga.grupo4.businesslogic.Worker;
import com.tartanga.grupo4.dataaccess.CloseableFactory;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Main Class that starts the server. In a loop accepting client sockets until
 * an admin breaks the loop when it wants to shut it down. At which point the
 * program will run its course only after making sure that there are no more
 * active connections.
 *
 * @author Aitor
 */
public class ApplicationS {
    /**
     * Resource bundle for loading configuration properties, such as maximum connections allowed.
     */
    ResourceBundle resourceBundle = ResourceBundle.getBundle("com/tartanga/grupo4/resources/connectionServer");
    /**
     * Port number on which the server will listen for incoming client connections.
     */
    private final int PUERTO = Integer.parseInt(resourceBundle.getString("port"));
    /**
     * Maximum number of client connections allowed simultaneously, loaded from configuration.
     */
    private final int MAX_CONEXIONES = Integer.parseInt(resourceBundle.getString("max_conections"));
    /**
     * Server socket that listens for client connection requests.
     */
     private ServerSocket servidor = null;
    /**
     * Client socket that represents the connection between the server and a client.
     */
    private Socket cliente = null;
    /**
     * Counter to track the number of active client connections.
     */
    private static int conexiones = 0;
    /**
     * Flag indicating whether the server is running; used to control the server's main loop.
     */
    private static boolean running = true;
    /**
     * Logger to record server events, information, and errors at various log levels.
     */
    private static final Logger logger = Logger.getLogger("ApplicationS");
    /**
     * Output stream used to send messages to clients; primarily for transmitting errors.
     */
    private ObjectOutputStream salida = null;

    /**
     * The main method that launches the server. It initializes the server,
     * starts listening for client connections, and handles incoming requests.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationS application = new ApplicationS();
        Thread listener = new Thread(new Listener(application));

        try {
            logger.log(Level.INFO, "Starting server...");
            listener.start();
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
     * <code>ObjectOutputStream</code>.
     */
    public void iniciar() throws IOException {

        try {
            servidor = new ServerSocket(PUERTO);

            while (running) {
                logger.log(Level.INFO, "Wating conection from client");
                System.out.println("Press \"ENTER\" to close the server");
                cliente = servidor.accept();
                
                if (conexiones < MAX_CONEXIONES) {
                    controlarConexion(1);
                    Worker hilo = new Worker(cliente, this);
                    hilo.start();

                } else {
                    
                    logger.log(Level.INFO, "Max conections reached, refusing service");
                    salida = new ObjectOutputStream(cliente.getOutputStream());
                    Message message = new Message();
                    message.setSignInSignUpEnum(SignInSignUpEnum.MAX_CONNECTIONS);
                    salida.writeObject(message);
                    if (cliente != null) {
                        cliente.close();
                        cliente=null;
                    }
                    if (salida != null) {
                        salida.close();
                        salida=null;
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("The server has been closed");
            logger.log(Level.INFO, "Server had been closed");
        } catch (Exception e) {
            System.out.println("The server has not been closed correctly");
        } finally {
            finalizar();

        }
    }

    /**
     * Closed the server and client sockets. This method is called when the
     * server has been ordered to shut down.
     */
    public void finalizar() {
        try {
            if (servidor != null && !servidor.isClosed()) {
                servidor.close();
                servidor=null;
            }

            if (cliente != null && !cliente.isClosed()) {
                cliente.close();
                cliente=null;
            }

        } catch (IOException e) {
            System.out.println("en catch");
            e.printStackTrace();
        }
    }

    /**
     * Synchronizes and controls the number of active client connections.
     *
     * @param tipo an integer value to tell the method to increase or decrease
     * <code>conexiones</code>. Pass 1 increase, any other value to decrease.
     */
    public synchronized void controlarConexion(int tipo) {
        if (tipo == 1) {
            conexiones++;
            logger.log(Level.INFO, "Clients connected: {0}", conexiones);
        } else {
            conexiones--;
            logger.log(Level.INFO, "Clients connected: {0}", conexiones);
        }

    }
    /**
     * Stops the server loop and closes all connections after verifying
     * that no client connections are still active.
     */
    public void stopLoop() {
        ApplicationS.running = false;
        while (conexiones != 0);
        try {
            CloseableFactory.getInstance().getCloseable().close();
            logger.log(Level.INFO, "Closing pool connections");
        } catch (SQLException error) {
            logger.log(Level.SEVERE, "ERROR when closing the connections in the pool{0}", error);
        }
        finalizar();
        
    }

}