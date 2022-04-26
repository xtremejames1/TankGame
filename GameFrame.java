import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class GameFrame extends JFrame implements KeyListener, ActionListener {
    private Tank localTank, remoteTank;
    static final int FRAME_HEIGHT = 1000;
    static final int FRAME_WIDTH = 1000;
    private boolean up, down, left, right;
    private Timer timer;
    private GamePanel panel;
    private GameNetwork net;
    private GameInfo game;
    
    public GameFrame(GameNetwork n, GameInfo g) {
        System.out.println("Starting display...");
        panel = new GamePanel();
        game = g;
        net = n;
        localTank = game.getLocalTank();
        remoteTank = game.getRemoteTank();
        this.add(panel);
        this.setLayout(null);
        this.setFocusable(true);
        this.setSize(1000, 1000);
        this.addKeyListener(this);
        this.setTitle("Tank Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        startGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'w') {
            up = true;
        }
        if(e.getKeyChar() == 'a') {
            left = true;
        }
        if(e.getKeyChar() == 's') {
            down = true;
        }
        if(e.getKeyChar() == 'd'){
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'w') {
            up = false;
        }
        if(e.getKeyChar() == 'a') {
            left = false;
        }
        if(e.getKeyChar() == 's') {
            down = false;
        }
        if(e.getKeyChar() == 'd'){
            right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    public void update() {
       if(up && localTank.getY() > 0) {
           localTank.setLocation(localTank.getX(), localTank.getY() - 5);
       }
       if(down && localTank.getY() + localTank.getHeight() <= FRAME_HEIGHT) {
           localTank.setLocation(localTank.getX(), localTank.getY() + 5);
       }
       if(left && localTank.getX() > 0) {
           localTank.setLocation(localTank.getX() - 5, localTank.getY());
       }
       if(right && localTank.getX() + localTank.getWidth() <= FRAME_WIDTH) {
           localTank.setLocation(localTank.getX() + 5, localTank.getY());
       }
       
       remoteTank.setLocation(net.getRTankX(), net.getRTankY());
       net.setTankLoc(localTank.getX(),localTank.getY());
    }

    public boolean isValidCoordinate()
    {
        if(localTank.getX() > 0 && localTank.getY() > 0 && (localTank.getX() + localTank.getWidth() < FRAME_WIDTH) && (localTank.getY() + localTank.getHeight() < FRAME_HEIGHT)) {
            return true;
        }
        return false;
    }
    public void startGame()
    {
        this.add(localTank);
        this.add(remoteTank);
        up = down = left = right = false;
        timer = new Timer(1000/60, this);
        timer.start();
        localTank.setLocation(0,0);
        remoteTank.setLocation(900,900);
    }
    
    public void mainMenu()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        
        
        JLabel title = new JLabel("Tank Game");
        JButton host = new JButton("Host Game");
        JButton client = new JButton("Join Game");
        
        this.add(title);
        this.add(host);
        this.add(client);
    }
    public void hostMenu()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        JLabel title = new JLabel("Host Game:");
        JLabel ip = new JLabel("IP: ");
        JLabel tcp = new JLabel("TCP: ");
        JLabel udp = new JLabel("UDP: ");
        
        this.add(title);
        this.add(ip);
        this.add(tcp);
        this.add(udp);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}
