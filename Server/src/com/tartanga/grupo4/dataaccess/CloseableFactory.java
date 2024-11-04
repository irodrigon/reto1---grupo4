package com.tartanga.grupo4.dataaccess;

import com.tartanga.grupo4.pool.Pool;

/**
 *
 * @author Aitor
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
     * @return instance of a <code>CloseableFactory</code>
     */
    public static synchronized CloseableFactory getInstance() {
        if (instance == null) {
            instance = new CloseableFactory();
        }
        return instance;
    }
    
     /**
     * Returns a Poll that implements the <code>Closeable</code> interface.
     * 
     * @return Pool contains the method to interact with the DB
     */
    public Closeable getCloseable() {
        return new Pool(); 
    }
}
