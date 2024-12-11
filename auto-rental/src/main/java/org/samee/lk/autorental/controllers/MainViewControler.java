package org.samee.lk.autorental.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.samee.lk.autorental.models.VehicleModel;
import org.samee.lk.autorental.tm.vehicleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewControler implements Initializable {
    public Button optionBtn;
    public Button rentalButton;
    public Button backBtn;
    public Button addvehicleBtn;
    public Button vehicleDeleteBtn;
    public Button vehicleupdateBtn;

    public void handleoption(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/option-view.fxml"));
        Stage stage = (Stage) optionBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();

    }

    public void handleRental(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/rentVehicle/rental-view.fxml"));
        Stage stage = (Stage) rentalButton.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
    }


    public TableView<vehicleTM> tblVehicle;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<vehicleTM> tms = null;
        try {
            tms = VehicleModel.loadVehicle();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblVehicle.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblVehicle.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tblVehicle.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("model"));
        tblVehicle.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("color"));
        tblVehicle.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblVehicle.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblVehicle.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblVehicle.setItems(FXCollections.observableArrayList(tms));
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/loginPage/login-view.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
    }

    public void handleAddVehicle(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/addVehicle/add-view.fxml"));
        Stage stage = (Stage) addvehicleBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
    }

    public void handleVehicleDelete(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/deleteVehicle/delete-view.fxml"));
        Stage stage = (Stage) vehicleDeleteBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
    }

    public void handleVehicleUpdate(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/updateVehicle/update-view.fxml"));
        Stage stage = (Stage) vehicleupdateBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
    }
}
