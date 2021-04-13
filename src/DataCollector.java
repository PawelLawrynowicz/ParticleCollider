import java.awt.Graphics;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class responsible for gathering data, drawing and saving results to .txt file.
 */
public class DataCollector {
    static private long startTime = System.currentTimeMillis();

    /**
     * Drawing data.
     */
    static public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        String howManyCollisions = Integer.toString(Simulation.getCollisions());
        g.drawString("Collision count: " + howManyCollisions, 1020, 20);
        String howMuchTime = Long.toString((System.currentTimeMillis() - startTime) / 1000);
        g.drawString("Time in seconds: " + howMuchTime, 1020, 40);
        String howManyDestroyed = Integer.toString(Simulation.getDestroyed());
        g.drawString("Particles destroyed: " + howManyDestroyed, 1020, 60);
    }

    /**
     * Saving results of simulation to Data.txt file.
     */
    public void saveToFile() throws IOException {
        String fileContent = Integer.toString(Simulation.getCollisions());
        BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt"));
        writer.write("Liczba kolizji: " + fileContent + '\n' + "Czas symulacji: " + (System.currentTimeMillis() - startTime) / 1000 + "s" + '\n' + "Wynik: " + Simulation.getEndCause());
        writer.close();
    }

    /**
     * Method returning time of simulation in seconds.
     */
    public static long getCurrentTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}
