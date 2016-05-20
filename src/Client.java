import java.net.*;
import java.io.*;

public class Client {
	public static void ClientConnect(String serverName, int portNumber) throws ClassNotFoundException
	   {
	      int port = portNumber;
	      try
	      {
	         
			System.out.println("Connecting to " + serverName +
			 " on port " + port);
	         Socket client = new Socket(serverName, port);
	         System.out.println("Just connected to " 
			 + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         ObjectOutputStream oos = new ObjectOutputStream(outToServer);
	         
	         SyncObj sampleText = new SyncObj("It's high noon");
	         oos.writeObject(sampleText);
	         DataOutputStream out = new DataOutputStream(outToServer);

	         
	         
	         InputStream inFromServer = client.getInputStream();
	         ObjectInputStream inObj = new ObjectInputStream(inFromServer);

	         SyncObj received = (SyncObj)inObj.readObject();
	         
	         System.out.println(received.getText());
	         
	      }
	      catch(ConnectException e)
	      {
	    	  System.out.println("No game hosted");
	      }
	      catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	   }
}
