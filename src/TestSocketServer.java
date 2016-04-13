import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServer extends Thread {
	private ServerSocket serverSocket;

	public TestSocketServer() throws IOException {
		serverSocket = new ServerSocket(60000);
	}

	public static void main(String[] args) throws Exception {
		Thread thread = new TestSocketServer();
		 thread.start();
	}

	@Override
	public void run() {

		while (true) {
			try {
				System.out.println("Waiting for client on port  " + serverSocket.getLocalPort());
				Socket socket =serverSocket.accept();
				System.out.println("Connected to " + socket.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String dataInput = in.readUTF();
				System.out.println("Message from Server-Client " + dataInput);
				socket.close();
				
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}

}
