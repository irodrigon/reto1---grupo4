/**
 * Package for all related to the accessing of data.
 * 
 * This package contains the DAO Class, DAOFactory Class, Closeable interface, and
 * CloseableFactory Class. The DAO class directly access the database and inserts
 * or queries the user data. The DAO factory will be called by the Worker thread
 * to maintain layer isolation as Closeable interface will be called by the pool
 * of connections when it needs to be closed. The Closeable factory is also 
 * called from the main application during the server shutdown to maintain layer
 * isolation.
 * 
 */

package com.tartanga.grupo4.dataaccess;