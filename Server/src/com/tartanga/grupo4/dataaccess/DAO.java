package com.tartanga.grupo4.dataaccess;

import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.exceptions.UserPasswdException;
import com.tartanga.grupo4.model.Signable;
import com.tartanga.grupo4.model.User;
import com.tartanga.grupo4.pool.Pool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Accesssible Object Class (DAO) class, responsible of accessing
 * the Odoo postgresql database and querying one table(res_users) via the signIn
 * method or modifying and inserting data in two other tables 
 *(res_partner and res_users) through the signUp method.
 * It implements the Signable interface to perform its operations
 * through the User class.
 * 
 * @author Iñi
 */
public class DAO implements Signable {
    
    /**
     * This String is used to insert a line into the res_partner table, with the company_id: 1 as a default value
     */
    private final String INSERT_RES_PARTNER = "INSERT INTO RES_PARTNER(company_id,name,street,city,zip,email) VALUES (1,?,?,?,?,?)";
    /**
     * This String is used to insert a line into the res_users table, with the company_id: 1 as a default value
     * and uses a subquery to get previous partner_id using the field email (which is the username in the application). It also fills in
     * other required fields, such as notification_type.
     */
    private final String INSERT_RES_USERS = "INSERT INTO RES_USERS(company_id,partner_id,login,password,active,notification_type) VALUES (1,(SELECT id FROM RES_PARTNER WHERE email = ?),?,?,?,'email')";
    /**
     * This String is used to count if there is a user with this same name in the database.
     */
    private final String SELECT_RES_USERS = "SELECT COUNT(*) FROM RES_USERS WHERE login = ?";
    /**
     * This String is used to check if the login and password is in the database.
     */
    private final String CHECK_USER = "SELECT * from RES_USERS where LOGIN = ? and PASSWORD = ?";
    
    /**
     * The connection which is going to get the connection from the pool.
     */
    private Connection connection = null;
    
    /**
     * The statement is going to be used to set the values to the queries, effectively substituting the interrogation signs.
     */
    private PreparedStatement preparedStatement = null;
    
    /**
     * The result set is used to check whether the user exists in the database or not.
     */
    private ResultSet resultSet = null;
    
    
    /**
    * This method checks if the user can login into the application:
    * The signIn method queries the res_users table in the 
    * Odoo postgresql database, previously getting a pool connection,  
    * checking whether both login
    * and password fields are the same when the user
    * enters it from keyboard all the way through
    * the client/server architecture as well as written
    * on the database. Checks for any exceptions that may
    * arise on this context and throws 
    * them for other instances of the server, 
    * in charge of exchanging messages
    * between the two applications, to catch and send them back 
    * to the client.
    * 
    * @param user The user that is coming from the original message and to be set in the query.
    * 
    * @return user It is going to return null if the user has not been created previously, the same user if it does exist in teh database.
    * 
    * @throws UserPasswdException If the user has not been created previously on the application. 
    * 
    * @throws ServerErrorException If the database is not connected, or if there is any problems with it.
    */
    @Override
    public synchronized User signIn(User user) throws UserPasswdException,ServerErrorException{

        Pool pool = new Pool();

        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_USER);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                user.setUsername(null);
                user.setPassword(null);
                throw new UserPasswdException();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "SQL error occurred: " + ex.getMessage());
            throw new ServerErrorException();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerErrorException();
        } finally {
            if (connection != null) {
                pool.freeConnection(connection);
            }
        }

        return user;
    }
    
    /**
     * 
     * This method creates the user that could login into the application:
     * The signUp method inserts in the res_partner table and also 
     * in the res_users table the necessary data for creating the so called
     * user, gets a connection from the pool, checks whether the user
     * has been already created or not, and, if it has not been created,
     * inserts the data. It is going to peform a commit operation if everything
     * is correct, and a rollback operation if something has gone wrong.
     * It also throws the necessary errors for other server instances to catch.
     * 
     * 
     * 
     * @param user The user data that is needed for creating the user in both tables.
     * 
     * @return The user if everything has gone well.
     * 
     * @throws UserExistInDatabaseException If the user has already been created.
     * 
     * @throws ServerErrorException If there is any kind of trouble with the database.
     */
    
    @Override
    public synchronized User signUp(User user) throws UserExistInDatabaseException, ServerErrorException {

        Pool pool = new Pool();

        try {

            connection = pool.getConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(SELECT_RES_USERS);

            preparedStatement.setString(1, user.getUsername());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    throw new UserExistInDatabaseException();
                } else {
                    preparedStatement = connection.prepareStatement(INSERT_RES_PARTNER);

                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setString(2, user.getStreet());
                    preparedStatement.setString(3, user.getCity());
                    preparedStatement.setInt(4, user.getZip());
                    preparedStatement.setString(5, user.getUsername());

                    preparedStatement.executeUpdate();

                    preparedStatement = connection.prepareStatement(INSERT_RES_USERS);

                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getUsername());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setBoolean(4, user.getActive());
                    

                    preparedStatement.executeUpdate();
                    
                    connection.commit();
                }
            }

        } catch (SQLException ex) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Rollback Exception: " + rollbackEx.getMessage());
                }
            }
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "SQL Error Occurred: " + ex.getMessage());
            throw new ServerErrorException();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerErrorException();
        } finally {
            if (connection != null) {
                pool.freeConnection(connection);
            }
        }

        return user;
    }

}
