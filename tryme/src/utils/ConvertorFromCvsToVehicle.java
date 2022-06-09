package utils;

import collection.Coordinates;
import collection.FuelType;
import collection.Vehicle;
import collection.VehicleType;

import java.util.Date;

public class ConvertorFromCvsToVehicle  {
    static Vehicle convert(String line){
        String[] fields = line.split(",");
        Coordinates coordinates =new Coordinates(Long.valueOf(fields[0]),Integer.valueOf(fields[1]));
        VehicleType vehicleType = VehicleType.valueOf(fields[2]);
        FuelType fuelType=FuelType.valueOf(fields[3]);
        long id =Long.parseLong(fields[4]);
        String name= fields[5];
        String[] date = fields[6].split("-");
        Date creationDate=new Date(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        Double enginePower= Double.parseDouble(fields[7]);
        return new Vehicle(id,name,coordinates,creationDate,enginePower,vehicleType,fuelType);
    }


}
