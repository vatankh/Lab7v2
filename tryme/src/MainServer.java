import Servier.Server;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;


public class MainServer {
    public static void main(String[] args) throws Exception {
        DatagramChannel datagramChannel=DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.socket().bind(new InetSocketAddress(80));
        Server server = new Server(datagramChannel);
        server.start();
    }

}
