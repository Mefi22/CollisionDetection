package geometry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static geometry.GeometricToolbox.clockwiseSort;
import static geometry.RayDetection.getVertexModel;

class BoundarySensitivePolygon extends Polygon {
    private ArrayList<Point> boundaryPoints = new ArrayList<>();
    private final transient BufferedImage image;
    private Point pivot;

    protected BoundarySensitivePolygon(BufferedImage image) {
        this.image = image;
    }

    protected void updateBoundary(int precision) {
        setPivot();
        boundaryPoints = (ArrayList<Point>) clockwiseSort(
                getVertexModel(image, precision, pivot, this),
                pivot);
    }

    private void setPivot() {
        int xAvg = 0;
        int yAvg = 0;

        for (int i=0; i<npoints; i++) {
            xAvg += xpoints[i];
            yAvg += ypoints[i];
        }

        pivot = new Point(xAvg/npoints, yAvg/npoints);
    }

    protected Point getPivot() {
        return pivot;
    }

    protected List<Point> getBoundaryPoints() {
        return boundaryPoints;
    }
}
