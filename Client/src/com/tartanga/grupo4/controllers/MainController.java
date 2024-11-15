package com.tartanga.grupo4.controllers;

import com.tartanga.grupo4.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * MainController is responsible for managing the main application view,
 * It handles a menu bar with "Log Out" and "Exit" as menu options.
 * 
 * @author Aitor
 */
public class MainController implements Initializable {
    /**
     * Logger instance for logging client operations and error messages.
     */
    private static final Logger logger = Logger.getLogger("Client");
    
    private User user;
    /**
     * BorderPane used to get the scece of this window.
     */
    @FXML
    private BorderPane BorderPaneMain;
    
    /**
     * MenuItem for logging out of the application.
     */
    @FXML
    private MenuItem mni_LogOut;
    
    /**
     * MenuItem for exiting the application.
     */
    @FXML
    private MenuItem mni_Exit;
    
    @FXML
    private Label userData;

    public MainController() {
        this.user = (User) BorderPaneMain.getScene().getWindow().getUserData();
    }
    /**
     * Initializes the MainController. Sets event handlers for the "Log Out"
     * and "Exit" menu items.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if resources are not provided.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userData.setText(user.getUsername());
        mni_LogOut.setOnAction(this::handleLogOut);
        mni_Exit.setOnAction(this::onCloseRequestWindowEvent);
        
    }
  
    /**
     * Handles the event triggered when the "Exit" menu item is selected.
     * Displays a confirmation dialog to the user before closing the application.
     * If the user confirms, the application exits; otherwise, the event is consumed.
     *
     * @param event The event that triggered this method.
     */
    @FXML
    private void onCloseRequestWindowEvent(Event event) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you wish to close the application?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exiting confirmation");
        alert.setHeaderText(null);
        alert.initOwner(getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
        if (alert.resultProperty().get().equals(ButtonType.YES)) {
            logger.log(Level.INFO, "Closing application");
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Handles the "Log Out" action, triggered when the "Log Out" menu item is selected.
     * Loads the SignIn view and switches the current stage to display the SignIn scene.
     * 
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    private void handleLogOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you wish to log out?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Closing confirmation");
        alert.setHeaderText(null);
        alert.initOwner(getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
        if (alert.resultProperty().get().equals(ButtonType.YES)) {
            try {
            FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/com/tartanga/grupo4/views/SignInView.fxml"));
            Parent SignIn = FXMLLoader.load();

            Stage stageIn = (Stage) mni_LogOut.getParentPopup().getOwnerWindow();
            logger.log(Level.INFO, "Returning to LogInView");
            Scene scene = new Scene(SignIn);
            stageIn.setScene(scene);
            stageIn.setTitle("SignIn");
            stageIn.show();
            
            } catch (IOException e) {
            logger.log(Level.SEVERE, "Error, cannot log out");
            new Alert(Alert.AlertType.ERROR, "An error happened while login out", ButtonType.OK).showAndWait();
            }
        } else {
            event.consume();
        }
        
    }
    private Stage getStage() {
        return (Stage) BorderPaneMain.getScene().getWindow();
    }
    
}
