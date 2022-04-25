import java.awt.*;
import javax.swing.*;

public class Tank extends JLabel {
    private int width;
    private int height;
    public Tank(int width, int height, Color c) {
        this.setBounds(0, 0, width, height);
        this.setBackground(c);
        this.setOpaque(true);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}