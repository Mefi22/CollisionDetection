import geometry.GraphicalObject;
import gui.CollisionTest;
import gui.TriangulationTest;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        testCollision();
    }

    private static void testTriangles() {
        Image ballImage = new ImageIcon("src/main/resources/ball.png").getImage();
        GraphicalObject ball1 = new GraphicalObject(ballImage);

        javax.swing.SwingUtilities.invokeLater(() -> {
            TriangulationTest ball1Test = new TriangulationTest(ball1, "Triangles of ball1");
            ball1Test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ball1Test.showGUI();

            System.out.println("Number of triangles in ball1: " + ball1.getTriangulation().size());
        });
    }

    private static void testCollision() {
        Image ballImage = new ImageIcon("src/main/resources/ball.png").getImage();
        GraphicalObject ball1 = new GraphicalObject(ballImage);
        GraphicalObject ball2 = new GraphicalObject(ballImage);

        javax.swing.SwingUtilities.invokeLater(() -> {
            CollisionTest ballCollision = new CollisionTest(ball1, ball2, "Collision of ball1 and ball2");
            ballCollision.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ballCollision.showGUI();
        });
    }
}