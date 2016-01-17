/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
			output = "TCP: Error remote input!\n";
		}
		else
		{
			dataTable.put(packet[1], packet[2]);
			output = "TCP: Success!\n";
		}	
		
		return output;
	}
	
	public String get(String[] packet)
	{
		String output;
		
		if(packet.length!=2)
		{
			output = "TCP: Error remote input!\n";
		}
		else
		{
			String value = dataTable.get(packet[1]);
			if(value != null)
			{
				output = "TCP: Success!\nvalue : " + dataTable.get(packet[1])+ "\n";
			}
			else
			{
				output = "TCP: Not found key " + packet[1] +"!\n";
			}
		}
		
		return output;
	}
	
	public String del(String[] packet)
	{
		String output;
		if(packet.length!=2)
		{
			output = "TCP: Error remote input!\n";
		}
		else
		{
			dataTable.remove(packet[1]);
			output = "TCP: Success!\n";
		}
		
		return output;
	}
	
	public void terminate()
	{
		
	}
	
	public String requestHandler(String request)
	{
		String[] packet = request.split(" ");
		String output = "";
		
		if(packet[0].equalsIgnoreCase("put"))
		{
			output = set(packet);
		}
		else if(packet[0].equalsIgnoreCase("get"))
		{
			output = get(packet);
		}
		else if(packet[0].equalsIgnoreCase("del"))
		{
			output = del(packet);
		}
		else{
			output = "TCP: Error remote input!\n";
		}
		
		return output;
	}
	
	
	public void run()
	{
		
		
		while(true)
		{
			try
			{
				Log.log("TCP: Server waiting for client on port " +
				serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				Log.log("TCP: Just received request from " + server.getRemoteSocketAddress());
				
				/* parsing a request from socket */
				DataInputStream in = new DataInputStream(server.getInputStream());
				String request = in.readUTF();				
				Log.log("TCP: Request: " + request);				
				
				/* handle the request */
				String output = null;
				output = requestHandler(request);
				System.out.print(output);
				
				/* display dataTable */
				Log.log("TCP: -dataTable---");
				Enumeration<String> key = dataTable.keys();
			    while(key.hasMoreElements()) 
			    {
			    	String str = key.nextElement();
			    	Log.log(str + ": " + dataTable.get(str));
			    }
			    System.out.print("------------------");
			    
			    /* send the result back to the client */
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF(output);
			    server.close();
				    
			}catch(SocketTimeoutException s)
			{
			    Log.log("TCP: Socket timed out!");
			    break;
			}catch(IOException e)
			{
			    e.printStackTrace();
			    break;
			}
		}
	}
	
	
}

