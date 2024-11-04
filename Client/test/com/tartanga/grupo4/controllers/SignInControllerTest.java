/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class SignInControllerTest extends ApplicationTest{
    
    @Override public void start(Stage stage) throws Exception{
        new ApplicationU().start(stage);
    }
    
    @Override public void stop(){}
    
    public SignInControllerTest() {
    }

    @Test
    public void testSomeMethod() {
        clickOn("#userField");
        write("user@user.com");
        clickOn("#passwordField");
        write("User1234");
        clickOn("#btnSeePassword");
        clickOn("#btn_Login");
        verifyThat("#mainPane",isVisible());
    }
    
}

