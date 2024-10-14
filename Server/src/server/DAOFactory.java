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
    
    public static Signable getSignable(){
        
        return new DAO();
        
    }
}
