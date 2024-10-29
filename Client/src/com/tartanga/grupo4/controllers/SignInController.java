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
 * Controlador para la ventana de Sign In.
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

        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
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
            FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignUp.fxml"));
            Parent mainView = FXMLLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        if (userField.getText().equals("") && passwordField.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Fill up the required fields.", ButtonType.OK).showAndWait();
        } else if (!userField.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid email address.", ButtonType.OK).showAndWait();
        } else if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}$")) {
            new Alert(Alert.AlertType.ERROR, "Password must be 6 characters long, and include at least one uppercase letter, one lowercase letter, and one number.", ButtonType.OK).showAndWait();
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
                stage.show();
                logger.info("Correct user.");
            } catch (UserPasswdException e) {
                logger.log(Level.SEVERE, "UserPasswdException", e.getMessage());
                new Alert(Alert.AlertType.ERROR, "User or password are incorrect.", ButtonType.OK).showAndWait();
            } catch (ServerErrorException srve) {
                logger.severe("ServerErrorException: " + srve.getMessage());
                new Alert(Alert.AlertType.ERROR, "Internal server error.", ButtonType.OK).showAndWait();
            } catch (ClientSideException e) {
                new Alert(Alert.AlertType.ERROR, "Error on Client Side.", ButtonType.OK).showAndWait();
            }catch(IOException e){
                new Alert(Alert.AlertType.ERROR, "Error on Client Side.", ButtonType.OK).showAndWait();
            }catch(MaxConnectionsException e){
                 new Alert(Alert.AlertType.ERROR, "Too much conenctions simultaneously, please be patient.", ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML
    private void onCloseRequestWindowEvent(Event event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you really want to close the application?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Closing Application");
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.resultProperty().get().equals(ButtonType.YES)) {
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

            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
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

            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
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
