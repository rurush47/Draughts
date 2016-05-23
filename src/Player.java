import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class representing server connection with a client.
 * @author Rurarz
 *
 */
public class Player extends Thread
{
	private Board.Colour colour;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerThread server;
	private boolean keepListening = true;

	/**
	 * Player constructor
	 * @param socket socket with a client
	 * @param colour colour of a player
	 * @param controller controller reference
	 * @param server server thread reference
	 */
	public Player(Socket socket, Board.Colour colour, Controller controller, ServerThread server)
	{
		this.server = server;
		this.socket = socket;
		System.out.println("Client connected " + socket.getLocalPort());
		this.colour = colour;
		try
		{
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println("Server got input");
			
			output = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Server got output");
			
			SyncObj welcomeMessage;
			
			if (this.colour == Board.Colour.WHITE)
			{
				welcomeMessage = new SyncObj("white");
			}
			else
			{
				welcomeMessage = new SyncObj("black");
			}
			output.writeObject(welcomeMessage);
		}
		catch (Exception e)
		{
			System.out.println("Exception:" + e);
		}
	}

	/**
	 * Runs player thread.
	 */
	public void run()
	{
		try
		{
			System.out.println("MESSAGE All players connected");
		
			if (colour == Board.Colour.WHITE) {
	            System.out.println("MESSAGE White moves first");
	        }
			
			while (keepListening)
			{
				SyncObj receivedMove = (SyncObj)input.readObject();
				server.handleMove(receivedMove.getSourceVector(), this.colour);
			}
		} catch (IOException e) {
			System.out.println("Player disconnected");
			server.handlePlayerDisconnect();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		finally 
		{
	        try {socket.close();} catch (IOException e) {}
	    }
	}
	
	/**
	 * Sends message to client.
	 * @param message message to send
	 * @throws IOException
	 */
	public synchronized void sendMessageToClient(SyncObj message) throws IOException
	{
		output.writeObject(message);
		output.reset();
		if(message.getText() != null)
		{
			keepListening = false;
			socket.close();
		}
	}
}
