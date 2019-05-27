import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/*
TO DO
warunek stopu
więcej danych do DataCollectora
automatyczne budowanie
user input
 */

// Logika i wyświetlanie całej sumulacji

public class Simulation extends Window {

    //Tło do symulacji i do wyświetlania danych
    private Environment particleEnvironment;
    private Environment dataEnvironment;

    // Ilość, wielkość oraz prędkość cząstek
    static private int particleCount = 50;
    static private int obstacleCount = 15;
    static private int rigidBodyCount = obstacleCount + particleCount;
    static private int destroyedCount = 0;

    private int particleRadius = 20;
    private int obstacleRadius = 2;
    private double particleVelocity = 5d;

    private ArrayList<RigidBody> rigidBodies = new ArrayList<>();
    static private int collisions = 0;

    static private int runTime = 0;

    public Simulation() {

        // Inicjowanie obiektów RigidBody
        Random generator = new Random();
        int ID = 0;
        for (int i = 0; i < particleCount; i++) {
            double x = generator.nextDouble() * 1000 - particleRadius + 1 + particleRadius;
            double y = generator.nextDouble() * 1000 - particleRadius + 1 + particleRadius;
            double angle = generator.nextDouble() * 360;
            rigidBodies.add(new Particle(x, y, particleRadius, particleVelocity, angle, ID));
            ID++;
        }



        ID = 0;
        for (int i = 0; i < obstacleCount; i++) {
            double x = generator.nextDouble() * (1000 - 2 * (100 + obstacleRadius)) + 100 + obstacleRadius;
            double y = generator.nextDouble() * (1000 - 2 * (100 + obstacleRadius)) + 100 + obstacleRadius;
            rigidBodies.add(new Obstacle(x, y, obstacleRadius, ID));
            ID++;
        }

        // Inicjowanie teł
        particleEnvironment = new Environment(0, 0, 1000, 1000, Color.lightGray, Color.black);
        dataEnvironment = new Environment(1010, 0, 500, 1000, Color.darkGray, Color.black);
        // Tworzenie panelu do rysowania
        Panel canvas = new Panel();
        this.setLayout(new BorderLayout());
        this.add(canvas);

        start();
    }

    //Funkcja startująca symulacje
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
                    if(false) {
                        DataCollector dataCollector = new DataCollector();
                        try {
                            dataCollector.saveToFile();
                        } catch (IOException ex) {
                            break;
                        }
                    }
                }
            }
        };
        thread.start();
    }

    //Funkcja odpowiadająca za każdy krok symulacji
    private void update() {
        for (int i = 0; i < particleCount; i++) {
            rigidBodies.get(i).move();
        }

        handleCollision();
        handleRemoval();
        timeInSec();
    }

    // Panel do rysowania
    class Panel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            particleEnvironment.draw(g);
            dataEnvironment.draw(g);
            for (int i = 0; i < rigidBodies.size(); i++) {
                rigidBodies.get(i).draw(g);
            }
            DataCollector.draw(g);
        }
    }

    private void handleCollision() {
        for (int i = 0; i < particleCount; i++) {
            for (int j = i + 1; j < rigidBodyCount; j++) {

                double x2 = rigidBodies.get(i).getXPosition();
                double y2 = rigidBodies.get(i).getYPosition();
                double x1 = rigidBodies.get(j).getXPosition();
                double y1 = rigidBodies.get(j).getYPosition();

                int r1 = rigidBodies.get(i).getRadius();
                int r2 = rigidBodies.get(j).getRadius();

                double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

                if (distance - (r1 + r2) < 0) {

                    double collisionVectorY = y2 - y1;
                    double collisionVectorX = x2 - x1;
                    double theta = Math.atan2(collisionVectorY, collisionVectorX);

                    double tempVX1 = Math.cos(theta) * rigidBodies.get(i).getXVelocity() + Math.sin(theta) * rigidBodies.get(i).getYVelocity();
                    double tempVY1 = -Math.sin(theta) * rigidBodies.get(i).getXVelocity() + Math.cos(theta) * rigidBodies.get(i).getYVelocity();
                    double endVX1, endVY1;

                    if (j < particleCount) {

                        double tempVX2 = Math.cos(theta) * rigidBodies.get(j).getXVelocity() + Math.sin(theta) * rigidBodies.get(j).getYVelocity();
                        double tempVY2 = -Math.sin(theta) * rigidBodies.get(j).getXVelocity() + Math.cos(theta) * rigidBodies.get(j).getYVelocity();


                        // Obrócenie układu z powrotem
                        endVX1 = Math.cos(theta) * tempVX2 - Math.sin(theta) * tempVY1;
                        endVY1 = Math.sin(theta) * tempVX2 + Math.cos(theta) * tempVY1;
                        double endVX2 = Math.cos(theta) * tempVX1 - Math.sin(theta) * tempVY2;
                        double endVY2 = Math.sin(theta) * tempVX1 + Math.cos(theta) * tempVY2;

                        // Przypisanie nowych prędkości

                        rigidBodies.get(j).setXVelocity(endVX2);
                        rigidBodies.get(j).setYVelocity(endVY2);

                    } else {
                        tempVX1 = -tempVX1;
                        endVX1 = Math.cos(theta) * tempVX1 - Math.sin(theta) * tempVY1;
                        endVY1 = Math.sin(theta) * tempVX1 + Math.cos(theta) * tempVY1;
                        if (rigidBodies.get(j).getRadius() <= rigidBodies.get(j).getMaxObstacleRadius()){
                            rigidBodies.get(i).changeSize();
                            rigidBodies.get(j).changeSize();
                        }
                        collisions++;
                    }

                    rigidBodies.get(i).setXVelocity(endVX1);
                    rigidBodies.get(i).setYVelocity(endVY1);


                    // Przesuwamy o część wspólną obydwu cząstek
                    double tempX = Math.cos(theta) * x2 + Math.sin(theta) * y2;
                    double tempY = -Math.sin(theta) * x2 + Math.cos(theta) * y2;
                    double overlap = r1 + r2 - distance + 1;
                    double newX = tempX + overlap;

                    // Obrócenie układu współrzędnych o kąt -theta

                    double endX = Math.cos(theta) * newX - Math.sin(theta) * tempY;
                    double endY = Math.sin(theta) * newX + Math.cos(theta) * tempY;

                    //Ustawienie cząstek na nowych pozycjach
                    rigidBodies.get(i).setXPosition(endX);
                    rigidBodies.get(i).setYPosition(endY);


                }
            }
        }


    }

    private void handleRemoval() {
        for (int i = 0; i < particleCount; i++) {
            if (rigidBodies.get(i).kill()){
                rigidBodies.remove(rigidBodies.get(i));
                particleCount-=1;
                rigidBodyCount-=1;
                destroyedCount++;
            }
        }
    }

    static public int timeInSec() {runTime++; return runTime/50;}
    static public int getCollisions() {return collisions;}
    static public int getDestroyed() {return destroyedCount;}
}