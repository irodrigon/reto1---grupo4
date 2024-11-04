package com.tartanga.grupo4.main;

import com.tartanga.grupo4.controllers.SignUpController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 
 * Autor: Aratz Eguren Zarraga
 */
public class AbrirSignUp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignUp.fxml"));
        Parent root = loader.load();
        
       
        SignUpController controller = loader.getController(); 
        controller.setStage(stage);
        controller.initStage(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
