/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testMainView;

import com.tartanga.grupo4.main.ApplicationU;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Test class for MainController to validate the application's user interface functionality.
 * <p>
 *  It signs in to the mainView twice to test that the logout and exit menu items do work 
 * as intended
 * </p>
 * 
 * @author Aitor
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainControllerTest extends ApplicationTest {
    
    /**
     * Starts the applicationU.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        new ApplicationU().start(stage);

    }
    
    /**
     * Stops the application after tests. Not in use at the moment.
     */
    @Override
    public void stop() {
    }
    /**
     * Default constructor for MainControllerTest.
     */
    public MainControllerTest() {
    }
    /**
     * Log in test that is done before any test to get access to MainView.
     * It verifies that the MainView has been accesses.
     * 
     */
    @Before
    public void logInTest() {
        clickOn("#userField").write("rabiosterr@gmail.com");
        clickOn("#passwordField").write("Abcd*1234");

        clickOn("#btnSeePassword");
        clickOn("#btn_Login");
        sleep(1000);
        clickOn("OK");

        verifyThat("#BorderPaneMain", isVisible());
        
    }
    /**
     * Test menu item Logout. Click on the menu "Opciones" showng the menu items.
     * Then it clicks on the log out menu item to return to the SignInView and
     * verifies the signIn window
     * 
     */
    @Test
    public void testA_logOut() {
        clickOn("Opciones");
        clickOn("#mni_LogOut");
        sleep(1000);
        clickOn("Yes");

        verifyThat("#panelLogin", isVisible());

    }
    /**
     * Test menu item Logout. Click on the menu "Opciones" showng the menu items.
     * Then it clicks on exit out menu item to close the application from the
     * client side
     * 
     */
    @Test
    public void testB_exit() {
        clickOn("Opciones");
        clickOn("#mni_Exit");
        sleep(1000);
        clickOn("Yes");

        verifyThat("#panelLogin", isVisible());

    }
}
