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

import java.io.IOException;

/**
 * Controller class for the Sign-Up view.
 * <p>
 * This class manages the Sign-Up user interface, handling input validation,
 * user registration, and navigation back to the Sign-In view.
 * The main responsibilities include:
 * <ul>
 *     <li>Validating user inputs to ensure correctness and completeness.</li>
 *     <li>Attempting to register a new user and handling potential errors.</li>
 *     <li>Navigating back to the Sign-In view when requested.</li>
 *     <li>Displaying alerts for user feedback and information.</li>
 * </ul>
 */
public class SignUpController {

    /**
     * The button that allows the user to go back to the Sign-In view.
     */
    @FXML
    private Button btn_Back;

    /**
     * The button that initiates the registration process.
     */
    @FXML
    private Button btn_Register;

    /**
     * The text field for the user to input their email address.
     */
    @FXML
    private TextField fld_Email;

    /**
     * The text field for the user's name.
     */
    @FXML
    private TextField fld_Name;

    /**
     * The text field for the user's city of residence.
     */
    @FXML
    private TextField fld_City;

    /**
     * The text field for the user's street address.
     */
    @FXML
    private TextField fld_Street;

    /**
     * The text field for the user's ZIP code.
     */
    @FXML
    private TextField fld_Zip;

    /**
     * The password field for the user to input their password.
     */
    @FXML
    private PasswordField fld_Password;

    /**
     * The password field for the user to confirm their password.
     */
    @FXML
    private PasswordField fld_Confirm;

    /**
     * The checkbox indicating whether the user is active.
     */
    @FXML
    private CheckBox chb_Active;

    /**
     * The label displaying error messages related to email input.
     */
    @FXML
    private Label lbl_error_Email;

    /**
     * The label displaying error messages related to password input.
     */
    @FXML
    private Label lbl_error_Password;

    /**
     * The label displaying error messages related to password confirmation input.
     */
    @FXML
    private Label lbl_error_Confirm;

    /**
     * The label displaying error messages related to name input.
     */
    @FXML
    private Label lbl_error_Name;

    /**
     * The label displaying error messages related to city input.
     */
    @FXML
    private Label lbl_error_City;

    /**
     * The label displaying error messages related to street input.
     */
    @FXML
    private Label lbl_error_Street;

    /**
     * The label displaying error messages related to ZIP code input.
     */
    @FXML
    private Label lbl_error_Zip;

    /**
     * Initializes the Sign-Up view.
     * <p>
     * This method is automatically called after the FXML elements have been loaded.
     * It sets up event handlers for the Back and Register buttons.
     */
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

    /**
     * Handles navigation back to the Sign-In view when the Back button is clicked.
     * <p>
     * Loads the Sign-In view FXML and switches the current stage's scene to it.
     *
     * @param event The action event triggered by clicking the Back button.
     */
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

    /**
     * Handles the registration process when the Register button is clicked.
     * <p>
     * This method validates all user inputs for correctness. If any input is invalid,
     * error messages are displayed next to the corresponding fields. If all inputs are
     * valid, an attempt is made to register the user by calling the server API.
     * Appropriate feedback is given based on the outcome of the registration attempt.
     *
     * @param event The action event triggered by clicking the Register button.
     */
    private void handleRegister(ActionEvent event) throws Exception {
        String email = fld_Email.getText();
        String password = fld_Password.getText();
        String confirm = fld_Confirm.getText();
        String name = fld_Name.getText();
        String city = fld_City.getText();
        String street = fld_Street.getText();
        String zip = fld_Zip.getText();
        boolean isActive = chb_Active.isSelected();
        boolean hasError = false;

        // Validate email
        if (email.isEmpty()) {
            lbl_error_Email.setText("Email is required.");
            hasError = true;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            lbl_error_Email.setText("Please enter a valid email address.");
            hasError = true;
        } else {
            lbl_error_Email.setText("");
        }

        // Validate password
        if (password.isEmpty()) {
            lbl_error_Password.setText("Password is required.");
            hasError = true;
        } else if (!password.matches("^.{6,}$")) {
            lbl_error_Password.setText("Password must be at least 6 characters long.");
            hasError = true;
        } else if (!password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*")) {
            lbl_error_Password.setText("Password must include at least one uppercase letter, one lowercase letter, and one number.");
            hasError = true;
        } else {
            lbl_error_Password.setText("");
        }

        // Validate password confirmation
        if (confirm.isEmpty()) {
            lbl_error_Confirm.setText("Password confirmation is required.");
            hasError = true;
        } else if (!password.equals(confirm)) {
            lbl_error_Confirm.setText("Passwords don’t match.");
            hasError = true;
        } else {
            lbl_error_Confirm.setText("");
        }

        // Validate name
        if (name.isEmpty()) {
            lbl_error_Name.setText("Name is required.");
            hasError = true;
        } else if (name.matches(".*\\d.*")) {
            lbl_error_Name.setText("Name cannot contain numbers.");
            hasError = true;
        } else {
            lbl_error_Name.setText("");
        }

        // Validate city
        if (city.isEmpty()) {
            lbl_error_City.setText("City is required.");
            hasError = true;
        } else if (city.matches(".*\\d.*")) {
            lbl_error_City.setText("City cannot contain numbers.");
            hasError = true;
        } else {
            lbl_error_City.setText("");
        }

        // Validate street
        if (street.isEmpty()) {
            lbl_error_Street.setText("Street is required.");
            hasError = true;
        } else if (street.matches(".*\\d.*")) {
            lbl_error_Street.setText("Street cannot contain numbers.");
            hasError = true;
        } else {
            lbl_error_Street.setText("");
        }

        // Validate ZIP code
        if (zip.isEmpty()) {
            lbl_error_Zip.setText("ZIP code is required.");
            hasError = true;
        } else if (!zip.matches("\\d{1,10}")) {
            lbl_error_Zip.setText("Invalid ZIP code.");
            hasError = true;
        } else {
            lbl_error_Zip.setText("");
        }

        // Proceed with registration if no errors
        if (!hasError) {
            User user = new User(email, password, name, street, isActive, city, Integer.parseInt(zip));
            try {
                user = ClientFactory.getInstance().getSignable().signUp(user);
                alert("Successful", "User created successfully.", "Go back to sign in to your account.");
                clearFields();
            } catch (UserExistInDatabaseException error) {
                alert("Error", "Login already exists.", "Introduce a different e-mail.");
            } catch (ServerErrorException error) {
                alert("Error", "An error occurred on the server.", "Try again later.");
            } catch (MaxConnectionsException error) {
                alert("Error", "Max connections exceeded.", "Try again later.");
            }
        }
    }

    /**
     * Displays an alert with the specified title, header, and content.
     *
     * @param title   The title of the alert.
     * @param header  The header message of the alert.
     * @param content The content message of the alert.
     */
    private void alert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Clears all input fields in the Sign-Up form.
     */
    private void clearFields() {
        fld_Email.clear();
        fld_Name.clear();
        fld_City.clear();
        fld_Street.clear();
        fld_Zip.clear();
        fld_Password.clear();
        fld_Confirm.clear();
        chb_Active.setSelected(false);
        lbl_error_Email.setText("");
        lbl_error_Name.setText("");
        lbl_error_City.setText("");
        lbl_error_Street.setText("");
        lbl_error_Zip.setText("");
        lbl_error_Password.setText("");
        lbl_error_Confirm.setText("");
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