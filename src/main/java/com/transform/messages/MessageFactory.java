package com.transform.messages;

import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;

public class MessageFactory {

	public static String createNetworkRequest(String MTI, int field11, String field24) {
		HashMap<String, String> message = new HashMap<String, String>();
		String stan = String.format("%06d" , field11);
		message.put("MTI", MTI);
		message.put("11", stan);
		message.put("12", createDateTimeString());
		message.put("24", field24);
		JSONObject JSONMessage = new JSONObject(message);
        return JSONMessage.toString();
	}

	public static String createNetworkResponse(String MTI, int field11, String field24, String field39) {
		HashMap<String, String> message = new HashMap<String, String>();
		String stan = String.format("%06d" , field11);
		message.put("MTI", MTI);
		message.put("11", stan);
		message.put("12", createDateTimeString());
		message.put("24", field24);
		message.put("39", field39);
		JSONObject JSONMessage = new JSONObject(message);
        return JSONMessage.toString();
	}
	
	/**
	 * 
	 * @param field2 primary account number - 11+sender bank sort code
	 * @param field3 processing code e.g. 5700077
	 * @param field4 transaction amount N12 + fees
	 * @param field11 system trace audit number
	 * @param field24 function code, default use 200
	 * @param field25 message reason code, default use 1510
	 * @param field26 card acceptor business code
	 * @param field32 Acquirer Institution Identification Code
	 * @param field33 Forwarding Institution Identification Code
	 * @param field37 retrieval ref number
	 * @param field41 Card Acceptor Terminal Identification
	 * @param field42 Card Acceptor Identification Code
	 * @param field46 Amounts, Fees
	 * @param field49 Currency Code, Transaction
	 * @param field93 Transaction Destination Institution Identification Code
	 * @param field94 Transaction Originator Institution Identification Code
	 * @param field100 Receiving Institution Identification Code
	 * @param field102 Account Identification 1
	 * @param field126 Narration
	 * @return JSONMessageString
	 */

	public static HashMap<String, String> createTransactionMessage(String field2,String field3,
			int field4,int field11,String field24,String field25,String field26,String field32,String field33,
			int field37,String field41,int field42,int field46,String field49,String field93,
			String field94,String field100,String field102,String field126) {
		HashMap<String, String> message = new HashMap<String, String>();
		
		// format the amount
		String amount = String.format("%012d", field4 * 100);
		String stan = String.format("%06d", field11);
		String ref = String.format("%012d", field37);
		String cardAcceptorId = String.format("%015d" , field42);
		String fees = generateFees(field4, Integer.parseInt(field49), "D");
		message.put("2", field2);
		message.put("3", field3);
		message.put("4", amount);
		message.put("11", stan);
		message.put("12", createDateTimeString());
		message.put("14", "9912");
		message.put("24", field24);
		message.put("25", field25);
		message.put("26", field26);
		message.put("32", field32);
		message.put("33", field33);
		message.put("37", ref);
		message.put("41", field41);
		message.put("42", cardAcceptorId);
		message.put("46", fees);
		message.put("49", field49);
		message.put("93", field93);
		message.put("94", field94);
		message.put("100", field100);
		message.put("102", field102);
		message.put("126", field126);
        return message;
	}

	/**
	 * 
	 * @param message
	 * @param sort sender bank sort code
	 * @param pan2 receiver card number
	 * @return
	 */
	
	public static String createAccountToCardRequest(HashMap<String, String> message, String MTI, String sort, String pan2, String terminalId) {
		String field72 = createField72Data(sort, null, null, null, null, pan2, null, terminalId, "credit_push");
		message.put("MTI", MTI);
		message.put("72", field72);
		JSONObject JSONMessage = new JSONObject(message);
        return JSONMessage.toString();
	}
	
	/**
	 * 
	 * @param message
	 * @param field103 receivers account number
	 * @param sort senders bank sort code
	 * @param sort2 receivers bank sort code
	 * @param terminalId
	 * @return
	 */

	public static String createAccountToAccountRequest(HashMap<String, String> message, String MTI, String field103,
			String sort, String sort2, String terminalId) {
		String field72 = createField72Data(sort, sort2, null, null, null, null, null, terminalId, "credit_push");
		message.put("MTI", MTI);
		message.put("72", field72);
		message.put("103", field103);
		JSONObject JSONMessage = new JSONObject(message);
        return JSONMessage.toString();
	}

	/**
	 * 
	 * @param message
	 * @param field103 receiver's account number
	 * @param sort sender bank sort code
	 * @param sort2 receiver bank sort code
	 * @param phone2 receiver phone number
	 * @param terminalId
	 * @return
	 */
	public static String createAccountToPhoneRequest(HashMap<String, String> message, String MTI,
			String sort, String sort2, String phone2, String terminalId) {
		String field72 = createField72Data(sort, sort2, null, phone2, null, null, null, terminalId, "credit_push");
		message.put("MTI", MTI);
		message.put("72", field72);
		JSONObject JSONMessage = new JSONObject(message);
        return JSONMessage.toString();
	}
	
	public static String createDateTimeString() {
		Calendar cal = Calendar.getInstance();
		String MM = "" + (1 + cal.get(Calendar.MONTH));
		String DD = "" + cal.get(Calendar.DAY_OF_MONTH);
		String hh = "" + cal.get(Calendar.HOUR_OF_DAY);
		String mm = "" + cal.get(Calendar.MINUTE);
		String ss = "" + cal.get(Calendar.SECOND);
		String YY = "" + cal.get(Calendar.YEAR);
		if (MM.length() < 2) {
			MM = "0" + MM;
		}
		if (DD.length() < 2) {
			DD = "0" + DD;
		}
		if (hh.length() < 2) {
			hh = "0" + hh;
		}
		if (mm.length() < 2) {
			mm = "0" + mm;
		}
		if (ss.length() < 2) {
			ss = "0" + ss;
		}
		return YY + MM + DD + hh + mm + ss;
	}
	
	/**
	 * 
	 * @param sort sender bank sort code
	 * @param sort2 receiver bank sort code
	 * @param phone sender phone
	 * @param phone2 receiver phone
	 * @param pan sender pan
	 * @param pan2 receiver pan
	 * @param expiry sender card expiry date
	 * @param terminalId originator pos/amt/ibank
	 * @param transactionType transaction type
	 * @return data
	 */
	public static String createField72Data(String sort, String sort2, String phone, String phone2, String pan, String pan2,
			String expiry, String terminalId, String transactionType) {
		String data = "EBP292SVCT0000204PAYD0027";
		data += "sort|" + (sort == null ? "" : sort);
		data += "|;phone|" + (phone == null ? "" : phone);
		data += "|;sort2|" + (sort2 == null ? "" : sort2);
		data += "|;phone2|" + (phone2 == null ? "" : phone2);
		data += "|;pan|" + (pan == null ? "" : pan);
		data += "|;pan2|" + (pan2 == null ? "" : pan2);
		data += "|;expiry|" + (expiry == null ? "" : expiry);
		data += "|;terminal_id|" + (terminalId == null ? "" : terminalId); 
		data += "|;transaction_type|" + transactionType + "|";
		return data;
	}
	
	private static String generateFees(int amount, int currency, String type) {
		String fees = "70";
		fees += currency;
		fees += type;
		fees += String.format("%07d", amount);
		fees += "0";
		fees += String.format("%06d", 0);
		fees += "D";
		fees += String.format("%07d", amount);
		fees += currency;
		return fees;
	}
}
