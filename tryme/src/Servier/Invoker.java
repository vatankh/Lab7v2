package Servier;

import Control.Storage;
import LoginAndRegiester.AbsAuth;
import commands.AbsCommand;
import database.UserHandler;
import javafx.util.Pair;
import utils.AbsTransfer;

import java.net.SocketAddress;

public class Invoker {
    String message;
    UserHandler userHandler;
    Storage storage;
    public Invoker(String message,UserHandler userHandler, Storage storage){
        this.message=message;
        this.userHandler=userHandler;
        this.storage=storage;
    }

    public String getReceiver() {
        return message;
    }

    public String execute(Pair<AbsTransfer, SocketAddress> request,ServerNetworkUdp2 networkUdp2) throws Exception {
        AbsTransfer obj = request.getKey();
        SocketAddress theClient= request.getValue();
        networkUdp2.client=theClient;
        if (obj instanceof AbsCommand) {
            AbsCommand command = (AbsCommand) obj;
            command.loadStorage(storage);
            message = command.work();
            new  SenderThread(theClient,networkUdp2,message).start();
        } else if (obj instanceof AbsAuth) {
            AbsAuth auth = (AbsAuth) obj;
            auth.loadUserHandler(userHandler);
            message = auth.work();
            new  SenderThread(theClient,networkUdp2,message).start();
            storage = new Storage(auth.getVehicleStack(), userHandler);
        }
        return message;
    }
}
