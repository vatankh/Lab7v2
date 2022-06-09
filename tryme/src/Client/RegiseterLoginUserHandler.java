package Client;

import LoginAndRegiester.AbsAuth;
import LoginAndRegiester.Login;
import LoginAndRegiester.Register;

import java.util.Scanner;

 public   class RegiseterLoginUserHandler {
    public static AbsAuth handle(){
        Scanner scanner= new Scanner(System.in);
        System.out.println("to register new account press 1");
        System.out.println("to login to account press 2");
        String val =scanner.nextLine();
        while (val.length() != 1 || (val.charAt(0) != '1' && val.charAt(0) != '2')){
            System.out.println("please choose 1 or 2");
            val=scanner.nextLine();
        }
        System.out.print("user:");
        String user =scanner.nextLine().trim();
        System.out.print("password:");
        String pass = scanner.nextLine().trim();
        if (val.equals("1")){
            return new Register(user,pass);
        }
        else {
            return new Login(user,pass);
        }
    }

}
