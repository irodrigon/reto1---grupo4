/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.model;

/**
 *
 * @author egure
 */
public class Message {
  
      private User usuario;
      private SignInSignUpEnum type;

    public Message(User usuario, SignInSignUpEnum type) {
        this.usuario = usuario;
        this.type = type;
    }
    
     public Message() {
         this.usuario = null;
        this.type = null;
    }

    public SignInSignUpEnum getType() {
        return type;
    }

    public void setType(SignInSignUpEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" + "usuario=" + usuario + ", type=" + type + '}';
    }
     
     
      
      
      
}
