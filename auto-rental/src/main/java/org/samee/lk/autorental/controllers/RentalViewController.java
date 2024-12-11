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
import javafx.stage.Window;
import org.samee.lk.autorental.dto.OrderDTO;
import org.samee.lk.autorental.dto.OrderDetailsDTO;
import org.samee.lk.autorental.dto.VehicleDTO;
import org.samee.lk.autorental.models.OrderModel;
import org.samee.lk.autorental.models.VehicleModel;
import org.samee.lk.autorental.tm.orderTM;
import org.samee.lk.autorental.tm.vehicleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.print.PrinterJob;

public class RentalViewController implements Initializable {
    public TextField vehicleIdInput;
    public Button orderBtn;
    public Button searchBtn;
    public Button backBtn;
    public TableView<vehicleTM> tblVehicle;
    public Label brandlbl;
    public Label modellbl;
    public Label colorlbl;
    public Label yearlbl;
    public Label qtylbl;
    public Label pricelbl;
    public TextField qtyInput;
    public TableView<orderTM> tblOrder;
    public Label totalLbl;
    public Button addBtn;
    public Button billBtn;

    private List<orderTM> orderTMS = null;
    private ArrayList<OrderDetailsDTO> orderDetailDtos = null;

    private double subTotal = 0;

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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        tblVehicle.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblVehicle.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tblVehicle.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("model"));
        tblVehicle.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("color"));
        tblVehicle.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblVehicle.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblVehicle.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("price"));

        tblVehicle.setItems(FXCollections.observableArrayList(tms));

        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("brand"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("model"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("color"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTMS = new ArrayList<>();
        orderDetailDtos = new ArrayList<>();
        tblOrder.setItems(FXCollections.observableArrayList(orderTMS));
    }

    public void handleSearch(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(vehicleIdInput.getText());
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

    public void handleAddCart(ActionEvent actionEvent) {
        String brand = brandlbl.getText();
        String model = modellbl.getText();
        String color = colorlbl.getText();
        int year = Integer.parseInt(yearlbl.getText());
        int qty = Integer.parseInt(qtylbl.getText());
        double unitPrice = Double.parseDouble(pricelbl.getText());
        int orderQty = Integer.parseInt(qtyInput.getText());

        if (orderQty > qty) {
            showErrorAlert("Please enter a valid order quantity");
            return;
        }

        double total = unitPrice * orderQty;

        orderTMS.add(new orderTM(brand, model, color, year, orderQty, unitPrice, total));
        tblOrder.setItems(FXCollections.observableArrayList(orderTMS));

        int id = Integer.parseInt(vehicleIdInput.getText());
        orderDetailDtos.add(new OrderDetailsDTO(id, orderQty, total));
        subTotal += total;
        int newQty = qty - orderQty;
        qtylbl.setText(String.valueOf(newQty));
        qtyInput.clear();
        totalLbl.setText(String.valueOf(subTotal));
    }

    public void handlePlaceOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = dateFormat.format(date);
        boolean status = OrderModel.placeOrder(new OrderDTO(format, subTotal, orderDetailDtos));

        if (status) {
            showSuccessAlert("Order Placed Successfully");
            refreshTable();
           handleGenerateBill(actionEvent);
        } else {
            showErrorAlert("Order Placed Failed");
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

    private void refreshTable() {
        try {
            ArrayList<vehicleTM> tms = VehicleModel.loadVehicle();
            tblVehicle.setItems(FXCollections.observableArrayList(tms));
        } catch (ClassNotFoundException | SQLException e) {
            showErrorAlert("Error refreshing table: " + e.getMessage());
        }
    }

    public void handleGenerateBill(ActionEvent actionEvent) {
        // Step 1: Generate the bill content
        StringBuilder bill = new StringBuilder();
        bill.append("========= Auto Rental Bill =========\n");
        bill.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append("\n");
        bill.append("-------------------------------------------------\n");

// Adjusted column width for better portrait view
        bill.append(String.format("%-15s %-10s %-10s %-6s %-6s %-10s\n", "Brand", "Model", "Color", "Year", "Qty", "Total"));
        bill.append("-------------------------------------------------\n");

        for (orderTM order : orderTMS) {
            // Ensure numbers are right-aligned for better readability in portrait view
            bill.append(String.format(
                    "%-15s %-10s %-10s %-6d %-6d %10.2f\n",
                    order.getBrand(),
                    order.getModel(),
                    order.getColor(),
                    order.getYear(),
                    order.getQty(),
                    order.getTotalPrice()
            ));
        }

        bill.append("-------------------------------------------------\n");
// Right-align subtotal for a cleaner finish
        bill.append(String.format("Subtotal: %10.2f\n", subTotal));
        bill.append("========= Thank You! =============\n");

        // Step 2: Display the bill in a dialog
        TextArea textArea = new TextArea(bill.toString());
        textArea.setEditable(false);
        textArea.setStyle("-fx-font-family: 'roboto'; -fx-font-size: 14px; -fx-padding: 10px;");
        textArea.setPrefWidth(200); // Adjust as needed
        textArea.setPrefHeight(300); // Adjust as needed

        Alert billPreview = new Alert(Alert.AlertType.INFORMATION);
        billPreview.setTitle("Bill Preview");
        billPreview.setHeaderText("Generated Bill");
        billPreview.getDialogPane().setContent(textArea);
        billPreview.getDialogPane().setMinHeight(400); // Adjust as needed
        billPreview.getDialogPane().setMinWidth(400);
        ButtonType printButton = new ButtonType("Print", ButtonBar.ButtonData.OK_DONE);
        billPreview.getButtonTypes().setAll(printButton, ButtonType.CLOSE);

        Optional<ButtonType> result = billPreview.showAndWait();
        if (result.isPresent() && result.get() == printButton) {
            printBill(textArea);
        }
    }

    public void printBill(TextArea textArea) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {
            boolean success = printerJob.printPage(textArea);
            if (success) {
                printerJob.endJob();
            } else {
                System.out.println("Failed to print the page.");
            }
        } else {
            System.out.println("Failed to create printer job.");
        }
    }
}
