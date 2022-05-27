import java.io.IOException;
import java.util.Scanner;

//TODO:
// - exit chat and check if tcp still connected
// - Graphics for login
// - save previous connections?

public class Game
{
    public static void main(String[] args) throws IOException {
        GameInfo game = new GameInfo();

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

            while(true) {
                if(game.getClientFound()) {
                    Chat ch = new Chat(game, net);
                    Runnable update = () -> {
                        while(true) {
                            ch.update();
                        }
                    };
                    Thread t = new Thread(update, "chat update");
                    t.start();
                    break;
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

            Chat ch = new Chat(game, net);

            Runnable update = () -> {
                while(true) {
                    ch.update();
                }
            };
            Thread t = new Thread(update, "chat update");
            t.start();
        }
    }
}
