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

public class UpdateViewController implements Initializable {
    public TextField vehicleBrandInput;
    public TextField vehicleModelInput;
    public TextField vehicleColorInput;
    public TextField vehicleYearInput;
    public TextField vehicleQtyInput;
    public TextField vehiclePriceInput;
    public Button updateBtn;
    public TextField vehicleIdInput;
    public Button searchBtn;

    public TableView<vehicleTM> tblVehicle;
    public Button backBtn;


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


    public void handleSearch(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int id  = Integer.parseInt(vehicleIdInput.getText());
        if(vehicleIdInput.getText().equals("")) {
            showErrorAlert("Please enter vehicle ID");
        }
        VehicleDTO vehicleDTO = VehicleModel.vehicleSearchById(id);
        vehicleBrandInput.setText(vehicleDTO.getBrand());
        vehicleModelInput.setText(vehicleDTO.getModel());
        vehicleColorInput.setText(vehicleDTO.getColor());
        vehicleYearInput.setText(String.valueOf(vehicleDTO.getYear()));
        vehicleQtyInput.setText(String.valueOf(vehicleDTO.getQty()));
        vehiclePriceInput.setText(String.valueOf(vehicleDTO.getPrice()));

    }


    public void handleUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            String idText = vehicleIdInput.getText().trim();
            if (idText.isEmpty()) {
                throw new IllegalArgumentException("Vehicle ID cannot be empty.");
            }
            int id = Integer.parseInt(idText);
            if (id <= 0) {
                throw new IllegalArgumentException("Vehicle ID must be a positive number.");
            }


            String brand = vehicleBrandInput.getText().trim();
            if (brand.isEmpty()) {
                throw new IllegalArgumentException("Vehicle brand cannot be empty.");
            }


            String model = vehicleModelInput.getText().trim();
            if (model.isEmpty()) {
                throw new IllegalArgumentException("Vehicle model cannot be empty.");
            }


            String color = vehicleColorInput.getText().trim();
            if (color.isEmpty()) {
                throw new IllegalArgumentException("Vehicle color cannot be empty.");
            }


            String yearText = vehicleYearInput.getText().trim();
            if (yearText.isEmpty()) {
                throw new IllegalArgumentException("Vehicle year cannot be empty.");
            }
            int year = Integer.parseInt(yearText);
            int currentYear = java.time.Year.now().getValue();
            if (year < 1886 || year > currentYear) { // 1886 is the year the first car was invented
                throw new IllegalArgumentException("Vehicle year must be between 1886 and the current year.");
            }


            String qtyText = vehicleQtyInput.getText().trim();
            if (qtyText.isEmpty()) {
                throw new IllegalArgumentException("Vehicle quantity cannot be empty.");
            }
            int qty = Integer.parseInt(qtyText);
            if (qty <= 0) {
                throw new IllegalArgumentException("Vehicle quantity must be a positive number.");
            }


            String priceText = vehiclePriceInput.getText().trim();
            if (priceText.isEmpty()) {
                throw new IllegalArgumentException("Vehicle price cannot be empty.");
            }
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                throw new IllegalArgumentException("Vehicle price cannot be negative.");
            }


            VehicleDTO vehicle = new VehicleDTO(id, brand, model, color, year, qty, price);
            boolean status = VehicleModel.updateVehicle(vehicle);

            if (status) {
                showSuccessAlert("Vehicle updated successfully");
                refreshTable();
            } else {
                showErrorAlert("Vehicle could not be updated");
            }

        } catch (NumberFormatException e) {
            showErrorAlert("Invalid number format. Please check the inputs.");
        } catch (IllegalArgumentException e) {
            showErrorAlert(e.getMessage());
        } catch (Exception e) {
            showErrorAlert("An unexpected error occurred: " + e.getMessage());
        }



    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        Parent mainPageRoot = FXMLLoader.load(getClass().getResource("/org/samee/lk/autorental/mainPage/main-view.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();

        stage.setScene(new Scene(mainPageRoot));

        stage.show();
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
