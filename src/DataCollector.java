import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataCollector {
    static private long startTime = System.currentTimeMillis();

    static public void draw(Graphics g) { //in dataEnvironment DataCollector.draw();
        g.setColor(Color.WHITE);
        String howManyCollisions = Integer.toString(Simulation.getCollisions());
        g.drawString("Collision count: " + howManyCollisions, 1020, 20);
        String howMuchTime = Long.toString((System.currentTimeMillis() - startTime) / 1000);
        g.drawString("Time in seconds: " + howMuchTime, 1020, 40);
        String howManyDestroyed = Integer.toString(Simulation.getDestroyed());
        g.drawString("Particles destroyed: " + howManyDestroyed, 1020, 60);
    }; //rysuje dane

   void saveToFile() throws IOException {
        String fileContent = Integer.toString(Simulation.getCollisions());
        BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt"));
        writer.write(fileContent);
        writer.close();
   }

   public static long getCurrentTime(){return (System.currentTimeMillis() - startTime)/1000;}
}
