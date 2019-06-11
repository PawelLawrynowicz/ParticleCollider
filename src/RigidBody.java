import java.awt.*;

/**
 * Konstruktor RigidBody
 */

public abstract class RigidBody {
    protected double x, y;
    protected double xVelocity, yVelocity, velocity;
    protected int radius;
    protected int ID;
    protected int collisionCounter = 0;
    public int maxObstacleRadius = 100;

    /**
     * Rysowanie RigidBody
     */
    public abstract void draw(Graphics g);

    /**
     * Zmiana rozmiaru RigidBody
     */
    public abstract void changeSize();

    /**
     * Poruszanie się RigidBody
     */
    public abstract void move();

    /**
     * Warunek zabicia RigidBody
     */
    public abstract boolean kill();


    /**
     * Gettery i settery
     */
    public void setXPosition(double newX) {
        this.x = newX;
    }

    public void setYPosition(double newY) {
        this.y = newY;
    }

    public void setXVelocity(double newXVelocity) {
        this.xVelocity = newXVelocity;
    }

    public void setYVelocity(double newYVelocity) {
        this.yVelocity = newYVelocity;
    }

    public double getXPosition() {
        return x;
    }

    public double getYPosition() {
        return y;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public int getRadius() { return radius; }

    public int getMaxObstacleRadius() {return maxObstacleRadius; }
}


