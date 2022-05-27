
/**
 * Thread that runs the client-side TCP networking.
 *
 * @author James Xiao
 * @version 05/26/2022
 */
public class ClientThread extends Thread
{
    private final GameNetwork net;
    
    public ClientThread(GameNetwork n)
    {
        net = n;
    }
    @Override
    public void run()
    {
        try
        {
            net.client();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    @Override
    public void start() {
        System.out.println("Starting client thread...");
        super.start();
        System.out.println("Started client thread.");
    }
}
