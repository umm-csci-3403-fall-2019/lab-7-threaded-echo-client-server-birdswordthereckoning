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

	private void readFromStdIn(OutputStream outputStream) throws IOException {
		int i = 0;
		while (i < 10000) {
			outputStream.write(System.in.read());
			i++;
		}
		outputStream.flush();
	}

	private void writeToStdOut(InputStream inputStream) throws IOException {
		int i = 0;
		while (i < 10000) {
			System.out.write(inputStream.read());
			i++;
		}
		System.out.flush();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
	}
}