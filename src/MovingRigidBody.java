import java.awt.*;

public abstract class MovingRigidBody extends RigidBody{
    protected double xVelocity, yVelocity, velocity;

    public abstract void draw(Graphics g);
    public abstract void changeSize();
    //public abstract boolean canBeKilled();
    public abstract void move();

    public void setXVelocity(double newXVelocity) {
        this.xVelocity = newXVelocity;
    }

    public void setYVelocity(double newYVelocity) {
        this.yVelocity = newYVelocity;
    }

    public double getXVelocity() { return xVelocity; }

    public double getYVelocity() { return yVelocity; }

}
