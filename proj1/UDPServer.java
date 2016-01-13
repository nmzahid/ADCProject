import java.net.*;
import java.io.*;


public class UDPServer extends Thread{
	private ServerSocket serverSocket;
	
	public UDPServer(int port) throws IOException
	{
	      serverSocket = new ServerSocket(port);
	      serverSocket.setSoTimeout(30000);
	}
	
	public void set()
	{
		
	}
	
	public void get()
	{
		
	}
	
	public void del()
	{
		
	}
	
	public void terminate()
	{
		
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
					System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in =
					new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF());
				DataOutputStream out =
				    new DataOutputStream(server.getOutputStream());
					out.writeUTF("Thank you for connecting to "
					  + server.getLocalSocketAddress() + "\nGoodbye!");
				    server.close();
			}catch(SocketTimeoutException s)
			{
			    System.out.println("Socket timed out!");
			    break;
			}catch(IOException e)
			{
			    e.printStackTrace();
			    break;
			}
		}
	}
	
	public static void main(String [] args)
	{
		int port = Integer.parseInt(args[0]);
		try
		{
			Thread t = new UDPServer(port);
			t.start();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
