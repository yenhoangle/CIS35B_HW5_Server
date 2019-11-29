package driver;
import server.*;

public class driver5 {
    public static void main(String[] args) {
        int port = 4444;
        DefaultServerSocket server = new DefaultServerSocket(port);
        server.start();
    }
}
