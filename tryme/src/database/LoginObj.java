package database;

import collection.Vehicle;

import java.util.Stack;

public class LoginObj {
    public Stack<Vehicle> stack;
    public String massage;
    public LoginObj(Stack<Vehicle> stack,String massage){
        this.stack=stack;
        this.massage=massage;
    }
}
