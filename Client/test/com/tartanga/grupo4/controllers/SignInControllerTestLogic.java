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
public class SignInControllerTestLogic extends ApplicationTest{
    
    @Override public void start(Stage stage) throws Exception{
        new ApplicationU().start(stage);
    }
    
    @Override public void stop(){}
    
    public SignInControllerTestLogic() {
    }
    
    //Test if a user is a user is already in the database, correct if it is able to login.
    @Test
    public void testA_signInOK() {
        clickOn("#userField");
        write("inigo@user.com");
        clickOn("#passwordField");
        write("Inigo1234");
        clickOn("#btnSeePassword");
        clickOn("#btn_Login");
        verifyThat("#BorderPaneMain",isVisible());
    }
    
    @Test
    public void testB_testErrorOnEmail() {
        clickOn("#userField");
        write("user@user.com");
        clickOn("#passwordField");
        write("Inigo1234");
        clickOn("#btnSeePassword");
        clickOn("#btn_Login");
        verifyThat("The user or password are incorrect. Please try again.",isVisible());
        clickOn("OK");
    }
    
    @Test
    public void testC_testErrorOnPassword() {
        clickOn("#userField");
        write("inigo@user.com");
        clickOn("#passwordField");
        write("User1234");
        clickOn("#btnSeePassword");
        clickOn("#btn_Login");
        verifyThat("The user or password are incorrect. Please try again.",isVisible());
        clickOn("OK");
    }
}

