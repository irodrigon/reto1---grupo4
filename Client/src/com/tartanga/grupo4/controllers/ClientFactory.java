/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.controllers;

import Example.Signable;
import com.tartanga.grupo4.main.Cliente;

/**
 *
 * @author rabio
 */
public class ClientFactory {
    
   private static ClientFactory instance;


    private ClientFactory() {
    }

    public static synchronized ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }

    public Signable getSignable() {
        return new Cliente(); 
    }
}