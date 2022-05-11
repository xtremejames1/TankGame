
/**
 * Write a description of class iptest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.net.*;
import java.util.Enumeration;

public class iptest
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class iptest
     */
    public static void main() throws SocketException,UnknownHostException
    {
        // initialise instance variables
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        while(n.hasMoreElements())
            System.out.println(n.nextElement());
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
