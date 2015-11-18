
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Fei
 */
public class FastCollinearPoints {

    private final Point[] copyPoints;
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        // Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.
        copyPoints = new Point[points.length];  // sorted points copy
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
            copyPoints[i] = points[i];
        }
        Arrays.sort(copyPoints);  // sort point
        for (int i = 0; i < copyPoints.length - 1; i++) {   // find duplicates
            if (copyPoints[i].compareTo(copyPoints[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        //Point[] points2 = Arrays.copyOf(copyPoints, copyPoints.length); // used for sorting by slope
        Arrays.sort(points); // sort input points?

        List<Point> slopePts;
        for (Point oPt : points) {
            Arrays.sort(copyPoints, oPt.slopeOrder());  // sorted by slope to oPt, the first one always is itself (Negative_infinity)
            slopePts = new ArrayList<Point>();
            double slope0 = Double.NEGATIVE_INFINITY;
            double slope1 = 0;

            for (int i = 1; i < copyPoints.length; i++) {
                slope1 = oPt.slopeTo(copyPoints[i]);
                if (slope1 == slope0) {
                    slopePts.add(copyPoints[i]);
                } else {
                    if (slopePts.size() >= 3) {
                        slopePts.add(oPt);
                        Collections.sort(slopePts);
                        if (slopePts.get(0).compareTo(oPt) >= 0) // if <0, this slopePts was already used
                        {
                            segments.add(new LineSegment(slopePts.get(0), slopePts.get(slopePts.size() - 1)));
                        }
                    }

                    // clean slopePts and start over
                    slopePts.clear();
                    slopePts.add(copyPoints[i]);
                }
                slope0 = slope1;
            }
            
            if (slopePts.size() >= 3) {
                slopePts.add(oPt);
                Collections.sort(slopePts);
                if (slopePts.get(0).compareTo(oPt) >= 0) // if <0, this slopePts was already used
                {
                    segments.add(new LineSegment(slopePts.get(0), slopePts.get(slopePts.size() - 1)));
                }
            }

        }
    }

    public int numberOfSegments() {       // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {               // the line segments    
        return segments.toArray(new LineSegment[segments.size()]);
    }

}
