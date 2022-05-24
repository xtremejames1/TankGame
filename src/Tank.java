import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tank {
    private double x, y;
    private boolean shooting;
    private double turretDegree, baseDegree;
    private int width;
    private int height;
    private Color color;
    private double slope;

    public Tank(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.turretDegree = 0;
        this.baseDegree = 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double xPos() {
        return x;
    }

    public double yPos() {
        return y;
    }

    public void setTurretDegree(double degree) {
        this.turretDegree = degree;
    }
    public double getTurretDegree() {
        return turretDegree;
    }

    public void setBaseDegree(double degree) {
        this.baseDegree = degree;
    }

    public void addBaseDegree(double degree) {
        this.baseDegree += degree;
    }

    public double getBaseDegree() {
        return this.baseDegree;
    }

    public boolean isShooting() {
        return this.shooting;
    }

    public void setShooting(boolean bool) {
        this.shooting = bool;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}