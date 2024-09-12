package gui;

import geometry.GraphicalObject;
import imagetools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TriangulationTestPanel extends JPanel {
    private final transient Image img;
    Random random = new Random();


    TriangulationTestPanel(Image img) {
        this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(img, 0, 0, null);

        GraphicalObject gObj = new GraphicalObject(img);
        gObj.refine();
        System.out.println("Number of triangles: " + gObj.getTriangulation().size());
        drawBoundary(g2d, gObj);
        drawTriangles(g2d, gObj);
    }

    private void drawBoundary(Graphics2D g2d, GraphicalObject gObj) {
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawPolygon(gObj.getBoundingPolygon());
    }

    private void drawTriangles(Graphics2D g2d, GraphicalObject gObj) {
        g2d.setStroke(new BasicStroke(1));
        for (Polygon triangle : gObj.getTriangulation()) {
            g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 200));
            g2d.fillPolygon(triangle);
            g2d.setColor(Color.BLUE);
            g2d.drawPolygon(triangle);
        }
    }
}
