
/**
 * Write a description of class udpP2P here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class udpP2P
{
    public static void main(String[] args){
        udpBaseClient_2 client = new udpBaseClient_2("client1");
        client.start();
        udpBaseServer_2 server = new udpBaseServer_2("server1");
        server.start();
        
        GameFrame gameFrame = new GameFrame(client, server);
    }
}
