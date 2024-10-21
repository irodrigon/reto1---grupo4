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
    private Button btn_Back, btn_Register;
    @FXML
    private TextField fld_Email, fld_Password, fld_Confirm, fld_Name, fld_City, fld_Street, fld_Zip;
    @FXML
    private CheckBox chb_Active;
    @FXML
    private Label lbl_error_Email, lbl_error_Password, lbl_error_Confirm, lbl_error_Name, lbl_error_City, lbl_error_Street, lbl_error_Z;

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

    private void handleRegister(ActionEvent event) {
        String email = fld_Email.getText();
        String password = fld_Password.getText();
        String confirm = fld_Confirm.getText();
        String name = fld_Name.getText();
        String city = fld_City.getText();
        String street = fld_Street.getText();
        String zip = fld_Zip.getText();
        boolean isActive = chb_Active.isSelected();
        boolean hasError = false;

        if (email.isEmpty()) {
            lbl_error_Email.setText("Email is required");
            hasError = true;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            lbl_error_Email.setText("Please enter a valid email address.");
            hasError = true;
        }

        if (password.isEmpty()) {
            lbl_error_Password.setText("Password is required");
            hasError = true;
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$")) {
            lbl_error_Password.setText("Password must be at least 6 characters long and include uppercase, lowercase letters, and numbers.");
            hasError = true;
        }

        if (confirm.isEmpty()) {
            lbl_error_Confirm.setText("Password confirmation is required");
            hasError = true;
        } else if (!password.isEmpty() && !password.equals(confirm)) {
            lbl_error_Confirm.setText("Passwords don´t match.");
            hasError = true;
        }
        
        if (name.isEmpty()){
            lbl_error_Name.setText("Name is required");
            hasError = true;
        }
                

        if (hasError) {
        return;
    }
        
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
}