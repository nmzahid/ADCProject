/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anamzahid
 */
import java.io.*;
import java.net.*;

public class UDPClient {
	protected static DatagramSocket client;
	private static String serverName;
	private static int port;
	byte[] inData = new byte[1024];             
        byte[] outData = new byte[1024]; 
	public UDPClient(String destAddr, int destPort){
		serverName = destAddr;
		port = destPort;
	}
	public boolean connect(){
		try{
			client = new DatagramSocket();
			client.setSoTimeout(3000);
			return true;
		}
		catch(IOException e){
			return false;
	    }
	}
	
	public void disconnect(){
		
			client.close();
		
	}
	
	public String sendRequest(String[] args){		
		
		String packet="";
		try{
			if(args.length>0){
				packet = args[0];
			}
			if(args.length>1){
				packet = packet + " " + args[1];
			}
			if(args.length>2){
				packet = packet + " " + args[2];
			}
			InetAddress ipAddress = InetAddress.getByName(serverName); 
			outData=packet.getBytes();
			DatagramPacket outpacket=new DatagramPacket(outData,outData.length,ipAddress, port);
			client.send(outpacket);
			DatagramPacket inpacket=new DatagramPacket(inData, inData.length);
                        client.receive(inpacket);
			String response=new String(inpacket.getData());
			return response;
			
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
	    }
	}
}

