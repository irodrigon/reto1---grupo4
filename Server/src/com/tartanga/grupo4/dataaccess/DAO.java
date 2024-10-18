/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.dataaccess;

import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.model.Signable;
import com.tartanga.grupo4.model.User;
import com.tartanga.grupo4.pool.Pool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author rabio
 */
public class DAO implements Signable {

    private final String INSERT_RES_PARTNER = "INSERT INTO RES_PARTNER(name,street,city,zip,email) VALUES (?,?,?,?,?)";
    private final String INSERT_RES_USERS = "INSERT INTO RES_USERS(company_id,partner_id,login,password,active,notification_type) VALUES (1,(SELECT id FROM RES_PARTNER WHERE email = ?),?,?,'email')";
    private final String SELECT_RES_USERS = "SELECT COUNT(*) FROM RES_USERS WHERE login = ?";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public User signIn(User user) {

        return user;
    }

    @Override
    public User signUp(User user) throws UserExistInDatabaseException {

        try {
            Pool pool = new Pool();
            BasicDataSource dataSource = pool.getDataSource();
            connection = dataSource.getConnection();

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
                    
                    preparedStatement = connection.prepareStatement(INSERT_RES_USERS);

                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setBoolean(3, user.getActive());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

}
