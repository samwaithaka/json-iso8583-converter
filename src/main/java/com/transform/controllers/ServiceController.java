package com.transform.controllers;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transform.iso.ISOMessagePackager;

@RestController
public class ServiceController {
	
	public static void main(String[] args) {
		//000378
		String msg = "1210F03405C18AC480000100000604000004101140435000500077000001180000009378201907221646249912100010164110200151059990663964206BANK35000000009378909ABC0000100000000000023403470404D0000000000000000D00000000404404021EBP015P_ID0000659748206BANK353500030150000001324628370005006NATIVE150011900022992290081NRTV021Test Push TransactionMSSR0460115SAMUEL WAITHAKA020003001115001190001000062";
		msg = "1200F03405C188C480000100000C14000004101140435000500077000001180000009391201907221650019912100010164110200151059990663964206BANK35000000009391ABC0000100000000000023403470404D0000000000000000D00000000404404108EBP102SVCT0000204PAYD00082sort|40435000|;sort2|40457000|;terminal_id|ABC00001|;transaction_type|credit_push|06BANK5706BANK3506BANK57150011900022992290081NRTV021Test Push TransactionMSSR0460115SAMUEL WAITHAKA020003001115001190001000062";
		msg = "1420F27005C58AC081400100000E1500000416114043500000000026000000000006660020200317093712610000003062132020031709371260051010011040040215999       66600            0644078206BANK35007709306213400BANK35  000000000105817404321200306213202003170937120644078206404005153EBP147SVCT0000204PAYD00127sort|40411000|;pan2|1140435000000000|;terminal_id|OMS04895|;transaction_type|credit_push|;sort2|40435000|;pan|1140411000000000|06BANK3506BANK353500030150000001543062110005006NATIVE06BANK351401125743969500038SORT00840435000PFEE00800001160PFEF001Y0064MSSR0430113Mohamed Sidiq02000300111401125413334100NRTV007payment";
		try {
			System.out.println(ISOMessagePackager.parseISOMessage(msg));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/")
	public String index() {
		return "Data format transform service";
	}

	@PostMapping(path = "/json-to-iso", consumes = "application/json")
	public String transformToIso(@RequestBody HashMap<String, String> message){
		JSONObject jsonMessage = new JSONObject(message);
		try {
			String isoString = ISOMessagePackager.buildISOMessage(jsonMessage.toString());
			System.out.println(isoString);
			return isoString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping(path = "/iso-to-json", produces = "application/json")
	public String transformJSON(@RequestBody String message){
		try {
			return ISOMessagePackager.parseISOMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}