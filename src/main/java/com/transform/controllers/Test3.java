package com.transform.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import com.transform.utils.HTTPClient;
import com.transform.utils.MessageClient;
import com.transform.utils.MessageServer;

public class Test3 {

	public static void main(String[] args) {
		
		String url = "http://localhost:8080/iso-to-json";
		String data = "1200F270054188C080000100000E1500000416114041100000000026000000000000550020160720180707610000005787812016072018070760051010011020059990644078206BANK11620218578781BANK11  000000000105817404154EBP147SVCT0000204PAYD00127sort|404110334|;pan2|1140411000000000|;terminal_id|TPS04895|;transaction_type|credit_push|;sort2|40411002|;pan|1140411000000000|06BANK1106BANK113500030150000000085787790005006NATIVE06BANK1112254720376999008404110000027NRTV020254720376999 we code";
		//System.out.println(HTTPClient.postRequest(url, data));
		
		String url2 = "http://localhost:8080/json-to-iso";
		String data2 = "{\"22\":\"600510100110\",\"24\":\"200\",\"MTI\":\"1200\",\"26\":\"5999\",\"49\":\"404\",\"93\":\"BANK11\",\"72\":\"EBP147SVCT0000204PAYD00127sort|404110334|;pan2|1140411000000000|;terminal_id|TPS04895|;transaction_type|credit_push|;sort2|40411002|;pan|1140411000000000|\",\"94\":\"BANK11\",\"95\":\"00030150000000085787790005006NATIVE\",\"10\":\"61000000\",\"32\":\"440782\",\"11\":\"578781\",\"33\":\"BANK11\",\"12\":\"20160720180707\",\"37\":\"620218578781\",\"100\":\"BANK11\",\"2\":\"1140411000000000\",\"3\":\"260000\",\"102\":\"254720376999\",\"4\":\"000000005500\",\"104\":\"40411000\",\"126\":\"NRTV020254720376999 we coded\",\"7\":\"20160720180707\",\"41\":\"BANK11  \",\"42\":\"000000000105817\"}";
		//System.out.println(HTTPClient.postRequest(url2, data2));
		
		String response = MessageClient.send("localhost", 10001, data);
		System.out.println("Our response: " + response);
	}
}
