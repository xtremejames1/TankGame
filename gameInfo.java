import java.net.InetAddress;

/**
 * Write a description of class GameInfo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameInfo
{
    // instance variables - replace the example below with your own
    private int type, phase;
    private Tank localTank, remoteTank;
    
    private String name;
    
    private String clientIP;
    
    private String remoteIP, localIP;
    
    private boolean confirm, hostFoundIP;
    
    /**
     * Constructor for objects of class GameInfo
     */
    public GameInfo(int t, Tank l, Tank r, String n)
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
        System.out.println("Phase set to "+p);
        phase = p;
    }
    
    public void setConfirm(boolean c)
    {
        confirm = c;
    }
    
    public void setClientIP(String i)
    {
        clientIP = i;
    }
    
    public String getClientIP()
    {
        return clientIP;
    }
    
    public void setRemoteIP(String i)
    {
        remoteIP = i;
    }
    
    public String getRemoteIP()
    {
        return remoteIP;
    }
    
    
    public void setLocalIP(String i)
    {
        localIP = i;
    }
    
    public String getLocalIP()
    {
        return localIP;
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
