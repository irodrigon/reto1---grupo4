/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.main.ApplicationU;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;



/**
 *
 * @author egure
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest{
    
    @Override
    public void start(Stage stage)throws Exception{
        new ApplicationU().start(stage);
    }
    
    public SignUpControllerTest() {
    }

    
    @Test
    public void testSomeMethod() {
    }
    /*
    @BeforeClass
    @Ignore
    @Test
    */
    @Ignore
    @Test
    public void testSignInOK(){
        clickOn("#userField");
        write("egurenaratz@gmail.com");
        clickOn ("#passwordField");
        write("Abcd1234");
        clickOn("#btn_Login");
        verifyThat("File",isVisible());
    }
    @Ignore
    @Test
    public void test_A_SignUpEmpty(){
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Ignore
    @Test
    public void test_B_SignUpEmailFormatError(){
        clickOn("#fld_Email");
        write("abcdfjhwodj");
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Ignore
    @Test
    public void test_C_SignUpPasswordFormatError(){
        clickOn("#fld_Email");
        write("test@gmail.com"); 
        clickOn("#fld_Password");
        write("abcd*1234");
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Ignore
    @Test
    public void test_D_SignUpConfirmationError(){
        clickOn("#fld_Email");
        write("test@gmail.com"); 
        clickOn("#fld_Password");
        write("SoyTop1");
        clickOn("#fld_Confirm");
        write("Soutop1");
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Ignore
    @Test
    public void test_E_SignUpNumbersNotAllowed(){
        clickOn("#fld_Email");
        write("test@gmail.com"); 
        clickOn("#fld_Password");
        write("SoyTop1");
        clickOn("#fld_Confirm");
        write("SoyTop1");
        clickOn("#fld_Name");
        write("Test67");
        clickOn("#fld_City");
        write("Bilbo123");
        clickOn("#fld_Street");
        write("Lehendakari 7");
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Ignore
    @Test
    public void test_F_SignUpLettersNotAllowed(){
        clickOn("#fld_Email");
        write("test@gmail.com"); 
        clickOn("#fld_Password");
        write("SoyTop1");
        clickOn("#fld_Confirm");
        write("SoyTop1");
        clickOn("#fld_Name");
        write("Test");
        clickOn("#fld_City");
        write("Bilbo");
        clickOn("#fld_Street");
        write("Lehendakari");
        clickOn("#fld_Zip");
        write("qwe123");
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Ignore
    @Test
    public void test_G_SignUpAllErrors(){
        clickOn("#fld_Email");
        write("test@gmailcom"); 
        clickOn("#fld_Password");
        write("soytop1");
        clickOn("#fld_Confirm");
        write("soyTop1");
        clickOn("#fld_Name");
        write("Test67");
        clickOn("#fld_City");
        write("Bilbo123");
        clickOn("#fld_Street");
        write("Lehendakari 7");
        clickOn("#fld_Zip");
        write("qwe123");
        clickOn("#btn_Register");
        verifyThat("Please try again.",isVisible());
    }
    @Test
    public void test_H_SignUpOK(){
        clickOn("#hl_create");
        clickOn("#fld_Email");
        write("test@gmail.com");
        clickOn("#fld_Password");
        write("abcD*1234");
        clickOn("#fld_Confirm");
        write("abcD*1234");
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
        verifyThat("Close", isVisible());
        clickOn("Close");
    }

}