import java.net.*;
import java.io.*;

public class Client extends Thread
{
	private int portNumber = 6066;
	private Socket socket    = null;
	private ObjectInputStream  input  =  null;
	private ObjectOutputStream output = null;
	
	public Client(String serverName) throws Exception
	{
		socket = new Socket(serverName, portNumber);
	      
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream()); 
	}
	
	public void play() throws Exception
	{
		SyncObj response;
		
		try
		{
			response = (SyncObj)input.readObject();
			if (response.getText().equals("white"))
			{
				//player = white
			}
			else
			{
				//player = black
			}
			while(true)
			{
				response = (SyncObj)input.readObject();
				//update map etc m8
			}
		}
		finally
		{
			socket.close();
		}
	}
	
	public void sendMessage(Vector2 source, Vector2 destination) throws Exception
	{
		SyncObj message = new SyncObj(source, destination);
		output.writeObject(message);
	}
}
