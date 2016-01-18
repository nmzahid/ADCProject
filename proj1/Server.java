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


public class Server extends Thread{	
	public static void main(String [] args)
	{
		int port = 0;
		boolean isUdp = false;
		if((args.length==2) && args[0].equals("-u")){
			port = Integer.parseInt(args[1]);
			isUdp = true;
		}
		else if(args.length==1){
			port = Integer.parseInt(args[0]);
		}
		else{
			System.out.println("Usage: java Server [-u] <Port Number>");
			System.exit(1);
		}

		if(port<=0 || port>65535){
			System.out.println("Error, port number not in range.");
			System.exit(1);
		}

		try
		{
			if(isUdp){
				Thread t=new UDPServer(port);
            	t.start();
			}
			else{
				Thread t = new TCPServer(port);
				t.start();
			}
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
