package com.rauOhon.frontLine.cmmn.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResultVO {
	
	private int errorCode;
	private String errorMsg;
	
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
	
	public String toJson() {
		Gson gson = new GsonBuilder().create();
		String result = gson.toJson(this);
		return result;
	}
	

}
