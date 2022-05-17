import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("ALL")
public class GamePanel extends JPanel implements ActionListener
{
    static final int FRAME_HEIGHT = 720;
    static final int FRAME_WIDTH = 1280;
    private int focus = JComponent.WHEN_IN_FOCUSED_WINDOW;
    //private boolean up, down, left, right;

    private final String RIGHT = "right";
    private final String LEFT = "left";
    private final String UP = "up";
    private final String DOWN = "down";
    private final String RIGHTP = "rightp";
    private final String LEFTP = "leftp";
    private final String UPP = "upp";
    private final String DOWNP = "downp";
    private final String RIGHTR = "rightr";
    private final String LEFTR = "leftr";
    private final String UPR = "upr";
    private final String DOWNR = "downr";
    private boolean moveUp, moveDown, moveRight, moveLeft;
    private GameInfo game;
    private GameNetwork net;
    private Timer timer;
    private Tank localTank, remoteTank;
    private BufferedImage blueTankBase, redTankBase, blueTankTurret, redTankTurret;
    private moveAction leftpress, rightpress, uppress, downpress, leftrelease, rightrelease, uprelease, downrelease;

    public GamePanel(GameInfo g, GameNetwork n){
        localTank = g.getLocalTank();
        remoteTank = g.getRemoteTank();
        net = n;
        game = g;
        try {
            blueTankBase = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/bluetankbase.png")));
            redTankBase = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/redtankbase.png")));
            blueTankTurret = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/bluetankturret.png")));
            redTankTurret = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/redtankturret.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        leftpress = new moveAction(LEFT, 1);
        rightpress = new moveAction(RIGHT, 1);
        uppress = new moveAction(UP, 1);
        downpress = new moveAction(DOWN, 1);


        leftrelease = new moveAction(LEFT, 0);
        rightrelease = new moveAction(RIGHT, 0);
        uprelease = new moveAction(UP, 0);
        downrelease = new moveAction(DOWN, 0);

        this.getInputMap(focus).put(KeyStroke.getKeyStroke("pressed A"), LEFTP);
        this.getActionMap().put(LEFTP, leftpress);
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("pressed D"), RIGHTP);
        this.getActionMap().put(RIGHTP, rightpress);
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("pressed W"), UPP);
        this.getActionMap().put(UPP, uppress);
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("pressed S"), DOWNP);
        this.getActionMap().put(DOWNP, downpress);

        this.getInputMap(focus).put(KeyStroke.getKeyStroke("released A"), LEFTR);
        this.getActionMap().put(LEFTR, leftrelease);
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("released D"), RIGHTR);
        this.getActionMap().put(RIGHTR, rightrelease);
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("released W"), UPR);
        this.getActionMap().put(UPR, uprelease);
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("released S"), DOWNR);
        this.getActionMap().put(DOWNR, downrelease);

        startGame();
    }

    private class moveAction extends AbstractAction {

        String direction;
        int state;
        moveAction(String d, int s) {
            direction = d;
            state = s;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(direction.equals(RIGHT)&&state==1) {
                moveRight=true;
            }
            if(direction.equals(LEFT)&&state==1) {
                moveLeft=true;
            }
            if(direction.equals(UP)&&state==1) {
                moveUp=true;
            }
            if(direction.equals(DOWN)&&state==1) {
                moveDown=true;
            }

            if(direction.equals(RIGHT)&&state==0) {
                moveRight=false;
            }
            if(direction.equals(LEFT)&&state==0) {
                moveLeft=false;
            }
            if(direction.equals(UP)&&state==0) {
                moveUp=false;
            }
            if(direction.equals(DOWN)&&state==0) {
                moveDown=false;
            }
        }
    }

    public void update() throws InterruptedException {
        if(moveUp) {
            localTank.setLocation(localTank.getX(), localTank.getY() - 5);
        }
        if(moveDown) {
            localTank.setLocation(localTank.getX(), localTank.getY() + 5);
        }
        if(moveLeft && localTank.getX() > 0) {
            localTank.setLocation(localTank.getX() - 5, localTank.getY());
        }
        if(moveRight && localTank.getX() + localTank.getWidth() <= FRAME_WIDTH) {
            localTank.setLocation(localTank.getX() + 5, localTank.getY());
        }

        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int mouseX = (int) b.getX();
        int mouseY = (int) b.getY();
        int tankX = localTank.getX();
        int tankY = localTank.getY();

        game.setSendData("mX"+mouseX+"mY"+mouseY+"tX"+tankX+"tY"+tankY);

        if(game.getReceiveData()!=null) {
            String tankData = game.getReceiveData(); //Gets data that is received.
            System.out.println(tankData);
            String tankXString = tankData.substring(tankData.indexOf("tX") + 2, tankData.indexOf("tY")); //Finds tank coords
            String tankYString = tankData.substring(tankData.indexOf("tY") + 2);

            String mouseXString = tankData.substring(tankData.indexOf("mX") + 2, tankData.indexOf("mY")); //Find mouse coords
            String mouseYString = tankData.substring(tankData.indexOf("mY") + 2, tankData.indexOf("tX"));

            remoteTank.setLocation(Integer.parseInt(tankXString), Integer.parseInt(tankYString)); //set tank coords
            remoteTank.setMouseLocation(Integer.parseInt(mouseXString), Integer.parseInt(mouseYString)); //set mouse coords
        }
        else {
            Thread.sleep(100);
            System.out.println("Not received data");
        }

        this.setBackground(Color.white);
        repaint();
    }
    @Override
    public void paint(Graphics g)
    {
        this.setBackground(Color.white);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setBackground(Color.white);
        g2D.setColor(Color.white);
        g2D.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        g2D.drawImage(blueTankBase, localTank.getX(), localTank.getY(), 134, 204, null);
        g2D.drawImage(blueTankTurret, localTank.getX()+33, localTank.getY()-10, 68, 176, null);

        g2D.drawImage(redTankBase, remoteTank.getX(), remoteTank.getY(), 134, 204, null);
        g2D.drawImage(redTankTurret, remoteTank.getX()+33, remoteTank.getY()-10, 68, 176, null);
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            update();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    public void startGame()
    {
        timer = new Timer(1000/30, this);
        timer.start();
    }
}
