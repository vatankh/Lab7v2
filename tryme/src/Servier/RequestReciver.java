package Servier;

import Control.Storage;
import LoginAndRegiester.AbsAuth;
import commands.AbsCommand;
import commands.Exit;
import database.UserHandler;
import javafx.util.Pair;
import utils.AbsTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Handler;

public class RequestReciver {
    String message;
    Queue<Pair<AbsTransfer, SocketAddress>> queue;
    ExecutorService threadPool;
    Invoker invoker;
    ForkJoinPool forkJoinPool;
    RequestHandler requestHandler;

    public RequestReciver(String message, Storage storage, AbsAuth auth, ServerNetworkUdp2 networkudp, UserHandler userHandler, Queue<Pair<AbsTransfer, SocketAddress>> queue, ExecutorService cachedThreadPool,Invoker invoker,ForkJoinPool forkJoinPool) {
        this.threadPool=cachedThreadPool;
        this.queue=queue;
        this.message = message;
        this.storage = storage;
        this.auth = auth;
        this.networkudp = networkudp;
        this.userHandler = userHandler;
        this.invoker=invoker;
        this.forkJoinPool=forkJoinPool;
        this.requestHandler=new RequestHandler(invoker,forkJoinPool);
    }

    Storage storage;
    AbsAuth auth;
    ServerNetworkUdp2 networkudp;
    UserHandler userHandler;
    public void handle(BufferedReader br) throws Exception {
        Runnable checkForCommands =new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (isSave(br)) {
                            Exit save = new Exit(auth);
                            save.loadStorage(storage);
                            message = save.work();
                        } else {
                            if (!queue.isEmpty()){
                                requestHandler.process(queue.poll(), networkudp);
                            }


                        }

                    }
                }catch (Exception e){
                    System.out.println("eror in server comand hadler");
                    System.out.println(e.getMessage());

                }
            }
        };
        threadPool.submit(checkForCommands);

        }



    boolean isSave(BufferedReader br) throws IOException {
        return  br.ready() && br.readLine().equals("save");
    }






}
