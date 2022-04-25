
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
    
    private boolean confirm;
    
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
    
    public boolean getConfirm()
    {
        return confirm;
    }
}
