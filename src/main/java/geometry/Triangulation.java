package geometry;

import earcut4j.Earcut;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class Triangulation {
    private final BufferedImage image;
    private final Polygon polygon;
    private final ArrayList<BoundarySensitivePolygon> triangles = new ArrayList<>();

    protected Triangulation(BufferedImage image, Polygon polygon) {
        this.image = image;
        this.polygon = polygon;

        triangulate();
    }

    private void triangulate() {
        double[] coordinates = new double[polygon.npoints * 2];
        for (int i = 0; i < polygon.npoints; i++) {
            coordinates[i * 2] = polygon.xpoints[i];
            coordinates[i * 2 + 1] = polygon.ypoints[i];
        }

        ArrayList<Integer> triangleIndices = (ArrayList<Integer>) Earcut.earcut(coordinates);
        for (int i=0; i<triangleIndices.size(); i+=3) {
            BoundarySensitivePolygon triangle = new BoundarySensitivePolygon(image);
            for (int j=0; j<3; j++) {
                int index = triangleIndices.get(i+j);
                triangle.addPoint((int) coordinates[index*2], (int) coordinates[index*2+1]);
            }
            triangles.add(triangle);
        }
    }

    protected List<BoundarySensitivePolygon> getTriangles() {
        return triangles;
    }
}
