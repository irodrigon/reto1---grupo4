/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.model;

import exceptions.ServerErrorException;
import exceptions.UserPasswdException;


/**
 *
 * @author rabio
 */
public interface Signable {
    
    public User signIn(User user)throws UserPasswdException, ServerErrorException;
    public User signUp(User user);
}
