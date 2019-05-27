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
			
			that.loading(true);
			
			console.log(JSON.stringify(obj.data));
			
			if (cmmn.isEmpty(obj.processData)) {
				console.log("------------");
				console.log(obj.data);
				console.log("------------");
				jsonObjData = {
					  url: obj.url
					, accept: DEF_ACCEPT
					, headers: {'Accept': DEF_HEADER_ACCEPT
						, 'Content-Type': 'application/json'
					}
					, type: DEF_TYPE
					, timeout: DEF_TIMEOUT
					, data: JSON.stringify(obj.data)
					, async: DEF_ASYNC
					, success: function(data) {
						that.callBackSuccess.call(this, obj, data);
					}
					, error: function(XMLHttpRequest, textStatus, errorThrown) {
						that.callBackError(XMLHttpRequest, textStatus, errorThrown, obj);
					}
						
				}
			} else {
				console.log("------------");
				console.log(obj.data);
				console.log("------------");
				jsonObjData = {
					  url: obj.url
					, accept: DEF_ACCEPT
					, type: DEF_TYPE
					, timeout: DEF_TIMEOUT
					, processData: false
					, contentType: false
					, data: JSON.stringify(obj.data)
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
		tran.loading(false);
		if (typeof data == "string") {
			if (data.indexOf("errorCode") > -1) {
				if (data.indexOf("&#034;") > -1) {
					data = data.replace(/&#034;/g,"\"");
				}
				
				data = eval("(" + data + ")");
			}
		}
		obj.fn_callback(data);
	}
,
	callBackError : function (XMLHttpRequest, textStatus, errorThrown, obj) {
		tran.loading(false);
		
		var msg = "서버와 통신 중 오류가 발생했습니다.";
		var status =  XMLHttpRequest.status;
		
		if (textStatus == "error") {
			if (!cmmn.isEmpty(XMLHttpRequest.responseText)) {
				if (status == 403 || status == 404 || status == 405
						|| status == 407 || status == 500 || status == 502
						|| status == 503) {
					
					try {
						var json = eval ("(" + XMLHttpRequest.responseText + ")");
						tran.callBackRsltError(obj.svcType, json.errorMsg, "");
					} catch(e) {
						tran.callBackRsltError(obj.svcType, msg, "");
					}
				} else if (status == 401) {
					tran.callBackRsltError(obj.svcType, "로그인이 필요한 기능입니다.", "");
				} else {
					tran.callBackRsltError(obj.svcType, msg, "");
				}
			} else {
				tran.callBackRsltError(obj.svcType, msg, "");
			}
		} else {
			tran.callBackRsltError(obj.svcType, msg, "");
		}
	}
,
	callBackRsltError : function (type, msg, lgon) {
		if (cmmn.isEmpty(lgon)) {
			popup.create(msg);
		} else {
			var objFuncData = {
					  func: "callBackRsltErrorPop"
					, data: "/public/lgonPage.do"
			};
			popup.create(msg, objFuncData);
		}
	}
,
	callBackRsltError : function (data, type) {
		window.location.href = data;
	}
,
	loading : function (flag) {
		var that = this;
		
		if (flag == undefined) {
			flag = true;
		}
		
		if (!that._isMake()) {
			that._make();
		}
		
		$("#loadingDiv").css("display", "none");
		if (flag) {
			$("#loadingDiv").css("display", "block");
		}
	}
,
	_isMake() {
		return $("#lodingDiv").length > 0;
	}
,
	_make() {
		var that = this;
		var _size = 50;
		var _maxZindex = that._getMaxZindex();
		var _$div = $(document.createElement("div"));
		
		_$div.css({
		  	  'position': 'fixed'
			, 'left': '0'
			, 'right': '0'
			, 'top': '0'
			, 'bottom': '0'
			, 'z-index': _maxZindex
			, 'background': '#000'
			, 'opacity': '0.6'
		});
		
		_$div.attr("id", "loadingDiv");
		_$div.hide();
		
		var _$img = $(document.createElement("img"));
		_$img.css("z_index", _maxZindex + 2);
		
		_$img.css({
		  	  'position': 'fixed'
			, 'left': 'px'
			, 'height': 'px'
			, 'right': '50%'
			, 'top': '50%'
			, '-webkit-transform': 'translate(-50%, -50%)'
			, 'transform': 'translate(-50%, -50%)'
		});
		
		_$img.attr("id", "loadingImg");
		_$img.attr("src", "/resources/images/cmmn/loading_01.gif");
		
		_$div.append(_$img);
		
		$("body").append(_$div);
	}
,
	_getMaxZindex() {
		var _maxZindex = 0;
		
		$("*").each(function() {
			var _thisZindex = $(this).css("z-index") * 1;
			if (!isNaN(_thisZindex)) {
				if (_maxZindex < _thisZindex) {
					_maxZindex = _thisZindex;
				}
			}
		});
		
		return 6000;
	}
}



























