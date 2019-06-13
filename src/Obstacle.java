import java.awt.*;

public class Obstacle extends RigidBody {


    /**Konstruktor przeszkody z niezbędnymi parametrami
     *
     * @param x - pozycja x przeszkody
     * @param y - pozycja y przeszkody
     * @param radius - promień przeszkody
     * @param ID - ID przeszkody
     */
    public Obstacle(double x, double y, int radius, int ID) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.ID = ID;
    }

    @Override
    public void draw(Graphics g, Color color) {
        super.draw(g, color);
    }


    /** Metoda odpowiedzialna za inkrementację cząsteczki */
    public void changeSize(){
        collisionCounter++;
        if(collisionCounter<= maxObstacleRadius)
            radius++;
    }

    //Metoda od ruchu Obstacle
    public void move(){
        xVelocity = 0;
        yVelocity = 0;
    }

    /** Metoda która mówi kiedy cząsteczka może zostać zabita */
    public boolean kill(){
        return false;
    }

}
