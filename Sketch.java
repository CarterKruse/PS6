import java.awt.*;
import java.util.*;

/**
 * A class used to maintain sketches (the shapes shared among the editors).
 * Stores a list of the shapes used by various server clients.
 *
 * @author Carter Kruse & John DeForest, Dartmouth CS 10, Spring 2022
 */

public class Sketch
{
    Map<Integer, Shape> IDMap;
    int IDIndex;

    /**
     * Constructor - Initializes the ID map and index.
     */
    public Sketch()
    {
        IDMap = new HashMap<>();
        IDIndex = 0;
    }

    /**
     * Clicked - Determines which shape is clicked by its ID.
     *
     * @param p The point to consider.
     */
    public int IDFromClicked(Point p)
    {
        for (int ID : IDMap.keySet())
        {
            if (IDMap.get(ID).contains(p.x, p.y))
                return ID;
        }

        return -1;
    }

    /**
     * Add Shape - Adds a shape to the map (and to the editor).
     *
     * @param shape The shape to add.
     */
    public synchronized void addShape(Shape shape)
    {
        IDMap.put(IDIndex, shape);
        IDIndex += 1;
    }

    /**
     * Move Shape - Moves a shape by dx and dy, depending on its ID.
     *
     * @param ID The ID of the shape to move.
     * @param dx The x coordinate of movement.
     * @param dy The y coordinate of movement.
     */
    public synchronized void moveShape(int ID, int dx, int dy)
    {
        IDMap.get(ID).moveBy(dx, dy);
    }

    /**
     * Recolor Shape - Recolors a shape, depending on its ID.
     *
     * @param ID The ID of the shape to move.
     * @param color The color to use when recoloring the shape.
     */
    public synchronized void recolorShape(int ID, Color color)
    {
        IDMap.get(ID).setColor(color);
    }

    /**
     * Deletes a shape from the list (and the editor), determined by ID.
     *
     * @param ID The ID of the shape to delete.
     */
    public synchronized void deleteShape(int ID)
    {
        IDMap.remove(ID);
    }

    /**
     * Output of the list of shapes, which may be sent from client to client.
     */
    @Override
    public String toString()
    {
        String output = "Sketch {";

        Shape firstShape = IDMap.get(0);
        if (firstShape != null)
            output += firstShape.toString();

        for (int i = 1; i < IDMap.keySet().size(); i += 1)
        {
            output += ", " + IDMap.get(i).toString();
        }

        output += "}";

        return output;
    }
}
