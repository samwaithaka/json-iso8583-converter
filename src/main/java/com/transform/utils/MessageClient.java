package com.transform.utils;

import java.net.*;
import java.io.*;

		public class MessageClient {
		
			private static PrintWriter out;
		    private static BufferedReader in;
		    
			public static String send(String serverName, int port, String data) {
				String response = null;
				try {
					Socket clientSocket = new Socket(serverName, port);
					out = new PrintWriter(clientSocket.getOutputStream(), true);
			        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			        out.println(data);
			        response = in.readLine();
			        clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return response;
			}
		}
