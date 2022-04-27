import java.net.InetAddress;

/**
 * Write a description of class GameInfo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameInfo
{
    private Tank localTank, remoteTank;
    
    private String name, rName;
    
    private String clientIP;
    
    /**
     * Constructor for objects of class GameInfo
     */
    public GameInfo(Tank l, Tank r, String n)
    {
        localTank = l;
        remoteTank = r;
        name = n;
    }
    
    public Tank getLocalTank() {return localTank;}
    
    public Tank getRemoteTank() {return remoteTank;}
    
    public String getName() {return name;}
    
    public String getRName() {return rName;}
    
    public void setRName(String n) {rName = n;}
}
