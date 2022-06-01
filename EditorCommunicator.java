import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Iterator;

/**
 * Editor Communicator - Handles communication to/from the server for the editor.
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012, Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015
 * @author Carter Kruse & John DeForest, Dartmouth CS 10, Spring 2022
 */
public class EditorCommunicator extends Thread
{
    private PrintWriter out; // To Server
    private BufferedReader in; // From Server
    protected Editor editor; // Handling Communicator For

    /**
     * Constructor - Establishes a connection and in/out pair.
     */
    public EditorCommunicator(String serverIP, Editor editor)
    {
        this.editor = editor;
        System.out.println("Connecting To " + serverIP + "...");

        try
        {
            Socket socket = new Socket(serverIP, 4242);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("...Connected");
        }

        catch (IOException e)
        {
            System.err.println("Could Not Connect");
            System.exit(-1);
        }
    }

    /**
     * Sends a message to the server.
     */
    public void send(String msg)
    {
        out.println(msg);
    }

    /**
     * Keeps listening for and handling (your code) messages from the server.
     */
    public void run()
    {
        try
        {
            String message;
            while ((message = in.readLine()) != null)
            {
                System.out.println("Received: " + message);

                String[] messageParts = message.split(" ");
                if (messageParts.length < 2)
                    System.err.println("Invalid message from server.");

                String command = messageParts[0];

                if (command.equals("ADD"))
                    handleAdd(message);

                if (command.equals("MOVE"))
                    handleMove(message);

                if (command.equals("RECOLOR"))
                    handleRecolor(message);

                if (command.equals("DELETE"))
                    handleDelete(message);

                if (command.equals("Sketch"))
                    initializeSketch(message);

                editor.repaint();
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            System.out.println("Server Hung Up");
        }
    }

    public synchronized void handleAdd(String message)
    {
        if (!message.equals("ADD "))
        {
            String[] messageParts = message.split(" ");
            if (messageParts.length < 7)
                System.err.println("Invalid message from server.");

            Shape shape = null;

            if (messageParts[1].equals("Ellipse"))
                shape = new Ellipse(Integer.parseInt(messageParts[2]), Integer.parseInt(messageParts[3]),
                        Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), new Color(Integer.parseInt(messageParts[6])));

            if (messageParts[1].equals("Rectangle"))
                shape = new Rectangle(Integer.parseInt(messageParts[2]), Integer.parseInt(messageParts[3]),
                        Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), new Color(Integer.parseInt(messageParts[6])));

            if (messageParts[1].equals("Segment"))
                shape = new Segment(Integer.parseInt(messageParts[2]), Integer.parseInt(messageParts[3]),
                        Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), new Color(Integer.parseInt(messageParts[6])));

            // TODO: How do we handle a Polyline?? By parsing? How do we get all the points.
            if (messageParts[1].equals("Polyline"))
            {
                shape = new Polyline(Integer.parseInt(messageParts[2]), Integer.parseInt(messageParts[3]), new Color(Integer.parseInt(messageParts[messageParts.length - 1])));

                for (int i = 4; i < messageParts.length - 1; i += 2)
                {
                    ((Polyline) shape).addPoint(Integer.parseInt(messageParts[i]), Integer.parseInt(messageParts[i + 1]));
                }
            }

            if (shape != null)
            {
                editor.getSketch().addShape(shape);
                editor.repaint();
            }
        }
    }

    public synchronized void handleMove(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 4)
            System.err.println("Invalid message from server.");

        editor.getSketch().moveShape(Integer.parseInt(messageParts[1]), Integer.parseInt(messageParts[2]), Integer.parseInt(messageParts[3]));
    }

    public synchronized void handleRecolor(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 3)
            System.err.println("Invalid message from server.");

        editor.getSketch().recolorShape(Integer.parseInt(messageParts[1]), new Color(Integer.parseInt(messageParts[2])));
    }

    public synchronized void handleDelete(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 2)
            System.err.println("Invalid message from server.");

        editor.getSketch().deleteShape(Integer.parseInt(messageParts[1]));
        editor.repaint();
    }

    public synchronized void initializeSketch(String message)
    {
        String[] shapes = message.substring(message.indexOf("{") + 1, message.indexOf("}")).split(", ");

        for (String shape : shapes)
        {
            handleAdd("ADD " + shape.substring(shape.indexOf("=") + 1));
        }
    }

    // Send editor requests to the server
    // TODO: YOUR CODE HERE
}
