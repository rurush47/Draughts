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
	}
	
	public ServerSocket getServerThread()
	{
		return server;
	}
	
	public void run()
	{
		try {
            while (true) {
            	System.out.println("waiting for connection");
            	whitePlayer = new Player(server.accept(), Board.Colour.WHITE, controller);
        		blackPlayer = new Player(server.accept(), Board.Colour.BLACK, controller);
        		
        		whitePlayer.start();
        		blackPlayer.start();
            }
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
}
