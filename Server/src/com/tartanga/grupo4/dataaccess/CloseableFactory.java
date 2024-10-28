/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.dataaccess;

import com.tartanga.grupo4.pool.Pool;

/**
 *
 * @author rabio
 */
public class CloseableFactory {
    
   private static CloseableFactory instance;

    /**
     * Private constructor to prevent instantiation from other classes.
     */
    private CloseableFactory() {
    }
    /**
     * Synchronized method to return the same <code>CloseableFactory</code> through a
     * singleton design pattern
     * 
     * @return instance of a <code>DAOFactory</code>
     */
    public static synchronized CloseableFactory getInstance() {
        if (instance == null) {
            instance = new CloseableFactory();
        }
        return instance;
    }
    
     /**
     * Returns a DAO that implements the <code>Signable</code> interface.
     * 
     * @return DAO contains the method to interact with the DB
     */
    public Closeable getCloseable() {
        return new Pool(); 
    }
}