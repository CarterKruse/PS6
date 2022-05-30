import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Sketch Server Communicator - Handles communication between the server and one client.
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012, Winter 2014
 * @author Carter Kruse, Dartmouth CS 10, Spring 2022
 */
public class SketchServerCommunicator extends Thread
{
    private Socket socket; // To Talk With Client
    private BufferedReader in; // From Client
    private PrintWriter out; // To Client
    private SketchServer server; // Handling Communication For

    public SketchServerCommunicator(Socket socket, SketchServer server)
    {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Sends a message to the client.
     */
    public void send(String message)
    {
        out.println(message);
    }

    /**
     * Keeps listening for and handling messages from the client.
     */
    public void run()
    {
        try
        {
            System.out.println("Someone Connected");

            // Communication Channel
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Tell the client the current state of the world.
            send(server.getSketch().toString());
            // TODO: YOUR CODE HERE

            // Keep getting and handling messages from the client
            String message;
            while ((message = in.readLine()) != null)
            {
                System.out.println("Received: " + message);

                String[] messageParts = message.split(" ");
                if (messageParts.length < 2)
                    System.err.println("Invalid message from client.");

                String command = messageParts[0];

                if (command.equals("ADD"))
                    handleAdd(message);

                if (command.equals("MOVE"))
                    handleMove(message);

                if (command.equals("RECOLOR"))
                    handleRecolor(message);

                if (command.equals("DELETE"))
                    handleDelete(message);
            }
            // TODO: YOUR CODE HERE

            // Clean Up - Note that also remove self from server's list so it doesn't broadcast here.
            server.removeCommunicator(this);
            out.close();
            in.close();
            socket.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void handleAdd(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 7)
            System.err.println("Invalid message from client.");

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
            server.getSketch().addShape(shape);
            server.broadcast("ADD " + shape);
        }
    }

    public synchronized void handleMove(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 4)
            System.err.println("Invalid message from client.");

        server.getSketch().moveShape(Integer.parseInt(messageParts[1]), Integer.parseInt(messageParts[2]), Integer.parseInt(messageParts[3]));
        server.broadcast(message);
    }

    public synchronized void handleRecolor(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 3)
            System.err.println("Invalid message from client.");

        server.getSketch().recolorShape(Integer.parseInt(messageParts[1]), new Color(Integer.parseInt(messageParts[2])));
        server.broadcast(message);
    }

    public synchronized void handleDelete(String message)
    {
        String[] messageParts = message.split(" ");
        if (messageParts.length < 2)
            System.err.println("Invalid message from client.");

        server.getSketch().deleteShape(Integer.parseInt(messageParts[1]));
        server.broadcast(message);
    }
}
