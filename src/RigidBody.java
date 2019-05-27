import java.awt.*;

public abstract class RigidBody {
    protected double x, y;
    protected double xVelocity, yVelocity, velocity;
    protected int radius;
    protected int ID;
    protected int obstacleSizeIterator = 0;
    protected int collisionCounter = 0;
    protected Color rigidBodyColor;
    public int maxObstacleRadius = 100;

    public abstract void draw(Graphics g);

    public abstract void changeSize();

    public abstract void move();

    public abstract boolean kill();



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


