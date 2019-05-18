import javax.swing.*;
import java.awt.image.ImageObserver;

public class Window extends JPanel {

    static final int WIDTH = 1540;
    static final int HEIGHT = 1080;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                JFrame frame = new JFrame("Simulation");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                BallWorld simulation = new BallWorld();
                frame.add(simulation);
                frame.setSize(WIDTH,HEIGHT);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }

}
