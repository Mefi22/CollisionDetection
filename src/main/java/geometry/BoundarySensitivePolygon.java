package geometry;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static geometry.GeometricToolbox.clockwiseSort;
import static geometry.GeometricToolbox.crossProduct;
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

    protected boolean intersects(BoundarySensitivePolygon t2) {
        for (int i=0; i<npoints; i++) {
            for (int j=0; j<t2.npoints; j++) {
                if (Line2D.linesIntersect(
                        xpoints[i], ypoints[i],
                        xpoints[(i+1)%npoints], ypoints[(i+1)%npoints],
                        t2.xpoints[j], t2.ypoints[j],
                        t2.xpoints[(j+1)%t2.npoints], t2.ypoints[(j+1)%t2.npoints]))
                    return true;
            }
        }

        return containsHelper(new Point(t2.xpoints[0], t2.ypoints[0])) ||
                t2.containsHelper(new Point(xpoints[0], ypoints[0]));
    }

    private boolean containsHelper(Point p) {
        for (int i=0; i<npoints; i++) {
            if (sameSide(
                    p,
                    new Point(xpoints[i], ypoints[i]),
                    new Point(xpoints[(i+1)%npoints], ypoints[(i+1)%npoints]),
                    new Point(xpoints[(i+2)%npoints], ypoints[(i+2)%npoints])))
                return true;
        }
        return false;
    }

    private boolean sameSide(Point p1, Point p2, Point a, Point b) {
        return crossProduct(a, b, a, p1) * crossProduct(a, b, a, p2) >= 0;
    }
}
