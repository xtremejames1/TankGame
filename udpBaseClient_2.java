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

public class udpBaseClient_2 implements Runnable
{
    private Thread t;
    private String threadName;
    
    private DatagramSocket ds;
    private InetAddress ip;
    
    
    private JFrame f;
    private int mouseX, mouseY, tankX, tankY;
    public udpBaseClient_2(String name)
    {
        threadName = name;
    }
    
    public void run()
    {

        try
        {
            Scanner sc = new Scanner(System.in);
            DatagramSocket ds = new DatagramSocket();
            
            System.out.println("What IP would you like to connect to?\n");
            String i = sc.nextLine();
            
            ip = InetAddress.getByName(i);
            System.out.println("Connected to "+i);
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
                // break the loop if user enters "bye"
                

                
                if (inp.equals("bye"))
                    break;
                
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
