import java.net.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 * Write a description of class GameNetwork here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameNetwork
{
    // instance variables - replace the example below with your own
    private DatagramSocket clientUDP, serverUDP;
    
    private ServerSocket socket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    
    private int tickrate;
    
    private InetAddress remoteIP;
    
    private GameInfo game;
    
    private int type;
    private int tcp, udp;
    private int tankX, tankY, mouseX, mouseY, rTankX, rTankY, rMouseX, rMouseY;

    /**
     * Constructor for objects of class GameNetwork
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
    public GameNetwork(int t, GameInfo g, int tcpport, int udpport, String ip) throws UnknownHostException {
        game = g;
        type=t;
        tickrate = 64;
        tcp = tcpport;
        udp = udpport;
        remoteIP = InetAddress.getByName(ip);
        System.out.println("Created type "+type+" Network with ports"+tcpport+" and "+udpport+" and IP "+ip);
    }
    
    
    public GameNetwork(int t, GameInfo g, int tr)
    {
        game = g;
        type=t;
        tickrate = tr;
    }
    
    
    public void server() throws InterruptedException, IOException {
        synchronized(this)
        {
            System.out.println("Server synchronized stage started.");
            if(type==0)
            {
                System.out.println("Host TCP sequence started.");
                socket = new ServerSocket(tcp);
                System.out.println("test");
                clientSocket = socket.accept();
                System.out.println("Host TCP network started.");

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                System.out.println("Host waiting for IP...");
                remoteIP = InetAddress.getByName(in.readLine());
                System.out.println("Host received IP. Host waiting for remote player name...");
                game.setRName(in.readLine());
                System.out.println("Host received remote player name. Host sent local player name.");
                out.println(game.getName());
                
                System.out.println(game.getRName()+" connected to local host with IP "+remoteIP+".");
                game.setClientFound(true);
                notify();
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


            
            receive = new byte[65535];
            Thread.sleep(1000/tickrate);
        }
        
    }
    
    public void client() throws IOException,InterruptedException
    {
        synchronized(this)
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
                wait();
                game.setClientFound(true);
                System.out.println("Client thread received IP.");
            }
        }
        
        System.out.println("Client connection made to "+remoteIP);

        clientUDP = new DatagramSocket();
        byte buf[] = null;
        while (true)
        {
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            mouseX = (int) b.getX();
            mouseY = (int) b.getY();
            tankX = game.getLocalTank().getX();
            tankY = game.getLocalTank().getY();
            String inp = "mX"+mouseX+"mY"+mouseY+"tX"+tankX+"tY"+tankY;
            // convert the String input into the byte array.
            buf = inp.getBytes();
            
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
}
