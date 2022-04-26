// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;

public class udpBaseServer_2 implements Runnable
{
    
    private Thread t;
    private String threadName;
    private int tankX, tankY;
    
    private String ip;
    
    private boolean foundIP;
    
    private ServerSocket socket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    private gameInfo game;
    
    public udpBaseServer_2(String name, gameInfo g)
    {
        threadName = name;
        game = g;
        foundIP = false;
    }
    
    public void run()
    {
        try
        {
            // Step 1 : Create a socket to listen at port 1234
            DatagramSocket ds = new DatagramSocket(1234);
            byte[] receive = new byte[65535];
            
            if(game.getType()==0)
            {
                socket = new ServerSocket(1235);
                clientSocket = socket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ip = in.readLine();
                out.println("received IP");
                System.out.println("Server received client IP: "+ip);
                game.setRemoteIP(ip);
                game.HostFoundIP();
            }
            
            DatagramPacket DpReceive = null;
            while (true)
            {
                DpReceive = new DatagramPacket(receive, receive.length);
    
                // Step 3 : revieve the data in byte buffer.
                ds.receive(DpReceive);
                
                String tankData = data(receive).toString();
                
                String tankXString = tankData.substring(tankData.indexOf("tankX")+7,tankData.indexOf(" tankY"));
                String tankYString = tankData.substring(tankData.indexOf("tankY")+7);

                tankX = Integer.parseInt(tankXString);
                tankY = Integer.parseInt(tankYString);
                
                
                receive = new byte[65535];
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    // A utility method to convert the byte array
    // data into a string representation.
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
    
    public int getTankX()
    {
        return tankX;
    }
    public int getTankY()
    {
        return tankY;
    }
    
    public String getIP()
    {
        return ip;
    }
    
    public boolean getFoundIP()
    {
        return foundIP;
    }
    
    public void start()
    {
        System.out.println("Starting "+threadName);
        if(t==null)
        {
            t = new Thread(this,threadName);
            t.start();
        }
    }
}
