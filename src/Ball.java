import java.awt.*;
import java.util.Random;

public class Ball {
    private int x, y;           // Ball's center x and y (package access)
    private double xVelocity, yVelocity, velocity; // Ball's speed per step in x and y (package access)
    private int radius;         // Ball's radius (package access)

    public Ball(int x, int y, int radius, float velocity, int angle) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.xVelocity = velocity*Math.cos((angle*Math.PI)/180);
        this.yVelocity = velocity*Math.sin((angle*Math.PI)/180);
        this.radius = radius;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.red);
        g.fillOval(x, y , radius, radius);
        g.setColor(Color.black);
        g.drawOval(x, y , radius, radius);
    }


    public void move() {

        x += xVelocity;
        y += yVelocity;

        if (x >= 1000 - radius) {
            xVelocity = -1.05 * xVelocity;
            x = 1000 - radius;
        } else if (x <= 0) {
            xVelocity = -1.05 * xVelocity;
            x = 0;
        }
        if (y >= 1000 - radius) {
            yVelocity = -1.05 * yVelocity;
            y = 1000 - radius;
        } else if (y <= 0) {
            yVelocity = -1.05 * yVelocity;
            y = 0;
        }
    }
    public int getXPosition() {
        return x;
    }
    public int getYPosition() {
        return y;
    }
    public int getRadius() {return radius; }


}