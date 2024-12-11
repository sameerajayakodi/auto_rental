package org.samee.lk.autorental.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController extends Application {

    public TextField usernameInput;
    public TextField passwordInput;
    public Button loginBtn;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginViewController.class.getResource("/org/samee/lk/autorental/loginPage/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {
        String userName = usernameInput.getText();
        String password = passwordInput.getText();

       if(userName.isEmpty() || password.isEmpty()) {
           System.out.println("Username or password is empty");
           showErrorAlert("All fields must be filled out.");
       } else if (userName.equals("admin") && password.equals("admin")) {
           System.out.println("Admin logged in");
           showSuccessAlert("Admin logged in");
           Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/mainPage/main-view.fxml"));
           mainPageRoot.setFocusTraversable(true);
           Stage stage = (Stage) loginBtn.getScene().getWindow();

           stage.setScene(new Scene(mainPageRoot));


           stage.show();
       }else{
           showErrorAlert("Username or password is incorrect.");
       }


    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/assets/successAlert.css").toExternalForm());
        alert.setTitle("Success");
        alert.setHeaderText("Operation Successful!");
        alert.show();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/assets/errorAlert.css").toExternalForm());
        alert.setTitle("Error");
        alert.setHeaderText("Validation Error");
        alert.show();
    }
}
