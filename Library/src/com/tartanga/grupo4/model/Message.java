/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.model;

import java.io.Serializable;

/**
 *
 * @author rabio
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private User user;
    private Enum SignInSignUpEnum;
    public Message() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Enum getSignInSignUpEnum() {
        return SignInSignUpEnum;
    }

    public void setSignInSignUpEnum(Enum SignInSignUpEnum) {
        this.SignInSignUpEnum = SignInSignUpEnum;
    }
}
