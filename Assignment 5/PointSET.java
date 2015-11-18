import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fei
 */

public class PointSET {

    private SET<Point2D> points;

    public PointSET() { // construct an empty set of points 
        points = new SET<Point2D>();
    }

    public boolean isEmpty() { // is the set empty? 
        return points.isEmpty();
    }

    public int size() { // number of points in the set 
        return points.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        points.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p? 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return points.contains(p);
    }

    public void draw() { // draw all points to standard draw 
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        List<Point2D> rangePts = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                rangePts.add(p);
            }
        }
        return rangePts;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        double minDist = Double.MAX_VALUE;
        Point2D thePoint = null;
        double distance;
        for (Point2D pt : points) {
            distance = pt.distanceTo(p);
            if (minDist > distance) {
                minDist = distance;
                thePoint = pt;
            }
        }
        return thePoint;
    }

    public static void main(String[] args) { // unit testing of the methods (optional) 

    }

}
