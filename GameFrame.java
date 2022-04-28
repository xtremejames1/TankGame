import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;
import javax.swing.*;


public class GameFrame{

    private JFrame frame = new JFrame();
    static final int FRAME_HEIGHT = 720;
    static final int FRAME_WIDTH = 1280;
    private GamePanel panel;
    private GameNetwork net;
    private GameInfo game;

    public GameFrame(GameInfo g) {
        System.out.println("Starting display...");
        game = g;
        frame.setLayout(null);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Tank Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        mainMenu();
    }
    public void mainMenu() {
        MainMenu m = new MainMenu(frame, game, this);
    }
    public void host() {
        net = new GameNetwork(0, game, 1235, 1234);
        HostMenuPanel h = new HostMenuPanel(frame,this, net.getIP(), net.getTCP(), net.getUDP());

        ClientThread client = new ClientThread(net);
        ServerThread server = new ServerThread(net);

        client.start();
        server.start();

        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getClientFound())
                    game();
            }
        };
        Timer timer = new Timer(1000/60, l);
        timer.start();
    }
    public void client() {
        ClientMenu c = new ClientMenu(frame, net, this);
    }
    public void clientStart(int tcp, int udp, String ip) throws UnknownHostException {
        net = new GameNetwork(1, game, tcp, udp, ip);
        game();
    }
    public void game() {
        panel = new GamePanel(game, net);
        frame.setContentPane(panel);
    }


}
