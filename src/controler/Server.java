package controler;

import java.io.IOException;
import java.net.*;

public class Server {

	private ServerSocket serverSocket;
	
	public Server(ServerSocket serv) {
		this.serverSocket=serv;
	}
	
	public void demarrerServer() {
		try {
		while(!serverSocket.isClosed()) {
			Socket socket = serverSocket.accept();
			GestionnaireClient gest = new GestionnaireClient(socket);
			
			Thread thread = new Thread(gest);
			thread.start();
			
		
		} 
		} catch(Exception e) {
			deconnection();
		}
	}
	
	public static void main(String[]args) throws IOException {
		ServerSocket ss = new ServerSocket(1234);
		Server serv = new Server(ss);
		serv.demarrerServer();
		
		
	}
	
	public void deconnection() {
		try {
		if(serverSocket!=null) {
			serverSocket.close();
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
}
