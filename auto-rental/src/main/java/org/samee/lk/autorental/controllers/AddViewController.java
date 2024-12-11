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

public class AddViewController implements Initializable {

    public TextField vehicleBrandInput;
    public TextField vehicleModelInput;
    public TextField vehicleColorInput;
    public TextField vehicleYearInput;
    public TextField vehicleQtyInput;
    public TextField vehiclePriceInput;
    public Button saveBtn;
    public Button backBtn;
    public TableView<vehicleTM> tblVehicle;

    public void handleSave(ActionEvent actionEvent) {
        try {

            String brand = vehicleBrandInput.getText();
            String model = vehicleModelInput.getText();
            String color = vehicleColorInput.getText();
            String yearText = vehicleYearInput.getText();
            String qtyText = vehicleQtyInput.getText();
            String priceText = vehiclePriceInput.getText();

            if (brand.isEmpty() || model.isEmpty() || color.isEmpty() ||
                    yearText.isEmpty() || qtyText.isEmpty() || priceText.isEmpty()) {
                showErrorAlert("All fields must be filled out.");
                return;
            }


            int year;
            int qty;
            double price;
            try {
                year = Integer.parseInt(yearText);
                if (year < 1900 || year > 2100) {
                    showErrorAlert("Year must be between 1900 and 2100.");
                    return;
                }

                qty = Integer.parseInt(qtyText);
                if (qty <= 0) {
                    showErrorAlert("Quantity must be a positive number.");
                    return;
                }

                price = Double.parseDouble(priceText);
                if (price <= 0) {
                    showErrorAlert("Price must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorAlert("Year, Quantity, and Price must be numeric values.");
                return;
            }


            VehicleDTO vehicle = new VehicleDTO(brand, model, color, year, qty, price);
            boolean status = VehicleModel.addVehicle(vehicle);

            if (status) {
                showSuccessAlert(brand + " " + model + " added successfully.");
                clearInputs();
                refreshTable();
            } else {
                showErrorAlert("Failed to add vehicle. Please try again.");
            }

        } catch (Exception e) {
            showErrorAlert("An unexpected error occurred: " + e.getMessage());
        }




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

        private void clearInputs() {
        vehicleBrandInput.clear();
        vehicleModelInput.clear();
        vehicleColorInput.clear();
        vehicleYearInput.clear();
        vehicleQtyInput.clear();
        vehiclePriceInput.clear();
    }


    public void handleBack(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/mainPage/main-view.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
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
