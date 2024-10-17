package com.tartanga.grupo4.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Controlador para la ventana de Sign In.
 */
public class SignInController {
    private Stage stage;
    private final Logger logger;
    @FXML
    private Button btn_Login;
    @FXML
    private Hyperlink hl_create;
    
    @FXML
    private void initialize() {
    btn_Login.setOnAction(this::handleLogin);
    hl_create.setOnAction(this::handleCreateUser);
}

    
    public void initStage(Parent root) {
        logger.info("Initializing Login stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        stage.show();
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
    try {
        FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/MainView.fxml"));
        Parent mainView = FXMLLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.show();
        
    } catch (IOException e) {
        e.printStackTrace();
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
