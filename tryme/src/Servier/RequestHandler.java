package Servier;

import javafx.util.Pair;
import utils.AbsTransfer;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ForkJoinPool;

public class RequestHandler {
    private final Invoker invoker;
    private final ForkJoinPool forkJoinPool;
    public  String response;

    public RequestHandler(Invoker anInvoker, ForkJoinPool aForkJoinPool) {
        invoker = anInvoker;
        forkJoinPool = aForkJoinPool;
    }

    public void process(Pair<AbsTransfer,SocketAddress> request,ServerNetworkUdp2 serverNetworkUdp2) {
        Task task = new Task(invoker, request,serverNetworkUdp2);
        response = forkJoinPool.invoke(task);
    }
}
