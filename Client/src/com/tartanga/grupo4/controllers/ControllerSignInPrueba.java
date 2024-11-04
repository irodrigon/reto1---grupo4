/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.businesslogic.ClientFactory;
import com.tartanga.grupo4.model.User;
import com.tartanga.grupo4.exceptions.MaxConnectionsException;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserPasswdException;

/**
 *
 * @author rabio
 * 
 */
public class ControllerSignInPrueba {
    //Esto es solo algo que uso para testear la conexion entre cliente y servidor, BORRAR cuando
    //no lo necesite mas o antes de entregar
    public static void main(String[] args) {
        User user = new User("ejemplo@gamil.com", "12345", "Aitor", "buenaventura", Boolean.TRUE, "Bizkaia", 45236);
        try {
            user = ClientFactory.getInstance().getSignable().signIn(user);
            if (user.getName().equals("Asier")) {
                System.out.println("prueba OK");
            } else {
                System.out.println("Mal");
            }
        } catch (UserPasswdException error) {
            System.out.println("Password/usuario mal");
        } catch (ServerErrorException error) {
            System.out.println("Error critico del server");
        } catch (MaxConnectionsException error) {
            System.out.println("Maximas conecsiones alcanzadas");
        } catch (Exception error) {
            System.out.println("Otro errores");
        }

    }
}
