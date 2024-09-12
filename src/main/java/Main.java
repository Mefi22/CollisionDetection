import geometry.GraphicalObject;
import gui.GraphicalObjectTest;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Image spikeImage = new ImageIcon("src/main/resources/4293815.png").getImage();
        Image ballImage = new ImageIcon("src/main/resources/ball.png").getImage();
        GraphicalObject spike = new GraphicalObject(spikeImage);
        GraphicalObject ball1 = new GraphicalObject(ballImage, 4);
        GraphicalObject ball2 = new GraphicalObject(ballImage, 4);
        ball2.refine();

        javax.swing.SwingUtilities.invokeLater(() -> {
            GraphicalObjectTest ball1Test = new GraphicalObjectTest(ball1, "Triangles of ball1");
            ball1Test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ball1Test.showGUI();

            GraphicalObjectTest ball2Test = new GraphicalObjectTest(ball2, "Triangles of ball2");
            ball2Test.showGUI();

            System.out.println("Number of triangles in ball1: " + ball1.getTriangulation().size());
            System.out.println("Number of triangles in ball2: " + ball2.getTriangulation().size());

        });
    }
}