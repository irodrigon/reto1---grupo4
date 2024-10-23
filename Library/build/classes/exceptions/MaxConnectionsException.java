/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author rabio
 */
public class MaxConnectionsException extends Exception {

    /**
     * Creates a new instance of <code>MaxConnectionsException</code> without
     * detail message.
     */
    public MaxConnectionsException() {
        super("Max conections (5) reached, refusing service");
    }

   
}
