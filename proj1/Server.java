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
		if(args.length < 1) 
		{
			System.out.println("Usage: java TCPServer <Port Number> UDPServer <Port Number>");
			System.exit(1);
		}
		System.out.println(args[0]+" "+args[1]+" "+args[2]);
		int tcpport = Integer.parseInt(args[0]);
                
		int udpport=Integer.parseInt(args[2]);
		try
		{
			Thread t = new TCPServer(tcpport);
			t.start();
                        Thread t2=new UDPServer(udpport);
                        t2.start();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
