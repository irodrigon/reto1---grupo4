/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.main;

import com.tartanga.grupo4.controllers.SignInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main application class that loads the sign-in view and sets up the corresponding controller.
 * Extends the JavaFX Application class.
 * 
 * @author Alin
 */

public class ApplicationU extends Application {

    /**
     * Starts the JavaFX application, loading the FXML file for the sign-in view
     * and initializing the associated controller.
     *
     * @param stage The main window of the application.
     * @throws Exception If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.getIcons().add(new Image("/com/tartanga/grupo4/resources/images/servericon.png"));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
        Parent root = (Parent)loader.load();
        
        SignInController controller = (SignInController)loader.getController();
        controller.setStage(stage);
        controller.initStage(root);
    }

    /**
     * Main method that launches the application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
