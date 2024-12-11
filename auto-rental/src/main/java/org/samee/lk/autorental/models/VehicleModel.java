package org.samee.lk.autorental.models;

import org.samee.lk.autorental.dto.VehicleDTO;
import org.samee.lk.autorental.tm.vehicleTM;

import java.sql.*;
import java.util.ArrayList;

public class VehicleModel {
public  static ArrayList<vehicleTM> loadVehicle() throws ClassNotFoundException, SQLException {
Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_rental","root", "acpt");
    PreparedStatement preparedStatement = connection.prepareStatement("select * from vehicle");
   ResultSet resultSet =  preparedStatement.executeQuery();
    ArrayList<vehicleTM> tms = new ArrayList();
   while (resultSet.next()) {
     tms.add(new vehicleTM(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getDouble(7)));
   }

   return tms;


}


public  static  boolean addVehicle(VehicleDTO vehicle) throws ClassNotFoundException, SQLException {
boolean status = false;

    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_rental","root", "acpt");
    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO vehicle(brand ,model ,color , year , qty , price) VALUES ( ?, ?, ?, ?, ?, ?)");
    preparedStatement.setObject(1,vehicle.getBrand());
    preparedStatement.setObject(2,vehicle.getModel());
    preparedStatement.setObject(3,vehicle.getColor());
    preparedStatement.setObject(4,vehicle.getYear());
    preparedStatement.setObject(5,vehicle.getQty());
    preparedStatement.setObject(6,vehicle.getPrice());
    int i = preparedStatement.executeUpdate();

    if(i>0){
        status = true;
    }else {
        return false;

    }

    return status;
}

public  static  boolean updateVehicle(VehicleDTO vehicle) throws ClassNotFoundException, SQLException {

    boolean status = false;

    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_rental","root", "acpt");
    PreparedStatement preparedStatement  = connection.prepareStatement("update vehicle set brand=?,model=?,color=?,year=?,qty=?,price=? where id=?");
    preparedStatement.setObject(1,vehicle.getBrand());
    preparedStatement.setObject(2,vehicle.getModel());
    preparedStatement.setObject(3,vehicle.getColor());
    preparedStatement.setObject(4,vehicle.getYear());
    preparedStatement.setObject(5,vehicle.getQty());
    preparedStatement.setObject(6,vehicle.getPrice());
    preparedStatement.setInt(7,vehicle.getId());

    int i = preparedStatement.executeUpdate();
    if(i>0){
        status = true;
    }
    return status;
}
public  static  boolean deleteVehicle(int id) throws ClassNotFoundException, SQLException {
    boolean status = false;
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_rental","root", "acpt");
    PreparedStatement preparedStatement = connection.prepareStatement("delete from vehicle where id=?");
    preparedStatement.setInt(1,id);
    int i = preparedStatement.executeUpdate();
    if(i>0){
        status = true;
    }
    return status;
}

public static VehicleDTO vehicleSearchById(int id) throws ClassNotFoundException, SQLException {
    VehicleDTO vehicleDTO = null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_rental","root", "acpt");
    PreparedStatement preparedStatement = connection.prepareStatement("select * from vehicle where id = ?");
    preparedStatement.setInt(1,id);
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
        vehicleDTO = new VehicleDTO(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6), resultSet.getDouble(7));
    }
    return vehicleDTO;

}


}
