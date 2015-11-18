import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;

/**
 *
 * @author Fei
 */

public class KdTree {

    private KdNode root;
    private int size;
    /* 
     Inner node of the 2d-tree 
     this Node class is static because it does not refer to a generic Key or Value type 
     that depends on the object associated with the outer class. This saves the 8-byte 
     inner class object overhead. 
     */

    private static class KdNode {

        private Point2D p;    // point created for the node
        private RectHV rect;     // outer rectangle surrounding the node
        private KdNode lb;  // left is also down
        private KdNode rt; // right is also up

        public KdNode(Point2D p, RectHV r) {
            this.p = p;
            this.rect = r;
        }
    }

    public KdTree() { // construct an empty set of points 
        root = null;
        size = 0;
    }

    public boolean isEmpty() { // is the set empty? 
        return size == 0;
    }

    public int size() { // number of points in the set 
        return size;
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    private KdNode insert(KdNode node, Point2D p, double x0, double y0, double x1, double y1, boolean isVertical) { // insert() helper

        if (node == null) { // insert new tree node
            size++;
            RectHV rect = new RectHV(x0, y0, x1, y1);
            return new KdNode(p, rect);
        }
        if (node.p.equals(p)) // point exists
        {
            return node;
        }

        double cmp = isVertical ? p.x() - node.p.x() : p.y() - node.p.y();
        if (cmp < 0) {
            node.lb = insert(node.lb, p, x0, y0, (isVertical ? node.p.x() : x1), (isVertical ? y1 : node.p.y()), !isVertical);
        } else {
            node.rt = insert(node.rt, p, (isVertical ? node.p.x() : x0), (isVertical ? y0 : node.p.y()), x1, y1, !isVertical);
        }

        return node;
    }

    public boolean contains(Point2D p) { // does the set contain point p? 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return contains(root, p, true);
    }

    private boolean contains(KdNode node, Point2D p, boolean isVertical) {
        // false if you didn't find it
        if (node == null) return false;
        // true if you found it
        else if (node.p.equals(p)) return true;
        else {
            // The current node is vertical: compare x-coordinates
            if (isVertical) {
                double cmp = p.x() - node.p.x();
                if (cmp < 0) return contains(node.lb, p, !isVertical);
                else return contains(node.rt, p, !isVertical);
            }
            // The current node is horizontal: compare y-coordinates
            else {
                double cmp = p.y() - node.p.y();
                if (cmp < 0) return contains(node.lb, p, !isVertical);
                else return contains(node.rt, p, !isVertical);
            }
        }
    }

    public void draw() { // draw all points to standard draw 
        draw(root, true);
    }

    private void draw(KdNode node, boolean isVertical) { // draw() helper
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        node.p.draw();
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());

        }
        draw(node.lb, !isVertical);
        draw(node.rt, !isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        List<Point2D> pts = new ArrayList<Point2D>();
        range(root, rect, pts);
        return pts;

    }

    private void range(KdNode node, RectHV rect, List<Point2D> pts) { // range() helper
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            pts.add(node.p);
        }
        if (rect.intersects(node.rect)) {
            range(node.lb, rect, pts);
            range(node.rt, rect, pts);
        }
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (root == null)
            return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(KdNode node, Point2D p, Point2D c, boolean isVertical) {
        Point2D closest = c;
        if (node == null) {
            return closest;
        }
        if (node.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            closest = node.p;
        }
        if (node.rect.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            // choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore
            KdNode same, opposite;
            if ((isVertical && (p.x() < node.p.x())) || (!isVertical && (p.y() < node.p.y()))) {
                same = node.lb;
                opposite = node.rt;
            } else {
                same = node.rt;
                opposite = node.lb;
            }
            closest = nearest(same, p, closest, !isVertical);
            closest = nearest(opposite, p, closest, !isVertical);
        }
        return closest;
    }
}
