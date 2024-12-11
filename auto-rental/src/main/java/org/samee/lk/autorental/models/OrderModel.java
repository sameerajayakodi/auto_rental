package org.samee.lk.autorental.models;

import org.samee.lk.autorental.dto.OrderDTO;
import org.samee.lk.autorental.dto.OrderDetailsDTO;

import java.sql.*;

public class OrderModel {
    public static boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_rental","root", "acpt");
        connection.setAutoCommit(false);
        
        //insert data
        PreparedStatement stm1 = connection.prepareStatement("INSERT INTO orders(order_date,amount) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        stm1.setObject(1,orderDTO.getOrderDate());
        stm1.setObject(2,orderDTO.getSubTotal());


        int orderSave = stm1.executeUpdate();
        if(orderSave >= 0){
            //get order Id from generated keys
            ResultSet generatedKeys = stm1.getGeneratedKeys();
            if(generatedKeys.next()){
                int id = generatedKeys.getInt(1);
                //save details into order details dto
                for(OrderDetailsDTO dto: orderDTO.getOrderDetailDTO()){
                    PreparedStatement stm2 = connection.prepareStatement("insert INTO order_details(oid,vid,qty,price) values(?,?,?,?) ");
                    stm2.setObject(1,id);
                    stm2.setObject(2,dto.getVehicleId());
                    stm2.setObject(3,dto.getOrderQty());
                    stm2.setObject(4,dto.getTotal());

                    int orderDetailsSave = stm2.executeUpdate();
                    if(orderDetailsSave > 0){
                        PreparedStatement stm3 = connection.prepareStatement("UPDATE vehicle SET qty=qty=? where id=?");
                        stm3.setObject(1,dto.getOrderQty());
                        stm3.setObject(2,dto.getVehicleId());

                        int updateVehicleStatus = stm3.executeUpdate();
                        if(updateVehicleStatus <= 0){
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return false;
                        }
                    }else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }
            }connection.commit();
            connection.setAutoCommit(true);


            return  true;

        }else{
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }


    }
}
