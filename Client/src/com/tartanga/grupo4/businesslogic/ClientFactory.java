/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.model.Signable;

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