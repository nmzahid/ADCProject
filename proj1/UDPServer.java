/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anamzahid
 */
import java.net.*;
import java.io.*;
import java.util.*;


public class UDPServer extends Thread{
	private DatagramSocket serverSocket;
	private Hashtable<String, String> dataTable = new Hashtable<String, String>();
	byte[] inData = new byte[1024];             
    byte[] outData = new byte[1024]; 
	public UDPServer(int port) throws IOException
	{
	      serverSocket = new DatagramSocket(port);
	}
	
	public String set(String[] packet)
	{
		String output;
		if(packet.length!=3)
		{
			output = "UDP: Error remote input!\n";
		}
		else
		{
			dataTable.put(packet[1], packet[2]);
			output = "UDP: Success!\n";
		}	
		
		return output;
	}
	
	public String get(String[] packet)
	{
		String output;
		
		if(packet.length!=2)
		{
			output = "UDP: Error remote input!\n";
		}
		else
		{
			String value = dataTable.get(packet[1]);
			if(value != null)
			{
				output = "UDP: Success!\nvalue : " + dataTable.get(packet[1])+ "\n";
			}
			else
			{
				output = "UDP: Not found key " + packet[1] +value+"!\n";
			}
		}
		
		return output;
	}
	
	public String del(String[] packet)
	{
		String output;
		if(packet.length!=2)
		{
			output = "UDP: Error remote input!\n";
		}
		else
		{
			dataTable.remove(packet[1]);
			output = "UDP: Success!\n";
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
			output = "UDP: Error remote input!\n";
		}
		
		return output;
	}
	
	
	public void run()
	{
		
		
		while(true)
		{
			try
			{
				inData=new byte[1024];
				outData=new byte[1024];
				Log.log("UDP: Server waiting for client on port " +
				serverSocket.getLocalPort() + "...");
				
                DatagramPacket datapacket=new DatagramPacket(inData, inData.length);
				serverSocket.receive(datapacket);
                Log.log("UDP: Just received request from " + datapacket.getAddress()+":"+datapacket.getPort());
				
				/* parsing a request from socket */
				
				String request = new String(datapacket.getData(),0,datapacket.getLength());
				Log.log("UDP: Request: " + request);				
				
				/* handle the request */
				String output = null;
				output = requestHandler(request);
				System.out.print(output);
				
				/* display dataTable */
				Log.log("TDP: -dataTable---");
				Enumeration<String> key = dataTable.keys();
			    while(key.hasMoreElements()) 
			    {
			    	String str = key.nextElement();
			    	Log.log(str + ": " + dataTable.get(str));
			    }
			    Log.log("------------------");
			    
			    /* send the result back to the client */
				outData=output.getBytes();
				DatagramPacket outpacket=new DatagramPacket(outData,outData.length,datapacket.getAddress(),datapacket.getPort() );
			    serverSocket.send(outpacket);
				    
			}catch(SocketTimeoutException s)
			{
			    Log.log("UDP: Socket timed out!");
			    break;
			}catch(IOException e)
			{
			    e.printStackTrace();
			    break;
			}
		}
	}
	
	
}
