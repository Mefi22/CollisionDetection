package gui;

import geometry.RayDetection;
import imagetools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RayDetectionTestPanel extends JPanel {
    private Point pivot = null;
    private int axes = 20;
    private final transient Image img;

    RayDetectionTestPanel(Image img) {
        this.img = img;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pivot = e.getPoint();
                repaint();
            }
        });

        this.addMouseWheelListener(e -> {
            if (e.getWheelRotation() > 0) {
                axes -= 1;
            } else {
                axes += 1;
            }
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        if (pivot != null)
            drawRayDetection(g, Toolbox.getBufferedImage(img), pivot, axes);
    }

    private static void drawRayDetection(Graphics g, BufferedImage bImage, Point pivot, int axes) {
        ArrayList<Point> points = (ArrayList<Point>) RayDetection.getVertexModel(bImage, axes, pivot);
        Color c = g.getColor();

        g.setColor(Color.GREEN);
        g.fillOval(pivot.x -2, pivot.y -2, 4, 4);
        for (Point point : points) {
            g.setColor(Color.RED);
            g.fillOval(point.x -2, point.y -2, 4, 4);
        }

        g.setColor(c);
    }
}
