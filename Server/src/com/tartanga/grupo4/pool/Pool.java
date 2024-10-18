/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.pool;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Iñi
 */
public class Pool {
     
    private BasicDataSource basicDataSource;

    public Pool(){

            basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName("org.postgresql.Driver");
            basicDataSource.setUrl("jdbc:postgresql://192.168.142.130:5432/firstDatabaseExample");
            basicDataSource.setUsername("odoo16");
            basicDataSource.setPassword("admin");
            basicDataSource.setInitialSize(5);
            basicDataSource.setMaxTotal(10);
            basicDataSource.setMaxWaitMillis(30000);
    }

    public BasicDataSource getDataSource() {

        return basicDataSource;

    }
}
