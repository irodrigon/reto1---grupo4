package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.businesslogic.ClientFactory;
import com.tartanga.grupo4.exceptions.ClientSideException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonType;

/**
 * Controller class for the Sign-Up view.
 * <p>
 * This class manages the Sign-Up user interface, handling input validation,
 * user registration, and navigation back to the Sign-In view. The main
 * responsibilities include:
 * <ul>
 * <li>Validating user inputs to ensure correctness and completeness.</li>
 * <li>Attempting to register a new user and handling potential errors.</li>
 * <li>Navigating back to the Sign-In view when requested.</li>
 * <li>Displaying alerts for user feedback and information.</li>
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
     * This button shows an eye next to the password field. It is used, when
     * clicked, to show the password in clear text or conceal it.
     */
    @FXML
    private Button btnSeePassword;

    /**
     * This text field is used to show the password in the password field in
     * clear text or hide it when needed.
     */
    @FXML
    private TextField hiddenFieldPassword;

    /**
     * This button shows an eye next to the confirm field. It is used, when
     * clicked, to show the password in clear text or conceal it.
     */
    @FXML
    private Button btnSeeConfirm;

    /**
     * This text field is used to show the password in the confirm field in
     * clear text or hide it when needed.
     */
    @FXML
    private TextField hiddenFieldConfirm;

    /**
     * This boolean is going to be used as a trigger on the button to see a
     * clear password.
     */
    private boolean isOn;

    /**
     * This boolean is going to be used as a trigger on the button to see a
     * clear password on the confirm password field.
     */
    private boolean isOnConfirm = false;

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
     * The label displaying error messages related to password confirmation
     * input.
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
     * This is used for showing messages principally for debugging purposes.
     */
    private static Logger logger;

    /**
     * Initializes the Sign-Up view.
     * <p>
     * This method is automatically called after the FXML elements have been
     * loaded. It sets up event handlers for the Back and Register buttons.
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
     * Handles navigation back to the Sign-In view when the Back button is
     * clicked.
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
     * This method validates all user inputs for correctness. If any input is
     * invalid, error messages are displayed next to the corresponding fields.
     * If all inputs are valid, an attempt is made to register the user by
     * calling the server API. Appropriate feedback is given based on the
     * outcome of the registration attempt.
     *
     * @param event The action event triggered by clicking the Register button.
     */
    private void handleRegister(ActionEvent event) {
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        Image icon = new Image("/com/tartanga/grupo4/resources/images/servericon.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        ButtonType CloseButton = new ButtonType("Close");
        alert2.getButtonTypes().setAll(CloseButton);
        Stage alertStage = (Stage) alert2.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);
        String email = fld_Email.getText();
        
        if (hiddenFieldPassword.isVisible()) {
            fld_Password.setText(hiddenFieldPassword.getText());
        }
        
        if (hiddenFieldConfirm.isVisible()) {
            fld_Confirm.setText(hiddenFieldConfirm.getText());
        } 
        
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
                alert2.setTitle("Successful");
                alert2.setHeaderText("User created successfully.");
                alert2.setContentText("Go back to sign in to your account.");
                alert2.showAndWait();
                clearFields();
                logger.info("User created correctly.");
            } catch (UserExistInDatabaseException error) {
                logger.log(Level.SEVERE, "UserExistInDatabaseException", error.getMessage());
                alert("Error", "Login already exists.", "Enter a different e-mail.");
            } catch (ServerErrorException error) {
                logger.log(Level.SEVERE, "ServerErrorException: {0}", error.getMessage());
                alert("Error", "An error occurred on the server.", "Try again later.");
            } catch (MaxConnectionsException error) {
                logger.log(Level.SEVERE, "MaxConnectionsException: {0}", error.getMessage());
                alert("Error", "Max connections exceeded.", "Try again later.");
            } catch (ClientSideException error) {
                logger.log(Level.SEVERE, "ClientSideException: {0}", error.getMessage());
                alert("Error", "Error on Client Side.", "Contact your System Administrator.");
            } catch (Exception error) {
                logger.log(Level.SEVERE, "Critical Error: {0}", error.getMessage());
                alert("Critical Error", "Critical Error.", "Contact your System Administrator.");
            }
        }
    }

    /**
     * Displays an alert with the specified title, header, and content.
     *
     * @param title The title of the alert.
     * @param header The header message of the alert.
     * @param content The content message of the alert.
     */
    private void alert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Image icon = new Image("/com/tartanga/grupo4/resources/images/servericon.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        ButtonType CloseButton = new ButtonType("Close");
        alert.getButtonTypes().setAll(CloseButton);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);
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
        hiddenFieldPassword.clear();
        hiddenFieldConfirm.clear();
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

    /**
     * This method shows the password in clear text with the pressing of a button or conceals
     * it with the pressing of the same button. It is going to hide the password field and 
     * set the text of the password field in text field and vice versa, to make the effect
     * the password is being shown. All of this is transparent to the user.
     * 
     * @param event Tha ActionEvent triggered.
     */
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
    
    /**
     * This method shows the password in clear text in the confirm field with the pressing 
     * of a button or conceals it with the pressing of the same button. It is going to hide 
     * the password field and set the text of the password field in text field and vice versa, 
     * to make the effect the password is being shown. All of this is transparent to the user.
     * 
     * @param event The ActionEvent triggered.
     */
    @FXML
    private void handleViewConfirm(ActionEvent event) {
        isOnConfirm = !isOnConfirm;

        if (isOnConfirm) {
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
    
    /**
     * This Constructor initiates the logger.
     */
    public SignUpController() {
        logger = Logger.getLogger(SignInController.class.getName());
    }
}
