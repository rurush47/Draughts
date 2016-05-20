import java.net.*;
import java.io.*;

public class Server extends Thread
{
	 private ServerSocket serverSocket;
	   
	   public Server(int port) throws IOException
	   {
		  serverSocket = new ServerSocket(port);  
	      //serverSocket.setSoTimeout(10000);

	   }

	   public void run()
	   {
	      while(true)
	      {
	         try
	         {
	            System.out.println("Waiting for client on port " +
	            serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Just connected to "
	                  + server.getRemoteSocketAddress());
	            ObjectInputStream in =
	                  new ObjectInputStream(server.getInputStream());
	            
	            SyncObj testObj = (SyncObj)in.readObject();
	            
	            System.out.println(testObj.getText());
	            
	            ObjectOutputStream out =
	                 new ObjectOutputStream(server.getOutputStream());
	            
	            SyncObj response = new SyncObj("ODP m8");
	            
	            out.writeObject(response);
	            
	            server.close();
	         }
	         catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }
	         catch(NullPointerException e)
	         {
	        	 System.out.println("Can't host game");
	        	 break;
	         } catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
	      }
	   }
	   
	   public static boolean available(int port) {
		    /*if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
		        throw new IllegalArgumentException("Invalid start port: " + port);
		    }*/

		    ServerSocket ss = null;
		    DatagramSocket ds = null;
		    try {
		        ss = new ServerSocket(port);
		        ss.setReuseAddress(true);
		        ds = new DatagramSocket(port);
		        ds.setReuseAddress(true);
		        return true;
		    } catch (IOException e) {
		    } finally {
		        if (ds != null) {
		            ds.close();
		        }

		        if (ss != null) {
		            try {
		                ss.close();
		            } catch (IOException e) {
		                /* should not be thrown */
		            }
		        }
		    }

		    return false;
		}
}
