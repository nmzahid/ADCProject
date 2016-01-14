import java.net.*;
import java.io.*;
import java.util.*;


public class TCPServer extends Thread{
	private ServerSocket serverSocket;
	private Hashtable<String, String> dataTable = new Hashtable<String, String>();
	
	public TCPServer(int port) throws IOException
	{
	      serverSocket = new ServerSocket(port);
	}
	
	public String set(String[] packet)
	{
		String output;
		if(packet.length!=3)
		{
			output = "Error remote input!";
		}
		else
		{
			dataTable.put(packet[1], packet[2]);
			output = "Success!\n";
		}	
		
		return output;
	}
	
	public String get(String[] packet)
	{
		String output;
		
		if(packet.length!=2)
		{
			output = "Error remote input!";
		}
		else
		{
			String value = dataTable.get(packet[1]);
			if(value != null)
			{
				output = "Success!\nvalue : " + dataTable.get(packet[1])+ "\n";
			}
			else
			{
				output = "Not found key " + packet[1] +"!\n";
			}
		}
		
		return output;
	}
	
	public String del(String[] packet)
	{
		String output;
		if(packet.length!=2)
		{
			output = "Error remote input!";
		}
		else
		{
			dataTable.remove(packet[1]);
			output = "Success!\n";
		}
		
		return output;
	}
	
	public void terminate()
	{
		
	}
	
	public String requestHandler(String request)
	{
		String[] packet = request.split(" ");
		String output = null;
		
		if(packet[0].equals("set"))
		{
			output = set(packet);
		}
		else if(packet[0].equals("get"))
		{
			output = get(packet);
		}
		else if(packet[0].equals("del"))
		{
			output = del(packet);
		}
		
		return output;
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
				System.out.println("Just received request from " + server.getRemoteSocketAddress());
				
				/* parsing a request from socket */
				DataInputStream in = new DataInputStream(server.getInputStream());
				String request = in.readUTF();				
				System.out.println("Request: " + request);				
				
				/* handle the request */
				String output = null;
				output = requestHandler(request);
				System.out.print(output);
				
				/* display dataTable */
				System.out.print("-dataTable--------\n");
				Enumeration<String> key = dataTable.keys();
			    while(key.hasMoreElements()) 
			    {
			    	String str = key.nextElement();
			    	System.out.println(str + ": " + dataTable.get(str));
			    }
			    System.out.print("------------------\n");
			    
			    /* send the result back to the client */
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF(output);
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
		if(args.length < 1) 
		{
			System.out.println("Usage: java TCPServer <Port Number>");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		
		try
		{
			Thread t = new TCPServer(port);
			t.start();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
