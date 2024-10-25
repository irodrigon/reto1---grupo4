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
public class UserExistInDatabaseException extends Exception {

    /**
     * Creates a new instance of <code>UserExistInDatabaseException</code>
     * without detail message.
     */
    public UserExistInDatabaseException() {
        super("The introduced login already exist in the DB");
    }

}
