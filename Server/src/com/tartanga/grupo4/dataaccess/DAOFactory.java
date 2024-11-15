package com.tartanga.grupo4.dataaccess;

import com.tartanga.grupo4.model.Signable;

/**
 * Synchronized DAO factory. It used singleton design pattern to make sure that
 * the same factory is used.
 * 
 * @author Aitor
 */
public class DAOFactory {
    
   private static DAOFactory instance;

    /**
     * Private constructor to prevent instantiation from other classes.
     */
    private DAOFactory() {
    }
    /**
     * Synchronized method to return the same <code>DAOFactory</code> through a
     * singleton design pattern
     * 
     * @return instance of a <code>DAOFactory</code>
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
    
     /**
     * Returns a DAO that implements the <code>Signable</code> interface.
     * 
     * @return DAO contains the method to interact with the DB
     */
    public Signable getSignable() {
        return new DAO(); 
    }
}
