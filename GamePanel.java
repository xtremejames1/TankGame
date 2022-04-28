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

    public void update() {
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


        remoteTank.setLocation(net.getRTankX(), net.getRTankY());
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
        g2D.drawImage(redTankBase, remoteTank.getX(), remoteTank.getY(), 134, 204, null);
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
    public void startGame()
    {
        timer = new Timer(1000/60, this);
        timer.start();
    }
}
