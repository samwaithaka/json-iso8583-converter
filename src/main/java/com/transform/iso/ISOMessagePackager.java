package com.transform.iso;

import java.io.InputStream;
import java.util.Iterator;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.json.JSONObject;

public class ISOMessagePackager {

	private static GenericPackager packager;
	
	private static void getPackager() {
		ClassLoader classLoader = ISOMessagePackager.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("fields.xml");
		try {
			packager = new GenericPackager(is);
		} catch (ISOException e) {
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) {
		try {
			String message = "1200F270054188C080000100000E1500000416114041100000000026000000000000550020160720180707610000005787812016072018070760051010011020059990644078206BANK11620218578781BANK11  000000000105817404153EBP147SVCT0000204PAYD00127sort|40411000|;pan2|1140411000000000|;terminal_id|TPS04895|;transaction_type|credit_push|;sort2|40411000|;pan|1140411000000000|06BANK1106BANK113500030150000000085787790005006NATIVE06BANK1112254720376999008404110000027NRTV020254720376999 we code";
			String isoMsg = parseISOMessage(message);
			System.out.println(isoMsg);
			String isoString = buildISOMessage(isoMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String message = "{\"11\":\"000033\",\"MTI\":\"1200\",\"3\":\"000010\",\"4\":\"1500\",\"49\":\"840\",\"7\":\"1206041200\",\"41\":\"12340001\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public static String parseISOMessage(String message) throws Exception {
		getPackager();
		try {
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			isoMsg.unpack(message.getBytes());
			String JSONString = convertISOtoJSON(isoMsg);
			return JSONString;
		} catch (ISOException e) {
			throw new Exception(e);
		}
	}

	public static String buildISOMessage(String jsonString) throws Exception {
		getPackager();
		JSONObject message = new JSONObject(jsonString);
		Iterator<String> keys = message.keys();
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		while(keys.hasNext()) {
			String key = keys.next();
			if(key.equalsIgnoreCase("MTI")) {
				isoMsg.setMTI(message.get(key).toString());
			} else {
				int fieldNo = Integer.parseInt(key);
				isoMsg.set(fieldNo, message.get(key).toString());
			}
		}
		//printISOMessage(isoMsg);
		byte[] result = isoMsg.pack();
		return new String(result);
	}
	
	private static String convertISOtoJSON(ISOMsg isoMsg) {
		JSONObject message = new JSONObject();
		try {
			message.put("MTI",  isoMsg.getMTI());
			for (int i = 1; i <= isoMsg.getMaxField(); i++) {
				if (isoMsg.hasField(i)) {
					String key = Integer.toString(i);
					message.put(key, isoMsg.getString(i));
				}
			}
		} catch (ISOException e) {
			e.printStackTrace();
		}
		return message.toString();
	}
	
	@SuppressWarnings("unused")
	private static void printISOMessage(ISOMsg isoMsg) {
		try {
			System.out.printf("MTI = %s%n", isoMsg.getMTI());
			for (int i = 1; i <= isoMsg.getMaxField(); i++) {
				if (isoMsg.hasField(i)) {
					System.out.printf("Field (%s) = %s%n", i, isoMsg.getString(i));
				}
			}
		} catch (ISOException e) {
			e.printStackTrace();
		}
	}
}
