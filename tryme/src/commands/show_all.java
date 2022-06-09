package commands;

import collection.Vehicle;

import java.io.Serializable;

public class show_all extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public String work() throws Exception {
        return storage.showAll();
    }
}
