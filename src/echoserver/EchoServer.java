package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			// Put your code here.
			Connection c = new Connection(socket);
			Thread t = new Thread(c);
			t.start();
		}
	}

	class Connection implements Runnable {
		private Socket s;
		private InputStream in;
		private OutputStream out;
		Connection(Socket s) throws IOException{
			this.s = s;
			in = s.getInputStream();
			out = s.getOutputStream();
		}

		public void run(){
			try{
				int i;
				while((i = in.read()) != -1){
					out.write(i);
				}
				out.flush();
				s.shutdownOutput();
			}catch(SocketException se){
				Thread.currentThread().interrupt();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}