import java.awt.*;
import javax.swing.*;

public class Tank extends JPanel {
    private int width;
    private int height;
    public Tank(int width, int height, Color c) {
        this.setBounds(0, 0, width, height);
        this.width = width;
        this.height = height;

    }
    public void paint(Graphics g) {
        Graphics tank = (Graphics2D) g;
        tank.setColor(Color.RED);
        tank.fillOval(0,0, width, height);
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}