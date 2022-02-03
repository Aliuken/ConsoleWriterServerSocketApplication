package com.mycompany.exampleproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleWriterServerSocketApplication implements CommandLineRunner {
	private static final int SERVER_SOCKET_PORT = 6666;
	private static final int SERVER_SOCKET_BACKLOG = 5000;
	private static final InetAddress SERVER_SOCKET_BIND_ADDRESS = InetAddress.getLoopbackAddress();

	public static void main(String[] args) {
		SpringApplication.run(ConsoleWriterServerSocketApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try ( 
            		ServerSocket serverSocket = this.getLocalhostServerSocket();
            		Socket clientSocket = this.getClientSocket(serverSocket);
			InputStream clientSocketInputStream = clientSocket.getInputStream();
			InputStreamReader clientSocketInputStreamReader = new InputStreamReader(clientSocketInputStream);
            		BufferedReader clientSocketBufferedReader = new BufferedReader(clientSocketInputStreamReader);
        	) {
            		String inputLine;
            		while ((inputLine = clientSocketBufferedReader.readLine()) != null) {
                		System.out.println(inputLine);
            		}
        	} catch (IOException e) {
            		System.out.println("Exception caught when trying to listen on " + SERVER_SOCKET_BIND_ADDRESS + ":" + SERVER_SOCKET_PORT + " with backlog " + SERVER_SOCKET_BACKLOG);
            		System.out.println(e.getMessage());
        	}
        	System.out.println("EchoServerSocketApplication ended");
	}
	
	private ServerSocket getLocalhostServerSocket() throws UnknownHostException, IOException {
		ServerSocket serverSocket = new ServerSocket(SERVER_SOCKET_PORT, SERVER_SOCKET_BACKLOG, SERVER_SOCKET_BIND_ADDRESS);
		System.out.println("serverSocket: " + serverSocket);
		System.out.println("serverSocket localSocketAddress: " + serverSocket.getLocalSocketAddress());
		System.out.println("serverSocket inetAddress: " + serverSocket.getInetAddress());
		System.out.println("serverSocket localPort: " + serverSocket.getLocalPort());
		
		return serverSocket;
	}
	
	private Socket getClientSocket(ServerSocket serverSocket) throws IOException {
		Socket clientSocket = serverSocket.accept();
		System.out.println("clientSocket: " + clientSocket);
		System.out.println("clientSocket localSocketAddress: " + clientSocket.getLocalSocketAddress());
		System.out.println("clientSocket localAddress: " + clientSocket.getLocalAddress());
		System.out.println("clientSocket inetAddress: " + clientSocket.getInetAddress());
		System.out.println("clientSocket localPort: " + clientSocket.getLocalPort());
		System.out.println("clientSocket port: " + clientSocket.getPort());
		
		return clientSocket;
	}
}
