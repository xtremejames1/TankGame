
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

public class Game
{
    public static void main(String[] args) throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        String name = "";
        System.out.println("Player Name: ");
        name = sc.nextLine();
        System.out.println("Type 1 to host a game, type 2 to connect to a game");
        int type = 0;
        while(run)
        {
            try
            {
                type = sc.nextInt()-1;
                
                if(type!=0&&type!=1)
                {
                    System.out.println("Bad input.");
                    continue;
                }
                else
                    run=false;
            }
            catch(Exception e)
            {
                System.out.println("Bad input.");
            }
        }
        GameInfo game = new GameInfo(new Tank(50,50, Color.blue), new Tank(50,50, Color.red), name);
        
        if(type == 0)
        {
            System.out.println("Started game. Waiting for connection...");
        }
        
        GameNetwork net = new GameNetwork(type, game);
        
        ClientThread client = new ClientThread(net);
        ServerThread server = new ServerThread(net);
        
        client.start();
        server.start();
        
        GameFrame gameFrame = new GameFrame(net, game);
    }
}
