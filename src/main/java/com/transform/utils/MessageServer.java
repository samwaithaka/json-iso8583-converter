package com.transform.utils;

import java.net.*;
import java.io.*;

public class MessageServer extends Thread {

	private ServerSocket serverSocket;
	
	private BufferedReader in;
	private PrintWriter out;

	public MessageServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(300000);
	}

	public void run() {
		while(true) {
			try {
				Socket clientSocket = serverSocket.accept();	
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String request = in.readLine();
	            System.out.println(request);
	            
	            String url = "http://localhost:8080/iso-to-json";
	            request = HTTPClient.postRequest(url, request);
	            
	            // Process transaction, convert to iso and post
	            String result = request;
	            
	            String url2 = "http://localhost:8080/json-to-iso";
	            String response = HTTPClient.postRequest(url2, result);
	            
	            out.println(response);
			} catch (SocketTimeoutException s) {
				System.out.println("Server: Server socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
