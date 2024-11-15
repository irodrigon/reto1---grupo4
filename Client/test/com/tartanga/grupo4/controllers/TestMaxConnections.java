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
public class TestMaxConnections extends ApplicationTest{
    
    @Override public void start(Stage stage) throws Exception{
        new ApplicationU().start(stage);
    }
    
    @Override public void stop(){}
    
    public TestMaxConnections() {
    }
    
    //The server must be running and with the max connections to zero to run the test.
    @Test
    public void testA_MaxConnectionsError() {
       clickOn("#userField");
       write("test2@gmail.com");
       clickOn("#passwordField");
       write("abcD*1234");
       clickOn("#btnSeePassword");
       clickOn("#btn_Login");
       verifyThat("Too many connections simulteanously. Please be patient.",isVisible());
       sleep(1000);
       clickOn("OK");
    }
}