package LoginAndRegiester;

import java.io.Serializable;

public class Register extends AbsAuth implements Serializable {

    public Register(String user, String pass) {
        super(user, pass);
    }

    @Override
    public String work() {
        return userHandler.registerUser(user,pass);
    }
}
