import java.net.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 * Adds networking functionality to the game
 *
 * @author James Xiao
 * @version 1
 */
public class GameNetwork
{
    //TODO more modularity methods:
    // - Get and Set UDP messages
    private DatagramSocket clientUDP, serverUDP; //UDP client and server socket
    
    private ServerSocket socket; //TCP server socket
    private Socket clientSocket; //TCP client socket
    private BufferedReader in; //TCP in reader
    private PrintWriter out; //TCP out writer
    
    private int tickrate; //Tickrate of the network
    
    private InetAddress remoteIP; //Remote IP Address
    
    private GameInfo game; //GameInfo that the GameNetwork communicates with
    
    private int type; //Game type, 0 for host, 1 for client
    private int tcp, udp; //TCP and UDP ports
    private int tankX, tankY, mouseX, mouseY, rTankX, rTankY, rMouseX, rMouseY; //Tank and mouse coordinates, WILL CHANGE LATER NOT MODULAR

    /**
     * Constructor for the game network
     * @param t Game type, 0 is host, 1 is client
     * @param g GameInfo that this links to
     * @param tcpport Port for TCP communication
     * @param udpport Port for UDP communication
     */
    public GameNetwork(int t, GameInfo g, int tcpport, int udpport)
    {
        game = g;
        type=t;
        tickrate = 64;
        tcp = tcpport;
        udp = udpport;
        System.out.println("Created type "+type+" Network with ports "+tcpport+" and "+udpport);
    }

    /**
     * Constructor for the game network
     * @param t Game type, 0 is host, 1 is client
     * @param g GameInfo that this links to
     * @param tcpport Port for TCP communication
     * @param udpport Port for UDP communication
     * @param ip String representation of IP
     * @throws UnknownHostException
     */
    public GameNetwork(int t, GameInfo g, int tcpport, int udpport, String ip) throws UnknownHostException {
        game = g;
        type=t;
        tickrate = 64;
        tcp = tcpport;
        udp = udpport;
        remoteIP = InetAddress.getByName(ip);
        System.out.println("Created type "+type+" Network with ports"+tcpport+" and "+udpport+" and IP "+ip);
    }

    /**
     * Starts the game network server
     * @throws InterruptedException
     * @throws IOException
     */
    public void server() throws InterruptedException, IOException {
        synchronized(this) //Synchronizes to make sure that the Host client-side receives IP
        {
            System.out.println("Server synchronized stage started.");
            if(type==0)
            {
                System.out.println("Host TCP sequence started.");
                socket = new ServerSocket(tcp); //Creates TCP server socket with port defined in constructor
                clientSocket = socket.accept(); //Creates client socket from server socket
                System.out.println("Host TCP network started.");

                out = new PrintWriter(clientSocket.getOutputStream(), true); //creates TCP output
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //creates TCP input

                System.out.println("Host waiting for IP...");
                remoteIP = InetAddress.getByName(in.readLine()); //Receives IP
                System.out.println("Host received IP. Host waiting for remote player name...");
                game.setRName(in.readLine()); //Receives remote name
                System.out.println("Host received remote player name. Host sent local player name.");
                out.println(game.getName()); //Sends local name
                
                System.out.println(game.getRName()+" connected to local host with IP "+remoteIP+".");
                game.setClientFound(true); //used for gameframe
                notify(); //Notifys client thread that IP has been found
            }
        }
        
        serverUDP = new DatagramSocket(udp);
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        while(true)
        {
            DpReceive = new DatagramPacket(receive, receive.length);

            serverUDP.receive(DpReceive);

            String tankData = data(receive).toString();

            game.setReceiveData(tankData);

            /*
            String tankXString = tankData.substring(tankData.indexOf("tX")+2,tankData.indexOf("tY"));
            String tankYString = tankData.substring(tankData.indexOf("tY")+2);

            String mouseXString = tankData.substring(tankData.indexOf("mX")+2,tankData.indexOf("mY"));
            String mouseYString = tankData.substring(tankData.indexOf("mY")+2, tankData.indexOf("tX"));

            rTankX = Integer.parseInt(tankXString);
            rTankY = Integer.parseInt(tankYString);

            rMouseX = Integer.parseInt(mouseXString);
            rMouseY = Integer.parseInt(mouseYString);

            game.getRemoteTank().setLocation(rTankX, rTankY);
            game.getRemoteTank().setMouseLocation(rMouseX, rMouseY);
            */

            
            receive = new byte[65535];
            Thread.sleep(1000/tickrate);
        }
        
    }
    
    public void client() throws IOException,InterruptedException
    {
        synchronized(this) //Synchronized to make sure that host receives IP
        {
            if(type==1)
            {
                System.out.println("Client TCP sequence started.");
                clientSocket= new Socket(remoteIP, tcp);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Client TCP network started.");
                
                out.println(clientSocket.getLocalAddress().getHostAddress());                
                out.println(game.getName());
                System.out.println("Client sent IP and local player name. Waiting for remote player name...");
                
                game.setRName(in.readLine());
                System.out.println("Client received remote player name.");
                
                System.out.println("Local client connected to "+game.getRName()+" with IP "+remoteIP+".");
            }
            else if(type==0)
            {
                System.out.println("Client thread waiting to receive IP");
                wait(); //waits for server thread to receive IP
                game.setClientFound(true);
                System.out.println("Client thread received IP.");
            }
        }
        
        System.out.println("Client connection made to "+remoteIP);

        clientUDP = new DatagramSocket();
        byte buf[] = null;
        while (true)
        {
            /*
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            mouseX = (int) b.getX();
            mouseY = (int) b.getY();
            tankX = game.getLocalTank().getX();
            tankY = game.getLocalTank().getY();
            */
            //String inp = "mX"+mouseX+"mY"+mouseY+"tX"+tankX+"tY"+tankY;
            // convert the String input into the byte array.
            buf = game.getSendData().getBytes();
            
            DatagramPacket DpSend =
                new DatagramPacket(buf, buf.length, remoteIP, udp);
            
            clientUDP.send(DpSend);
            
            Thread.sleep(1000/tickrate);
        }
    }
    
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
    public String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    public int getTCP() {
        return tcp;
    }
    public int getUDP() {
        return udp;
    }
    public void reset() throws IOException {
        if(socket != null)
            socket.close();
        if(clientSocket != null)
            clientSocket.close();
        if(clientUDP != null)
            clientUDP.close();
        if(serverUDP != null)
            serverUDP.close();
    }
}
