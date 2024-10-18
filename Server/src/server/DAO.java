/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.tartanga.grupo4.model.Signable;
import com.tartanga.grupo4.model.User;
import com.tartanga.grupo4.pool.Pool;
import exceptions.ServerErrorException;
import exceptions.UserPasswdException;
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

    private Connection connection = null;
    private String existeUsuarioYContrasena = "Select * from res_user where login = ? and password = ?";

    @Override
    public User signIn(User user) throws UserPasswdException , ServerErrorException{

        try {
            Pool pool = new Pool();
            BasicDataSource dataSource = pool.getDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT * FROM res_user WHERE login = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            
            if(!resultSet.next()){
               user.setUsername(null);
               user.setPassword(null);
               throw new UserPasswdException();
            } 

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerErrorException();
        }

        return user;
    }

    @Override
    public User signUp(User user) {

        return user;
    }

}
