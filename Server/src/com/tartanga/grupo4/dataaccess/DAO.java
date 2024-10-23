/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.dataaccess;


import com.tartanga.grupo4.model.Signable;
import com.tartanga.grupo4.model.User;

/**
 *
 * @author rabio
 */
public class DAO implements Signable{
    
    
    @Override
    public User signIn(User user){
        
        return user;
    }
    
    @Override
    public User signUp(User user){
        
        return user;
    }
}
