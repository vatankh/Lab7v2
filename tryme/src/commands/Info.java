package commands;


import java.io.Serializable;

public class Info extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public String work() throws Exception {
        messege= "type="+storage.getVehicles().getClass().getName()+"\n" +
                "date="+storage.date+"\n" +
                "number Of Elements="+ storage.getVehicles().toArray().length+"\n";
        return messege;

    }
}
