/**
 * Write a description of class GameInfo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameInfo
{
    private final Tank playerTank;
    private boolean clientFound;
    private String name, rName;
    private int type;
    
    /**
     * Constructor for objects of class GameInfo
     */
    public GameInfo(Tank p)
    {
        playerTank = p;
        clientFound = false;
    }
    
    public Tank getPlayerTank() {return playerTank;}
    
    public String getName() {return name;}
    
    public String getRName() {return rName;}
    public int getType() {return type;}
    public void setType(int t) {type = t;}
    public void setRName(String n) {rName = n;}
    public void setName(String n) {name = n;}
    public void setClientFound(boolean b) {
        clientFound = b;
    }
    public boolean getClientFound() {
        return clientFound;
    }
}
