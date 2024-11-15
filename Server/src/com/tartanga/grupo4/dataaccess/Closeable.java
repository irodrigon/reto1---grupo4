package com.tartanga.grupo4.dataaccess;

import java.sql.SQLException;

/**
 *Closeable interface is in charge of closing pool connections when the server is shut down.
 * It is going to be implemented in the pool and used when the server is powered off by the
 * pressing of a key.
 * 
 * @author Iñi
 */
public interface Closeable {
    /**
     * 
     * This close() method is implemented in the pool to close all pool connections.
     * 
     * @throws SQLException when something goes wrong during the connection closing.
     */
    public void close() throws SQLException;
}
