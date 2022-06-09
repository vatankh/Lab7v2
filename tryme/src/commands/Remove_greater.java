package commands;

import Client.InputHandler;
import Control.Storage;
import collection.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Remove_greater extends AbsCommand implements Serializable {
    Vehicle vehicle;
    private static final long serialVersionUID = -4507489610617393544L;

    public Remove_greater(Vehicle vehicle){
        this.vehicle=vehicle;
    }

    @Override
    public String work() throws Exception {
        storage.removeGreater(vehicle.getEnginePower());
        Vehicle[] vehiclesArray= storage.getVehiclesArray();
        List<Integer> integerList=new ArrayList<>();
        for (int i=0;i< vehiclesArray.length;i++){
            if (vehicle.getEnginePower() < vehiclesArray[i].getEnginePower()){
                integerList.add(i);
            }
        }
        for (int i=0;i<integerList.size();i++){
            storage.removeAtIndex(integerList.get(i)-i);
        }
        return messege;
    }
}
