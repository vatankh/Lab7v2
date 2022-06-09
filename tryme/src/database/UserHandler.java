package database;

import collection.Coordinates;
import collection.FuelType;
import collection.Vehicle;
import collection.VehicleType;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Stack;

public class UserHandler {
    public Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String GET_USERS="select  * from users;";
    public String user;
    public UserHandler(Connection connection){
        this.connection=connection;
    }
    public LoginObj loginUser(String user, String pass) {
        Statement statement;
        String message="";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_USERS);
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(user)) {
                    if (resultSet.getString("password").equals(SHA1Tool.encryptPassToSHA1(pass))){
                        System.out.println();
                        message="successfully connected to "+user;
                        this.user=user;
                        return new LoginObj(getVehicles(user),message);
                    }else {
                        message ="wrong password";
                        return new LoginObj(null,message);
                    }
                }
            }
            message="user does not exist";
             return new LoginObj(null,message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message="error";
            return new LoginObj(null,message);
        }
    }

    private Stack<Vehicle> getVehicles( String tableName){
        Statement statement;
        ResultSet rs=null;
        Stack<Vehicle> vehicleStack =new Stack<>();
        try {
            String query =String.format("select * from %s",tableName);
            statement=connection.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                vehicleStack.push(getVehicle(rs));
            }
            System.out.println("finished succefuly");

        }catch (Exception e){
            System.out.println("cannot get vechiles method");
            System.out.println(e.getMessage());
        }
        return vehicleStack;
    }
    public Vehicle getVehicle(ResultSet rs) throws SQLException {
        String name =rs.getString("name");
        Long x =rs.getLong("coordinate_x");
        Integer y =rs.getInt("coordinate_y");
        Double ep =rs.getDouble("engine_power");
        VehicleType vt= VehicleType.valueOf(rs.getString("vehicle_type"));
        FuelType fl=FuelType.valueOf(rs.getString("fuel_type"));
        Long id = rs.getLong("id");
        Date date = rs.getDate("creation_date");
        return new Vehicle(id,name,new Coordinates(x,y),date,ep,vt,fl);
    }


    public String registerUser(String user,  String pass) {
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet =statement.executeQuery(GET_USERS);
            while (resultSet.next()){
                if (resultSet.getString("username").equals(user)){
                    return "user already exist ";
                }
            }

            PreparedStatement ps =connection.prepareStatement(ADD_USER_REQUEST);
            ps.setString(1,user);
            ps.setString(2,SHA1Tool.encryptPassToSHA1(pass));
            int  result =ps.executeUpdate();
            this.user=user;
            initializeUser(user);
            return   "executed successfully ="+result;
        } catch (Exception e) {
            System.out.println("exception in insert to database");
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    private String initializeUser(String username){
        try {
            Statement statement =connection.createStatement();
            String query =String.format("create table %s(id bigserial  not null constraint %s_pk primary key," +
                    "name text not null," +
                    "coordinate_x  integer not null," +
                    "coordinate_y  integer not null," +
                    "creation_date date default date(now()) not null," +
                    "engine_power  double precision not null," +
                    "vehicle_type  varchar(20) not null," +
                    "fuel_type varchar(20) not null" +
                    ");",username,username);
            int res =statement.executeUpdate(query);
            System.out.println("created table successfully="+res);
            return username;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "-1";
        }

    }

    public  void logoutUser(){
        user=null;
    }


}
