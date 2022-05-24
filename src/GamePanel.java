import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("ALL")
public class GamePanel extends JPanel implements ActionListener, MouseListener
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
    private String tankData;
    private final int MOVEMENT_SPEED = 5;
    private boolean moveUp, moveDown, rotateRight, rotateLeft;
    private Point mouseLoc;
    private Point topMid, bottomMid;
    private Point2D centerBase, centerTurret;
    private int mouseLocX, mouseLocY;
    private double mouseDist, mouseDistX, mouseDistY;
    private double mouseDegree;
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
        this.addMouseListener(this);
        startGame();
    }

    public GamePanel(GameInfo g, GameNetwork n){
        localTank = g.getLocalTank();
        remoteTank = g.getRemoteTank();
        net = n;
        game = g;


        startGame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        localTank.setShooting(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class moveAction extends AbstractAction {

        String direction;
        int state;
        moveAction(String direction, int state) {
            this.direction = direction;
            this.state = state;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(direction.equals(RIGHT) && state == 1) {
                rotateRight = true;
            }
            if(direction.equals(LEFT) && state == 1) {
                rotateLeft = true;
            }
            if(direction.equals(UP) && state == 1) {
                moveUp = true;
            }
            if(direction.equals(DOWN) && state == 1) {
                moveDown = true;
            }

            if(direction.equals(RIGHT) && state == 0) {
                rotateRight = false;
            }
            if(direction.equals(LEFT) && state == 0) {
                rotateLeft = false;
            }
            if(direction.equals(UP) && state == 0) {
                moveUp = false;
            }
            if(direction.equals(DOWN) && state == 0) {
                moveDown = false;
            }
        }
    }

    public void update() throws InterruptedException {
        centerTurret = new Point2D.Double(localTank.xPos() + 67, localTank.yPos() + 125);
        centerBase = new Point2D.Double(localTank.xPos() + (LOCALTANK_PNG_WIDTH / 2), localTank.yPos() + (LOCALTANK_PNG_HEIGHT / 2));

        mouseLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLoc, this);

        mouseLocX = (int) mouseLoc.getX();
        mouseLocY = (int) mouseLoc.getY();

        mouseDistX = mouseLocX - centerTurret.getX();
        mouseDistY = mouseLocY - centerTurret.getY();

        mouseDegree = angleInRelation(mouseLoc, centerTurret);

        game.setSendData("mX"+mouseLocX+"mY"+mouseLocY+"tX"+((int)localTank.xPos())+"tY"+((int)localTank.yPos())+"e");

        if(game.getReceiveData()!=null) {
            tankData = game.getReceiveData(); //Gets data that is received.

            remoteTank.setLocation(Integer.parseInt(tankData.substring(tankData.indexOf("tX") + 2, tankData.indexOf("tY"))), Integer.parseInt(tankData.substring(tankData.indexOf("tY") + 2, tankData.indexOf("e")))); //set tank coords
            remoteTank.setMouseLocation(Integer.parseInt(tankData.substring(tankData.indexOf("mX") + 2, tankData.indexOf("mY"))), Integer.parseInt(tankData.substring(tankData.indexOf("mY") + 2, tankData.indexOf("tX")))); //set mouse coords
        }
        else {
            Thread.sleep(100);
            System.out.println("Not received data");
        }

        if(moveUp) {
            localTank.setLocation(localTank.xPos() + (MOVEMENT_SPEED * Math.sin(Math.toRadians(localTank.getBaseDegree()))), localTank.yPos() - MOVEMENT_SPEED * Math.cos(Math.toRadians(localTank.getBaseDegree())));
        }
        if(moveDown) {
            localTank.setLocation(localTank.xPos() - (MOVEMENT_SPEED * Math.sin(Math.toRadians(localTank.getBaseDegree()))), localTank.yPos() + MOVEMENT_SPEED * Math.cos(Math.toRadians(localTank.getBaseDegree())));
        }
        if(rotateLeft && localTank.xPos() >= 0) {
            localTank.setBaseDegree(localTank.getBaseDegree() - 5);
        }
        if(rotateRight && localTank.xPos() + localTank.getWidth() <= FRAME_WIDTH) {
            localTank.setBaseDegree(localTank.getBaseDegree() + 5);
        }

        mouseDegree -= localTank.getBaseDegree();


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

        paintLocalBase(g2D);

        localTank.setTurretDegree(mouseDegree);
        g2D.rotate(Math.toRadians(localTank.getTurretDegree()), localTank.xPos() + 67, localTank.yPos() + 125);
        paintLocalTurret(g2D);
    }
    public void paintLocalBase(Graphics2D g2D) {
        g2D.rotate(Math.toRadians(localTank.getBaseDegree()), centerBase.getX(), centerBase.getY());
        g2D.drawImage(blueTankBase, (int) localTank.xPos(), (int) localTank.yPos(), LOCALTANK_PNG_WIDTH, LOCALTANK_PNG_HEIGHT, null);
    }

    public void paintLocalTurret(Graphics2D g2D) {
        g2D.drawImage(blueTankTurret, (int) localTank.xPos()+33, (int) localTank.yPos() - 10, 68, 176, null);
    }

    public void paintRemoteBase(Graphics2D g2D) {
        g2D.drawImage(redTankBase, (int) remoteTank.xPos(), (int) remoteTank.yPos(), REMOTETANK_PNG_WIDTH, REMOTETANK_PNG_HEIGHT, null);
    }

    public void paintRemoteTurret(Graphics2D g2D) {
        g2D.drawImage(redTankTurret, (int) remoteTank.xPos() + 33, (int) remoteTank.yPos() - 10, 68, 176, null);
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            update();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    public double angleInRelation(Point mouseLoc, Point2D tankLoc) {
        double angle = Math.toDegrees(Math.atan2((mouseLoc.getY() - tankLoc.getY()), mouseLoc.getX() - tankLoc.getX()));
        angle += 90;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }
    public void startGame()
    {
        timer = new Timer(1000/60, this);
        timer.start();
    }
}