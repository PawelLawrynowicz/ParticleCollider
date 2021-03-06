import java.awt.*;

/**
 * Klasa odpowiadajaca za rysowania tla */
public class Environment {
    private int xStart, xEnd, yStart, yEnd;
    private Color fill, frame;

    /** Konstruktor tla */
    public Environment(int x, int y, int width, int height, Color fill, Color frame) {
        xStart = x;
        yStart = y;
        xEnd = x + width - 1;
        yEnd = y + height - 1;
        this.fill = fill;
        this.frame = frame;
    }
    /** Metoda opisujaca rysowanie tla */
    public void draw(Graphics g) {
        g.setColor(fill);
        g.fillRect(xStart, yStart, xEnd - xStart - 1, yEnd - yStart - 1);
        g.setColor(frame);
        g.drawRect(xStart, yStart, xEnd - xStart - 1, yEnd - yStart - 1);
    }
}
