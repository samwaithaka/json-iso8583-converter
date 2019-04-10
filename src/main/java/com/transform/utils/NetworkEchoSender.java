package com.transform.utils;

import java.sql.Timestamp;

import org.json.JSONObject;

import com.transform.logging.CustomLogger;
import com.transform.messages.MessageFactory;

public class NetworkEchoSender extends Thread {
	
	private static String isoToJSONURL = "http://localhost:8080/iso-to-json";
	private static String JSONToIsoURL = "http://localhost:8080/json-to-iso";
	
	public void run() {
		int i = 0;
		while(true) {
			try {
				// Send network message
				String MTI = "1404";
				int stan = (int)(Math.random() * 9999 + 1); 
				String field24 = "831";
				String echoMessage = MessageFactory.createNetworkRequest(MTI, stan, field24);
				String isoMessage = getIsoMessage(echoMessage);
				CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > echo request: " + isoMessage);
				// Post
				//String response = MessageClient.send("10.185.13.94", 6342, isoMessage);
				String response = isoMessage;
				// Get response
				CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > echo response: " + response);
				String jsonString = getJSONMessage(response);
				System.out.println(jsonString);
				JSONObject jsonResponse =new JSONObject(jsonString);
				if(!jsonResponse.get("24").toString().equalsIgnoreCase("801")) {
					sendSignOnMessage();
				}
				if(i == 10) break;
				i++;
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sendSignOnMessage() {
		// Send network message
		int stan = (int)(Math.random() * 9999 + 1); 
		String MTI = "1404";
		int field11 = stan;
		String field24 = "801";
		String signOnMessage = MessageFactory.createNetworkRequest(MTI, field11, field24);
		String signOnIsoMessage = getIsoMessage(signOnMessage);
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Signon request: " + signOnIsoMessage);
		////String response = MessageClient.send("10.185.13.94", 6342, signOnIsoMessage);
		String response = signOnIsoMessage;
		// Get response
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Signon response: " + response);
		String jsonString = getJSONMessage(response);
		System.out.println(jsonString);
		JSONObject jsonResponse =new JSONObject(jsonString);
		//System.out.println(jsonResponse);
		System.out.println(jsonResponse.get("24"));
		//System.out.println(signOnIsoMessage);		
	}

	public static String getIsoMessage(String message) {
		String messageIso = HTTPClient.postRequest(JSONToIsoURL, message);
		messageIso = String.format("%06d", messageIso.length()) + messageIso;
		return messageIso;
	}
	
	public static String getJSONMessage(String message) {
		message = message.substring(6);
		String messageJSON = HTTPClient.postRequest(isoToJSONURL, message);
		return messageJSON;
	}
}
