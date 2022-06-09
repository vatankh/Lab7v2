package Client;
import LoginAndRegiester.AbsAuth;
import commands.*;
import java.net.*;
import java.util.Scanner;

public class Client<T extends AbsCommand> {
    DatagramSocket socket;
    ClientNetworkUdp networkUdp;
    CommandHandler commandHandler;
    Scanner scanner;
    AbsAuth auth;


    public Client(DatagramSocket datagramSocket,Scanner scanner,AbsAuth auth)  {
        this.socket=datagramSocket;
        this.networkUdp=new ClientNetworkUdp(socket);
        this.scanner=scanner;
        this.auth=auth;
        this.commandHandler=new CommandHandler(networkUdp,this.scanner,auth);
    }

    public void start() throws Exception {
        HandelObject work=new HandelObject(true,true);
        boolean executing_script=false;
        networkUdp.sendobj(auth);
        String me=null;
        while (me == null) {
            me = (String) networkUdp.reciveObj();
        }
       while (!me.contains("successfully")){
           System.out.println(me);
           AbsAuth auth = RegiseterLoginUserHandler.handle();
           networkUdp.sendobj(auth);
           Object obj = null;
           while (obj==null){
               obj=networkUdp.reciveObj();
           }
           me=(String) obj;
       }
        System.out.println("passed");

           while (work.work){
               if (scanner.hasNext()){
                   String command =scanner.nextLine();
                   work=commandHandler.handle(command);
                   if (work.Continue){
                       if (work.inputStream != null){
                           executing_script=true;
                           scanner=new Scanner(work.inputStream);
                           commandHandler =new CommandHandler(networkUdp,scanner,auth);
                       }else{
                           String message = String.valueOf(networkUdp.reciveObj());
                           if (!message.equals("null")){
                               System.out.println(message);
                           }else {
                               System.out.println("exiting");
                               work.work=false;
                           }
                       }
                   }
               }
               else if (executing_script) {
                   scanner =new Scanner(System.in);
                   commandHandler=new CommandHandler(networkUdp,scanner,auth);
                   executing_script=false;
               }
           }


    }
}
