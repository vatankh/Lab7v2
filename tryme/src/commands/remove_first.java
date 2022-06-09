package commands;
import java.io.Serializable;

public class remove_first extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public String work() throws Exception {
        if (storage.getVehiclesArray().length ==0){
            return "stack is already empty";
        }else {
            storage.removeFirst();
            return messege;
        }
    }
}
