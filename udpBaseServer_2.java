// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class udpBaseServer_2 implements Runnable
{
    
    private Thread t;
    private String threadName;
    private int tankX, tankY;
    
    
    public udpBaseServer_2(String name)
    {
        threadName = name;
    }
    
    public void run()
    {
        try
        {
            // Step 1 : Create a socket to listen at port 1234
            DatagramSocket ds = new DatagramSocket(1234);
            byte[] receive = new byte[65535];
    
            DatagramPacket DpReceive = null;
            while (true)
            {
    
                // Step 2 : create a DatgramPacket to receive the data.
                DpReceive = new DatagramPacket(receive, receive.length);
    
                // Step 3 : revieve the data in byte buffer.
                ds.receive(DpReceive);
                
                String tankData = data(receive).toString();
                
                String tankXString = tankData.substring(tankData.indexOf("tankX")+7,tankData.indexOf(" tankY"));
                String tankYString = tankData.substring(tankData.indexOf("tankY")+7);

                tankX = Integer.parseInt(tankXString);
                tankY = Integer.parseInt(tankYString);
                // Exit the server if the client sends "bye"
                if (data(receive).toString().equals("bye"))
                {
                    System.out.println("Client sent bye.....EXITING");
                    break;
                }
    
                // Clear the buffer after every message.
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
