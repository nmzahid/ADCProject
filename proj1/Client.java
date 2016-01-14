import java.io.*;
import java.net.*;

public class Client {
	public static void main(String [] args)
	{
		if(args.length < 2)
		{
			System.out.print("Usage: java TCPClient <Host Name/IP> <Port Number> [Command]\n"
					+ "\tCommand: set <key> <value>\n"
					+ "\t         get <key>\n"
					+ "\t         del <key>\n");
			System.exit(1);
		}
		
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		String packet="";
		try
		{
			if(args.length>2)
			{
				packet = args[2];
				
			}
			if(args.length>3)
			{
				packet = packet + " " + args[3];
			}
			if(args.length>4)
			{
				packet = packet + " " + args[4];
			}
			
			System.out.println(packet);
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(packet);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			System.out.println("Server says " + in.readUTF());
			client.close();
			
		}catch(IOException e)
	    {
			e.printStackTrace();
	    }
	}
}
