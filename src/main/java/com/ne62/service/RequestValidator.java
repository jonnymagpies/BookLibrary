package com.ne62.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RequestValidator {

	public boolean checkValue(JSONObject request) throws JSONException, ParseException , NumberFormatException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Boolean valid = false;
			for (Object key : request.keySet()) {
				if (request.get(key.toString()) == null || request.getString(key.toString()).equals("")) {
					valid = false;
					break;
				} else {
					if (key.toString().equals("publish_date")) {
						Date date = sdf.parse(request.getString(key.toString()));
						valid = true;
					} else if (key.toString().equals("pages")) {
						Integer pages = Integer.valueOf(request.getString(key.toString()));
						valid = true;
					} else {
						valid = true;
					}
				}

			}
			return valid;
		

	}
}
