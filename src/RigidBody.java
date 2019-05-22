import java.awt.*;

public abstract class RigidBody {
    protected double x, y;
    protected double xVelocity, yVelocity, velocity;
    protected int radius;
    protected int particleID;
    protected int obstacleSizeIterator = 0;
    protected int collisionCounter = 0;


    public abstract void draw(Graphics g);

    public abstract void changeSize();



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
}


