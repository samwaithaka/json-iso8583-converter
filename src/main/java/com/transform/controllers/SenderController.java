package com.transform.controllers;

import java.sql.Timestamp;
import java.util.HashMap;

import com.transform.logging.CustomLogger;
import com.transform.messages.MessageFactory;
import com.transform.utils.HTTPClient;
import com.transform.utils.MessageClient;

public class SenderController {
	private static String isoToJSONURL = "http://localhost:8080/iso-to-json";
	private static String JSONToIsoURL = "http://localhost:8080/json-to-iso";
	private static int stan = (int)(Math.random() * 9999 + 1);
	public static void main(String[] args) {
		//sendSignOnMessage();
		sendAccountToCardMessage();
		// Send send to card message
	}
	
	public static void sendSignOnMessage() {
		// Send network message
		String MTI = "1404";
		int field11 = stan;
		String field24 = "801";
		String signOnMessage = MessageFactory.createNetworkRequest(MTI, field11, field24);
		String signOnIsoMessage = getIsoMessage(signOnMessage);
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Signon request: " + signOnIsoMessage);
		System.out.println(signOnIsoMessage);		
	}
	
	public static void sendSignOffMessage() {
		// Send network message
		String MTI = "1404";
		int field11 = stan;
		String field24 = "802";
		String signOffMessage = MessageFactory.createNetworkRequest(MTI, field11, field24);
		String signOffIsoMessage = getIsoMessage(signOffMessage);
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > echo request: " + signOffIsoMessage);
		System.out.println(signOffIsoMessage);		
	}
	
	public static void sendAccountToCardMessage() {
		// Send network message
		String MTI = "1200";
		int field11 = stan;
		String field24 = "802";
		String field2 = "1140485000";
		String field3 = "500077";
		int field4 = 118;
		String field25 = "1510";
		String field26 = "5999";
		String field32 = "417363";
		String field33 = "BANK35";
		int field37 = stan;
		String field41 = "ABC00001";
		int field42 = 234;
		int field46 = 0;
		String field49 = "404";
		String field93 = "BANK35";
		String field94 = "BANK35";
		String field100 = "BANK35";
		String field102 = "001190002299229";
		String field126 = "Test Credit Push Transaction";
		String sort = "40485000";
		String pan2 = "4737292930309191";
		String terminalId = "ABC350010";
		
		HashMap<String, String> message = MessageFactory.createTransactionMessage(field2, field3, field4, 
				field11, field24, field25, field26, field32,field33, field37, field41, field42, field46, 
				field49,field93, field94, field100, field102, field126);
		String accToCardTransactionMessage = MessageFactory.createAccountToCardRequest(message, MTI, sort, pan2, terminalId);
		String accToCardTransactionIsoMessage = getIsoMessage(accToCardTransactionMessage);
		System.out.println(accToCardTransactionIsoMessage);	
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Acc to Card request: " + accToCardTransactionIsoMessage);
		// Post
		String response = MessageClient.send("10.185.13.94", 6342, accToCardTransactionIsoMessage);
		// Get response
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Acc to Card response: " + response);
		System.out.println(getJSONMessage(response));
	}
	
	public static void sendAccountToAccountMessage() {
		// Send network message
		String MTI = "1200";
		int field11 = stan;
		String field24 = "802";
		String field2 = "1140485000";
		String field3 = "500077";
		int field4 = 118;
		String field25 = "1510";
		String field26 = "5999";
		String field32 = "417363";
		String field33 = "BANK35";
		int field37 = stan;
		String field41 = "ABC00001";
		int field42 = 234;
		int field46 = 0;
		String field49 = "404";
		String field93 = "BANK35";
		String field94 = "BANK35";
		String field100 = "BANK35";
		String field102 = "001190002299229";
		String field103 = "211900022944429";
		String field126 = "Test Credit Push Transaction";
		String sort = "40485000";
		String sort2 = "40457000";
		String terminalId = "ABC350010";
		
		HashMap<String, String> message = MessageFactory.createTransactionMessage(field2, field3, field4, 
				field11, field24, field25, field26, field32,field33, field37, field41, field42, field46, 
				field49,field93, field94, field100, field102, field126);
		String accToAcTransactionMessage = MessageFactory.createAccountToAccountRequest(message, MTI, field103, sort, sort2, terminalId);
		String accToAcTransactionIsoMessage = getIsoMessage(accToAcTransactionMessage);
		System.out.println(accToAcTransactionIsoMessage);	
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Acc to Ac request: " + accToAcTransactionIsoMessage);	
		// Post
		String response = MessageClient.send("10.185.13.94", 6342, accToAcTransactionIsoMessage);
		// Get response
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Acc to Ac response: " + response);
		System.out.println(getJSONMessage(response));
	}
	
	public static void sendAccountToPhoneMessage() {
		// Send network message
		String MTI = "1200";
		int field11 = stan;
		String field24 = "802";
		String field2 = "1140485000";
		String field3 = "500077";
		int field4 = 118;
		String field25 = "1510";
		String field26 = "5999";
		String field32 = "417363";
		String field33 = "BANK35";
		int field37 = stan;
		String field41 = "ABC00001";
		int field42 = 234;
		int field46 = 0;
		String field49 = "404";
		String field93 = "BANK35";
		String field94 = "BANK35";
		String field100 = "BANK35";
		String field102 = "001190002299229";
		String field126 = "Test Credit Push Transaction";
		String sort = "40485000";
		String sort2 = "40487000";
		String phone2 = "254720317929";
		String terminalId = "ABC350010";
		
		HashMap<String, String> message = MessageFactory.createTransactionMessage(field2, field3, field4, 
				field11, field24, field25, field26, field32,field33, field37, field41, field42, field46, 
				field49,field93, field94, field100, field102, field126);
		String accToPhoneTransactionMessage = MessageFactory.createAccountToPhoneRequest(message, MTI, sort, sort2, phone2, terminalId);
		String accToPhoneTransactionIsoMessage = getIsoMessage(accToPhoneTransactionMessage);
		System.out.println(accToPhoneTransactionIsoMessage);	
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Acc to Phone request: " + accToPhoneTransactionIsoMessage);	
		
	    // Post
		String response = MessageClient.send("10.185.13.94", 6342, accToPhoneTransactionIsoMessage);
		// Get response
		CustomLogger.log("INFO", new Timestamp(System.currentTimeMillis()) + " > Acc to Phone response: " + response);
		System.out.println(getJSONMessage(response));
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
