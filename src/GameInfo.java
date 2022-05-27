/**
 * Contains all of the message information and statuses of networks.
 *
 * @author James Xiao
 * @version 05/26/2022
 */
public class GameInfo
{
    private boolean clientFound;
    private String name, rName;
    private String receiveData = "";
    /**
     * Constructor for objects of class GameInfo
     */
    public GameInfo()
    {
        clientFound = false;
    }
    public String getName() {return name;}
    public String getRName() {return rName;}
    public void setRName(String n) {rName = n;}
    public void setName(String n) {name = n;}
    public void setClientFound(boolean b) {
        clientFound = b;
    }
    public boolean getClientFound() {
        return clientFound;
    }
    public String getReceiveData() {
        return receiveData;
    }
    public void addMsg(String name,String chat) {
        receiveData+=(name+": "+chat+"\n");
    }
    public void addData(String data) {
        receiveData +=data;
    }
}
