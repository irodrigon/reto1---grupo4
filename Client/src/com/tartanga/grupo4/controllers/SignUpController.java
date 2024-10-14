/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.controllers;

/**
 *
 * @author Alin
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private void handleGoBack(ActionEvent event) {
        try {
            // Cargar la nueva vista de SignIn
            Parent signInView = FXMLLoader.load(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
            
            // Obtener la ventana actual
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Configurar la nueva escena
            Scene signInScene = new Scene(signInView);
            window.setScene(signInScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace(); // Puedes usar un logger aquí si lo prefieres
        }
    }
}
