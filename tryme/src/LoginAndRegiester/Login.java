package LoginAndRegiester;

import database.LoginObj;

import java.io.Serializable;

public class Login extends AbsAuth implements Serializable {


    public Login(String user, String pass) {
        super(user, pass);
    }

    @Override
    public String work() {
        LoginObj loginObj =userHandler.loginUser(user,pass);
        vehicleStack=loginObj.stack;
        return loginObj.massage;
    }
}
