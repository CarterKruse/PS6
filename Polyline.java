import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A Multi-Segment Shape
 * Straight lines connect "joint" points: (x1, y1) to (x2, y2) to (x3, y3)...
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, Fall 2016
 * @author Carter Kruse & John DeForest, Dartmouth CS 10, Spring 2022
 */
public class Polyline implements Shape
{
    private List<Point> points;
    private Color color;

    /**
     * An initial 0-length segment at a point.
     */
    public Polyline(int x1, int y1, Color color)
    {
        points = new ArrayList<>();
        points.add(new Point(x1, y1));
        this.color = color;
    }

    /**
     * A complete segment from one point to the other.
     */
    public Polyline(int x1, int y1, int x2, int y2, Color color)
    {
        points = new ArrayList<>();
        points.add(new Point(x1, y1));
        points.add(new Point(x2, y2));
        this.color = color;
    }

    /**
     * Move By - Moves each segment of the polyline by the specified amount.
     *
     * @param dx The x movement in the polyline.
     * @param dy The y movement in the polyline.
     */
    @Override
    public void moveBy(int dx, int dy)
    {
        // Cycling through all the points in the list, adjusting each appropriately.
        for (Point p: points)
        {
            p.x += dx;
            p.y += dy;
        }
    }

    @Override
    public Color getColor()
    {
        return color;
    }

    @Override
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * Contains Method - Utilizes the static function in Segment.java to determine if a point is within the polyline shape.
     *
     * @param x The x coordinate of the point to consider.
     * @param y The y coordinate of the point to consider.
     */
    @Override
    public boolean contains(int x, int y)
    {
        // Cycling through each segment of the polyline.
        for (int i = 0; i < points.size() - 1; i += 1)
        {
            if (Segment.pointToSegmentDistance(x, y, points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y) <= 3)
                return true;
        }

        return false;
    }

    /**
     * Draw - Cycles through each segment of the polyline, drawing them in order.
     *
     * @param g The graphics to use when drawing the polyline.
     */
    @Override
    public void draw(Graphics g)
    {
        // Setting the color for the shape.
        g.setColor(color);

        // Cycling through each segment of the polyline and drawing it.
        for (int i = 0; i < points.size() - 1; i += 1)
        {
            g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
    }

    /**
     * toString Method - Returns a String representation of the polyline.
     */
    @Override
    public String toString()
    {
        String middle = "";

        // Cycling through all the points of the polyline.
        for (Point p: points)
        {
            middle += p.x + " " + p.y + " ";
        }

        return "Polyline " + middle + color.getRGB();
    }
}
