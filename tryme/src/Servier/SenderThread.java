package Servier;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.net.SocketAddress;

@AllArgsConstructor
public class SenderThread extends Thread {
    SocketAddress client;
    ServerNetworkUdp2 networkUdp;
    String message;

    @SneakyThrows
    @Override
    public void run() {
        try {
            networkUdp.sendObj(message,client);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
