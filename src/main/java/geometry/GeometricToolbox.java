package geometry;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeometricToolbox {
    private GeometricToolbox() {}

    public static List<Point> clockwiseSort(List<Point> points, Point pivot) {
        ArrayList<Point> sortedPoints = new ArrayList<>(points);
        sortedPoints.sort(clockwiseComparator(pivot));
        return sortedPoints;
    }

    public static boolean isInBound(Point point, Polygon bound) {
        boolean lastPointAbove = bound.ypoints[bound.npoints - 1] < point.y;

        int cnt = 0;
        for (int i=0; i<bound.npoints; i++) {
            int i2 = (i+1) % bound.npoints;
            if (Line2D.linesIntersect(
                    bound.xpoints[i], bound.ypoints[i],
                    bound.xpoints[i2], bound.ypoints[i2],
                    point.x, point.y,
                    0, point.y)) {
                // if intersecting with a vertex, and the two edges are on the opposite side of the line, count once
                if (point.y == bound.ypoints[i]  &&
                        lastPointAbove != bound.ypoints[i2] < point.y) {
                    continue;
                }
                cnt++;

                lastPointAbove = bound.ypoints[i] < point.y;
            }

            if (isBetween(new Point (bound.xpoints[i], bound.ypoints[i]), // handle edge case
                    new Point (bound.xpoints[i2], bound.ypoints[i2]),
                    point))
                return true;
        }
        return cnt % 2 == 1;
    }

    private static boolean isBetween(Point start, Point end, Point p) {
        int crossProduct = (end.y - start.y) * (p.x - start.x) - (end.x - start.x) * (p.y - start.y);

        // compare versus epsilon for floating point values, or != 0 if using integers
        if (Math.abs(crossProduct) > 0) {
            return false;
        }

        int dotProduct = (end.x - start.x) * (p.x - start.x) + (end.y - start.y) * (p.y - start.y);
        if (dotProduct < 0) {
            return false;
        }

        int squaredLengthBA = (p.x - start.x) * (p.x - start.x) + (p.y - start.y) * (p.y - start.y);
        return dotProduct <= squaredLengthBA;
    }


    private static Comparator<Point> clockwiseComparator(Point pivot) {
        return (p1, p2) -> {
            if (p1.x - pivot.x  >= 0 && p2.x - pivot.x < 0) return 1;
            if (p1.x - pivot.x < 0 && p2.x - pivot.x >= 0) return -1;
            if (p1.x - pivot.x == 0 && p2.x - pivot.x == 0) return Integer.compare(p1.y, p2.y);
            return crossProduct(pivot, p1, p2);
        };
    }

    private static int crossProduct (Point center, Point p1, Point p2) {
        // Cross product of two vectors: centerP1 and centerP2
        return (p1.x - center.x) * (p2.y - center.y) - (p1.y - center.y) * (p2.x - center.x);
    }

    public static Polygon pointsToPolygon(List<Point> points) {
        Polygon polygon = new Polygon();
        for (Point point : points) {
            polygon.addPoint(point.x, point.y);
        }
        return polygon;
    }
}
