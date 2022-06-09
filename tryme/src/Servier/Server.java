package Servier;
import Control.Storage;
import LoginAndRegiester.AbsAuth;
import database.Connector;
import database.UserHandler;
import javafx.util.Pair;
import utils.AbsTransfer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

import java.sql.Connection;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    private final static Logger lOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Storage storage;
    ServerNetworkUdp2 networkudp;
    DatagramChannel channel;
    String message;
    static Queue<Pair<AbsTransfer,SocketAddress>> queue= new LinkedBlockingQueue<>();


    public Server(DatagramChannel datagramChannel) {
        this.channel=datagramChannel;
        this.networkudp = new ServerNetworkUdp2(this.channel);
    }

    public void start() throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 3);

        AbsAuth auth =null;

        Connection connection = Connector.connectToDp();

        UserHandler userHandler =new UserHandler(connection);

        while (auth ==null ){
            Object object = networkudp.receiveObj();
            auth = (AbsAuth) object;
        }
        handleUser(auth,userHandler);
        lOGGER.log(Level.INFO,"server started working");
        lOGGER.log(Level.INFO,"server made a storage with date "+storage.date);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));


        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.submit(()->{
            try {
                ReciverFromClient.startReciving(queue,networkudp);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        Invoker invoker =new Invoker(message,userHandler,storage);
        RequestReciver sch = new RequestReciver(message,storage,auth,networkudp,userHandler,queue,cachedThreadPool,invoker,forkJoinPool);
        sch.handle(br);


    }
         String  handleUser(AbsAuth auth, UserHandler userHandler) throws IOException, ClassNotFoundException {
            auth.loadUserHandler(userHandler);
             message = auth.work();
            while (!message.contains("successfully")){
                networkudp.sendObj(message,networkudp.client);
                auth=null;
                while (auth ==null){
                    Object obj=networkudp.receiveObj();
                    auth=(AbsAuth) obj;
                }
                auth.loadUserHandler(userHandler);
                message=auth.work();
            }
            networkudp.sendObj(message,networkudp.client);
            storage = new Storage(auth.getVehicleStack(),userHandler);
            message="";
            return message;
        }

}



