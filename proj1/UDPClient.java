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
import java.util.*;

public class UDPClient {
	protected DatagramSocket client;
	private String serverName;
	private int port;
	byte[] inData = new byte[1024];             
    byte[] outData = new byte[1024]; 

	UDPClient(String destAddr, int destPort){
		serverName = destAddr;
		port = destPort;
	}

	boolean connect(){
		try{
			client = new DatagramSocket();
			client.setSoTimeout(3000);
			return true;
		}
		catch(IOException e){
			return false;
	    }
	}
	
	void disconnect(){
		
			client.close();
		
	}
	
	String sendRequest(String[] args){		
		inData=new byte[1024];
		outData=new byte[1024];
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
			/* send request */
			InetAddress ipAddress = InetAddress.getByName(serverName); 
			outData=packet.getBytes();
			DatagramPacket outpacket=new DatagramPacket(outData,outData.length,ipAddress, port);
			client.send(outpacket);

			/* receive response */
			DatagramPacket inpacket=new DatagramPacket(inData, inData.length);
            client.receive(inpacket);
			String response=new String(inpacket.getData(), 0, inpacket.getLength());
			return response;
			
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
	    }
	}

	public void run(){
		Log UDPClientLog = new Log("UDPClient.log");
        Scanner scanner = new Scanner(System.in);

        System.out.print(">");
		while(scanner.hasNextLine()){
			String input = scanner.nextLine();
			System.out.print(input);
			if(input.equals("quit"))
			{
				break;
			}
			
			if(connect() == false){
				UDPClientLog.log("Connecting to UDP Server at " + serverName + " on port " + port + " failed!");
				continue;
			}
			else{
			 	UDPClientLog.log("Connecting to UDP Server at " + serverName + " on port " + port);
				UDPClientLog.log("Just connected to UDP Server at " + serverName+":"+port);
			}
			
			/* send request to server */
			UDPClientLog.log(input);

			/* get response from server */
			String response = sendRequest(input.split(" "));
			disconnect();
			UDPClientLog.log(response);
            System.out.print(">");
        }
	}
}

