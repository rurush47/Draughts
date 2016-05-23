import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server thread class.
 * @author Rurarz
 *
 */
public class ServerThread extends Thread
{
	private ServerSocket server;
	Player whitePlayer;
	Player blackPlayer;
	Controller controller;
	
	/**
	 * Server thread constructor
	 * @param port port on which server is hosted 
	 * @param controller controller reference 
	 * @throws IOException
	 */
	public ServerThread(int port, Controller controller) throws IOException
	{
		server = new ServerSocket(port);
		this.controller = controller;
	}
	
	/**
	 * Returns a server reference.
	 * @return server reference
	 */
	public ServerSocket getServerThread()
	{
		return server;
	}
	
	/**
	 * Runs a server thread.
	 */
	public void run()
	{
		try {
            //while (true) {
            	System.out.println("waiting for connection");
            	whitePlayer = new Player(server.accept(), Board.Colour.WHITE, controller, this);
        		blackPlayer = new Player(server.accept(), Board.Colour.BLACK, controller, this);
        		
        		whitePlayer.start();
        		blackPlayer.start();
        		System.out.println("Both players started");
            //}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * Handles move message from client
	 * @param move click position to handle 
	 * @param playerCol colour of calling player
	 * @throws IOException
	 */
	public void handleMove(Vector2 move, Board.Colour playerCol) throws IOException
	{
		if(move != null)
		{
			controller.moveHandle(move, playerCol);	
		}
		else
		{
			System.out.println("null message");
		}
	}
	
	/*
	 * Handles player disconnect situation.
	 */
	public void handlePlayerDisconnect()
	{
		controller.handlePlayerDisconnnect();
	}
	/**
	 * Sends a message to all clients.
	 * @param message message to broadcast
	 * @throws IOException
	 */
	public synchronized void broadcastMessage(SyncObj message) throws IOException
	{
		whitePlayer.sendMessageToClient(message);
		blackPlayer.sendMessageToClient(message);
		if(message.getText() != null)
		{
			server.close();
		}
	}
}
