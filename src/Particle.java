import java.awt.*;

/**
 * Klasa czastki
 */
public class Particle extends RigidBody {

    /**Konstruktor Particle z niezbednymi parametrami
     *
     * @param x pozycja x czasteczki
     * @param y pozycja y czasteczki
     * @param radius promien cząsteczki
     * @param velocity prędkosc poczatkowa czasteczki
     * @param angle kat, ktory opisuje kierunek poruszania sie czasteczki przy jej spawnowaniu
     * @param ID numer czasteczki
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

    /**Rysowanie czasteczki*/
    @Override
    public void draw(Graphics g, Color color) {
        super.draw(g, color);
    }

    /**Metoda odpowiedzialna za ruch czasteczki
     * Wlaczane jest wygladzanie krawedzi, wybierany kolor wnetrza czasteczki, kolor ramki oraz wypisywane na niej wlasnego ID
     * Pilnowane rowniez jest to, aby czastka nie wyleciala za ramke
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

    /** !!!POLIMORFIZM!!! Metoda odpowiedzialna za dekrementacje promienia czasteczki */
    public void changeSize(){
        radius--;
    }

    /** Metoda ktora mowi kiedy czasteczka moze zostac zabita */
    public boolean kill(){
        if (radius <= 2){
            return true;
        }
        return false;
    }


}