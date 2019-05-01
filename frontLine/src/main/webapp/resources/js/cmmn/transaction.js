var tran = {
		saction : function (obj) {
			// DEF : defalt
			var DEF_HEADER_ACCEPT = "application/json";
			var DEF_ACCEPT = "application/json";
			var DEF_TYPE = "post";
			var DEF_DATATYPE = "json";
			var DEF_TIMEOUT = 60000;
			var DEF_ASYNC = true;
			var that = this;
			
			if (!cmmn.isEmpty(obj.accept)) {
				DEF_HEADER_ACCEPT = obj.header_accept;
			} else if (obj.header_accept == "empty") {
				DEF_HEADER_ACCEPT = "";
			}
			
			if (!cmmn.isEmpty(obj.accept)) {
				DEF_ACCEPT = obj.accept;
			} else if (obj.accept == "empty") {
				DEF_ACCEPT = "";
			}
			
			if (!cmmn.isEmpty(obj.type)) {
				DEF_TYPE = obj.type;
			} else if (obj.type == "empty") {
				DEF_TYPE = "";
			}
			
			if (!cmmn.isEmpty(obj.dataType)) {
				DEF_DATATYPE = obj.dataType;
			} else if (obj.dataType == "empty") {
				DEF_DATATYPE = "";
			}
			
			if (!cmmn.isEmpty(obj.timeout)) {
				DEF_TIMEOUT = obj.timeout;
			} else if (obj.timeout == "empty") {
				DEF_TIMEOUT = 20000;
			}
			
			if (!cmmn.isEmpty(obj.async)) {
				DEF_ASYNC = obj.async;
			} else if (obj.async == "empty") {
				DEF_ASYNC = "";
			}
			
			var jsonObjData = {};
			
			if (cmmn.isEmpty(obj.processData)) {
				jsonObjData = {
					  url: obj.url
					, accept: DEF_ACCEPT
					, headers: {'Accept': DEF_HEADER_ACCEPT
						, 'Content-Type': 'application/json'
					}
					, type: DEF_TYPE
					, timeout: DEF_TIMEOUT
					, data: obj.data
					, async: DEF_ASYNC
					, success: function(data) {
						that.callBackSuccess.call(this, obj, data);
					}
					, error: function(XMLHttpRequest, textStatus, errorThrown) {
						that.callBackError(XMLHttpRequest, textStatus, errorThrown, obj);
					}
						
				}
			} else {
				jsonObjData = {
					  url: obj.url
					, accept: DEF_ACCEPT
					, type: DEF_TYPE
					, timeout: DEF_TIMEOUT
					, processData: false
					, contentType: false
					, data: obj.data
					, async: DEF_ASYNC
					, success: function(data) {
						that.callBackSuccess.call(this, obj, data);
					}
					, error: function(XMLHttpRequest, textStatus, errorThrown) {
						that.callBackError(XMLHttpRequest, textStatus, errorThrown, obj);
					}
				}
			}
			$.ajax(jsonObjData);
		}
,
	callBackSuccess : function (obj, data) {
		if (typeof data == "string" && data.indexof("errorCode") > -1) {
			data = eval("(" + data + ")");
		}
		obj.fn_callback(data);
	}
,
	callBackError : function (XMLHttpRequest, textStatus, errorThrown, obj) {
		var that = this;
		that.loading(false);
		
		var msg = "서버와 통신 중 오류가 발생했습니다.";
		var status =  XMLHttpRequest.status;
		
		if (textStatus == "error") {
			if (!cmmn.isEmpty(XMLHttpRequest.responseText)) {
				if (status == 403 || status == 404 || status == 405
						|| status == 407 || status == 500 || status == 502
						|| status == 503) {
					
					try {
						var json = eval ("(" + XMLHttpRequest.responseText + ")");
						that.callBackRsltError(obj.svcType, json.errorMsg, "");
					} catch(e) {
						that.callBackRsltError(obj.svcType, msg, "");
					}
				} else if (status == 401) {
					that.callBackRsltError(obj.svcType, "로그인이 필요한 기능입니다.", "");
				} else {
					that.callBackRsltError(obj.svcType, msg, "");
				}
			} else {
				that.callBackRsltError(obj.svcType, msg, "");
			}
		} else {
			that.callBackRsltError(obj.svcType, msg, "");
		}
	}
,
	callBackRsltError : function (type, msg, lgon) {
		if (cmmn.isEmpty(lgon)) {
			cmmn.popup(msg);
		} else {
			var objFuncData = {
					  func: "callBackRsltErrorPop"
					, data: "/public/lgonPage.do"
			};
			cmmn.popup(msg, objFuncData);
		}
	}
,
	callBackRsltError : function (data, type) {
		window.location.href = data;
	}
,
	loading : function (flag) {
		console.log("asdfsdf");
	}
}



























