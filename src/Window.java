import javax.swing.*;

/** Klasa odpowiedzialna */

public class Window extends JPanel {

    static private final int WIDTH = 1540;
    static private final int HEIGHT = 1080;
    static public int particleCount;


    /**
     * Tworzenie okna, w ktorym uzytkownik inicjuje wartosc czastek
     */
    private void inputWindow(){
        JFrame inputWindow = new JFrame();
        boolean incorrectAmountOfParticles = true;

            while(incorrectAmountOfParticles){
                    //Metoda od tworzenia okna
                    String inputS = JOptionPane.showInputDialog(inputWindow, "Insert amount of particles (between 1 and 200)");
                    int i = Integer.parseInt(inputS);

                //Sprawdzenie czy wprowadzona wartość jest odpowiednia
                if(i >0 && i <201) {
                    particleCount = i;
                    incorrectAmountOfParticles = false;
                }
            }
    }

    /**
     * Tworzenie okna, w ktorym odbedzie się symulacja
     */
    private void simulationStartUp() {

        /*
        Robienie nowego okna symulacji określane są:
        jego nazwa,
        sposób zamykania,
        dodanie Simulation do okna,
        określenie rozmiaru okna,
        ustalenie czy okno będzie mogło zmieniać rozmiar oraz
        ustawienie widoczności okna
        */

        JFrame simulationWindow = new JFrame("Simulation");
        simulationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Simulation simulation = new Simulation();
        simulationWindow.add(simulation);
        simulationWindow.setSize(WIDTH, HEIGHT);
        simulationWindow.setResizable(false);
        simulationWindow.setVisible(true);

    }


    public static void main(String[] args) {
        Window window = new Window();
        window.inputWindow();
        window.simulationStartUp();

    }

}

