import java.awt.*;

/**
 * Klasa przeszkody
 */

public class Obstacle extends RigidBody {


    /**Konstruktor przeszkody z niezbednymi parametrami
     *
     * @param x pozycja x przeszkody
     * @param y pozycja y przeszkody
     * @param radius promien przeszkody
     * @param ID ID przeszkody
     */
    public Obstacle(double x, double y, int radius, int ID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.ID = ID;
    }

    /** Rysowanie przeszkody*/
    @Override
    public void draw(Graphics g, Color color) {
        super.draw(g, color);
    }

    /** !!!POLIMORFIZM!!! Metoda odpowiedzialna za inkrementacje promienia przeszkody */
    public void changeSize(){
        collisionCounter++;
        if(collisionCounter<= maxObstacleRadius)
            radius++;
    }

    /** Metoda opisująca brak ruchu przeszkody */
    public void move(){
        xVelocity = 0;
        yVelocity = 0;
    }

    /** Metoda ktora mowi kiedy przeszkoda moze zostac zabita */
    public boolean kill(){
        return false;
    }

}
