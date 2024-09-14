import geometry.GraphicalObject;
import gui.CollisionTestFrame;
import gui.TriangulationTestFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        testCollision();
    }

    private static void testTriangles() {
        Image image = new ImageIcon("src/main/resources/sonic-the-hedgehog.png").getImage();
        GraphicalObject gObj = new GraphicalObject(image,20);

        javax.swing.SwingUtilities.invokeLater(() -> {
            TriangulationTestFrame shapeTestFrame = new TriangulationTestFrame(gObj, "Triangulation of Sonic");
            shapeTestFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            shapeTestFrame.showGUI();

            System.out.println("Number of triangles: " + gObj.getTriangulation().size());
        });
    }

    private static void testCollision() {
        Image image1 = new ImageIcon("src/main/resources/ball.png").getImage();
        Image image2 = new ImageIcon("src/main/resources/sonic-the-hedgehog.png").getImage();
        GraphicalObject gObj1 = new GraphicalObject(image1);
        GraphicalObject gObj2 = new GraphicalObject(image2);

        javax.swing.SwingUtilities.invokeLater(() -> {
            CollisionTestFrame collisionTestFrame = new CollisionTestFrame(gObj1, gObj2, "Collision of gObj1 and gObj2");
            collisionTestFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            collisionTestFrame.showGUI();
        });
    }
}