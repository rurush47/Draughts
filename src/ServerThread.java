import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread extends Thread
{
	private ServerSocket server;
	Player whitePlayer;
	Player blackPlayer;
	Controller controller;
	
	
	public ServerThread(int port, Controller controller) throws IOException
	{
		server = new ServerSocket(port);
		this.controller = controller;
	}
	
	public ServerSocket getServerThread()
	{
		return server;
	}
	
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
	
	public synchronized void broadcastMessage(SyncObj message) throws IOException
	{
		whitePlayer.sendMessageToClient(message);
		blackPlayer.sendMessageToClient(message);
	}
}
