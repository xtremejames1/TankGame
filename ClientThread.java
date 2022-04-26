
/**
 * Write a description of class ClientThread here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ClientThread extends Thread
{
    private GameNetwork net;
    
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
}
