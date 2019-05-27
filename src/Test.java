import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Test {

    public static void main(String[] args) throws IOException {
        usingBufferedWritter();
    }
}

    public static void usingBufferedWritter() throws IOException {
        String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";

        BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt"));
        writer.write(fileContent);
        writer.close();
    }
}