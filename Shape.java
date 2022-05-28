import java.awt.Color;
import java.awt.Graphics;

/**
 * Shape
 * A geometric entity with a color.
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK
 * @author Carter Kruse & John DeForest, Dartmouth CS 10, Spring 2022
 */

public interface Shape
{
    /**
     * Get ID
     *
     * @return The shape's ID.
     */
    public String getID();

    /**
     * Set ID
     *
     * @param ID The shape's ID.
     */
    public void setID(String ID);

    /**
	 * Move By - Moves the shape by dx in the x coordinate and dy in the y coordinate.
     */
    public void moveBy(int dx, int dy);

    /**
	 * Contains - Whether the point is inside the shape.
     */
    public boolean contains(int x, int y);

    /**
	 * Get Color
     *
     * @return The shape's color.
     */
    public Color getColor();

    /**
	 * Set Color
     *
     * @param color The shape's color.
     */
    public void setColor(Color color);

    /**
     * Draws the shape.
     */
    public void draw(Graphics g);
}
