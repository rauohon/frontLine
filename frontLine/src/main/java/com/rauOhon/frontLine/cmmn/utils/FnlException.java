package com.rauOhon.frontLine.cmmn.utils;

public class FnlException extends RuntimeException {

	private static final long serialVersionUID = 755575494205559580L;

	public FnlException() {
		super("시스템 오류가 발생했습니다.");
	}
	
	public FnlException(String msg) {
		super(msg);
	}
}
