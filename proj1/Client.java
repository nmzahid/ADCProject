public class Client {
	public static void main(String[] args){
		if(args.length < 2){
			System.out.print("Usage: java TCPClient <Host Name/IP> <Port Number>\n");
			System.exit(1);
		}
		
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		
		TCPClient client = new TCPClient(serverName, port);

		while(true){
			System.out.print(">");
			String input = System.console().readLine();
			Log.log(input);
			if(client.connect() == false){
				Log.log("Connecting to " + serverName + " on port " + port + " failed!");
				continue;
			}
			else{
				Log.log("Connecting to " + serverName + " on port " + port);
				Log.log("Just connected to " + client.getRemoteSocketAddress());
			}

			String response = client.sendRequest(input.split(" "));
			client.disconnect();
			Log.log(response);
		}
	}
}
