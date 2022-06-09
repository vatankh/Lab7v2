package database;

import collection.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class UserVehiclesHandler {
    UserHandler userHandler;
    public UserVehiclesHandler(UserHandler userHandler){
        this.userHandler=userHandler;
    }
    public Vehicle insertVehicle(Vehicle vehicle) {
        Statement statement;
        try {
            String query = String.format("insert into %s(name, coordinate_x, coordinate_y, engine_power, vehicle_type, fuel_type) values ('%s','%s','%s','%s','%s','%s');",
                    userHandler.user
                    ,vehicle.getName()
                    , vehicle.getCoordinates().getX()
                    , vehicle.getCoordinates().getY()
                    ,vehicle.getEnginePower()
                    , vehicle.getType().name(),
                    vehicle.getFuelType().name());
            statement = userHandler.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("inserted succefuly");
            query=String.format("SELECT * FROM %s WHERE id = (SELECT MAX (id) FROM %s);",userHandler.user,userHandler.user);
            statement = userHandler.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                  return userHandler.getVehicle(resultSet);
            }

        } catch (Exception e) {
            System.out.println("exiption in insert to database");
            System.out.println(e.getMessage());
        }
        return null;
    }
    public  void  update_by_id(Vehicle vehicle , long id){
        Statement statement;
        try {
            String query = String.format("update %s set  name='%s', engine_power='%s' , coordinate_x='%s', coordinate_y='%s',vehicle_type='%s',fuel_type='%s'  where id='%s';"
                    ,userHandler.user,vehicle.getName(),vehicle.getEnginePower(),vehicle.getCoordinates().getX(),vehicle.getCoordinates().getY(),vehicle.getType().name(),vehicle.getFuelType().name(),id);
            statement=userHandler.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("updated successfully");
        }catch (Exception e){
            System.out.println("error in update by id in database");
            System.out.println(e.getMessage());
        }
    }
    public  void clear(){
        Statement statement;
        try {
            String query=String.format("truncate %s",userHandler.user);
            statement=userHandler.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("cleared successfully ");

        }catch (Exception e){
            System.out.println("error in clear database");
            System.out.println(e.getMessage());
        }
    }
    public void saveCommandsVehicle( Stack<Vehicle> newStack){
        List<Vehicle> newList=newStack.stream().sorted(Comparator.comparingLong((Vehicle::getId))).collect(Collectors.toList());
        clear();
        newList.forEach(this::insertVehicle);
        System.out.println("finished");
    }


}
