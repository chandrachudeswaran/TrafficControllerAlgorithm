import java.io.DataOutputStream;
import java.net.Socket;

public class TestSocketClient {
	
	
	
	 public static void main(String[] args) throws Exception{
		 
		 String serverName = "localhost";
		 int port = 5005;
		 System.out.println("Connecting to " + serverName +" on port " + port);
		 
		 Socket socket = new Socket(serverName, port);
		 System.out.println("Just connected to " + socket.getRemoteSocketAddress());
		 
		 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		 out.writeUTF("IJ07100304");
		 
		
		 socket.close();
	 }

}
