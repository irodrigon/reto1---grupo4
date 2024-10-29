/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.main.ApplicationS;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rabio
 */
public class Listener implements Runnable {
    
    private ApplicationS appli;
    
    public Listener(ApplicationS appli){
        this.appli = appli;
    }
    
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
