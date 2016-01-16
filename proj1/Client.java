public class Client {
	public static void main(String[] args){
		if(args.length < 2){
			System.out.print("Usage: java TCPClient <Host Name/IP> <Port Number>\n");
			System.out.print("Usage: java TCPClient <Host Name/IP> <Port Number> UDPClient <Host Name/IP> <Port Number>\n");
			System.exit(1);
		}
		
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		
                String UDPServerName = args[3];
		int UDPport = Integer.parseInt(args[4]);
		TCPClient client = new TCPClient(serverName, port);
                UDPClient client2=new UDPClient(UDPServerName,UDPport);

		while(true){
			System.out.print(">");
			String input = System.console().readLine();
			Log.log(input);
			if(client.connect() == false){
				Log.log("Connecting to TCP Server at " + serverName + " on port " + port + " failed!");
				continue;
			}
                        else if(client2.connect()==false)
                        {
                            Log.log("Connecting to UDP Server at " + serverName + " on port " + port + " failed!");
				continue;
                        }
			else{
				Log.log("Connecting to TCP Server at " + serverName + " on port " + port);
				Log.log("Just connected to TCP Server at " + client.client.getRemoteSocketAddress()+"\n------------------\n");
			        Log.log("Connecting to UDP Server at " + UDPServerName + " on port " + UDPport);
				Log.log("Just connected to UDP Server at " + serverName+":"+port+"\n------------------\n");
			
                        }

			String response = client.sendRequest(input.split(" "));
                        String response2 = client2.sendRequest(input.split(" "));
			client.disconnect();
                        client2.disconnect();
			Log.log(response);
                        Log.log(response2);
		}
	}
}
