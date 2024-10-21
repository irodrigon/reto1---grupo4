/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Iñi
 */
public class Pool {

    private BasicDataSource basicDataSource;
    private Stack<Connection> pool = new Stack<Connection>();

    public Pool() {
        
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException{
            if(pool.empty()){
                pool.push(createNewConnection());
            }
            return pool.pop();
    }
    
    public void freeConnection(Connection connection){
            pool.push(connection);
    }
    
    public void close() throws SQLException{
        while(!pool.empty()){
            pool.pop().close();
        }
    }
    
    private Connection createNewConnection() throws ClassNotFoundException, SQLException{
        Connection connection = null;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("/com/tartanga/grupo4/resources/connection");
        Class.forName(resourceBundle.getString("driver"));
        connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("username"),resourceBundle.getString("password"));
        return connection;
    }

}
