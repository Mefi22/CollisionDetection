package geometry;

import imagetools.Toolbox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static geometry.GeometricToolbox.clockwiseSort;
import static geometry.GeometricToolbox.pointsToPolygon;
import static geometry.RayDetection.getVertexModel;

public class GraphicalObject {
    private final int precision;
    private final BufferedImage bImage;
    private Polygon boundingPolygon;
    private Triangulation triangulation;
    private final Point center;

    public GraphicalObject(Image image) {
        this(image, 20);
    }

    public GraphicalObject(Image image, int precision) {
        this.precision = precision;
        bImage = Toolbox.getBufferedImage(image);
        center = new Point(bImage.getWidth()/2, bImage.getHeight()/2);
        boundingPolygon = pointsToPolygon (clockwiseSort (
                getVertexModel(bImage, precision, center),
                center));
        triangulation = new Triangulation(bImage, boundingPolygon);
    }

    public void refine() {
        for (BoundarySensitivePolygon triangle : triangulation.getTriangles())
            triangle.updateBoundary(precision);
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
        triangulation = new Triangulation(bImage, boundingPolygon);
    }

    public Polygon getBoundingPolygon() {
        return boundingPolygon;
    }

    public List<Polygon> getTriangulation() {
        return new ArrayList<>(triangulation.getTriangles());
    }
}
