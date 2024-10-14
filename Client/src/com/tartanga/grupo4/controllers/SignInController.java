package com.tartanga.grupo4.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controlador para la ventana de Sign In.
 */
public class SignInController {

    @FXML
    private void handleCreateUser(ActionEvent event) {
        try {
            // Cargar la vista de registro (SignUpView)
            Parent signUpView = FXMLLoader.load(getClass().getResource("/com/tartanga/grupo4/views/SignUp.fxml"));
            
            // Obtener la ventana actual desde el evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Cambiar a la vista de registro
            Scene signUpScene = new Scene(signUpView);
            stage.setScene(signUpScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            // Cargar la vista principal (MainView)
            Parent mainView = FXMLLoader.load(getClass().getResource("/com/tartanga/grupo4/views/MainView.fxml"));
            
            // Obtener la ventana actual desde el evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Cambiar a la vista principal
            Scene mainScene = new Scene(mainView);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }
}
