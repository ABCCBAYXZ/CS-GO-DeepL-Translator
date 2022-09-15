package translator;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Telnet {

	private Socket socket;
	
	public Telnet() throws UnknownHostException, IOException, InterruptedException {
		
		int countConnections = 0;
		
		while(countConnections <= 20) {
		
		try {
			setSocket(new Socket("localhost", 2121));
			System.out.println("connected to Server");
			countConnections = 22;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			System.out.println("failed to connect");
			TimeUnit.SECONDS.sleep(5);
			countConnections++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		}
	}

	public void close() {
		try {
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
