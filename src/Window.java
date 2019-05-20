import javax.swing.*;
import java.awt.image.ImageObserver;

public class Window extends JPanel {

    static final int WIDTH = 1540;
    static final int HEIGHT = 1080;


    public void startUp() {
        JFrame frame = new JFrame("Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Simulation simulation = new Simulation();
        frame.add(simulation);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.startUp();
    }

}
