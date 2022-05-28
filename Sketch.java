import java.util.*;

/**
 * A class used to maintain sketches (the shapes shared among the editors).
 * Stores a list of the shapes used by various server clients.
 *
 * @author Carter Kruse & John DeForest, Dartmouth CS 10, Spring 2022
 */

public class Sketch
{
    private List<Shape> listOfShapes;

    /**
     * Constructor - Initializes empty list of shapes.
     */
    public Sketch()
    {
        this.listOfShapes = new ArrayList<Shape>();
    }

    /**
     * Adds a shape to the list (and the editor).
     *
     * @param shape The shape to add.
     */
    public void addShape(Shape shape)
    {
        listOfShapes.add(shape);
    }

    /**
     * Deletes a shape from the list (and the editor), determined by ID.
     */
    public void deleteShape(Shape shapeToDelete)
    {
        if (shapeToDelete.getID() == null)
            System.err.println("The shape has no ID.");

        listOfShapes.removeIf(shape -> shape.getID().equals(shapeToDelete.getID()));
    }

    /**
     * Returns the list of shapes.
     */
    public List<Shape> getListOfShapes()
    {
        return listOfShapes;
    }

    /**
     * Output of the list of shapes, which may be sent from client to client.
     */
    @Override
    public String toString()
    {
        String output = "Sketch {";

        Shape firstShape = listOfShapes.get(0);
        output += firstShape.toString();

        for (int i = 1; i < listOfShapes.size(); i += 1)
        {
            output += ", " + listOfShapes.get(i).toString() ;
        }

        output += "}";

        return output;
    }
}
