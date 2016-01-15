import java.net.*;
import java.io.*;


public class Server extends Thread{	
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
