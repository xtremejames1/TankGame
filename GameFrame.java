import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class GameFrame{

    private JFrame frame = new JFrame();
    static final int FRAME_HEIGHT = 720;
    static final int FRAME_WIDTH = 1280;
    private GamePanel panel;
    
    public GameFrame(GameInfo g, GameNetwork n) {
        System.out.println("Starting display...");
        panel = new GamePanel(g, n);
        frame.setLayout(null);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Tank Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);


    }
}
