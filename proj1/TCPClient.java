import java.io.*;
import java.net.*;

public class TCPClient {
	private static Socket client;
	private static String serverName;
	private static int port;
	
	public TCPClient(String destAddr, int destPort){
		serverName = destAddr;
		port = destPort;
	}
	public boolean connect(){
		try{
			client = new Socket(serverName, port);
			client.setSoTimeout(3000);
			return true;
		}
		catch(IOException e){
			return false;
	    }
	}
	
	public void disconnect(){
		try{
			client.close();
		}
		catch(IOException e){
			e.printStackTrace();
	    }
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
			
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(packet);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			return in.readUTF();
			
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
	    }
	}
}
