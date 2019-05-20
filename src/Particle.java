import java.awt.*;
import java.util.*;
import java.util.Random;

public class Particle extends RigidBody {


    public Particle(double x, double y, int radius, double velocity, double angle, int particleID) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.xVelocity = velocity * Math.cos((angle * Math.PI) / 180);
        this.yVelocity = velocity * Math.sin((angle * Math.PI) / 180);
        this.radius = radius;
        this.particleID = particleID;
    }

    public void draw(Graphics g, Color userColor) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(userColor);
        g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        g.setColor(Color.black);
        g.drawOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        String ID = Integer.toString(particleID);
        g.drawString(ID, (int) x - 5, (int) y + 5);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(DEFAULT_PARTICLE_COLOR);
        g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        g.setColor(Color.black);
        g.drawOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        String ID = Integer.toString(particleID);
        g.drawString(ID, (int) x - 5, (int) y + 5);
    }

    public void move() {

        x += xVelocity;
        y += yVelocity;


        if (x + radius + 2 > 1000) {
            xVelocity = -xVelocity;
            x = 1000 - radius - 2 - 1;

        } else if (x - radius < 0) {
            xVelocity = -xVelocity;
            x = radius + 2;
        }
        if (y + radius > 1000) {
            yVelocity = -yVelocity;
            y = 1000 - radius - 2 - 1;

        } else if (y - radius < 0) {
            yVelocity = -yVelocity;
            y = radius + 2;
        }

    }

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

    public int getRadius() {
        return radius;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public double getVelocity() {
        return velocity;
    }
}