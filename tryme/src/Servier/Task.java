package Servier;

import javafx.util.Pair;

import java.util.concurrent.RecursiveTask;

public class Task extends RecursiveTask<String> {

    private final Invoker invoker;
    private final Pair request;
    ServerNetworkUdp2 serverNetworkUdp2;

    public Task(Invoker anInvoker, Pair aRequest,ServerNetworkUdp2 serverNetworkUdp2) {
        invoker = anInvoker;
        request = aRequest;
        this.serverNetworkUdp2 =serverNetworkUdp2;
    }

    @Override
    protected String compute() {
        try {
            return invoker.execute(request,serverNetworkUdp2);
        } catch (Exception e) {
            e.printStackTrace();
            return "errorr";
        }
    }
}
