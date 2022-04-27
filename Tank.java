import java.awt.*;
import javax.swing.*;

public class Tank extends JPanel {
    private int width;
    private int height;
    private Color color;
    public Tank(int width, int height, Color color) {
        this.setBounds(0, 0, width, height);
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(0,0, width, height);
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}