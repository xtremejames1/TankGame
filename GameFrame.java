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
    private udpBaseClient_2 client;
    private udpBaseServer_2 server;
    public GameFrame(udpBaseClient_2 c, udpBaseServer_2 s) {
        client = c;
        server = s;
        localTank = new Tank(50, 50, Color.blue);
        remoteTank = new Tank(50,50, Color.red);
        this.setLayout(null);
        this.setFocusable(true);
        this.setSize(1000, 1000);
        this.add(localTank);
        this.add(remoteTank);
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
       
       remoteTank.setLocation(server.getTankX(), server.getTankY());
       client.setTankLoc(localTank.getX(),localTank.getY());
    }

    public boolean isValidCoordinate() {
        if(localTank.getX() > 0 && localTank.getY() > 0 && (localTank.getX() + localTank.getWidth() < FRAME_WIDTH) && (localTank.getY() + localTank.getHeight() < FRAME_HEIGHT)) {
            return true;
        }
        return false;
    }
    public void startGame() {
        up = down = left = right = false;
        timer = new Timer(1000/60, this);
        timer.start();
        localTank.setLocation(0,0);
        remoteTank.setLocation(900,900);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}
