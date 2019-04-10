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