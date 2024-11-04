package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.main.ApplicationU;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Iñi
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTestServer extends ApplicationTest{
    
    @Override public void start(Stage stage) throws Exception{
        new ApplicationU().start(stage);
    }
    
    @Override public void stop(){}
    
    public SignInControllerTestServer() {
    }
    
    //Test if a user is a user is already in the database, correct if it is able to login.
    @Test
    public void testA_serverError() {
       
    }
}
