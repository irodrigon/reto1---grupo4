package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.main.ApplicationS;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Listener} class provides a command-line interface that listens 
 * for user input to confirm server shutdown. Once a shutdown command is 
 * detected, the listener initiates the shutdown process.
 * <p>
 * This class implements {@code Runnable}, allowing it to be run as a 
 * separate thread, continuously listening for input until the server 
 * shutdown is confirmed.
 * </p>
 *
 * @see ApplicationS
 * @author Aitor
 */
public class Listener implements Runnable {
    /**
     * The application instance responsible for managing server operations.
     */
    private ApplicationS appli;
    /**
     * Constructs a {@code Listener} with the servers {@code ApplicationS} 
     * instance. This instance is used to initiate the server shutdown when 
     * triggered by user input.
     * 
     * @param appli the {@code ApplicationS} instance to control the server
     */
    public Listener(ApplicationS appli){
        this.appli = appli;
    }
    
     /**
     * The main loop of the {@code Listener}. Waits for user confirmation 
     * to shut down the server. It prompts the user to type "Yes" to confirm 
     * shutdown. If the user confirms, it calls the {@code stopLoop} method 
     * on the {@code ApplicationS} instance.
     */
    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean respuesta = true;

            while (respuesta) {
                scanner.nextLine();
                System.out.println("Are you sure you want close the server? \n"
                        + "If you want to close the server type \"Yes\"");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    respuesta = false;
                }
            }
            System.out.println("Closing the server...");
            appli.stopLoop();

        } catch (Exception error) {
            
            Logger.getLogger("Listener").log(Level.SEVERE, "Error When closing Scanner{0}", Arrays.toString(error.getStackTrace()));
        }
    }

}