package commands;

import Client.InputHandler;
import Control.Storage;
import collection.Vehicle;
import utils.Parser;

import java.io.Serializable;

public class Insert_at extends AbsCommand implements Serializable {
    String index;
    Vehicle vehicle;
    private static final long serialVersionUID = -4507489610617393544L;


    public Insert_at(String  index,Vehicle vehicle){
        this.index=index;
        this.vehicle=vehicle;
    }

    @Override
    public String work() {
        String text=Parser.ParseInt(index);
        if (text.equals("")) {
            int intIndex=Integer.parseInt(index);
            Vehicle[] vehicles = storage.getVehiclesArray();
            if (intIndex > vehicles.length){
                return "index is bigget than stack choose a smaller index";
            }else if (intIndex == vehicles.length){
                storage.add(vehicle);
                return messege;
            }else if (intIndex < vehicles.length){
                storage.insert_at_index(intIndex,vehicle);
                return messege;
            }else {
                return "something is wrong in insert at";
            }
        }else {
            return text;
        }
    }
}
