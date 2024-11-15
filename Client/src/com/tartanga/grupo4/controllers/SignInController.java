package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.businesslogic.ClientFactory;
import com.tartanga.grupo4.exceptions.ClientSideException;
import com.tartanga.grupo4.exceptions.MaxConnectionsException;
import com.tartanga.grupo4.exceptions.ServerErrorException;
import com.tartanga.grupo4.exceptions.UserPasswdException;
import com.tartanga.grupo4.model.User;
import java.io.IOException;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The SignIn Controller Class is in charge of letting the user know if they are
 * able to login into the application with their credentials and to access the
 * creating user or sign up window. It is going to interact with the user
 * through the keyboard and mouse, permitting them entering their username and
 * password. It will validate whether the email or password they are entering is
 * in the correct format and if it is, it is going to send the data to the
 * Client class via a Factory call. It will throw every needed exception when
 * these are returned from the Client.
 *
 *
 * @author Iñi
 */
public class SignInController {

    /**
     * This is the container where your fxml scene will be shown.
     */
    private Stage stage;

    /**
     * This is used for showing messages principally for debugging purposes.
     */
    private static Logger logger;

    /**
     * This boolean is going to be used as a trigger on the button to see a
     * clear password.
     */
    private boolean isOn = false;

    /**
     * The button that is going to be clicked when the user attempts to login.
     */
    @FXML
    private Button btn_Login;

    /**
     * A Hyperlink that leads onto the sign up window.
     */
    @FXML
    private Hyperlink hl_create;

    /**
     * The text field where the user enters the email.
     */
    @FXML
    private TextField userField;

    /**
     * The text field where the user enters the password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * This button is going to allow, when pressed, to see the password, which
     * was before hidden and to hide it again, pressing it a second time.
     */
    @FXML
    private Button btnSeePassword;

    /**
     * This text Field is used for making the effect of revealing and concealing
     * the password.
     */
    @FXML
    private TextField hiddenField;

    /**
     * This method is going to first, assign each button to the function it is
     * going to perform when it is clicked, then, it is going to set some
     * parameters that are needed for the functions to perform effectively. For
     * example, it is going to hide the field where the password is shown in
     * clear text, it is also showing the default image of an opened eye and
     * assigning it to the button, which would be changed by each click. It is
     * assigning the repsonsibility of opening a new sign up window to the
     * pressing of the hyperlink.
     */
    @FXML
    private void initialize() {
        hiddenField.setVisible(false);
        btn_Login.setOnAction(this::handleLogin);
        hl_create.setOnAction(this::handleCreateUser);
        btnSeePassword.setOnAction(this::handleViewPassword);

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

    /**
     * This method is really being called from the main application class, and
     * is in charge of launching the sign in window, proper to this class. It
     * initiates the window with all of it functionality, creates a new Scene,
     * with the proper sign in fxml view and shows the window stage. It is also
     * assigning the "X" closing button on the top right of the window to a
     * method which will call in a confirmation.
     *
     * @param root The root node from the ApplicationU class(Main client class).
     */
    public void initStage(Parent root) {
        logger.info("Initializing Login stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(this::onCloseRequestWindowEvent);
    }

    /**
     * This method is effectively launching the sign up Window by the click of a
     * hyperlink, in this case. It loads the indicated fxml into the scene and
     * shows the new stage, hiding the sign in window. It is going to center the
     * window in the screen.
     *
     * @param event The ActionEvent triggered.
     */
    @FXML
    private void handleCreateUser(ActionEvent event) {
        try {
            FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignUpView.fxml"));
            Parent mainView = FXMLLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.setTitle("SignUp");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Something went wrong when loading the window.", e.getMessage());
        }
    }

    /**
     * This method is going to be the core functionality of the sign in
     * controller, so to speak. Saving details, as customizing alert windows
     * with icons and such, it is going, not just to verify if the fields are
     * submitted in a format that is correct or else throwing an exception which
     * tells the user how to submit the data, but, it is also going to login
     * into the main users window if every piece of data submitted has been
     * verified and everything is working as intended. If everything is not
     * functioning as expected, it is going to let the user know of different
     * exceptions, which could be client, server, or user/password exceptions...
     *
     * @param event The ActionEvent triggered.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Image icon = new Image("/com/tartanga/grupo4/resources/images/servericon.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        alert.setHeaderText(null);
        ButtonType oKButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(oKButton);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);

        if (hiddenField.isVisible()) {
            passwordField.setText(hiddenField.getText());
        } 

        if (userField.getText().equals("") && passwordField.getText().equals("")) {
            alert.setTitle("Empty user fields");
            alert.setContentText("Please fill up the required fields.");
            alert.showAndWait();
        } else if (!userField.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            alert.setTitle("Invalid email address");
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
        } else if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}$")) {
            alert.setTitle("Invalid Password");
            alert.setContentText("The password must be at least 6 characters long and contain a capital letter and a number.");
            alert.showAndWait();
        } else {
            try {
                User user = new User();
                user.setUsername(userField.getText());
                user.setPassword(passwordField.getText());
                user = ClientFactory.getInstance().getSignable().signIn(user);
                
                System.out.print(user.toString());
                
                FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/MainView.fxml"));
                Parent mainView = (Parent)FXMLLoader.load();
                
                MainController controller =  ((MainController)FXMLLoader.getController());
                controller.setStage(this.stage);
                controller.initStage(mainView, user);
                /*Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(mainView);
                stage.setScene(scene);
                stage.setTitle("Main Window");
                stage.show();
                logger.info("Correct user.");*/
                
                
            } catch (UserPasswdException e) {
                logger.log(Level.SEVERE, "UserPasswdException", e.getMessage());
                alert.setTitle("Incorrect user or password");
                alert.setContentText("The user or password are incorrect. Please try again.");
                alert.showAndWait();
            } catch (ServerErrorException srve) {
                logger.log(Level.SEVERE, "ServerErrorException: {0}", srve.getMessage());
                alert.setTitle("Internal Server Error");
                alert.setContentText("Internal Server Error. Contact your System Administrator.");
                alert.showAndWait();
            } catch (MaxConnectionsException e) {
                logger.log(Level.SEVERE, "MaxConnectionsException: {0}", e.getMessage());
                alert.setTitle("Too Many Connections");
                alert.setContentText("Too many connections simulteanously. Please be patient.");
                alert.showAndWait();
            } catch (ClientSideException e) {
                logger.log(Level.SEVERE, "ClientSideException: {0}", e.getMessage());
                alert.setTitle("Client side error");
                alert.setContentText("Error on Client Side. Contact your System Administrator.");
                alert.showAndWait();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IOException: {0}", e.getMessage());
                alert.setTitle("I/O Error");
                alert.setContentText("Error on Client Side. Contact your System Administrator.");
                alert.showAndWait();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Critical Error: {0}", e.getMessage());
                alert.setTitle("Critical Error");
                alert.setContentText("Critical Error. Please escalate the request.");
                alert.showAndWait();
            }
        }
    }

    /**
     * This method is going to follow the pressing of the "X" button on the top
     * right corner of the sign in window and after an alert which asks for
     * confirmation, stay on thwe window if the answer is "no" or exit the app
     * is the answer is "yes".
     *
     * @param event The ActionEvent triggered.
     */
    @FXML
    private void onCloseRequestWindowEvent(Event event) {
        Image icon = new Image("/com/tartanga/grupo4/resources/images/servericon.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to close the application?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        alert.setTitle("Closing Application");
        alert.setHeaderText(null);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);
        alert.showAndWait();
        if (alert.resultProperty().get().equals(yesButton)) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * This method shows the password in clear text with the pressing of a
     * button or conceals it with the pressing of the same button. It is going
     * to hide the password field and set the text of the password field in text
     * field and vice versa, to make the effect the password is being shown. All
     * of this is transparent to the user.
     *
     * @param event Tha ActionEvent triggered.
     */
    @FXML
    private void handleViewPassword(ActionEvent event) {
        isOn = !isOn;

        if (isOn) {

            String password = passwordField.getText();
            passwordField.setVisible(false);
            hiddenField.setVisible(true);
            hiddenField.setText(password);
            passwordField.setText(password);

            //I need to reload the image, else I cannot make it work.
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

            hiddenField.setVisible(false);
            passwordField.setText(hiddenField.getText());
            passwordField.setVisible(true);

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
     * A get method for the stage.
     *
     * @return The stage that is going to be shown.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * A set method for the stage.
     *
     * @param stage The stage you must set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This Constructor is initializaing the logger.
     */
    public SignInController() {
        logger = Logger.getLogger(SignInController.class.getName());
    }

}
