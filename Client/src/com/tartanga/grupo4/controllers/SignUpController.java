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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {
    
    @FXML
    private Button btn_Back,btn_Register;
    @FXML
    private TextField fld_Email,fld_Password,fld_Confirm,fld_Name,fld_City,fld_Street,fld_Zip;
    @FXML
    private CheckBox chb_Active;
    @FXML
    private Label lbl_error_Email,lbl_error_Password,lbl_error_Confirm,lbl_error_Name,lbl_error_City,lbl_error_Street,lbl_error_Z;
    
    @FXML
    private void initialize() {
        btn_Back.setOnAction(this::handleGoBack);
        btn_Register.setOnAction(this::handleRegister);
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        try {
        FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
        Parent mainView = FXMLLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleRegister(ActionEvent event){
        
    }
}
