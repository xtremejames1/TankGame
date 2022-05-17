import java.awt.*;

public class Tank {
    private int x, y, mouseX, mouseY, degree;
    private int width;
    private int height;
    private Color color;
    private double slope;
    public Tank(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.degree = 0;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int xPos() {
        return x;
    }
    public int yPos() {
        return y;
    }
    public void setSlope(double slope) {
        this.slope = slope;
    }
    public double getSlope() {
        return slope;
    }
    public void setDegree(int degree) {
        this.degree = degree;
    }
    public void addDegree(int degree) {
        this.degree += degree;
    }
    public int getDegree() {
        return degree;
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