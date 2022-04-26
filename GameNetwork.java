import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;
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
    
    private InetAddress remoteIP;
    
    private int type;
    
    private int tankX, tankY, mouseX, mouseY, rTankX, rTankY, rMouseX, rMouseY;

    /**
     * Constructor for objects of class GameNetwork
     */
    public GameNetwork(int t)
    {
        type=t;
    }
    
    public void server() throws InterruptedException, IOException
    {
        synchronized(this)
        {
            if(type==0)
            {
                socket = new ServerSocket(1235);
                clientSocket = socket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                remoteIP = InetAddress.getByName(in.readLine());
                out.println("received IP");
                System.out.println("Server received client IP: "+remoteIP);
                notify();
            }
        }
        
        serverUDP = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        while(true)
        {
            DpReceive = new DatagramPacket(receive, receive.length);

            // Step 3 : revieve the data in byte buffer.
            serverUDP.receive(DpReceive);
            
            String tankData = data(receive).toString();
            
            String tankXString = tankData.substring(tankData.indexOf("tX")+2,tankData.indexOf("tY"));
            String tankYString = tankData.substring(tankData.indexOf("tY")+2);

            rTankX = Integer.parseInt(tankXString);
            rTankY = Integer.parseInt(tankYString);
            
            
            receive = new byte[65535];
        }
        
    }
    
    public void client() throws IOException,InterruptedException
    {
        synchronized(this)
        {
            if(type==1)
            {
                Scanner sc = new Scanner(System.in);
                System.out.println("What IP would you like to connect to?\n");
                String i = sc.nextLine();     
                remoteIP = InetAddress.getByName(i);
                
                clientSocket= new Socket(remoteIP, 1235);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                out.println(clientSocket.getLocalAddress().getHostAddress());
            }
            else if(type==0)
            {
                System.out.println("Client thread waiting to receive IP");
                wait();
                System.out.println("Client thread received IP.");
            }
        }
        
        System.out.println("Client connection made to "+remoteIP);
        
        DatagramSocket clientUDP = new DatagramSocket();
        byte buf[] = null;
        while (true)
        {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                mouseX = (int) b.getX();
                mouseY = (int) b.getY();
                String inp = "mX"+mouseX+"mY"+mouseY+"tX"+tankX+"tY"+tankY;
                // convert the String input into the byte array.
                buf = inp.getBytes();
                
                DatagramPacket DpSend =
                    new DatagramPacket(buf, buf.length, remoteIP, 1234);
                    
                clientUDP.send(DpSend);
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
    
    public int getRTankX()
    {
        return rTankX;
    }
    
    public int getRTankY()
    {
        return rTankY;
    }
    
    public void setTankLoc(int x, int y)
    {
        tankX = x;
        tankY = y;
    }
}
