import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Test {

    public static void main(String[] args) {

        ArrayList<Integer> integer = new ArrayList<>();
        integer.add(0);
        integer.add(1);
        integer.add(2);
        integer.add(3);
        integer.add(4);

        for (int i : integer) {
            System.out.print(integer.get(i) + " ");
        }
        System.out.println();

        integer.remove(2);


        for (int i = 0; i < integer.size(); i++) {
            System.out.print(integer.get(i));
        }

        System.out.println();
    }
}
