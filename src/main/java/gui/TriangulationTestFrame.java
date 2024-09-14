package gui;

import geometry.GraphicalObject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TriangulationTestFrame extends JFrame {
    private final transient GraphicalObject gObj;
    Random random = new Random();

    public TriangulationTestFrame(GraphicalObject gObj, String title) {
        super(title);
        this.gObj = gObj;
    }

    public void showGUI() {
        this.init();
        this.setVisible(true);
    }

    private void init() {
        this.setSize(gObj.getBImage().getWidth(null) + 100 ,
                    gObj.getBImage().getHeight(null) + 100);
        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JPanel contents = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(gObj.getBImage(), 0, 0, null);

                drawBoundary(g2d);
                drawTriangles(g2d);
            }
        };
        contents.setSize(gObj.getBImage().getWidth(), gObj.getBImage().getHeight());
        this.add(contents);


        JLabel precisionLabel = new JLabel("Precision: " + gObj.getPrecision());
        this.add(precisionLabel);

        JLabel triangleCountLabel = new JLabel("Number of Triangles: " + gObj.getTriangulation().size());
        this.add(triangleCountLabel);
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
