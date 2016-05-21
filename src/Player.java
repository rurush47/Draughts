import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread
{
	private Board.Colour colour;
	
	Player opponent;
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	Controller controller;
	
	public Player(Socket socket, Board.Colour colour, Controller controller)
	{
		this.socket = socket;
		this.colour = colour;
		this.controller = controller;
		try
		{
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			
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
	
	public void setOpponent(Player opponent) {
        this.opponent = opponent;
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
				if (controller.moveMan(receivedMove.getSourceVector(), receivedMove.getDestinationVector()))
				{
					SyncObj outMessage = new SyncObj(controller.getBoard(), false);
					output.writeObject(outMessage);
				}
				else
				{
					System.out.print("Wrong move");
				}
			}
		}
		catch (Exception e) 
		{
            System.out.println("Player died: " + e);
		} 
	}
}
