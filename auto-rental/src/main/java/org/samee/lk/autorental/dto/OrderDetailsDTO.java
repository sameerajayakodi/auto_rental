package org.samee.lk.autorental.dto;

public class OrderDetailsDTO {
    private int vehicleId;
    private int orderQty;
    private double total;

    public OrderDetailsDTO(int vehicleId, int orderQty, double total) {
        this.vehicleId = vehicleId;
        this.orderQty = orderQty;
        this.total = total;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
