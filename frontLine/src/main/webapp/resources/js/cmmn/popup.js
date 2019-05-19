var popup = {
	create : function (msg, obj) {
		var that = this;
		var popupHtml = that.setHtml(msg, obj);

		if (!cmmn.isEmpty(obj)) {
			if (!cmmn.isEmpty(obj.popid)) {
				$("#" + obj.popid).remove();
			} else {
				$("#cmmnPopupWrap").remove();
			}
		} else {
			$("#cmmnPopupWrap").remove();
		}
		
		$("body").append(popupHtml);
		
		if (!cmmn.isEmpty(obj)) {
			$("#cmmnPopup").data("popid", obj.popid);
			$("#cmmnPopup").data("data", obj.data);
			$("#cmmnPopup").data("cnfmfunc", obj.cnfmfunc);
			$("#cmmnPopup").data("cancfunc", obj.cancfunc);
			$("#cmmnPopup").data("foc", obj.foc);
			$("#cmmnPopup").data("loccnfm", obj.loccnfm);
			$("#cmmnPopup").data("loccanc", obj.loccanc);
		}
		$("#btnPopCnfm").focus();
		//resizePopup();
	}
,
	setHtml : function (msg, obj) {
		var popupHtml = "";
		$('html').scrollTop(0);
		if (!cmmn.isEmpty(obj)) {
			if (!cmmn.isEmpty(obj.popid)) {
				popupHtml += "	<section class=\"popup_wrap\" id=\"" + obj.popid + "\">";
			} else {
				popupHtml += "	<section class=\"popup_wrap\" id=\"cmmnPopupWrap\">";
			}
		} else {
			popupHtml += "	<section class=\"popup_wrap\" id=\"cmmnPopupWrap\">";
		}
		
		popupHtml += "		<div class=\"popup col-md-4\" id=\"cmmnPopup\">";
		if (!cmmn.isEmpty(obj)) {
			if (!cmmn.isEmpty(obj.title)) {
				popupHtml += "			<div class=\"panel-warning\">";
				popupHtml += "				<div class=\"panel-heading\">";
				popupHtml += "					<h3 class=\"panel-title\">" + obj.title + "</h3>";
				popupHtml += "				</div>";
				popupHtml += "			</div>";
			}
		}
		popupHtml += "			<div class=\"panel-body\">";
		popupHtml += "				<p><strong>" + msg + "<strong></p>";
		popupHtml += "				<div class=\"popup_btn\">";
		
		if (!cmmn.isEmpty(obj)) {
			if (!cmmn.isEmpty(obj.btncanc)) {
				popupHtml += "				<span><a href=\"javascript:void(0)\" id=\"btnPopCanc\" class=\"btn btn-danger btn-quirk btn-stroke\">" + obj.btncanc + "</a></span>";
			}
			
			if (!cmmn.isEmpty(obj.btnCnfm)) {
				popupHtml += "				<span><a href=\"javascript:void(0)\" id=\"btnPopCnfm\" class=\"btn btn-primary btn-quirk btn-stroke\">" + obj.btnCnfm + "</a></span>";
			} else {
				popupHtml += "				<span><a href=\"javascript:void(0)\" id=\"btnPopCnfm\" class=\"btn btn-primary btn-quirk btn-stroke\">확인</a></span>";
			}
		} else {
			popupHtml += "				<span><a href=\"javascript:void(0)\" id=\"btnPopCnfm\" class=\"btn btn-primary btn-quirk btn-stroke btn-block\">확인</a></span>";
		}

		popupHtml += "				</div>";
		popupHtml += "			</div>";
		popupHtml += "		</div>";
		popupHtml += "	</section>";
	
		return popupHtml;	
	}
}

$(document).on("click", "#btnPopCnfm", function (e) {
	e.preventDefault();
	
	var popid = $("#cmmnPopup").data("popid");
	var data = $("#cmmnPopup").data("data");
	var func = $("#cmmnPopup").data("cnfmfunc");
	var foc = $("#cmmnPopup").data("foc");
	var loccnfm = $("#cmmnPopup").data("loccnfm");
	
	if (!cmmn.isEmpty(popid)) {
		$("#" + popid).remove();
	} else {
		$("#cmmnPopupWrap").remove();
	}
	
	if (!cmmn.isEmpty(func)) {
		if (!cmmn.isEmpty(data)) {
			eval(func + "(\"" + data + "\", \"cnfm\")");
		} else {
			eval(func + "(\"cnfm\")");
		}
	}
	
	if (!cmmn.isEmpty(foc)) {
		$("#" + foc).focus();
	}
	
	if (!cmmn.isEmpty(loccnfm)) {
		window.location.href = loccnfm;
	}
});

$(document).on("click", "#btnPopCanc", function (e) {
	e.preventDefault();
	
	var popid = $("#cmmnPopup").data("popid");
	var data = $("#cmmnPopup").data("data");
	var func = $("#cmmnPopup").data("cancfunc");
	var foc = $("#cmmnPopup").data("foc");
	var loccanc = $("#cmmnPopup").data("loccanc");
	
	if (!cmmn.isEmpty(popid)) {
		$("#" + popid).remove();
	} else {
		$("#cmmnPopupWrap").remove();
	}
	
	if (!cmmn.isEmpty(func)) {
		if (!cmmn.isEmpty(data)) {
			eval(func + "(\"" + data + "\", \"canc\")");
		} else {
			eval(func + "(\"canc\")");
		}
	}
	
	if (!cmmn.isEmpty(foc)) {
		$("#" + foc).focus();
	}
	
	if (!cmmn.isEmpty(loccanc)) {
		window.location.href = loccanc;
	}
});
