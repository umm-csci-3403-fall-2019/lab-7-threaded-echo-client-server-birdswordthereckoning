package echoserver;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	private static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	static class ReadFromStd extends Thread {
		OutputStream os;
		Socket socket;
		ReadFromStd(Socket socket) throws IOException {
			this.os = socket.getOutputStream();
			this.socket = socket;
		}
		public void run() {
			try {
				int i;
				while ((i = System.in.read()) != -1) {
					os.write(i);
				}
				os.flush();
				socket.shutdownOutput();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class WriteToStd extends Thread {
		InputStream is;
		Socket sock;
		boolean toClose = false;
		public WriteToStd(Socket socket) throws IOException {
			this.is = socket.getInputStream();
			this.sock = socket;
		}
		public void run() {
			try {
				int i;
				while ((i =is.read()) != -1) {
					System.out.write(i);
				}
				System.out.flush();
				sock.shutdownInput();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);

		// Put your code here.
		WriteToStd writeThread = new WriteToStd(socket);
		ReadFromStd readerThread = new ReadFromStd(socket);
		writeThread.start();
		readerThread.start();

	}
}