import java.awt.*;
import javax.swing.*;

public class Tank{
    private int x, y;
    private int width;
    private int height;
    private Color color;
    public Tank(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}