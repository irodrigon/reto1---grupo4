/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Aitor
 */
public class MainController implements Initializable {

    @FXML
    private Stage stage;

    @FXML
    private MenuItem mni_LogOut;
    
    @FXML
    private MenuItem mni_Exit;
    
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        mni_LogOut.setOnAction(this::handleLogOut);
        mni_Exit.setOnAction(this::onCloseRequestWindowEvent);
        
    }
  

    @FXML
    private void onCloseRequestWindowEvent(Event event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "¿Desea cerrar la aplicacion?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmacion de cierre");
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.resultProperty().get().equals(ButtonType.YES)) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) {

        try {
            FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
            Parent SignIn = FXMLLoader.load();

            Stage stageIn = (Stage) mni_LogOut.getParentPopup().getOwnerWindow();

            Scene scene = new Scene(SignIn);
            stageIn.setScene(scene);
            stageIn.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
