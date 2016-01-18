import java.io.*;

public class Client {
	public static void main(String[] args){
		if(args.length < 2){
			System.out.print("Usage: java Client [-u] <Host Name/IP> <Port Number>\n");
			System.exit(1);
		}

		boolean isUdp = false;
		String serverName = "";
		int port = 0;

		if(args.length == 3){
			isUdp = true;
			serverName = args[1];
			port = Integer.parseInt(args[2]);
		}
		else if(args.length==2){
			serverName = args[0];
			port = Integer.parseInt(args[1]);
		}
		else{
			System.out.print("Usage: java Client [-u] <Host Name/IP> <Port Number>\n");
			System.exit(1);
		}
		
		if(!isUdp){
			TCPClient tcpClient = new TCPClient(serverName, port);
			tcpClient.run();
		}
		else{
        	UDPClient udpClient = new UDPClient(serverName,port);
        	udpClient.run();
        }
	}
}
