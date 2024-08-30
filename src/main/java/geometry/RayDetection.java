package geometry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RayDetection {
    private RayDetection() {}

    public static List<Point> getVertexModel(BufferedImage bImage, int axes, Point pivot) {
        ArrayList<Point> points = new ArrayList<>();

        //  Pivotal rays
        double dAngle = 360.0/axes;
        for (double angle = 0; angle < 360; angle += dAngle) {
            Point point = getRayHead(bImage, Math.toRadians(angle), pivot);
            if (point != null)
                points.add(point);
        }

        // Vertical rays
        double dWidth = bImage.getWidth() / (double) axes;
        for (double x = 0; x < bImage.getWidth(); x += dWidth) {
            Point point1 = getRayHead(bImage, Math.toRadians(90), new Point((int) x, 0));
            if (point1 != null)
                points.add(point1);
            Point point2 = getRayHead(bImage, Math.toRadians(270), new Point((int) x, bImage.getHeight() - 1));
            if (point2 != null)
                points.add(point2);
        }

        // Horizontal rays
        double dHeight = bImage.getHeight() / (double) axes;
        for (double y = 0; y < bImage.getHeight(); y += dHeight) {
            Point point1 = getRayHead(bImage, Math.toRadians(0), new Point(0, (int) y));
            if (point1 != null)
                points.add(point1);
            Point point2 = getRayHead(bImage, Math.toRadians(180), new Point(bImage.getWidth() - 1, (int) y));
            if (point2 != null)
                points.add(point2);
        }

        return points;
    }

    private static Point getRayHead(BufferedImage bImage, double angle, Point pivot) {
        double x = pivot.x;
        double y = pivot.y;
        while (x >= 0 && x < bImage.getWidth() && y >= 0 && y < bImage.getHeight()) {
            Point point = new Point((int) x, (int) y);
            if (!isTransparent(bImage, point) && hasTransparentAround(bImage, point))
                return point;

            x += Math.cos(angle);
            y += Math.sin(angle);
        }

        // If no transparent pixel is found, return the last point
        x = Math.min(Math.max(x, 0), bImage.getWidth() - 1.0);
        y = Math.min(Math.max(y, 0), bImage.getHeight() - 1.0);
        Point point = new Point((int) x, (int) y);
        if (!isTransparent(bImage, point) && hasTransparentAround(bImage, point))
            return point;
        return null;
    }

    private static boolean isTransparent(BufferedImage bImage, Point point) {
        return (bImage.getRGB(point.x, point.y) >> 24) == 0x00;
    }

    private static boolean hasTransparentAround(BufferedImage bImage, Point point) {
        int[] dir = new int[] { -1, 0, 1 };
        for (int dx : dir)
            for (int dy : dir)
                if (dx != 0 || dy != 0) {
                    Point p = new Point(point.x + dx, point.y + dy);
                    if (!isInside(bImage, p) || isTransparent(bImage, p))
                        return true;
                }

        return false;
    }

    private static boolean isInside(BufferedImage bImage, Point point) {
        return point.x >= 0 && point.x < bImage.getWidth() && point.y >= 0 && point.y < bImage.getHeight();
    }
}
