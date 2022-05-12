import java.awt.*;
import javax.swing.*;

public class Tank{
    private int x, y, mouseX, mouseY;
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

    public void rotate() {

    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setMouseLocation(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
}