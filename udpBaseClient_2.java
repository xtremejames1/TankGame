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
    
    private gameInfo game;
    
    private int mouseX, mouseY, tankX, tankY;
    public udpBaseClient_2(String name, gameInfo g)
    {
        threadName = name;
        game = g;
    }
    public udpBaseClient_2(String name, gameInfo g, InetAddress i)
    {
        System.out.println("Host client initialized");
        threadName = name;
        game = g;
        ip = i;
    }
    
    public void run()
    {

        try
        {
            Scanner sc = new Scanner(System.in);
            DatagramSocket ds = new DatagramSocket();
            
            byte buf[] = null;
            while (true)
            {
                if(game.getPhase()==0)
                {
                    if(game.getType()==1)
                    {
                        System.out.println("What IP would you like to connect to?\n");
                        String i = sc.nextLine();     
                        ip = InetAddress.getByName(i);
                        
                        clientSocket = new Socket(ip, 1234);
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        
                        out.println(clientSocket.getLocalAddress());
                        /*
                        String local;
                        try(final DatagramSocket socket = new DatagramSocket()){
                          socket.connect(ip, 10002);
                          local = socket.getLocalAddress().getHostAddress();
                        }

                        
                        buf = ("ip:"+local).getBytes();
                        
                        DatagramPacket DpSend =
                            new DatagramPacket(buf, buf.length, ip, 1234);
                        
                        ds.send(DpSend);
                        */
                        if(in.readLine().equals("received IP"))
                            game.setPhase(1);
                    }
                    else
                    {
                        boolean found = false;
                        while(!found)
                        {
                            found = game.getHostFoundIP();
                            System.out.println("searching...");
                        }
                        ip=InetAddress.getByName(game.getClientIP().substring(1));
                    }
                    System.out.println("Connected to "+ip);
                }
                if(game.getPhase()==1)
                {
                    System.out.println("Client Phase 1");
                    /*
                    if(game.getType()==0)
                    {
                        
                        while(!(game.getPhase()==2))
                        {
                            buf = ("confirm").getBytes();
                        
                            DatagramPacket DpSend =
                                new DatagramPacket(buf, buf.length, ip, 1234);
                        
                            ds.send(DpSend);
                        }
                        
                    }
                    else if(game.getType()==1)
                    {
                        while(!game.getConfirm()){}
                        buf = ("confirm").getBytes();
                    
                        DatagramPacket DpSend =
                            new DatagramPacket(buf, buf.length, ip, 1234);
                    
                        ds.send(DpSend);
                        game.setPhase(2);
                    }
                    */
                    if(game.getType()==0)
                    {
                        System.out.println("ip is "+ip);
                    }
                   game.setPhase(2);
                }
                //if(game.getPhase()==2)
                //{
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();
                    mouseX = (int) b.getX();
                    mouseY = (int) b.getY();
                    //String inp = sc.nextLine();
                    String inp = "x: "+mouseX+" y: "+mouseY+" tankX: "+game.getLocalTank().getX()+" tankY: "+game.getLocalTank().getY();
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
