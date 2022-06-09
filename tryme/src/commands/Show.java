package commands;
import collection.Vehicle;

import java.io.Serializable;

public class Show extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public String work() throws Exception {
        Vehicle[] array = storage.getVehiclesArray();
        StringBuilder stringBuilder =new StringBuilder("");
        for (Vehicle vehicle :
                array) {
            stringBuilder.append(vehicle.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
