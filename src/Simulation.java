import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// Logika i wyświetlanie całej sumulacji

public class Simulation extends Window {

    //Tło do symulacji i do wyświetlania danych
    private Environment particleEnvironment;
    private Environment dataEnvironment;

    // Ilość, wielkość oraz prędkość cząstek
    private int particleCount = 20;
    private int obstacleCount = 2;
    private int particleRadius = 32;
    private int obstacleRadius = 100;
    private double velocity = 3d;

    // Wektor z cząstkami
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    // Lista z dystansem każdej cząstki od każdej
    private double[][] particleParticleDistance = new double[particleCount][particleCount];
    // Lista z dystansem każdej przeszkody od każdej
    private double[][] obstacleParticleDistance = new double[obstacleCount][particleCount];

    public Simulation() {
        // Inicjowanie cząstek
        Random generator = new Random();
        int particleID = 0;
        for (int i = 0; i < particleCount; i++) {
            double x = generator.nextDouble() * 1000 - particleRadius + 1 + particleRadius;
            double y = generator.nextDouble() * 1000 - particleRadius + 1 + particleRadius;
            double angle = generator.nextDouble() * 360;
            particles.add(new Particle(x, y, particleRadius, velocity, angle, particleID));
            particleID++;
        }

        int obstacleID = 0;
        for (int i=0; i<obstacleCount; i++){
            double x = generator.nextDouble() * (1000 -2*(100+obstacleRadius))+100+obstacleRadius;
            double y = generator.nextDouble() * (1000 -2*(100+obstacleRadius))+100+obstacleRadius;
            obstacles.add(new Obstacle (x, y, obstacleRadius, obstacleID));
            obstacleID++;
        }

        //particles.add(new Particle(300, 100, 16, 2, 0, 1));
        //particles.add(new Particle(700,100,16,2,0,2));

        // Inicjowanie teł
        particleEnvironment = new Environment(0, 0, 1000, 1000, Color.lightGray, Color.black);
        dataEnvironment = new Environment(1010, 0, 500, 1000, Color.darkGray, Color.black);

        // Tworzenie panelu do rysowania
        DrawCanvas canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas);

        start();
    }

    //Funkcja startująca symulacje
    public void start() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    update();
                    repaint();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        };
        thread.start();
    }

    //Funkcja odpowiadająca za każdy krok symulacji
    public void update() {
        for (int i = 0; i < particleCount; i++) {
            particles.get(i).move();
        }
        obstacleParticleCollision();
        particleParticleCollision();
    }

    // Panel do rysowania
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            particleEnvironment.draw(g);
            dataEnvironment.draw(g);

            for (int i = 0; i< obstacleCount; i++) {
                obstacles.get(i).draw(g);
            }
            for (int i = 0; i < particleCount; i++) {
                particles.get(i).draw(g);
            }
        }
    }

    public void obstacleParticleCollision(){
        for (int i =0 ;i<obstacleCount;i++){
            for(int j=0; j<particleCount; j++){
                double xObstacle = obstacles.get(i).getXPosition();
                double yObstacle = obstacles.get(i).getYPosition();
                double xParticle = particles.get(j).getXPosition();
                double yParticle = particles.get(j).getYPosition();
                int rObstacle = obstacles.get(i).getRadius();
                int rParticle = particles.get(j).getRadius();

                obstacleParticleDistance[i][j] = Math.sqrt(Math.pow(xObstacle - xParticle, 2) + Math.pow(yObstacle - yParticle, 2));


                if(obstacleParticleDistance[i][j] - (rObstacle+rParticle) < 0){
                    double collisionVectorY = yParticle - yObstacle;
                    double collisionVectorX = xParticle - xObstacle;
                    double theta = Math.atan2(collisionVectorY, collisionVectorX);

                    double tempVX2 = -Math.cos(theta) * particles.get(j).getXVelocity() - Math.sin(theta) * particles.get(j).getYVelocity();
                    double tempVY2 = -Math.sin(theta) * particles.get(j).getXVelocity() + Math.cos(theta) * particles.get(j).getYVelocity();

                    double endVX2 = Math.cos(theta) * tempVX2 - Math.sin(theta) * tempVY2;
                    double endVY2 = Math.sin(theta) * tempVX2 + Math.cos(theta) * tempVY2;

                    particles.get(j).setXVelocity(endVX2);
                    particles.get(j).setYVelocity(endVY2);

                    double tempX = Math.cos(theta) * xParticle + Math.sin(theta) * yParticle;
                    double tempY = -Math.sin(theta) * xParticle + Math.cos(theta) * yParticle;

                    // Przesuwamy o część wspólną obydwu cząstek
                    double overlap = rObstacle + rParticle - obstacleParticleDistance[i][j];
                    double newX = tempX + overlap;

                    // Obrócenie układu współrzędnych o kąt -theta
                    double endX = Math.cos(theta) * newX - Math.sin(theta) * tempY;
                    double endY = Math.sin(theta) * newX + Math.cos(theta) * tempY;

                    //Ustawienie cząstek na nowych pozycjach
                    particles.get(j).setXPosition(endX);
                    particles.get(j).setYPosition(endY);

                    obstacles.get(i).changeSize();
                    particles.get(j).changeSize();


                }
            }
        }
    }

    public void particleParticleCollision() {
        for (int i = 0; i < particleCount; i++) {

            //System.out.print("x" + (i+1) + "=" + particles.get(i).getXPosition() + " ");
            //System.out.println("y" + (i+1) + "=" + particles.get(i).getYPosition());

            for (int j = i + 1; j < particleCount; j++) {

                // Dystans między i-tą a j-tą cząsteczką jest taki sam jak między j-tą a i-tą
                // więc liczymy tylko dystans miedzy i a j
                // między i a i oraz j i j pozostawiamy w tabeli jako 0

                // bierzemy x, y oraz r i-tej i j-tej cząsteczki
                double x1 = particles.get(i).getXPosition();
                double x2 = particles.get(j).getXPosition();
                double y1 = particles.get(i).getYPosition();
                double y2 = particles.get(j).getYPosition();
                int r1 = particles.get(i).getRadius();
                int r2 = particles.get(j).getRadius();

                //liczymy dystans między i-tą a j-tą cząsteczką
                particleParticleDistance[i][j] = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                //ustawiamy dystans od j do i taki sam jak od i do j
                particleParticleDistance[j][i] = particleParticleDistance[i][j];

                //System.out.println("Dystans między cząstką nr " + i + " a cząstką nr " + j + " wynosi: " + particleParticleDistance[i][j]);

                // Zachowanie przy zderzeniu
                if (particleParticleDistance[i][j] - (r1 + r2) <= 0) {

                    double collisionVectorY = y2 - y1;
                    double collisionVectorX = x2 - x1;
                    double theta = Math.atan2(collisionVectorY, collisionVectorX);

                    // Prędkości po zderzeniu, z rotacją układu współrzędnych
                    double tempVX1 = Math.cos(theta) * particles.get(i).getXVelocity() + Math.sin(theta) * particles.get(i).getYVelocity();
                    double tempVY1 = -Math.sin(theta) * particles.get(i).getXVelocity() + Math.cos(theta) * particles.get(i).getYVelocity();
                    double tempVX2 = Math.cos(theta) * particles.get(j).getXVelocity() + Math.sin(theta) * particles.get(j).getYVelocity();
                    double tempVY2 = -Math.sin(theta) * particles.get(j).getXVelocity() + Math.cos(theta) * particles.get(j).getYVelocity();

                    // Obrócenie układu z powrotem
                    double endVX1 = Math.cos(theta) * tempVX2 - Math.sin(theta) * tempVY1;
                    double endVY1 = Math.sin(theta) * tempVX2 + Math.cos(theta) * tempVY1;
                    double endVX2 = Math.cos(theta) * tempVX1 - Math.sin(theta) * tempVY2;
                    double endVY2 = Math.sin(theta) * tempVX1 + Math.cos(theta) * tempVY2;

                    // Przypisanie nowych prędkości
                    particles.get(i).setXVelocity(endVX1);
                    particles.get(i).setYVelocity(endVY1);
                    particles.get(j).setXVelocity(endVX2);
                    particles.get(j).setYVelocity(endVY2);

                    // Ustawienie cząstki po zderzeniu
                    // Obrócenie układu współrzędnych o theta
                    double tempX = Math.cos(theta) * x2 + Math.sin(theta) * y2;
                    double tempY = -Math.sin(theta) * x2 + Math.cos(theta) * y2;

                    // Przesuwamy o część wspólną obydwu cząstek
                    double overlap = r1 + r2 - particleParticleDistance[i][j];
                    double newX = tempX + overlap;

                    // Obrócenie układu współrzędnych o kąt -theta
                    double endX = Math.cos(theta) * newX - Math.sin(theta) * tempY;
                    double endY = Math.sin(theta) * newX + Math.cos(theta) * tempY;

                    //Ustawienie cząstek na nowych pozycjach
                    particles.get(j).setXPosition(endX);
                    particles.get(j).setYPosition(endY);
                }
            }
        }


    }



}
