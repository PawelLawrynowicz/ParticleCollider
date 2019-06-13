import java.awt.*;

/**
 * Klasa cząstki
 */
public class Particle extends RigidBody {

    /**Konstruktor Particle z niezbędnymi parametrami
     *
     * @param x - pozycja x cząsteczki
     * @param y - pozycja y cząsteczki
     * @param radius - promień cząsteczki
     * @param velocity - prędkość początkowa cząsteczki
     * @param angle - kąt, który opisuje kierunek poruszania sie cząsteczki przy jej spawnowaniu
     * @param ID - numer cząsteczki
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

    /**Metoda rysująca Particle**/
    @Override
    public void draw(Graphics g, Color color) {
        super.draw(g, color);
    }

    /**Metoda odpowiedzialna za ruch cząsteczki
     * Włączane jest wygładzanie krawędzi, wybierany kolor wnętrza cząsteczki, kolor ramki oraz wypisywane na niej własnego ID
     * Pilnowane również jest to, aby cząstka nie wyleciała za ramke
     */
    public void move() {

        x += xVelocity;
        y += yVelocity;


        //Zachowanie przy spotkaniu ze ścianą
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

    /** Metoda odpowiedzialna za dekrementację rozmiaru cząsteczki */
    public void changeSize(){
        radius--;
    }

    /** Metoda która mówi kiedy cząsteczka może zostać zabita */
    public boolean kill(){
        if (radius <= 2){
            return true;
        }
        return false;
    }


}