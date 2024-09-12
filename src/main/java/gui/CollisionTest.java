package gui;

import geometry.GraphicalObject;

import javax.swing.*;
import java.awt.*;

public class CollisionTest extends JFrame {
    private final transient GraphicalObject gObj1;
    private final transient GraphicalObject gObj2;

    public CollisionTest(GraphicalObject gObj1, GraphicalObject gObj2, String title) {
        super(title);
        this.gObj1 = gObj1;
        this.gObj2 = gObj2;
    }

    public void showGUI() {
        this.init();
        this.setVisible(true);
    }

    private void init() {
        this.setSize((gObj1.getBImage().getWidth() + gObj2.getBImage().getWidth()) * 2,
                (gObj1.getBImage().getHeight() + gObj2.getBImage().getHeight()) * 2);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JPanel contents = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(gObj1.getBImage(), gObj1.getUlCorner().x, gObj1.getUlCorner().y, null);
                g2d.drawImage(gObj2.getBImage(), gObj2.getUlCorner().x, gObj2.getUlCorner().y, null);
                drawTriangles(g2d, gObj1.collides(gObj2));
            }
        };

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gObj2.moveUlCornerTo(new Point(
                        evt.getX() - gObj2.getBImage().getWidth() / 2,
                        evt.getY() - gObj2.getBImage().getHeight() / 2));
                repaint();
            }
        });

        this.add(contents);
    }

    private void drawTriangles(Graphics2D g2d, boolean collision) {
        g2d.setStroke(new BasicStroke(.5f));

        Color color = collision ? Color.RED : Color.GREEN;
        color = new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                150);

        for (Polygon triangle : gObj1.getTriangulation()) {
            g2d.setColor(color);
            g2d.fillPolygon(triangle);
            g2d.setColor(Color.BLUE);
            g2d.drawPolygon(triangle);
        }

        for (Polygon triangle : gObj2.getTriangulation()) {
            g2d.setColor(color);
            g2d.fillPolygon(triangle);
            g2d.setColor(Color.BLUE);
            g2d.drawPolygon(triangle);
        }
    }
}
