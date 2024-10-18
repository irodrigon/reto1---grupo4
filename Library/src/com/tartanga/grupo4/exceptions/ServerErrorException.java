/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.exceptions;

/**
 *
 * @author Iñi
 */
public class ServerErrorException extends Exception{
     /**
     * Creates a new instance of <code>ServerErrorException</code> without
     * detail message.
     */
    public ServerErrorException() {
        super("El server devolvio un error");
    }
}
