package utils;

import collection.Vehicle;

import java.text.SimpleDateFormat;

public class ConvertorFromVehicleToCvs {
    public static String convert(Vehicle vehicle){
        StringBuilder stringBuilder =new StringBuilder();
        String stringDate = new SimpleDateFormat("dd-MM-yyyy").format(vehicle.getCreationDate());
        stringBuilder.append(vehicle.getCoordinates().getX()+","
        +vehicle.getCoordinates().getY() +","
        +vehicle.getType().toString() +","
        +vehicle.getFuelType().toString() +","
        +vehicle.getId() +","
        +vehicle.getName()+","
        +stringDate +","
        +vehicle.getEnginePower() + "\n");
        return stringBuilder.toString();

    }
}
