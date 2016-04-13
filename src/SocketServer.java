import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class SocketServer extends Thread{
	
	private ServerSocket serverSocket;

	public SocketServer() throws IOException{
		serverSocket = new ServerSocket(5005);
	}
	
	 public static void main(String[] args) throws Exception{
		 Thread thread = new SocketServer();
		 thread.start();
	 }
	 
	 
	@Override
	public void run() {
		
		while(true){
			
			try {
				System.out.println("Waiting for client on port  " + serverSocket.getLocalPort());
				Socket socket =serverSocket.accept();
				System.out.println("Connected to " + socket.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String dataInput = in.readUTF();
				ArrayList<Integer> list = new ArrayList<Integer>(); 
				for(int i=2;i<9;i=i+2){
					char val = dataInput.charAt(i);
					int value=Character.getNumericValue(val);
					if(value==0){
						list.add(Character.getNumericValue(dataInput.charAt(i+1)));
					}else{
						list.add(Character.getNumericValue(dataInput.charAt(i)+dataInput.charAt(i+1)));
					}
				}
				TrafficController controller = new TrafficController();
				String output =controller.doAnalysis(list.get(0), list.get(1), list.get(2), list.get(3));
				
				String serverName = "localhost";
				int port = 60000;
				Socket socket1 = new Socket(serverName, port);
				DataOutputStream out = new DataOutputStream(socket1.getOutputStream());
				out.writeUTF(generateOutput(output));
				socket1.close();
				
				
				socket.close();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			
		}
	}

	 public String generateOutput(String key){
		 StringBuilder sb = new StringBuilder();
		 sb.append("OJ");
		 switch(key){
		 case "A": sb.append("1000");
		 break;
		 case "B": sb.append("0100");
		 break;
		 case "C": sb.append("0010");
		 break;
		 case "D": sb.append("0001");
		 break;
		 }
		 return sb.toString();
	 }
	 
}
