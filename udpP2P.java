
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
import java.awt.*;

public class udpP2P
{
    public static void main(String[] args) throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        String name = "";
        System.out.println("Username: ");
        name = sc.nextLine();
        System.out.println("Type 1 to host a game, type 2 to connect to a game");
        int type = 0;
        while(run)
        {
            if (sc.hasNextInt())
            {
                if(sc.nextInt()==1||sc.nextInt()==2)
                {
                    type = sc.nextInt()-1;
                    break;
                }
                else
                {
                    System.out.println("Bad input.");
                    sc.next();
                    continue;
                }
            }
            else {
                System.out.println("Bad input.");
                sc.next();
                continue;
            }
        }
        gameInfo game = new gameInfo(type, new Tank(50,50, Color.blue), new Tank(50,50, Color.red), name);
        
        if(type == 0)
        {
            System.out.println("Started game. Waiting for connection...");
        }
        
        
        udpBaseServer_2 server = new udpBaseServer_2("server1", game);
        server.start();
        
        udpBaseClient_2 client;
        if(game.getType()==0)
        {
            while(!game.getHostFoundIP())
            {
            }
            client = new udpBaseClient_2("client1", game, server.getIP());
        }
        else
        {
            client = new udpBaseClient_2("client1", game);
        }
        
        client.start();
        
        GameFrame gameFrame = new GameFrame(client, server, game);
    }
}
