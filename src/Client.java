import java.net.*;
import java.io.*;

/**
 * Online client class
 * @author Rurarz
 *
 */
public class Client extends Thread
{
	private int portNumber = 6066;
	private Socket socket    = null;
	private ObjectInputStream  input  =  null;
	private ObjectOutputStream output = null;
	private Controller controller;
	
	/**
	 * Client constructor.
	 * @param serverName server address
	 * @param controller reference to a controller
	 * @throws Exception
	 */
	public Client(String serverName, Controller controller) throws Exception
	{
		this.controller = controller;
		 System.out.println("Connecting to " + serverName +
		 " on port " + portNumber);
		socket = new Socket(serverName, portNumber);
	      System.out.println("connected with" + socket.getPort());
	    InputStream inFromServer = socket.getInputStream();
	    output = new ObjectOutputStream(socket.getOutputStream()); 
		System.out.println("Client output created");
		input = new ObjectInputStream(inFromServer);
		System.out.println("Client input created");
	}
	
	/**
	 * Runs a client thread.
	 */
	public void run()
	{
		SyncObj response;
		
		try
		{
			response = (SyncObj)input.readObject();
			
			if (response.getText().equals("white"))
			{
				System.out.println("Connected as Player White");
			}
			else
			{
				System.out.println("Connected as Player Black");
			}
			while(true)
			{
				response = (SyncObj)input.readObject();
				
				if(response.getGameStatus() == null)
				{
					System.out.println("Client: view received");
					controller.updateView(response.getBoard());
				}
				else
				{
					controller.updateView(response.getBoard());
					controller.showWinMessage(response.getGameStatus());
					//socket.close();
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends message to server.
	 * @param clickPos position of a click
	 * @throws Exception
	 */
	public void sendMessage(Vector2 clickPos) throws Exception
	{
		SyncObj message = new SyncObj(clickPos);
		output.writeObject(message);
	}
}
