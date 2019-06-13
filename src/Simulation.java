import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Logika oraz wyswietlanie calej symulacji
 */

public class Simulation extends Window {

    /**Tlo do symulacji i do wyswietlania danych*/
    private Environment particleEnvironment;
    private Environment dataEnvironment;

    /**Ilosc, wielkosc oraz predkosc czastek i przeszkod*/
    static private int obstacleCount = 15;
    static private int rigidBodyCount = obstacleCount + particleCount;
    static private int destroyedCount = 0;

    private int particleRadius = 20;
    private int obstacleRadius = 10;
    private double particleVelocity = 10d;

    private ArrayList<RigidBody> rigidBodies = new ArrayList<>();
    static private int collisions = 0;

    private static String endCause;

    public Simulation() {
        initiateSimulation();
        Panel canvas = new Panel();
        this.setLayout(new BorderLayout());
        this.add(canvas);
        start();
    }

    /**
     * Petla symulacji
     */
    private void start() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    update();
                    repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (endSimulation()) {
                        DataCollector dataCollector = new DataCollector();
                        try {
                            dataCollector.saveToFile();
                        } catch (IOException ex) {
                            break;
                        }
                        break;
                    }
                }
            }
        };
        thread.start();
    }


    /**
     * Co dzieje sie co klatke
     */
    private void update() {
        for (int i = 0; i < particleCount; i++) {
            rigidBodies.get(i).move();
        }

        handleCollision();
        handleRemoval();
    }

    /**
     * Spawnowanie przeszkod, czasteczek i tel
     */
    private void initiateSimulation() {
        Random generator = new Random();

        /**Inicjowanie czastek*/
        int ID = 0;
        for (int i = 0; i < particleCount; i++) {
            double x = generator.nextDouble() * 1000 - particleRadius + 1 + particleRadius;
            double y = generator.nextDouble() * 1000 - particleRadius + 1 + particleRadius;
            double angle = generator.nextDouble() * 360;
            rigidBodies.add(new Particle(x, y, particleRadius, particleVelocity, angle, ID));
            ID++;
        }

        /**Inicjowanie przeszkod*/
        ID = 0;
        for (int i = 0; i < obstacleCount; i++) {
            double x = generator.nextDouble() * (1000 - 2 * (100 + obstacleRadius)) + 100 + obstacleRadius;
            double y = generator.nextDouble() * (1000 - 2 * (100 + obstacleRadius)) + 100 + obstacleRadius;
            rigidBodies.add(new Obstacle(x, y, obstacleRadius, ID));
            ID++;
        }

        //Inicjowanie tel
        particleEnvironment = new Environment(0, 0, 1000, 1000, Color.lightGray, Color.black);
        dataEnvironment = new Environment(1010, 0, 500, 1000, Color.darkGray, Color.black);
    }

    /**
     * Panel do rysowania
     */
    class Panel extends JPanel {
        /**Wywolywanie metod draw() obiektow w odpowiedniej kolejnosci*/
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            particleEnvironment.draw(g);
            dataEnvironment.draw(g);
            for (int i = 0; i < rigidBodies.size(); i++) {
                if (i < particleCount){
                    rigidBodies.get(i).draw(g, Color.red);
                }
                else{
                    rigidBodies.get(i).draw(g, Color.darkGray);
                }

            }
            DataCollector.draw(g);
        }
    }

    /**
     * Zderzenia czastek z czastkami i przeszkodami. Badanie czy zachodzi zderzenie, jezeli tak obliczana jest nowa predkosc dla zderzajacych sie obiektow.
     * Do ustalenia nowych predkosci uzywamy macierzy przejscia R^2 -> R^2. "Obracamy" uklad wspolrzednych tak, aby wektor predkosci wyznaczal os X.
     * Nastepnie sprawdzamy czy jest to kolizja czastka-czastka, czy nie.
     * Potem rozbijamy predkosc w nowym ukladzie wspolrzednych na skladowe i obliczamy predkosci po zderzeniu.
     * Na samym końcu metody rozdzielamy czastki, aby nie nachodzily na siebie po zderzeniu.
     */
    private void handleCollision() {
        for (int i = 0; i < particleCount; i++) {
            for (int j = i + 1; j < rigidBodyCount; j++) {

                //Pobranie pozycji oraz promieni obiektow
                double x2 = rigidBodies.get(i).getXPosition();
                double y2 = rigidBodies.get(i).getYPosition();
                double x1 = rigidBodies.get(j).getXPosition();
                double y1 = rigidBodies.get(j).getYPosition();
                int r1 = rigidBodies.get(i).getRadius();
                int r2 = rigidBodies.get(j).getRadius();

                //Obliczenie dystansu miedzy kazdym a kazdym obiektem (miedzy ich srodkami). Kiedy znamy dystans miedzy A a B, nie liczyby dystansu miedzy B a A.
                double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

                if (distance - (r1 + r2) < 0) {


                    double collisionVectorY = y2 - y1;
                    double collisionVectorX = x2 - x1;
                    double theta = Math.atan2(collisionVectorY, collisionVectorX);

                    // Wektory predkosci pierwszego ciala po obroceniu ukladu wspolrzednych
                    double tempVX1 = Math.cos(theta) * rigidBodies.get(i).getXVelocity() + Math.sin(theta) * rigidBodies.get(i).getYVelocity();
                    double tempVY1 = -Math.sin(theta) * rigidBodies.get(i).getXVelocity() + Math.cos(theta) * rigidBodies.get(i).getYVelocity();

                    // Wektory predkosci po zderzeniu
                    double endVX1, endVY1, endVX2, endVY2;

                    // Zderzenie Particle-Particle
                    if (j < particleCount) {

                        // Wektory predkosci drugiego ciala po obroceniu ukladu wspolrzednych
                        double tempVX2 = Math.cos(theta) * rigidBodies.get(j).getXVelocity() + Math.sin(theta) * rigidBodies.get(j).getYVelocity();
                        double tempVY2 = -Math.sin(theta) * rigidBodies.get(j).getXVelocity() + Math.cos(theta) * rigidBodies.get(j).getYVelocity();

                        // Obrocenie ukladu z powrotem
                        endVX1 = Math.cos(theta) * tempVX2 - Math.sin(theta) * tempVY1;
                        endVY1 = Math.sin(theta) * tempVX2 + Math.cos(theta) * tempVY1;
                        endVX2 = Math.cos(theta) * tempVX1 - Math.sin(theta) * tempVY2;
                        endVY2 = Math.sin(theta) * tempVX1 + Math.cos(theta) * tempVY2;

                        // Przypisanie nowych predkosci
                        rigidBodies.get(j).setXVelocity(endVX2);
                        rigidBodies.get(j).setYVelocity(endVY2);

                    }

                    // Zderzenie Particle-Obstacle
                    else {

                        // Wektory predkosci pierwszego ciala po obroceniu ukladu wspolrzednych
                        tempVX1 = -tempVX1;

                        // Obrocenie ukladu z powrotem
                        endVX1 = Math.cos(theta) * tempVX1 - Math.sin(theta) * tempVY1;
                        endVY1 = Math.sin(theta) * tempVX1 + Math.cos(theta) * tempVY1;

                        //Zmiana rozmiaru po zderzeniu
                        if (rigidBodies.get(j).getRadius() <= rigidBodies.get(j).getMaxObstacleRadius()) {
                            rigidBodies.get(i).changeSize();
                            rigidBodies.get(j).changeSize();
                        }
                        collisions++;
                    }

                    // Przypisanie nowych predkosci
                    rigidBodies.get(i).setXVelocity(endVX1);
                    rigidBodies.get(i).setYVelocity(endVY1);

                    //Naprawa glitcha ze sklejajacymi sie kulkami
                    // Przesuwamy oba obiekty o ich czesc wspolna
                    double tempX = Math.cos(theta) * x2 + Math.sin(theta) * y2;
                    double tempY = -Math.sin(theta) * x2 + Math.cos(theta) * y2;
                    double overlap = r1 + r2 - distance + 1;
                    double newX = tempX + overlap;

                    // Obrocenie ukladu wspolrzednych o kat -theta
                    double endX = Math.cos(theta) * newX - Math.sin(theta) * tempY;
                    double endY = Math.sin(theta) * newX + Math.cos(theta) * tempY;

                    //Ustawienie czastek na nowych pozycjach
                    rigidBodies.get(i).setXPosition(endX);
                    rigidBodies.get(i).setYPosition(endY);
                }
            }
        }


    }

    /**
     * Usuniecie czastki
     */
    private void handleRemoval() {
        //Dla kazdego Particle sprawdzane jest czy mozna go zabic, jesli tak to jest usuwany z tablicy
        for (int i = 0; i < particleCount; i++) {
            if (rigidBodies.get(i).kill()) {
                rigidBodies.remove(rigidBodies.get(i));
                particleCount -= 1;
                rigidBodyCount -= 1;
                destroyedCount++;
            }
        }
    }

    /**
     * Warunek stopu
     */
    private boolean endSimulation() {

        //Symulacja kończy sie, gdy czas przekroczy 120s, wszystkie czastku umra lub wszystkie przeszkody urosna maksymalnie
        int totalObstacleRadius = 0;
        for (int i = particleCount; i < rigidBodyCount; i++) {
            totalObstacleRadius += rigidBodies.get(i).getRadius();
        }
        if (DataCollector.getCurrentTime() == 120) {
            endCause = "End of time";
            return true;
        } else if (particleCount == 0) {
            endCause = "All particles died";
            return true;

        } else if (totalObstacleRadius >= 100 * obstacleCount) {
            endCause = "All obstacles reached full size";
            return true;
        }
        return false;
    }

    static public int getCollisions() {
        return collisions;
    }

    static public int getDestroyed() {
        return destroyedCount;
    }

    static public String getEndCause() {
        return endCause;
    }
}