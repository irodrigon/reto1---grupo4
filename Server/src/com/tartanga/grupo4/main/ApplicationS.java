/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.main;

import com.tartanga.grupo4.businesslogic.Worker;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
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
    private boolean running = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationS application = new ApplicationS();
        try {
            application.iniciar();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void iniciar() throws IOException {

        try {
            Logger.getLogger("SERVIDOR").log(Level.INFO, "Iniciando servidor...");
            servidor = new ServerSocket(PUERTO);

            System.out.println("Presiona ENTER para salir del servidor...");

            new Thread(this::finishServer).start();

            while (running) {
                Logger.getLogger("SERVIDOR").log(Level.INFO, "Esperando conexion del cliente");
                cliente = servidor.accept();

                if (conexiones < MAX_CONEXIONES) {
                    conexiones++;
                    Worker hilo = new Worker(cliente, this);
                    hilo.start();
                } else {
                    Logger.getLogger("SERVIDOR").log(Level.INFO, "Se ha denegado a un cliente por ser mas del maximo permitido");
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

    public void finishServer() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        running = false;
        System.out.println("Cerrando servidor...");
        System.exit(0);
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

    public synchronized void liberarConexion() {
        conexiones--;
        Logger.getLogger("SERVIDOR").log(Level.INFO, "Cliente desconectado conexiones actuales: {0}", conexiones);

    }

}
