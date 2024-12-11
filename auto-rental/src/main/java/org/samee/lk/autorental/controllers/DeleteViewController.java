package org.samee.lk.autorental.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.samee.lk.autorental.dto.VehicleDTO;
import org.samee.lk.autorental.models.VehicleModel;
import org.samee.lk.autorental.tm.vehicleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeleteViewController  implements Initializable {
    public TextField vehicleIdInput;

    public Button deleteBtn;
    public Button searchBtn;
    public Button backBtn;

    public TableView<vehicleTM> tblVehicle;
    public Label brandlbl;
    public Label modellbl;
    public Label colorlbl;
    public Label yearlbl;
    public Label qtylbl;
    public Label pricelbl;

    public void handleDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(vehicleIdInput.getText());
        boolean status = VehicleModel.deleteVehicle(id);
        if (status) {
            showSuccessAlert("Vehicle deleted Successfully");
            refreshTable();
        }else {
            showErrorAlert("Vehicle deletion Failed");
        }
    }

    public void handleSearch(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int id  = Integer.parseInt(vehicleIdInput.getText());
        if(vehicleIdInput.getText().equals("")) {
            showErrorAlert("Please enter vehicle ID");
        }
        VehicleDTO vehicleDTO = VehicleModel.vehicleSearchById(id);
        brandlbl.setText(vehicleDTO.getBrand());
        modellbl.setText(vehicleDTO.getModel());
        colorlbl.setText(vehicleDTO.getColor());
        yearlbl.setText(String.valueOf(vehicleDTO.getYear()));
        qtylbl.setText(String.valueOf(vehicleDTO.getQty()));
        pricelbl.setText(String.valueOf(vehicleDTO.getPrice()));
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/mainPage/main-view.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
    }

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


    private void refreshTable() {
        try {
            ArrayList<vehicleTM> tms = VehicleModel.loadVehicle();
            tblVehicle.setItems(FXCollections.observableArrayList(tms));
        } catch (ClassNotFoundException | SQLException e) {
            showErrorAlert("Error refreshing table: " + e.getMessage());
        }
    }
}
