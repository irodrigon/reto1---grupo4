/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.exceptions;

/**
 *
 * @author 2dami
 */
public class ClientSideException extends Exception {

    /**
     * Creates a new instance of <code>ClientSideException</code> without detail
     * message.
     */
    public ClientSideException() {
        super("An error in the client side just happened, please contact support service or try later");
    }

}
