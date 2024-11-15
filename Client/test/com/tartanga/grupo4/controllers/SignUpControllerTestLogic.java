/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.main.ApplicationU;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;



/**
 *
 * @author Alin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTestLogic extends ApplicationTest{
    
    @Override
    public void start(Stage stage)throws Exception{
        new ApplicationU().start(stage);
    }
    
    public SignUpControllerTestLogic() {
    }

    
    /*@Test
    public void testSomeMethod() {
    }
    /*
    @BeforeClass
    @Ignore
    @Test
    */
    
    @Before
    public void enterTest() {
        clickOn("Create User");
    }
    
    //The server must be running and the user must not exist within the database.
    @Test
    public void test_A_SignUpOK(){
        clickOn("#fld_Email");
        write("test23@gmail.com");
        clickOn("#fld_Password");
        write("abcD*1234");
        clickOn("#btnSeePassword");
        clickOn("#fld_Confirm");
        write("abcD*1234");
        clickOn("#btnSeeConfirm");
        clickOn("#fld_Name");
        write("Test");
        clickOn("#fld_City");
        write("Bilbao");
        clickOn("#fld_Street");
        write("Lehendakari Aguirre");
        clickOn("#fld_Zip");
        write("123456");
        clickOn("#chb_Active");
        clickOn("#btn_Register");
        sleep(1000);
        verifyThat("User created successfully.", isVisible());
    }
    
    @After 
    public void exitTest(){
        clickOn("Close");
    }
    
    //The server must be running.
    @Test
    public void test_B_UserAlreadyExist(){
        clickOn("#fld_Email");
        write("test23@gmail.com");
        clickOn("#fld_Password");
        write("abcD*1234");
        clickOn("#btnSeePassword");
        clickOn("#fld_Confirm");
        write("abcD*1234");
        clickOn("#btnSeeConfirm");
        clickOn("#fld_Name");
        write("Test");
        clickOn("#fld_City");
        write("Bilbao");
        clickOn("#fld_Street");
        write("Lehendakari Aguirre");
        clickOn("#fld_Zip");
        write("123456");
        clickOn("#chb_Active");
        clickOn("#btn_Register");
        sleep(1000);
        verifyThat("Login already exists.", isVisible());
    }

}