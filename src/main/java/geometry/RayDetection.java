package geometry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RayDetection {
    private RayDetection() {}

    protected static List<Point> getVertexModel(BufferedImage bImage, int axes, Point pivot) {
        Polygon bound = new Polygon();
        bound.addPoint(0, 0);
        bound.addPoint(bImage.getWidth() - 1, 0);
        bound.addPoint(bImage.getWidth() - 1, bImage.getHeight() - 1);
        bound.addPoint(0, bImage.getHeight() - 1);

        return getVertexModel(bImage, axes, pivot, bound);
    }

    protected static List<Point> getVertexModel(BufferedImage bImage, int axes, Point pivot, Polygon bound) {
        ArrayList<Point> points = new ArrayList<>();

        //  Pivotal rays
        double dAngle = 360.0/axes;
        for (double angle = 0; angle < 360; angle += dAngle) {
            Point point = getRayHead(bImage, Math.toRadians(angle), pivot, bound);
            if (point != null)
                points.add(point);
        }

        // Vertical rays
        double dWidth = bound.getBounds().getWidth() / axes;
        for (double x = bound.getBounds().getMinX(); x < bound.getBounds().getMaxX(); x += dWidth) {
            Point point1 = getRayHead(
                    bImage,
                    Math.toRadians(90),
                    new Point((int) x, (int) bound.getBounds().getMinY()),
                    bound);
            if (point1 != null)
                points.add(point1);
            Point point2 = getRayHead(bImage,
                    Math.toRadians(270),
                    new Point((int) x, (int) (bound.getBounds().getMaxY() - 1)),
                    bound);
            if (point2 != null)
                points.add(point2);
        }

        // Horizontal rays
        double dHeight = bound.getBounds().getHeight() / axes;
        for (double y = bound.getBounds().getMinY(); y < bound.getBounds().getMaxY(); y += dHeight) {
            Point point1 = getRayHead(
                    bImage,
                    Math.toRadians(0),
                    new Point((int) bound.getBounds().getMinX(), (int) y),
                    bound);
            if (point1 != null)
                points.add(point1);
            Point point2 = getRayHead(
                    bImage,
                    Math.toRadians(180),
                    new Point((int) (bound.getBounds().getMaxX() - 1), (int) y),
                    bound);
            if (point2 != null)
                points.add(point2);
        }

        Set<Point> uniquePoints = new HashSet<>(points);
        points.clear();
        points.addAll(uniquePoints);

        return points;
    }

    private static Point getRayHead(BufferedImage bImage, double angle, Point pivot, Polygon bound) {
        double x = pivot.x;
        double y = pivot.y;

        while (x >= bound.getBounds().getMinX() && x < bound.getBounds().getMaxX() &&
                y >= bound.getBounds().getMinY() && y < bound.getBounds().getMaxY()) {
            Point point = new Point((int) x, (int) y);
            if (!isTransparent(bImage, point) && hasTransparentAround(bImage, point) &&
                    GeometricToolbox.isInBound(point, bound))
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
        Polygon bound = new Polygon();
        bound.addPoint(1, 1);
        bound.addPoint(bImage.getWidth() - 1, 1);
        bound.addPoint(bImage.getWidth() - 1, bImage.getHeight() - 1);
        bound.addPoint(1, bImage.getHeight() - 1);

        int[] dir = new int[] { -1, 0, 1 };
        for (int dx : dir)
            for (int dy : dir)
                if (dx != 0 || dy != 0) {
                    Point p = new Point(point.x + dx, point.y + dy);
                    if (!GeometricToolbox.isInBound(p, bound) || isTransparent(bImage, p))
                        return true;
                }

        return false;
    }
}
