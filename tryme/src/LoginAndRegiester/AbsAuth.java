package LoginAndRegiester;

import collection.Vehicle;
import database.UserHandler;
import utils.AbsTransfer;

import java.io.Serializable;
import java.util.Stack;

public abstract class AbsAuth extends AbsTransfer implements Serializable {
    String user;
    String pass;
    UserHandler userHandler;
    Stack<Vehicle> vehicleStack=new Stack<Vehicle>();
    private static final long serialVersionUID = -4507489610617393544L;

    public AbsAuth(String user , String pass){
        this.user=user;
        this.pass=pass;
    }
    public void loadUserHandler(UserHandler userHandler){
        this.userHandler=userHandler;
    }
    public abstract String work();

    public Stack<Vehicle> getVehicleStack() {
        return vehicleStack;
    }
}
