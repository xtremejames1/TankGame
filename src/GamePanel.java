import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("ALL")
public class GamePanel extends JPanel implements ActionListener
{
    static final int FRAME_HEIGHT = 720;
    static final int FRAME_WIDTH = 1280;
    static final int LOCALTANK_PNG_WIDTH = 134;
    static final int LOCALTANK_PNG_HEIGHT = 204;
    static final int REMOTETANK_PNG_WIDTH = 134;
    static final int REMOTETANK_PNG_HEIGHT = 204;
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
    private Point mouseLoc;
    private Point topMid, bottomMid;
    private int mouseX, mouseY;
    private AffineTransform transform;
    private GameInfo game;
    private GameNetwork net;
    private Timer timer;
    private Tank localTank, remoteTank;
    private BufferedImage blueTankBase, redTankBase, blueTankTurret, redTankTurret;
    private moveAction leftpress, rightpress, uppress, downpress, leftrelease, rightrelease, uprelease, downrelease;
    public GamePanel(GameInfo g) {
        game = g;
        localTank = g.getLocalTank();
        remoteTank = g.getRemoteTank();

        try {
            blueTankBase = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/bluetankbase.png")));
            redTankBase = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/redtankbase.png")));
            blueTankTurret = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/bluetankturret.png")));
            redTankTurret = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/redtankturret.png")));
        } catch (IOException e) {
            System.out.println(e);
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
                moveRight = true;
            }
            if(direction.equals(LEFT)&&state==1) {
                moveLeft = true;
            }
            if(direction.equals(UP) && state == 1) {
                moveUp = true;
            }
            if(direction.equals(DOWN) && state == 1) {
                moveDown = true;
            }

            if(direction.equals(RIGHT) && state == 0) {
                moveRight = false;
            }
            if(direction.equals(LEFT) && state == 0) {
                moveLeft = false;
            }
            if(direction.equals(UP) && state == 0) {
                moveUp = false;
            }
            if(direction.equals(DOWN) && state==0) {
                moveDown = false;
            }
        }
    }

    public void update() {
        mouseLoc = MouseInfo.getPointerInfo().getLocation();
        mouseX = (int) mouseLoc.getX();
        mouseY = (int) mouseLoc.getY();

        topMid = new Point(localTank.xPos() + (int) (LOCALTANK_PNG_WIDTH / 2), localTank.yPos());
        bottomMid = new Point((int) localTank.xPos() + (LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + LOCALTANK_PNG_HEIGHT);

        localTank.setSlope((bottomMid.getY() - topMid.getY()) / (bottomMid.getX() - topMid.getX()));
        transform = new AffineTransform();
        //System.out.println(localTank.getHeight());
        //System.out.println("TOP X: "+topMid.getX());
        //System.out.println("TOP Y: "+topMid.getY());
        //System.out.println("BOTTOM X: "+bottomMid.getX());
        //System.out.println("BOTTOM Y: "+bottomMid.getY());
        //System.out.println("bottomMid "+ bottomMid);
        //System.out.println(localTank.getSlope());
        //localTank.setMouseLocation((int) mouseLoc.getX(), (int) mouseLoc.getY());

        if(moveUp && localTank.yPos() >= 0) {
            localTank.setLocation(localTank.xPos(), localTank.yPos() - 5);
        }
        if(moveDown && localTank.yPos() + localTank.getHeight() <= FRAME_HEIGHT) {
            localTank.setLocation(localTank.xPos(), localTank.yPos() + 5);
        }
        if(moveLeft && localTank.xPos() >= 0) {
            localTank.setLocation(localTank.xPos() - 5, localTank.yPos());
        }
        if(moveRight && localTank.xPos() + localTank.getWidth() <= FRAME_WIDTH) {
            localTank.setLocation(localTank.xPos() + 5, localTank.yPos());
        }
        /*
        if(mouseX <= localTank.xPos()) {
            transform.rotate(Math.toRadians(localTank.getDegree() + 10), localTank.xPos() + ((int) LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + ((int) LOCALTANK_PNG_HEIGHT) / 2);
            localTank.addDegree(10);
        }
        if(mouseX >= localTank.xPos()) {
            transform.rotate(Math.toRadians(localTank.getDegree() - 10), localTank.xPos() + ((int) LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + ((int) LOCALTANK_PNG_HEIGHT) / 2);
            localTank.addDegree(-10);
        }
        */
        this.setBackground(Color.white);
        repaint();
    }
    @Override
    public void paint(Graphics g) {
        this.setBackground(Color.white);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setBackground(Color.white);
        g2D.setColor(Color.white);


        g2D.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        //g2D.setTransform(transform);

        if(mouseX <= localTank.xPos()) {
            g2D.rotate(Math.toRadians(localTank.getDegree() - 10), localTank.xPos() + ((int) LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + ((int) LOCALTANK_PNG_HEIGHT) / 2);
            //transform.rotate(Math.toRadians(-10), localTank.xPos() + ((int) LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + ((int) LOCALTANK_PNG_HEIGHT) / 2);
            localTank.addDegree(-10);
        }
        if(mouseX >= localTank.xPos()) {
            g2D.rotate(Math.toRadians(localTank.getDegree() + 10), localTank.xPos() + ((int) LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + ((int) LOCALTANK_PNG_HEIGHT) / 2);
            //transform.rotate(Math.toRadians(10), localTank.xPos() + ((int) LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + ((int) LOCALTANK_PNG_HEIGHT) / 2);
            localTank.addDegree(10);
        }
        paintLocalTank(g2D);
    }
    public void paintLocalTank(Graphics2D g2D) {
        g2D.drawImage(blueTankBase, localTank.xPos(), localTank.yPos(), LOCALTANK_PNG_WIDTH, LOCALTANK_PNG_HEIGHT, null);
        g2D.drawImage(blueTankTurret, localTank.xPos()+33, localTank.yPos() - 10, 68, 176, null);
    }

    public void paintRemoteTank(Graphics2D g2D) {
        g2D.drawImage(redTankBase, remoteTank.xPos(), remoteTank.yPos(), REMOTETANK_PNG_WIDTH, REMOTETANK_PNG_HEIGHT, null);
        g2D.drawImage(redTankTurret, remoteTank.xPos()+33, remoteTank.yPos() - 10, 68, 176, null);
    }

    /*
    public AffineTransform rotate() {
        return null;
    }
    */
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
