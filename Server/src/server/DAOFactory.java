/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import Example.Signable;

/**
 *
 * @author rabio
 */
public class DAOFactory {
    
   private static DAOFactory instance;


    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public Signable getSignable() {
        return new DAO(); 
    }
}
