import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 * The control logic and main display panel for game.
 */
public class BallWorld extends Window {

    private static final int UPDATE_RATE = 60;  // Frames per second (fps)
    private Environment particleEnvironment;
    private Environment dataEnvironment;

    private DrawCanvas canvas; // Custom canvas for drawing the box/ball
    private int environmentWidth = 1000;
    private int environmentHeight = 1000;


    private int particleCount = 5;
    private ArrayList<Ball> particles = new ArrayList<>();

    public BallWorld() {

        // Init the ball at a random location (inside the box) and moveAngle

        int radius = 100;
        int speed = 2;

        Random generator = new Random();
        for (int i=0; i<particleCount; i++){
            int x = generator.nextInt(1000-radius+1) + radius;
            int y = generator.nextInt(1000-radius+1) + radius;
            int angle = generator.nextInt(360);
            particles.add(new Ball(x,y,radius,speed,angle));
        }



        //ball = new Ball(x, y, radius, speed, angle);

        // Init the Container Box to fill the screen
        particleEnvironment = new Environment(0, 0, 1000,1000, Color.lightGray, Color.black);
        dataEnvironment = new Environment(1010, 0, 500, 1000, Color.darkGray, Color.black);
        // Init the custom drawing panel for drawing the game

        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas);

        // Start the ball bouncing
        gameStart();
    }

    /** Start the ball bouncing. */
    public void gameStart() {
        // Run the game logic in its own thread.
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    gameUpdate();
                    repaint();
                    try {
                        Thread.sleep(1000 / UPDATE_RATE);
                    } catch (InterruptedException ex) {}
                }
            }
        };
        gameThread.start();  // Invoke GameThread.run()
    }

    /**
     * One game time-step.
     * Update the game objects, with proper collision detection and response.
     */
    public void gameUpdate() {

        for(int i=0 ;i < particleCount; i++){
            particles.get(i).move();
        }
        isColliding();

    }

    /** The custom drawing panel for the bouncing ball (inner class). */
    class DrawCanvas extends JPanel {
        /** Custom drawing codes */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            particleEnvironment.draw(g);
            dataEnvironment.draw(g);
            for (int i=0; i<particleCount; i++) {
                particles.get(i).draw(g);
            }
        }
    }

    public void isColliding(){
        for (int i=0; i<particleCount;i++){
            System.out.print("x" + (i+1) + "=" + particles.get(i).getXPosition() + " ");
            System.out.println("y" + (i+1) + "=" + particles.get(i).getYPosition());
            for (int j=0; j<i; j++){
                int x1 = particles.get(i).getXPosition();
                int x2 = particles.get(j).getXPosition();
                int y1 = particles.get(i).getYPosition();
                int y2 = particles.get(j).getYPosition();
                int r1 = particles.get(i).getRadius();
                int r2 = particles.get(j).getRadius();
                double distance = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2))-(r1+r2);
            }
        }
    }

//    for (int i=0; i<particleCount; i++){
////        int x = generator.nextInt(1000-radius+1) + radius;
////        int y = generator.nextInt(1000-radius+1) + radius;
////        int angle = generator.nextInt(360);
////        particles.add(new Ball(x,y,radius,speed,angle));
////    }
/*
    xVector[i] = Particle.xSpawn();
    yVector[i] = Particle.ySpawn();
            if (i!=0)
    {
        for (int j = 0; j < i; j++)
        {
            double distance = (Math.sqrt(Math.pow((xVector[i]-xVector[j]), 2)+Math.pow((yVector[i]-yVector[j]),2)))-particleDiameter; //d=sqrt((x2-x1)^2+(y2-y1)^2)

            if (distance < 0)
            {
                xVector[i] = Particle.xSpawn();
                yVector[i] = Particle.ySpawn();
                j=-1;
            }
        }
    }

    void generateCoordinates(){
        for (int i=0; i<)
    }

 */
}
