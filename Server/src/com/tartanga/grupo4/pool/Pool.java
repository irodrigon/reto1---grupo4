package com.tartanga.grupo4.pool;

import com.tartanga.grupo4.dataaccess.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * The Pool class is basically a pool of connections used by a Data Accesible Object (DAO) (or
 * any similar object that needs to access multiple connections) to optimize the use of connections
 * when connecting, in this case, to a database. It is going to create a Stack of connections 
 * (then create a new connection) where it stores the connections 
 * and get or pull the connections from there with the
 * proper methods from the Stack class(pop() and push()).
 * 
 * @author Iñi
 */
public class Pool implements Closeable{
    
    /**
     * The Stack where the connections are going to be stored.
     */
    private Stack<Connection> pool = new Stack<Connection>();
    
    /**
     * Constructor of the pool Class:
     * This is an empty constructor.
     */
    public Pool() {
        
    }
    
    /**
     * This method is going to check if the connection stack previously created
     * is empty and if it isn't, create and add a new connection via the push()
     * method, proper to the Stack() class. Then it is going to make it available
     * via the pop() method.
     * 
     * @return The connection via the pop() method.
     * 
     * @throws ClassNotFoundException In case something with the connection fails.
     * 
     * @throws SQLException In case something with the connection fails.
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException{
            if(pool.empty()){
                pool.push(createNewConnection());
            }
            return pool.pop();
    }
    
    /**
     * This method is going to release the connection which is being used by the 
     * application and return it to the pool.
     * 
     * 
     * @param connection The connection returned to the pool through the push() method.
     */
    public void freeConnection(Connection connection){
            pool.push(connection);
    }
    
    /**
     * The close() method is going to close all the connections in the pool, first
     * checking if it is not empty, then closing each connection separately.
     * 
     * @throws SQLException In case something with the connection fails.
     */
    @Override
    public void close() throws SQLException{
        while(!pool.empty()){
            pool.pop().close();
        }
    }
    
    
    /**
     * This method creates the connection, calling the necessary data from the properties
     * file where the data about the driver, IP, port, database name, username and password
     * are stored. 
     * 
     * @return The connection that is going to be managed by the pool.
     * 
     * @throws ClassNotFoundException Needed to call the driver manager.
     * 
     * @throws SQLException If something with the database fails .
     */
    private Connection createNewConnection() throws ClassNotFoundException, SQLException{
        Connection connection = null;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/tartanga/grupo4/resources/connection");
        Class.forName(resourceBundle.getString("driver"));
        connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("username"),resourceBundle.getString("password"));
        return connection;
    }

}
