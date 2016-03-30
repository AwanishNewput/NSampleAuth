package com.sampleAuth.utility;


import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


/**
 * 
 * @author Newput Description : Use to create and parse the json object
 */

@Service
public class JsonResService {

	private boolean success;
	private String rcode;
	private String error;
	private Object data;


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Description : Create a Json object of time sheet to send as a response to
	 * UI.
	 * 
	 * @param map
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createTimeSheetJson(HashMap<String, String> map) {
		JSONObject obj = new JSONObject();
		obj.put("workDate", map.get("workDate"));
		obj.put("in", map.get("in"));
		obj.put("out", map.get("out"));
		obj.put("lunchIn", map.get("lunchIn"));
		obj.put("lunchOut", map.get("lunchOut"));
		obj.put("nightIn", map.get("nightIn"));
		obj.put("nightOut", map.get("nightOut"));
		obj.put("workDesc", map.get("workDesc"));
		return obj;
	}

	
	@SuppressWarnings("unchecked")
	public JSONObject responseSender() {
		JSONObject obj = new JSONObject();
		obj.put("success", isSuccess());
		obj.put("data", getData());
		obj.put("rcode", getRcode());
		obj.put("error", getError());
		return obj;
	}

	public void errorResponse(String response) {
		setData(null);
		setError(response);
		setRcode("505");
		setSuccess(false);
	}

	public void successResponse() {
		setError(null);
		setRcode(null);
		setSuccess(true);
	}

}
