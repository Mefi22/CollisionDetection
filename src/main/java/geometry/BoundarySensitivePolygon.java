package geometry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static geometry.GeometricToolbox.clockwiseSort;
import static geometry.RayDetection.getVertexModel;

public class BoundarySensitivePolygon extends Polygon {
    public static final int PRECISION = 10;
    private ArrayList<Point> boundaryPoints = new ArrayList<>();
    private final transient BufferedImage image;
    private Point pivot;

    public BoundarySensitivePolygon(BufferedImage image) {
        this.image = image;
    }

    public void updateBoundary() {
        setPivot();
        boundaryPoints = (ArrayList<Point>) clockwiseSort(getVertexModel(image, PRECISION, pivot, this), pivot);
    }

    public void setPivot() {
        int xAvg = 0;
        int yAvg = 0;

        for (int i=0; i<npoints; i++) {
            xAvg += xpoints[i];
            yAvg += ypoints[i];
        }

        pivot = new Point(xAvg/npoints, yAvg/npoints);
    }

    public Point getPivot() {
        return pivot;
    }

    public List<Point> getBoundaryPoints() {
        return boundaryPoints;
    }
}
