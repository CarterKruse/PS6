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
    private String ID; // Used to distinguish between shapes when using multiple clients.

    public Polyline(int x1, int y1, Color color)
    {
        points = new ArrayList<>();
        points.add(new Point(x1, y1));
        this.color = color;
    }

    public Polyline(int x1, int y1, int x2, int y2, Color color)
    {
        points = new ArrayList<>();
        points.add(new Point(x1, y1));
        points.add(new Point(x2, y2));
        this.color = color;
    }

    public Polyline(int x1, int y1, int x2, int y2, Color color, String ID)
    {
        points = new ArrayList<>();
        points.add(new Point(x1, y1));
        points.add(new Point(x2, y2));
        this.color = color;
        this.ID = ID;
    }

    @Override
    public void moveBy(int dx, int dy)
    {

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

    @Override
    public String getID()
    {
        return ID;
    }

    @Override
    public void setID(String ID)
    {
        this.ID = ID;
    }

    @Override
    public boolean contains(int x, int y)
    {

    }

    @Override
    public void draw(Graphics g)
    {

    }

    @Override
    public String toString()
    {
        return "Polyline " + " " + color.getRGB();
    }
}
