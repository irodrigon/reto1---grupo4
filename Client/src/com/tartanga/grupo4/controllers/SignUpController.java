package com.tartanga.grupo4.controllers;


import com.tartanga.grupo4.businesslogic.ClientFactory;
import com.tartanga.grupo4.exceptions.MaxConnectionsException;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserExistInDatabaseException;
import com.tartanga.grupo4.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SignUpController {

    @FXML
    private Button btn_Back, btn_Register;
    @FXML
    private TextField fld_Email, fld_Name, fld_City, fld_Street, fld_Zip;
    @FXML
    private PasswordField fld_Password, fld_Confirm;
    @FXML
    private CheckBox chb_Active;
    @FXML
    private Label lbl_error_Email, lbl_error_Password, lbl_error_Confirm, lbl_error_Name, lbl_error_City, lbl_error_Street, lbl_error_Zip;
    
    @FXML
    private Button btnSeePassword;
    
    @FXML
    private TextField hiddenFieldPassword;
    
    @FXML
    private Button btnSeeConfirm;
    
    @FXML
    private TextField hiddenFieldConfirm;
    
    private boolean isOn;
    
    @FXML
    private void initialize() {
        hiddenFieldPassword.setVisible(false);
        hiddenFieldConfirm.setVisible(false);
        btn_Back.setOnAction(this::handleGoBack);
        btn_Register.setOnAction(this::handleRegister);
        btnSeePassword.setOnAction(this::handleViewPassword);
        btnSeeConfirm.setOnAction(this::handleViewConfirm);
        
        Image image = new Image("/com/tartanga/grupo4/resources/images/eyeopened.png");

        ImageView imageView = new ImageView(image);
        ImageView imageViewConfirm = new ImageView(image);

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        
        imageViewConfirm.setFitWidth(50);
        imageViewConfirm.setFitHeight(50);
        imageViewConfirm.setPreserveRatio(true);

        btnSeePassword.setMinSize(25, 25);
        btnSeePassword.setMaxSize(25, 25);

        btnSeePassword.setGraphic(imageView);

        btnSeePassword.setStyle("-fx-background-color: transparent; -fx-border-color:transparent");
        
        btnSeeConfirm.setMinSize(25, 25);
        btnSeeConfirm.setMaxSize(25, 25);

        btnSeeConfirm.setGraphic(imageViewConfirm);

        btnSeeConfirm.setStyle("-fx-background-color: transparent; -fx-border-color:transparent");
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
            Parent mainView = fxmlLoader.load();

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

        // Email validation
        if (email.isEmpty()) {
            lbl_error_Email.setText("Email is required.");
            hasError = true;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            lbl_error_Email.setText("Please enter a valid email address.");
            hasError = true;
        } else {
            lbl_error_Email.setText("");
        }

        // Password validation
        if (password.isEmpty()) {
            lbl_error_Password.setText("Password is required.");
            hasError = true;
        } else if (!password.matches("^.{6,}$")) {
            lbl_error_Password.setText("Password must be at least \n 6 characters long.");
            hasError = true;
        } else if (!password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*")) {
            lbl_error_Password.setText("Password must include at least one uppercase letter, one lowercase letter, and one number.");
            hasError = true;
        } else {
            lbl_error_Password.setText("");
        }

        // Confirm password validation
        if (confirm.isEmpty()) {
            lbl_error_Confirm.setText("Password confirmation is required.");
            hasError = true;
        } else if (!password.equals(confirm)) {
            lbl_error_Confirm.setText("Passwords don´t match.");
            hasError = true;
        } else {
            lbl_error_Confirm.setText("");
        }

        // Name validation
        if (name.isEmpty()) {
            lbl_error_Name.setText("Name is required.");
            hasError = true;
        } else if (name.matches(".*\\d.*")) {
            lbl_error_Name.setText("Name cannot contain numbers.");
            hasError = true;
        } else {
            lbl_error_Name.setText("");
        }

        // City validation
        if (city.isEmpty()) {
            lbl_error_City.setText("City is required.");
            hasError = true;
        } else if (city.matches(".*\\d.*")) {
            lbl_error_City.setText("City cannot contain numbers.");
            hasError = true;
        } else {
            lbl_error_City.setText("");
        }

        // Street validation
        if (street.isEmpty()) {
            lbl_error_Street.setText("Street is required.");
            hasError = true;
        } else if (street.matches(".*\\d.*")) {
            lbl_error_Street.setText("Street cannot contain numbers.");
            hasError = true;
        } else {
            lbl_error_Street.setText("");
        }

        // Zip code validation
        if (zip.isEmpty()) {
            lbl_error_Zip.setText("ZIP code is required.");
            hasError = true;
        } else if (!zip.matches("\\d{1,10}")) {
            lbl_error_Zip.setText("Invalid ZIP code.");
            hasError = true;
        } else {
            lbl_error_Zip.setText("");
        }

        // If no errors, proceed with registration logic
        if (!hasError) {
            User user = new User(email, password, name, street, isActive, city, Integer.parseInt(zip));
            try {
                user = ClientFactory.getInstance().getSignable().signUp(user);

            } catch (UserExistInDatabaseException error) {
                System.out.println("Password/usuario mal");
            } catch (ServerErrorException error) {
                System.out.println("Error critico del server");
            } catch (MaxConnectionsException error) {
                System.out.println("Maximas conecsiones alcanzadas");
            } catch (Exception error) {
                System.out.println("Otro errores");
            } 

            Alert correct = new Alert(AlertType.NONE);
            correct.setTitle("Successful");
            correct.setHeaderText("User created successfully.");
            correct.setContentText("Go back to sign in to your account.");
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
            correct.getButtonTypes().add(closeButton);
            correct.showAndWait();

            // Clear fields after successful registration
            clearFields();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error in one of the fields.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        fld_Email.setText("");
        fld_Password.setText("");
        fld_Confirm.setText("");
        fld_Name.setText("");
        fld_City.setText("");
        fld_Street.setText("");
        fld_Zip.setText("");
        chb_Active.setSelected(false);
    }
    
     @FXML
    private void handleViewPassword(ActionEvent event) {
        isOn = !isOn;
        
        if (isOn) {
          
            String password = fld_Password.getText();
            fld_Password.setVisible(false);
            hiddenFieldPassword.setVisible(true);
            hiddenFieldPassword.setText(password);
            fld_Password.setText(password);
            
            Image image = new Image("/com/tartanga/grupo4/resources/images/eyeclosed.png");

            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);

            btnSeePassword.setMinSize(25, 25);
            btnSeePassword.setMaxSize(25, 25);

            btnSeePassword.setGraphic(imageView);

            btnSeePassword.setStyle("-fx-background-color: transparent; -fx-border-color:transparent");
        } else {
            
            hiddenFieldPassword.setVisible(false);
            fld_Password.setText(hiddenFieldPassword.getText());
            fld_Password.setVisible(true);
            
            Image image = new Image("/com/tartanga/grupo4/resources/images/eyeopened.png");

            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);

            btnSeePassword.setMinSize(25, 25);
            btnSeePassword.setMaxSize(25, 25);

            btnSeePassword.setGraphic(imageView);

            btnSeePassword.setStyle("-fx-background-color: transparent; -fx-border-color:transparent");
        }
    }
    
     @FXML
    private void handleViewConfirm(ActionEvent event) {
        isOn = !isOn;
        
        if (isOn) {
          
            String password = fld_Confirm.getText();
            fld_Confirm.setVisible(false);
            hiddenFieldConfirm.setVisible(true);
            hiddenFieldConfirm.setText(password);
            fld_Confirm.setText(password);
            
            Image image = new Image("/com/tartanga/grupo4/resources/images/eyeclosed.png");

            ImageView imageViewConfirm = new ImageView(image);

            imageViewConfirm.setFitWidth(50);
            imageViewConfirm.setFitHeight(50);
            imageViewConfirm.setPreserveRatio(true);

            btnSeeConfirm.setMinSize(25, 25);
            btnSeeConfirm.setMaxSize(25, 25);

            btnSeeConfirm.setGraphic(imageViewConfirm);

            btnSeeConfirm.setStyle("-fx-background-color: transparent; -fx-border-color:transparent");
        } else {
            
            hiddenFieldConfirm.setVisible(false);
            fld_Confirm.setText(hiddenFieldConfirm.getText());
            fld_Confirm.setVisible(true);
            
            Image image = new Image("/com/tartanga/grupo4/resources/images/eyeopened.png");

            ImageView imageViewConfirm = new ImageView(image);

            imageViewConfirm.setFitWidth(50);
            imageViewConfirm.setFitHeight(50);
            imageViewConfirm.setPreserveRatio(true);

            btnSeeConfirm.setMinSize(25, 25);
            btnSeeConfirm.setMaxSize(25, 25);

            btnSeeConfirm.setGraphic(imageViewConfirm);

            btnSeeConfirm.setStyle("-fx-background-color: transparent; -fx-border-color:transparent");
        }
    }
}
