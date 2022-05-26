import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Game
{
    private JFrame frame = new JFrame();
    private JTextArea text;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel label;
    public static void main(String[] args) throws IOException {
        GameInfo game = new GameInfo(new Tank(50,50, Color.blue), new Tank(50,50, Color.red));

        //GameFrame gameFrame = new GameFrame(game);
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = sc.nextLine();
        game.setName(name);
        System.out.println("host or client (0 or 1)");
        int type = sc.nextInt();

        if(type == 0) {
            GameNetwork net = new GameNetwork(type, game, 1235, 1234);

            ClientThread client = new ClientThread(net); //creates client thread
            ServerThread server = new ServerThread(net); //creates server thread

            client.start(); //starts client thread
            server.start(); //starts server thread

            String oldData = game.getReceiveData();
            Scanner chatsc = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String chat = "";
            Chat ch = new Chat(game);

            while(true) {
                if(game.getClientFound()) {
                    ch.update();
                    if(sc.hasNext()) {
                        chat = sc.nextLine();
                        net.sendMessage(chat);
                        game.addMsg(game.getName(), chat);
                    }
                }
                else {
                    System.out.println("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Waiting for client...");
                }
            }
        }
        if(type == 1) {
            Scanner ipsc = new Scanner(System.in);
            System.out.println("What ip");
            String ip = ipsc.nextLine();
            GameNetwork net = new GameNetwork(type, game, 1235, 1234, ip);

            System.out.println("Network Created");
            ClientThread client = new ClientThread(net); //Creates client thread
            ServerThread server = new ServerThread(net); //Creates server thread

            client.start(); //Starts client thread
            server.start(); //Starts server thread

            String oldData = game.getReceiveData();
            Scanner chatsc = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String chat = "";
            Chat ch = new Chat(game);
            while(true) {
                if(sc.hasNext()) {
                    chat = sc.nextLine();
                    net.sendMessage(chat);
                    game.addMsg(game.getName(), chat);
                }
                ch.update();
            }
        }


    }
}
