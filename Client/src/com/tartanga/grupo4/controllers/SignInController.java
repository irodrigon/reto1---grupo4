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
 * @author Iñi
 */
public class SignInController {

    private Stage stage;
    private final Logger logger;
    private boolean isOn = false;
    @FXML
    private Button btn_Login;
    @FXML
    private Hyperlink hl_create;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button btnSeePassword;
    
    @FXML
    private TextField hiddenField;

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

    public void initStage(Parent root) {
        logger.info("Initializing Login stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(this::onCloseRequestWindowEvent);
    }

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
            logger.log(Level.SEVERE, "Something went worng when loading the window.", e.getMessage());
        }
    }

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
                //new Alert(Alert.AlertType.INFORMATION, "User verified.", ButtonType.OK).showAndWait();

                FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/MainView.fxml"));
                Parent mainView = FXMLLoader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(mainView);
                stage.setScene(scene);
                stage.setTitle("Main Window");
                stage.show();
                logger.info("Correct user.");
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
            } catch(MaxConnectionsException e){
                logger.log(Level.SEVERE, "MaxConnectionsException: {0}", e.getMessage());
                alert.setTitle("Too Many Connections");
                alert.setContentText("Too many connections simulteanously. Please be patient.");
                alert.showAndWait();
            } catch (ClientSideException e) {
                logger.log(Level.SEVERE, "ClientSideException: {0}", e.getMessage());
                alert.setTitle("Client side error");
                alert.setContentText("Error on Client Side. Contact your System Administrator.");
                alert.showAndWait();
            }catch(IOException e){
                logger.log(Level.SEVERE, "IOException: {0}", e.getMessage());
                alert.setTitle("I/O Error");
                alert.setContentText("Error on Client Side. Contact your System Administrator.");
                alert.showAndWait();
            }catch(Exception e){
                logger.log(Level.SEVERE, "Critical Error: {0}", e.getMessage());
                alert.setTitle("Critical Error");
                alert.setContentText("Critical Error. Please escalate the request.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onCloseRequestWindowEvent(Event event) {
        Image icon = new Image("/com/tartanga/grupo4/resources/images/servericon.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to close the application?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton,noButton);
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

    @FXML
    private void handleViewPassword(ActionEvent event) {
        isOn = !isOn;
        
        if (isOn) {
          
            String password = passwordField.getText();
            passwordField.setVisible(false);
            hiddenField.setVisible(true);
            hiddenField.setText(password);
            passwordField.setText(password);
            
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SignInController() {
        logger = Logger.getLogger(SignInController.class.getName());
    }

}
