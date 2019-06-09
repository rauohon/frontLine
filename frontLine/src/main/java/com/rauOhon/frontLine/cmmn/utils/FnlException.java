package com.rauOhon.frontLine.cmmn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FnlException extends RuntimeException {

	private static final long serialVersionUID = 755575494205559580L;

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public FnlException() {
		super("시스템 오류가 발생했습니다.");
		log.info("시스템 오류 발생");
	}
	
	public FnlException(String msg) {
		super(msg);
		log.info("시스템 오류 발생 : {}", msg);
	}
}
