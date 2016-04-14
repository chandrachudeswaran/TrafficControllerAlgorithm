import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String dataInput = in.readLine();
				System.out.println("Data received from port 5005 is  " +dataInput );
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
				System.out.println("Output from traffic algorithm  " + output);
				Socket socket1 = new Socket("localhost", 60000);  // change the localhost to ip address if u want to change
				PrintWriter out = new PrintWriter(socket1.getOutputStream(),true);
				out.println(generateOutput(output));
				socket1.close();
				System.out.println("Data sent to port 60000");
				
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
