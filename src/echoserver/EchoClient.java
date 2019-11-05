package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	static class ReadFromStd extends Thread {
		OutputStream os;
		public ReadFromStd(OutputStream os) {
			this.os = os;
		}
		public void run() {
			try {
				int i = 0;
				while (i < 10000) {
					os.write(System.in.read());
					i++;
				}
				os.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					os.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class WriteToStd extends Thread {
		InputStream is;
		public WriteToStd(InputStream is) {
			this.is = is;
		}
		public void run() {
			try {
				int i = 0;
				while (i < 10000) {
					System.out.write(is.read());
					i++;
				}
				System.out.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
		WriteToStd writeThread = new WriteToStd(socketInputStream);
		ReadFromStd readerThread = new ReadFromStd(socketOutputStream);
		writeThread.start();
		readerThread.start();
	}
}