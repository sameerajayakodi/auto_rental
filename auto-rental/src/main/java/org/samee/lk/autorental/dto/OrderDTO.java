package org.samee.lk.autorental.dto;

import java.util.ArrayList;

public class OrderDTO {
    public OrderDTO(String orderDate, double subTotal, ArrayList<OrderDetailsDTO> orderDetailDTO) {
        this.setOrderDate(orderDate);
        this.setSubTotal(subTotal);
        this.setOrderDetailDTO(orderDetailDTO);
    }

    private String orderDate;
    private double subTotal;
    private ArrayList<OrderDetailsDTO> orderDetailDTO;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public ArrayList<OrderDetailsDTO> getOrderDetailDTO() {
        return orderDetailDTO;
    }

    public void setOrderDetailDTO(ArrayList<OrderDetailsDTO> orderDetailDTO) {
        this.orderDetailDTO = orderDetailDTO;
    }
}
