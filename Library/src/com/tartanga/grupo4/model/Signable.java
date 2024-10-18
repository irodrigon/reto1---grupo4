/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.model;

import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;

/**
 *
 * @author rabio
 */
public interface Signable {
    
    public User signIn(User user);
    public User signUp(User user) throws UserExistInDatabaseException;
}
