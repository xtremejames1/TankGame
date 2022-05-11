
/**
 * Write a description of class ServerThread here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ServerThread extends Thread
{
    private GameNetwork net;
    
    public ServerThread(GameNetwork n)
    {
        net = n;
    }
    @Override
    public void run()
    {
        try
        {
            net.server();
        }
        catch(Exception e)
        {
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
