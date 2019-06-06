var cmmn = {
	isEmpty : function (value) {
		if ( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ) {
			return true;
		} else {
			return false;
		}
	}
,
	makeOption : function (options) {
		if (option == null || options.cdBigCode == null || option.select == null) {
			return;
		}
		
		if (option.isShowValue == null) {
			option.isShowValue = false;
		}
		
		if (option.insertBlackNm == null) {
			option.insertBlackNm = "";
		}
		
		var $this = option.select;
		
		if ($this.length > 0) {
			tran.saction({
				  url: "/cmmn/cdListJson.do"
				, data: {
					  cdBigCode: options.cdBigCode
					, returnType: "option"
				}
				, fn_callBack: function (json) {
					if (json.errorCode == null) {
						if (option.insertBlankNm != "") {
							json = "	<option value\"\">" + option.insertBlankNm + "</option>" + json;
						}
						
						$this.html(json);
						
						$(option.skipList).each(function (i, v) {
							$this.find("[value='" + v + "']").remove();
						});
						
						if ( typeof options.defaultSelect !== "undefined" ) {
							$this.find("[value = '" + options.defaultSelect + "']").attr("selected", "selected");
						}
						
						// 명칭(코드) 처리
						if (options.isShowValue) {
							$this.children().each(function () {
								if ($(this).val() != "") {
									$(this).attr("data-text", $(this).text());
									$(this).text($(this).text() + "(" + $(this).val() + ")");
								}
							});
						}
					} else {
						popup.create(json.errorMsg);
					}
				}
			})
		}
	}
,
	tableToJson : function ($table) { // 테이블 컬럼 선택 추가(개발중)
		var jsonArray = [];
		var jsonEl;
		$($table).find("tr").each(function (index, item) {
			jsonEl = {};
			jsonEl.idx = index;
			var $itCode = $("#invoice").children().eq(index).attr("id"); 
			jsonEl.itCode = $itCode.substring($itCode.indexOf("-") + 1, $itCode.length);
			jsonEl.basCost = $("#invoice").children().eq(index).children().eq(2).html();
			jsonEl.qnty = $("#invoice").children().eq(index).children().eq(3).children().val();
			var sellBuy = 1;
			var flagText = $("#invoice").children().eq(index).children().eq(4).html().substring(1,3);
			
			if (flagText == "판매") {
				sellBuy = -1;
			}
			
			jsonEl.sellBuy = sellBuy;
			
			jsonArray.push(jsonEl);
		});
		
		return jsonArray;
	}
}