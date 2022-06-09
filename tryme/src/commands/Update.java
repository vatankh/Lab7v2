package commands;

import Control.VerifyIdInStorage;
import collection.Vehicle;

import java.io.Serializable;


public class Update extends AbsCommand implements Serializable {
    Vehicle vehicle;
    String id;
    public Update(Vehicle vehicle,String id){
        this.vehicle=vehicle;
        this.id =id;
    }

    @Override
    public String work() throws Exception {
        long[] index = VerifyIdInStorage.verify(storage,id);
        if (index[0] ==-1){
            return "there is no vehicle with this id";
        }else {
            vehicle.setId(index[1]);
            storage.updateById(Long.parseLong(id),vehicle,index);
            return messege;
        }
    }
}
