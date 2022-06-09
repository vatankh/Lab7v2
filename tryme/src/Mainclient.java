import Client.Client;
import Client.RegiseterLoginUserHandler;
import LoginAndRegiester.AbsAuth;

import java.net.DatagramSocket;
import java.util.Scanner;

public class Mainclient {
    public static void main(String[] args) throws Exception {
        String fileName;
        if (args.length >0){
            fileName=args[0];
            System.out.println(fileName);
        }else {
            fileName="mydata.csv";
        }
        Scanner scanner = new Scanner(System.in);
        DatagramSocket datagramSocket =new DatagramSocket();
        datagramSocket.setSoTimeout(5000);
        AbsAuth auth = RegiseterLoginUserHandler.handle();
        Client client =new Client(datagramSocket,scanner,auth);
        client.start();

    }







}
