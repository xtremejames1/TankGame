import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;
import javax.swing.*;


public class GameFrame{
    //TODO RESET NETWORK WITH BACK BUTTON
    private JFrame frame = new JFrame(); //creates the frame
    static final int FRAME_HEIGHT = 720; //should be changed in settings later
    static final int FRAME_WIDTH = 1280; //should be changed in settings later
    private GamePanel panel; //used for the actual game
    // private GameNetwork net; //game network
    private GameInfo game; //the game information

    /**
     * Starts a GameFrame and initialized the JFrame properly
     * @param g GameInfo object that it should pull the information from
     */
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
        //mainMenu();
        game();
    }

    /**
     * Sets JFrame to MainMenu panel
     */
    public void mainMenu() {
        MainMenu m = new MainMenu(frame, game, this); //creates MainMenu object
    }
    /**
     * Sets JFrame to host menu panel
     */
    public void host() {
        //net = new GameNetwork(0, game, 1235, 1234); //creates GameNetwork
        //HostMenuPanel h = new HostMenuPanel(frame,this, net.getIP(), net.getTCP(), net.getUDP()); //creates host panel

        //ClientThread client = new ClientThread(net); //creates client thread
        //ServerThread server = new ServerThread(net); //creates server thread

        //client.start(); //starts client thread
        //server.start(); //starts server thread

        ActionListener l = new ActionListener() { //checks if that the timer is running defined later
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getClientFound()) //if found remote client, it starts the game
                    game();
            }
        };
        Timer timer = new Timer(1000/60, l); //creates a timer that repeats 60 times every second
        timer.start(); //starts timer
    }

    public void game() {
        panel = new GamePanel(game); //Creates GamePanel object
        panel.setVisible(true);
        frame.setContentPane(panel);
    }
}


