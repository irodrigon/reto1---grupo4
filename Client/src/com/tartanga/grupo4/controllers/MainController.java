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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author rabio
 */
public class MainController implements Initializable {

    @FXML
    private Stage stage;
    
    @FXML
    private MenuItem mni_LogOut;
    
    @FXML
    @Override
public void initialize(URL location, ResourceBundle resources) {
    mni_LogOut.setOnAction(event -> {
        try {
            handlelogOut(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }
    

    @FXML
private void handlelogOut(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
    Parent root = fxmlLoader.load();

 Stage stage = (Stage) mni_LogOut.getParentPopup().getOwnerWindow();    
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}


    @FXML
    private void cerrarnAction(ActionEvent event) throws IOException {

        Platform.exit();
    }



}