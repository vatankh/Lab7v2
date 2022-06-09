package commands;

import Client.InputHandler;
import Control.Storage;
import collection.Vehicle;

import java.io.Serializable;

public class Add extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;
    Vehicle vehicle;
    public Add(Vehicle vehicle){
        this.vehicle=vehicle;
    }

    @Override
    public String work() {
        storage.add(vehicle);
        return messege;
    }
}
