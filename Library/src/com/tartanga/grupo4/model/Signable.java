/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.model;

import com.tartanga.grupo4.exceptions.MaxConnectionsException;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.exceptions.UserPasswdException;

/**
 *
 * @author rabio
 */
public interface Signable {
<<<<<<< HEAD
=======
    
>>>>>>> origin/Alin
    public User signIn (User user) throws UserPasswdException,ServerErrorException,Exception,MaxConnectionsException;
    public User signUp(User user)throws ServerErrorException,UserExistInDatabaseException,Exception,MaxConnectionsException;
}
