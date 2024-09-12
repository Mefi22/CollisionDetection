package gui;

import geometry.GraphicalObject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GraphicalObjectTest extends JFrame {
    private final transient GraphicalObject gObj;
    Random random = new Random();

    public GraphicalObjectTest(GraphicalObject gObj, String title) {
        super(title);
        this.gObj = gObj;
    }

    public void showGUI() {
        this.init();
        this.setVisible(true);
    }

    private void init() {
        this.setSize(gObj.getbImage().getWidth(null) + 50,
                    gObj.getbImage().getHeight(null) + 50);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JPanel contents = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(gObj.getbImage(), 0, 0, null);

                drawBoundary(g2d);
                drawTriangles(g2d);
            }
        };

        this.add(contents);
    }

    private void drawBoundary(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(gObj.getBoundingPolygon());
    }

    private void drawTriangles(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(.2f));
        for (Polygon triangle : gObj.getTriangulation()) {
            g2d.setColor(new Color(
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255),
                    200));
            g2d.fillPolygon(triangle);
            g2d.setColor(Color.BLUE);
            g2d.drawPolygon(triangle);
        }
    }
}
