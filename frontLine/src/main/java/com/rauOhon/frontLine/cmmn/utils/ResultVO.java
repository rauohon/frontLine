package com.rauOhon.frontLine.cmmn.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ResultVO {
	
	private int errorCode;
	private String errorMsg;
	private Object resultData;

	public ResultVO() {
		this.errorCode = 0;
		this.errorMsg = "success";
	}
	
	public ResultVO(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	
	public String toJsonString() {
		JsonObject jsonObj = new JsonObject();
		
		jsonObj.addProperty("errorCode", errorCode);
		jsonObj.addProperty("errorMsg", errorMsg);
		
		if (resultData != null) {
			Gson gson = new GsonBuilder().create();
			String result = gson.toJson(resultData);
			jsonObj.addProperty("resultData", result);
		}
		
		return jsonObj.toString();
	}
	

}
