/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Example.Signable;
import Example.User;
import exceptions.ServerErrorException;
import exceptions.UserPasswdException;

/**
 *
 * @author rabio
 */
public class DAO implements Signable{
    
    
    @Override
    public User signIn(User user) throws UserPasswdException,ServerErrorException{
        throw new ServerErrorException();
        
    }
    
    @Override
    public User signUp(User user){
        
        return user;
    }
}
