
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

    /**
     * Constructor for objects of class gameInfo
     */
    public gameInfo(int t, Tank l, Tank r)
    {
        // initialise instance variables
        type = t;
        localTank = l;
        remoteTank = r;
        phase = 0;
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
    
    public void setPhase(int p)
    {
        phase = p;
    }
}
