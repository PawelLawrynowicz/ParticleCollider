import java.awt.Graphics;
import java.awt.Color;

/**
 * Obstacle class.
 */
public class Obstacle extends RigidBody {

    /**
     * Construct of obstacle with necessary parameters.
     *
     * @param x position x of obstacle
     * @param y position y of obstacle
     * @param radius radius of obstacle
     * @param ID ID of obstacle
     */
    public Obstacle(double x, double y, int radius, int ID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.ID = ID;
    }

    /**
     * Method that draws obstacle.
     */
    @Override
    public void draw(Graphics g, Color color) {
        super.draw(g, color);
    }

    /**
     * Polimorphism
     * Method responsible for incrementing radius of obstacle.
     */
    public void changeSize(){
        collisionCounter++;
        if(collisionCounter<= maxObstacleRadius)
            radius++;
    }

    /**
     *  Metoda opisujaca brak ruchu przeszkody Method that determines obstacle stationarity.
     */
    public void move(){
        xVelocity = 0;
        yVelocity = 0;
    }

    /** 
     * Method that determines if obstacle can be killed.
     */
    public boolean kill(){
        return false;
    }

}
