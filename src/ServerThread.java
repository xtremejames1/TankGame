
/**
 * Thread that runs the server-side TCP and UDP networking
 *
 * @author James Xiao
 * @version 05/26/2022
 */
public class ServerThread extends Thread {
    private GameNetwork net;
    public ServerThread(GameNetwork n) {
        net = n;
    }
    @Override
    public void run() {
        try {
            net.server();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    public void start() {
        System.out.println("Starting server thread...");
        super.start();
        System.out.println("Started server thread.");
    }
}
