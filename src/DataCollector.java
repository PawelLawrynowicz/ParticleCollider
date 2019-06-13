import java.awt.Graphics;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za zbieranie danych, rysowanie ich oraz ich zapis do pliku
 */
public class DataCollector {
    static private long startTime = System.currentTimeMillis();

    /**
     * Rysowanie danych
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
     * Zapis danych wyniku symulacji do pliku Data.txt
     */
    public void saveToFile() throws IOException {
        String fileContent = Integer.toString(Simulation.getCollisions());
        BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt"));
        writer.write("Liczba kolizji: " + fileContent + '\n' + "Czas symulacji: " + (System.currentTimeMillis() - startTime) / 1000 + "s" + '\n' + "Wynik: " + Simulation.getEndCause());
        writer.close();
    }

    /**
     * Funkcja zwracajaca aktualny czas trwania symulacji w sekundach
     */
    public static long getCurrentTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}
