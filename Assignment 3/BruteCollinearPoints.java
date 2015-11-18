import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Fei
 */
public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    public BruteCollinearPoints(Point[] points) {
        // Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        // Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.
        Point[] copyPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
            copyPoints[i] = points[i];
        }
        Arrays.sort(copyPoints);
        for (int i = 0; i < copyPoints.length - 1; i++) {
            if (copyPoints[i].compareTo(copyPoints[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        for (int i = 0; i < copyPoints.length - 3; i++) {
            for (int j = i + 1; j < copyPoints.length - 2; j++) {
                for (int k = j + 1; k < copyPoints.length - 1; k++) {
                    for (int l = k + 1; l < copyPoints.length; l++) {
                        if (copyPoints[i].slopeTo(copyPoints[j]) == copyPoints[i].slopeTo(copyPoints[k]) && copyPoints[i].slopeTo(copyPoints[k]) == copyPoints[i].slopeTo(copyPoints[l])) {
                            segments.add(new LineSegment(copyPoints[i], copyPoints[l]));
                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            lines[i] = segments.get(i);
        }
        return lines;
    }
}
