package Servier;

import LoginAndRegiester.AbsAuth;
import commands.AbsCommand;
import javafx.util.Pair;
import utils.AbsTransfer;

import java.net.SocketAddress;
import java.util.Queue;

 public class  ReciverFromClient {
 public  static void startReciving(Queue<Pair<AbsTransfer,SocketAddress>> queue, ServerNetworkUdp2 serverNetworkUdp2) throws ClassNotFoundException {
     while (true){
        Object obj = serverNetworkUdp2.receiveObj();
        if (obj instanceof AbsTransfer){
            //send obj
            AbsTransfer transfer = (AbsTransfer) obj;
            Pair<AbsTransfer, SocketAddress> pair =new Pair<>(transfer, serverNetworkUdp2.client);
            queue.add(pair);
        }

     }
 }
}
