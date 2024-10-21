package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getName());

    @FXML
    private Button btn_Back, btn_Register;
    @FXML
    private TextField fld_Email, fld_Password, fld_Confirm, fld_Name, fld_City, fld_Street, fld_Zip;
    @FXML
    private CheckBox chb_Active;
    @FXML
    private Label lbl_error_Email, lbl_error_Password, lbl_error_Confirm, lbl_error_Name, lbl_error_City, lbl_error_Street, lbl_error_Zip;

    @FXML
    private void initialize() {
        btn_Back.setOnAction(this::handleGoBack);
        btn_Register.setOnAction(this::handleRegister);
        LOGGER.log(Level.INFO, "SignUpController initialized and ready");
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        try {
            LOGGER.log(Level.INFO, "Navigating back to SignInView");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
            Parent mainView = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load SignInView.fxml", e);
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

        // Email validation
        if (email.isEmpty()) {
            lbl_error_Email.setText("Email is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "Email field is empty");
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            lbl_error_Email.setText("Please enter a valid email address.");
            hasError = true;
            LOGGER.log(Level.WARNING, "Invalid email format: {0}", email);
        } else {
            lbl_error_Email.setText("");
        }

        // Password validation
        if (password.isEmpty()) {
            lbl_error_Password.setText("Password is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "Password field is empty");
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$")) {
            lbl_error_Password.setText("Password must be at least 6 characters long and include uppercase, lowercase letters, and numbers.");
            hasError = true;
            LOGGER.log(Level.WARNING, "Invalid password format: {0}", password);
        } else {
            lbl_error_Password.setText("");
        }

        // Confirm password validation
        if (confirm.isEmpty()) {
            lbl_error_Confirm.setText("Password confirmation is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "Password confirmation field is empty");
        } else if (!password.equals(confirm)) {
            lbl_error_Confirm.setText("Passwords don't match.");
            hasError = true;
            LOGGER.log(Level.WARNING, "Passwords do not match");
        } else {
            lbl_error_Confirm.setText("");
        }

        // Name validation
        if (name.isEmpty()) {
            lbl_error_Name.setText("Name is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "Name field is empty");
        } else if (name.matches(".*\\d.*")) {
            lbl_error_Name.setText("Name cannot contain numbers");
            hasError = true;
            LOGGER.log(Level.WARNING, "Invalid name format (contains numbers): {0}", name);
        } else {
            lbl_error_Name.setText("");
        }

        // City validation
        if (city.isEmpty()) {
            lbl_error_City.setText("City is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "City field is empty");
        } else if (city.matches(".*\\d.*")) {
            lbl_error_City.setText("City cannot contain numbers");
            hasError = true;
            LOGGER.log(Level.WARNING, "Invalid city format (contains numbers): {0}", city);
        } else {
            lbl_error_City.setText("");
        }

        // Street validation
        if (street.isEmpty()) {
            lbl_error_Street.setText("Street is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "Street field is empty");
        } else if (street.matches(".*\\d.*")) {
            lbl_error_Street.setText("Street cannot contain numbers");
            hasError = true;
            LOGGER.log(Level.WARNING, "Invalid street format (contains numbers): {0}", street);
        } else {
            lbl_error_Street.setText("");
        }

        // Zip code validation
        if (zip.isEmpty()) {
            lbl_error_Zip.setText("ZIP code is required");
            hasError = true;
            LOGGER.log(Level.WARNING, "ZIP code field is empty");
        } else if (!zip.matches("\\d{1,10}")) {
            lbl_error_Zip.setText("Invalid ZIP code");
            hasError = true;
            LOGGER.log(Level.WARNING, "Invalid ZIP code format: {0}", zip);
        } else {
            lbl_error_Zip.setText("");
        }

        // If no errors, proceed with registration logic
        if (!hasError) {
            User user = new User(email, password, name, street, isActive, city, Integer.parseInt(zip));
            LOGGER.log(Level.INFO, "User registered successfully: {0}", user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error en alguno de los campos");
            alert.setContentText("Por favor, inténtalo de nuevo.");
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, "Registration failed due to validation errors");
        }
    }
}
