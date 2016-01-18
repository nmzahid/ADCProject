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

public class TCPClient {
	protected Socket client;
	private String serverName;
	private int port;
	
	TCPClient(String destAddr, int destPort){
		serverName = destAddr;
		port = destPort;
	}

	boolean connect(){
		try{
			client = new Socket(serverName, port);
			client.setSoTimeout(3000);
			return true;
		}
		catch(IOException e){
			return false;
	    }
	}
	
	void disconnect(){
		try{
			client.close();
		}
		catch(IOException e){
			e.printStackTrace();
	    }
	}
	
	String sendRequest(String[] args){		
		
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
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(packet);

			/* receive response */
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			return in.readUTF();
			
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
	    }
	}

	public void run(){
		Log TCPClientLog = new Log("TCPClient.log");
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
				TCPClientLog.log("Connecting to TCP Server at " + serverName + " on port " + port + " failed!");
				continue;
			}
			else{
				TCPClientLog.log("Connecting to TCP Server at " + serverName + " on port " + port);
				TCPClientLog.log("Just connected to TCP Server at " + client.getRemoteSocketAddress());
			}

			/* send request to server */
			TCPClientLog.log(input);

			/* get response from server */
			String response = sendRequest(input.split(" "));
			disconnect();
			TCPClientLog.log(response);
            System.out.print(">");
        }
	}
}

