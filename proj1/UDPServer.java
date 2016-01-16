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
				System.out.println("UDP Server waiting for client on port " +
				serverSocket.getLocalPort() + "...");
				
                                DatagramPacket datapacket=new DatagramPacket(inData, inData.length);
				serverSocket.receive(datapacket);
                                System.out.println("Just received request from " + datapacket.getAddress()+":"+datapacket.getPort());
				
				/* parsing a request from socket */
				
				String request = new String(datapacket.getData());
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
				outData=output.getBytes();
				DatagramPacket outpacket=new DatagramPacket(outData,outData.length,datapacket.getAddress(),datapacket.getPort() );
			    serverSocket.send(outpacket);
				    
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
	
	
}
