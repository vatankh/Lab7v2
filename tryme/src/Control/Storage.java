package Control;

import collection.Vehicle;
import database.UserHandler;
import database.UserVehiclesHandler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Date;
import java.util.Stack;

public class Storage {
    private Stack<Vehicle> vehicles = new Stack<>();
    public Date date;
    public static long id=1;
    public UserHandler userHandler;
    public UserVehiclesHandler userVehiclesHandler;

    public Storage(Stack<Vehicle> vehicles,UserHandler userHandler){
        this.vehicles=vehicles;
        this.date=new Date(System.currentTimeMillis());

        if (vehicles.size() >1) {
            long max=1;
             max = vehicles.stream().max((a, b) -> {
                return Long.compare(a.getId(), b.getId());
            }).get().getId();
             id =max;
        }
        this.userHandler=userHandler;
        this.userVehiclesHandler=new UserVehiclesHandler(userHandler);

    }

    public Stack<Vehicle> getVehicles() {
        return vehicles;
    }

    public void add(Vehicle vehicle){
         vehicle =userVehiclesHandler.insertVehicle(vehicle);
        vehicles.push(vehicle);
    }
    public void updateId(Vehicle vehicle, int index){
        vehicles.setElementAt(vehicle,index);

    }
    public void removeAtIndex(int index){
        vehicles.removeElementAt(index);
    }
    public Vehicle[] getVehiclesArray(){
        return vehicles.stream().sorted(Comparator.comparingLong(value -> value.getId())).toArray(Vehicle[]::new);
    }
    public void insert_at_index(int index,Vehicle vehicle){
        vehicle=userVehiclesHandler.insertVehicle(vehicle);
        vehicles.insertElementAt(vehicle,index);
    }
    public  int count_greater_than_engine_power(Double power){
        return (int) vehicles.stream().filter(vehicle -> vehicle.getEnginePower() > power).count();
    }

    public String filter_by_engine_power(Double power){
        StringBuilder stringBuilder=new StringBuilder();
        vehicles.stream().filter((vehicle -> vehicle.getEnginePower().equals(power))).sorted(Comparator.comparingLong(value -> value.getId())).forEach((vehicle -> stringBuilder.append(vehicle.toString()).append("\n")));
        return stringBuilder.toString();
    }
    public String filter_greater_than_engine_power(Double power){
        StringBuilder stringBuilder=new StringBuilder();
        vehicles.stream().filter(vehicle -> vehicle.getEnginePower() > power).sorted(Comparator.comparingLong(value -> value.getId())).forEach(vehicle -> stringBuilder.append(vehicle.toString()).append("\n"));
        return stringBuilder.toString();
    }
    public void clear() {
        userVehiclesHandler.clear();
        this.vehicles = new Stack<Vehicle>();
    }
    public void remove_by_id(int id, int index) throws SQLException {
        String query = String.format("DELETE FROM %s WHERE id = ?::bigint;",userHandler.user);
        PreparedStatement preparedStatement =userHandler.connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(id));
        preparedStatement.executeUpdate();
        Vehicle vehicle = vehicles.stream().filter(vehicle1 -> vehicle1.getId() ==id).findFirst().get();
        vehicles.remove(vehicle);
    }
    public void removeFirst() throws SQLException {
        String query=String.format("SELECT * FROM %s WHERE id = (SELECT min (id) FROM %s);",userHandler.user,userHandler.user);
        Statement statement = userHandler.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()){
            Vehicle vehicle= userHandler.getVehicle(resultSet);
             query = String.format("delete  from %s where id =(SELECT min (id) FROM %s);",userHandler.user,userHandler.user);
             statement =userHandler.connection.createStatement();
            statement.executeUpdate(query);
            Vehicle obj=vehicles.stream().filter(vehicle1 -> vehicle1.getId()==vehicle.getId()).findFirst().get();
            vehicles.remove(obj);
            System.out.println("removed succefuly");

        }
    }
    public void removeGreater(double enginePower) throws SQLException {
        String query = String.format("delete  from  %s where engine_power>?;",userHandler.user);
        PreparedStatement preparedStatement =userHandler.connection.prepareStatement(query);
        preparedStatement.setDouble(1,enginePower);
        preparedStatement.executeUpdate();
    }
    public  void  updateById(long id,Vehicle vehicle,long[] index) throws SQLException {
        try {
            String query = String.format("UPDATE %s SET name = ?,coordinate_x =?,coordinate_y=?,creation_date=date(now()),engine_power=?,vehicle_type=?,fuel_type=? WHERE id = ?;",userHandler.user);
            PreparedStatement preparedStatement=userHandler.connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setLong(2,vehicle.getCoordinates().getX());
            preparedStatement.setInt(3,vehicle.getCoordinates().getY());
            preparedStatement.setDouble(4,vehicle.getEnginePower());
            preparedStatement.setString(5,vehicle.getType().name());
            preparedStatement.setString(6,vehicle.getFuelType().name());
            preparedStatement.setLong(7,id);
            preparedStatement.executeUpdate();
            query = String.format("select * from %s where id=?",userHandler.user);
            preparedStatement =userHandler.connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                vehicle= userHandler.getVehicle(resultSet);
                updateId(vehicle, (int) index[0]);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public String showAll() throws SQLException {
        PreparedStatement preparedStatement = userHandler.connection.prepareStatement("SELECT * FROM users");
        ResultSet users = preparedStatement.executeQuery();
        StringBuilder result = new StringBuilder();
        try {
            while (users.next()) {
                String user = users.getString("username");
                result.append("     "+ user+"     "+"\n");
                String query=String.format("SELECT * FROM %s",user);
                Statement statement = userHandler.connection.createStatement();
                ResultSet vehicles =statement.executeQuery(query);;
                while (vehicles.next()){
                    Vehicle vehicle = userHandler.getVehicle(vehicles);
                    result.append(vehicle.toString() +"\n");
                }
            }

        }catch (Exception e){

            System.out.println(e.getMessage());
            System.out.println("user="+users.getString("username"));
        }
        return result.toString();
    }
}
