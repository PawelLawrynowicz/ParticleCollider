import java.awt.Graphics;
import java.awt.Color;

/**
 * Particle class
 */
public class Particle extends RigidBody {

    /**
     * Particle constructor with necessary parameters.
     *
     * @param x position x of particle
     * @param y position y of particle
     * @param radius particle radius
     * @param velocity particle initial velocity
     * @param angle the angle between bottom screen border and particle velocity at spawn
     * @param ID particle id
     */
    public Particle(double x, double y, int radius, double velocity, double angle, int ID) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.xVelocity = velocity * Math.cos((angle * Math.PI) / 180);
        this.yVelocity = velocity * Math.sin((angle * Math.PI) / 180);
        this.radius = radius;
        this.ID = ID;
    }

    /**
     * Method that draws particle.
     */
    @Override
    public void draw(Graphics g, Color color) {
        super.draw(g, color);
    }

    /**
     * Method that determines particle movement.
     */
    public void move() {

        x += xVelocity;
        y += yVelocity;

        // Particle behaviour at collision with frame.
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

    /**
     * Polimorphism
     * Method responsible for decrementing particle radius.
     */
    public void changeSize(){
        radius--;
    }

    /** 
     * Method that determines if particle can be killed.
     */
    public boolean kill(){
        if (radius <= 2){
            return true;
        }
        return false;
    }


}