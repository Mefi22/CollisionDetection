package gui;

import geometry.BoundarySensitivePolygon;
import geometry.Triangulation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static geometry.GeometricToolbox.clockwiseSort;
import static geometry.GeometricToolbox.pointsToPolygon;
import static geometry.RayDetection.getVertexModel;

public class GraphicalObject {
    private static final int PRECISION = 20;
    private final BufferedImage image;
    private Polygon boundingPolygon;
    private Triangulation triangulation;
    private final Point center;

    public GraphicalObject(BufferedImage image) {
        this.image = image;
        center = new Point(image.getWidth()/2, image.getHeight()/2);
        ArrayList<Point> points = (ArrayList<Point>) clockwiseSort(
                getVertexModel(image, PRECISION, center),
                center);
        boundingPolygon = pointsToPolygon(points);
        triangulation = new Triangulation(image, boundingPolygon);
    }

    public void refine() {
        for (BoundarySensitivePolygon triangle : triangulation.getTriangles())
            triangle.updateBoundary();
        ArrayList<Point> pivots = new ArrayList<>();
        HashMap<Point, BoundarySensitivePolygon> pivotToTriangle = new HashMap<>();
        for (BoundarySensitivePolygon triangle : triangulation.getTriangles()) {
            if (!triangle.getBoundaryPoints().isEmpty()) {
                pivots.add(triangle.getPivot());
                pivotToTriangle.put(triangle.getPivot(), triangle);
            }
        }
        ArrayList<Point> sortedPivots = (ArrayList<Point>) clockwiseSort(pivots, center);
        ArrayList<Point> refinedBoundary = new ArrayList<>();
        for (Point pivot : sortedPivots) {
            BoundarySensitivePolygon triangle = pivotToTriangle.get(pivot);
            refinedBoundary.addAll(triangle.getBoundaryPoints());
        }

        boundingPolygon = pointsToPolygon(refinedBoundary);
        triangulation = new Triangulation(image, boundingPolygon);
    }

    public Polygon getBoundingPolygon() {
        return boundingPolygon;
    }

    public Triangulation getTriangulation() {
        return triangulation;
    }
}
