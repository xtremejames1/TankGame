// Java program to illustrate Client side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;

public class udpBaseClient_2 implements Runnable
{
    private Thread t;
    private String threadName;
    
    private DatagramSocket ds;
    private InetAddress ip;
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    private GameInfo game;
    
    private int mouseX, mouseY, tankX, tankY;
    public udpBaseClient_2(String name, GameInfo g)
    {
        threadName = name;
        game = g;
    }
    
    public void run()
    {

        try
        {
            Scanner sc = new Scanner(System.in);
            DatagramSocket ds = new DatagramSocket();
            
            if(game.getType()==1)
            {
                System.out.println("What IP would you like to connect to?\n");
                String i = sc.nextLine();     
                ip = InetAddress.getByName(i);
                
                game.setRemoteIP(i);
                
                clientSocket = new Socket(ip, 1235);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                game.setLocalIP(clientSocket.getLocalAddress().getHostAddress());
                
                out.println(game.getLocalIP());
                while(true)
                {
                    if(in.readLine().equals("received IP"))
                        break;
                }
            }
            else
            {
                ip=InetAddress.getByName(game.getRemoteIP().substring(1));       
            }
            System.out.println("Client connected to "+ip);         
            byte buf[] = null;
            while (true)
            {
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();
                    mouseX = (int) b.getX();
                    mouseY = (int) b.getY();
                    //String inp = sc.nextLine();
                    String inp = "x: "+mouseX+" y: "+mouseY+" tankX: "+tankX+" tankY: "+tankY;
                    // convert the String input into the byte array.
                    buf = inp.getBytes();
        
                    // Step 2 : Create the datagramPacket for sending
                    // the data.
                    DatagramPacket DpSend =
                        new DatagramPacket(buf, buf.length, ip, 1234);
        
                    // Step 3 : invoke the send call to actually send
                    // the data.
                    ds.send(DpSend);
                //}   
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    public void setTankLoc(int x, int y)
    {
        tankX = x;
        tankY = y;
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
