package utils;

import collection.Vehicle;

import java.util.Stack;

public class CvsReader {
        public  static  Stack<Vehicle> getInStack(String fileData){
            Stack<Vehicle> vehicles=new Stack<>();
        try {
            if (fileData.isEmpty()){
                return vehicles;
            }
            String[] data = fileData.split("\n");
            for (String line :
                    data) {
                if (line.split(",").length !=8){
                    System.out.println("can not read file ,the file have  another data already");
                    System.out.println("choose another file please");
                    return vehicles;
                }
                if (!line.isEmpty()) {
                    Vehicle vehicle = ConvertorFromCvsToVehicle.convert(line);
                    vehicles.push(vehicle);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }


}
//    StringBuilder builder =new StringBuilder("");
//            int t;
//            while((t=in_strm.read())!= -1){
//                char letter =(char) t;
//                builder.append(letter);
//            }