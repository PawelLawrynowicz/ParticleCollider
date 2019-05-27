import java.awt.*;

public class Obstacle extends RigidBody {

    public Obstacle(double x, double y, int radius, int ID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.ID = ID;
    }
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.darkGray);
        g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        g.setColor(Color.black);
        g.drawOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);

        String ID = Integer.toString(this.ID);
        g.drawString(ID, (int) x - 5, (int) y + 5);
    }

    public void changeSize(){
        collisionCounter++;
        if(collisionCounter<= maxObstacleRadius)
            radius++;
    }

    public void move(){
        xVelocity = 0;
        yVelocity = 0;
    }

    public boolean kill(){
        return false;
    }

}
