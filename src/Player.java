import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Player extends Thread
{
	private Board.Colour colour;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerThread server;
	
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
	
	public void run()
	{
		try
		{
			System.out.println("MESSAGE All players connected");
		
			if (colour == Board.Colour.WHITE) {
	            System.out.println("MESSAGE White moves first");
	        }
			
			while (true)
			{
				SyncObj receivedMove = (SyncObj)input.readObject();
				server.handleMove(receivedMove.getSourceVector(), this.colour);
			}
		} catch (IOException e) {
			System.out.println("Player disconnected");
			//TODO alert
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		finally 
		{
	        try {socket.close();} catch (IOException e) {}
	    }
	}
	
	public synchronized void sendMessageToClient(SyncObj message) throws IOException
	{
		output.writeObject(message);
		output.reset();
		System.out.println("Message send");
	}
}
