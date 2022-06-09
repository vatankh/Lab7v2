package commands;

import LoginAndRegiester.AbsAuth;

import java.io.*;

public class Exit extends AbsCommand implements Serializable {
    AbsAuth auth;
    public Exit(AbsAuth auth){
        this.auth=auth;
    }
    @Override
    public String work() throws Exception {
        storage.userVehiclesHandler.saveCommandsVehicle(storage.getVehicles());
        return messege;
    }
}
