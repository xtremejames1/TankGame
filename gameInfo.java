import java.net.InetAddress;

/**
 * Write a description of class gameInfo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class gameInfo
{
    // instance variables - replace the example below with your own
    private int type, phase;
    private Tank localTank, remoteTank;
    
    private String name;
    
    private InetAddress clientIP;
    
    private boolean confirm, hostFoundIP;
    
    /**
     * Constructor for objects of class gameInfo
     */
    public gameInfo(int t, Tank l, Tank r, String n)
    {
        // initialise instance variables
        type = t;
        localTank = l;
        remoteTank = r;
        phase = 0;
        name = n;
        hostFoundIP=false;
    }
    
    public int getPhase()
    {
        return phase;
    }
    
    public int getType()
    {
        return type;
    }
    
    public Tank getLocalTank()
    {
        return localTank;
    }
    
    public Tank getRemoteTank()
    {
        return remoteTank;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setPhase(int p)
    {
        phase = p;
    }
    
    public void setConfirm(boolean c)
    {
        confirm = c;
    }
    
    public void setClientIP(InetAddress i)
    {
        clientIP = i;
    }
    
    public InetAddress getClientIP()
    {
        return clientIP;
    }
    
    public void HostFoundIP()
    {
        System.out.println("Found client");
        hostFoundIP=true;
    }
    public boolean getHostFoundIP()
    {
        return hostFoundIP;
    }
    
    public boolean getConfirm()
    {
        return confirm;
    }
}
