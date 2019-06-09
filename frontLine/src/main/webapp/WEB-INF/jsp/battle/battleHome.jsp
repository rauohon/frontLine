<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	var g_monsterSkillList = "";
	var g_monsterItemList = "";
	
	$(function () {
		
		getbattleInfo();
		
		$("#skillListOpen").on("click", function () {
			var divStatus = $("#skillListDiv").attr("class").indexOf("loc_display_off") > -1 ? true : false;
			toggleDivVisible($("#battleLogDiv"), !divStatus, "N");
			toggleDivVisible($("#skillListDiv"), divStatus, "N");
		});
		
		$("#invenListOpen").on("click", function () {
			var divStatus = $("#invenListPopup").attr("class").indexOf("loc_display_off") > -1 ? true : false;
			toggleDivVisible($("#invenListPopup"), divStatus, "Y");
		});
		
		$("#runAway").on("click", function () {
			$("#frm").attr("action", "/battle/runAway.do");
			$("#frm").attr("method", "post");
			$("#frm").submit();
		});
		
		$("#invenListPopupCancBtn").on("click", function () {
			toggleDivVisible($("#invenListPopup"), false, "Y");
		});
		
		$(document).on("click", ".loc_skill_btn", function () {
			var $data = $(this).children(0).eq(0);
			var damage = $data.data("damage");
			var heal = $data.data("heal");
			var defense = $data.data("defense");
			var ap = $data.data("ap");
			var data = [damage, heal, defense, ap];
			
			
			setTimeout(function() {
				effectSkill(data, "USER", "MONS")
				}, 500);// status, owner, trgt
		});
		
	})
	
	function getbattleInfo () {
		var tranObj = {
				  url: "/battle/getBattleInfo.do"
				, data: null
				, fn_callback: function (json) {
					if (json.errorCode > -1) {
						aftGetbattleInfo(json);
					} else {
						errGetbattleInfo(json);
					}
				} 
		}
		tran.saction(tranObj);
	}
	
	function aftGetbattleInfo (json) {
		var data = JSON.parse(json.resultData).map;
		var charaDtlInfo = data.charaDtlInfo;
		var monsDtlInfo = data.monsDtlInfo;
		var charaSkillList = data.charaSkillList;
		g_monsterSkillList = data.monsSkillList;
		var charaInvenList = data.charaInvenList;
		g_monsterItemList = data.monsItemList;
		
		randerCharaInfo(charaDtlInfo);
		randerMonsInfo(monsDtlInfo);
		randerCharaSkillList(charaSkillList);
		randerCharaInvenList(charaInvenList);
		
	}
	
	function randerCharaInfo (charaDtlInfo) {
		// 1. 이미지 그리기
		var html = "";
		html += "<span id=\"charaImgSpan\" style=\"text-align: center; display: inherit;\">";
		html += "	<img alt=\"" + charaDtlInfo.mcName + "\" src=\"" + charaDtlInfo.mcImglocation + charaDtlInfo.mcImgname + "\" style=\"height: 200px\">";
		html += "</span>";
		$("#charaImg").html(html);
		
		// 2. 체력바 그리기
		var attpt = fn_attckPtCalc(charaDtlInfo.mcStr, charaDtlInfo.mcDex, charaDtlInfo.mcCon);
		var defpt = fn_defencePtCalc(charaDtlInfo.mcStr, charaDtlInfo.mcCon);
		var ap = fn_defencePtCalc(charaDtlInfo.mcStr, charaDtlInfo.mcCon);
		var hp = fn_hpCalc(charaDtlInfo.mcStr, charaDtlInfo.mcCon);
		
		html = "";
		html += "<div class=\"panel-heading\">";
		html += "	<h4 class=\"panel-title\">";
		html += "		L<span>" + charaDtlInfo.mcLevel + "</span>";
		html += "	</h4>";
		html += "</div>";
		html += "<div class=\"panel-body\">";
		html += "	<span>HP : <span id=\"charaHpCnt\">" + hp + "</span> / <span id=\"charaHpBas\">" + hp + "</span></span><br>";
		html += "	<span>AP : <span id=\"charaApCnt\">" + ap + "</span> / <span id=\"charaApBas\">" + ap + "</span></span><br>";
		html += "	<div id=\"charaHpSlideDiv\" class=\"slider-success mb20 ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all\">";
		html += "		<div id=\"charaHpSlide\" class=\"ui-slider-range ui-widget-header ui-corner-all ui-slider-range-min\" style=\"width: 100%;\"></div>";
		html += "		<span id=\"charaHpSlideHandle\" class=\"ui-slider-handle ui-state-default ui-corner-all\" tabindex=\"0\" style=\"left: 100%;\">";
		html += "		</span>";
		html += "	</div>";
		html += "	<input id=\"charaDtlInfo\" type=\"hidden\" data-charanm=\"" + charaDtlInfo.mcName + "\" data-attpt=\"" + attpt + "\" data-defpt=\"" + defpt + "\" data-ap=\"" + ap + "\" data-hp=\"" + hp + "\" >"
		html += "</div>";
		$("#charaInfo").append(html);
	}
	
	function randerMonsInfo (monsDtlInfo) {
		// 1. 체력바이미지 그리기
		var attpt = fn_attckPtCalc(monsDtlInfo.moStr, monsDtlInfo.moDex, monsDtlInfo.moCon);
		var defpt = fn_defencePtCalc(monsDtlInfo.moStr, monsDtlInfo.moCon);
		var ap = fn_defencePtCalc(monsDtlInfo.moStr, monsDtlInfo.moCon);
		var hp = fn_hpCalc(monsDtlInfo.moStr, monsDtlInfo.moCon);
		
		var html = "";
		html += "<div class=\"panel-heading\">";
		html += "	<h4 class=\"panel-title\">";
		html += "		<span>" + monsDtlInfo.moName + "</span>";
		html += "	</h4>";
		html += "</div>";
		html += "<div class=\"panel-body\">";
		html += "	<span>HP : <span id=\"monsHpCnt\">" + hp + "</span> / <span id=\"monsHpBas\">" + hp + "</span></span>";
		html += "	<div id=\"monsHpSlideDiv\" class=\"slider-success mb20 ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all\">";
		html += "		<div id=\"monsHpSlide\" class=\"ui-slider-range ui-widget-header ui-corner-all ui-slider-range-min\" style=\"width: 100%;\"></div>";
		html += "		<span id=\"monsHpSlideHandle\" class=\"ui-slider-handle ui-state-default ui-corner-all\" tabindex=\"0\" style=\"left: 100%;\">";
		html += "		</span>";
		html += "	</div>";
		html += "	<input id=\"monsDtlInfo\" type=\"hidden\" data-monsnm=\"" + monsDtlInfo.moName + "\" data-attpt=\"" + attpt + "\" data-defpt=\"" + defpt + "\" data-ap=\"" + ap + "\" data-hp=\"" + hp + "\" data-moexp=\"" + monsDtlInfo.moExp + "\">"
		html += "</div>";
		$("#monsInfo").append(html);
		// 2. 이미지 그리기
		html = "";
		html += "<span id=\"monsImgSpan\" style=\"text-align: center; display: inherit;\">";
		html += "	<img alt=\"" + monsDtlInfo.moName + "\" src=\"" +  monsDtlInfo.moImglocation + monsDtlInfo.moImgname + "\" style=\"height: 200px\">";
		html += "</span>";
		$("#monsImg").html(html);
		
	}
	
	function randerCharaSkillList (charaSkillList) {
		var html = "";
		html += "<div class=\"panel\" style=\"margin-bottom: 0px;\">";
		html += "	<div class=\"panel-body\" style=\"padding-bottom: 0px; padding-top: 0px;\">";
		html += "		<div class=\"col-sm-7\">";
		if (!cmmn.isEmpty(charaSkillList) || charaSkillList.length < 1) {
			for (var i = 0; i < charaSkillList.length; i++) {
				html += "			<div class=\"col-sm-12\">";
				html += "				<div class=\"panel\">";
				html += "					<button class=\"btn btn-default btn-quirk btn-quirk loc_skill_btn loc_btn_width\">";
				html += "						<h3 data-damage=\"" + charaSkillList[i].skDamage + "\"";
				html += "							data-heal=\"" + charaSkillList[i].skHeal + "\"";
				html += "							data-defense=\"" + charaSkillList[i].skDefense + "\"";
				html += "							data-ap=\"" + charaSkillList[i].skAp + "\"";
				html += "						>"
				html += "						" + charaSkillList[i].skName + "</h3>(사용ap : " + charaSkillList[i].skAp + ")";
				html += "					</button>";
				html += "				</div>";
				html += "			</div>";
			}
		}
		html += "		</div>";
		html += "	</div>";
		html += "</div>";
		$("#skillListDiv").html(html);
	}
	
	function randerCharaInvenList (charaInvenList) {
		var html = "";
 		html += "<table class=\"table table-bordered nomargin table-hover table-striped-col\">";
		html += "	<colgroup>";
		html += "		<col style=\"width:40%;\">";
		html += "		<col style=\"width:11%;\">";
		html += "		<col style=\"width:16%;\">";
		html += "		<col style=\"width:12%;\">";
		html += "		<col style=\"width:16%;\">";
		html += "		<col style=\"width:5%;\">";
		html += "	</colgroup>";
		html += "	<thead>";
		html += "		<tr>";
		html += "			<th>이름</th>";
		html += "			<th>구분</th>";
		html += "			<th>회복량</th>";
		html += "			<th>보유개수</th>";
		html += "			<th>사용개수</th>";
		html += "			<th>사용</th>";
		html += "		</tr>";
		html += "	</thead>";
		html += "	<tbody>";
		if (!cmmn.isEmpty(charaInvenList) || charaInvenList.length > 0) {
			for (var i = 0; i <charaInvenList.length; i++) {
				html += "		<tr id=\"momRow" + charaInvenList[i].ivItcode + "\">";
				html += "			<td>" + charaInvenList[i].itName + "</td>";
				if (charaInvenList[i].itTypeCd == "POHP") {
					html += "			<td>HP</td>";
				} else {
					html += "			<td>AP</td>";
				}
				if (cmmn.isEmpty(charaInvenList[i].itPlusCnt) || 0 == charaInvenList[i].itPlusCnt) {
					html += "			<td id=\"effectVal" + charaInvenList[i].ivItcode + "\">x" + charaInvenList[i].itMultiCnt + "</td>";
				} else {
					html += "			<td id=\"effectVal" + charaInvenList[i].ivItcode + "\">+" + charaInvenList[i].itPlusCnt + "</td>";
				}
				html += "			<td id=\"ivAmount" + charaInvenList[i].ivItcode + "\" >" + charaInvenList[i].ivAmount + "</td>";
				html += "			<td><input id=\"useVal" + charaInvenList[i].ivItcode + "\" type=\"text\" class=\"itemQnty\" placeholder=\"\" value=\"1\"></td>";
				html += "			<td><button class=\"btn btn-primary btn-quirk btn-stroke\" onclick=\"useItem('" + charaInvenList[i].ivItcode + "')\">사용</button></td>";
				html += "		</tr>";
			}			
		}
		html += "	</tbody>";
		html += "</table>";
		$("#invenUseContent").html(html);
	}
	
	function effectSkill (data, owner, trgt) {
		// 0 : damage, 1 : heal, 2 : defense, 3 : ap
		// "USER", "MONS"
		var $charaInfo = $("#charaDtlInfo");
		var $charaHpSlide = $("#charaHpSlide");
		var $charaHpSlideHandle = $("#charaHpSlideHandle");
		var $monsInfo = $("#monsDtlInfo");
		var $monsHpSlide = $("#monsHpSlide");
		var $monsHpSlideHandle = $("#monsHpSlideHandle");
	
		if (!cmmn.isEmpty(data[0]) || 0 != data[0] * 1) {
			// 공격
			// 체력 - (주인 스킬 공격력 + 주인 공격력 - 대상 방어력)
			if (owner == "USER") {
				var hp = $monsInfo.data("hp");
				var ap = $charaInfo.data("ap");
				var skillAttpt = data[0];
				var attpt = $charaInfo.data("attpt");
				var defpt = $monsInfo.data("attpt");
				var damage = skillAttpt + attpt - defpt;
				
				if (damage < 0) {
					damage = 1;
				}
				
				var remainAp = ap - data[3];
				if (remainAp < 0) {
					popup.create("기술 포인트가 모자랍니다.");
					return;
				} else {
					$("#charaApCnt").html(remainAp);
					$charaInfo.data("ap", remainAp);
				}
				
				var result = hp - (damage);
				var resultPercent = (100 * result) / $("#monsHpBas").html() * 1;
				$monsHpSlide.css("width", resultPercent + "%");
				$monsHpSlideHandle.css("left", resultPercent + "%");

				if (result < 0) {
					$monsInfo.data("hp", 0);
					$("#monsHpCnt").html(0);
					$("#monsImg").css("background", "black");
					$("#monsImg").css("opacity", "0.5");
					// 전투 끝 승리
					battleFinsh("VICTORY");
				} else {
					
					if (26 < resultPercent && resultPercent < 51) {
						$("#monsHpSlideDiv").removeClass("slider-success");
						$("#monsHpSlideDiv").addClass("slider-warning");
					} else if (resultPercent < 26) {
						$("#monsHpSlideDiv").removeClass("slider-success");
						$("#monsHpSlideDiv").removeClass("slider-warning");
						$("#monsHpSlideDiv").addClass("slider-danger");
					}
					
					$monsInfo.data("hp", result);
					$("#monsHpCnt").html(result);
					$("#skillListDiv").removeClass("loc_display_on");
					$("#skillListDiv").addClass("loc_display_off");
					$("#cmmnd-panel").find("button").prop("disabled", true);
					
					setTimeout(function() {
						monsterAttckStart()
						}, 500);
					
					setBattleLog($charaInfo.data("charanm"), $monsInfo.data("monsnm"), damage);
				}
				
				blinkImg($("#monsImgSpan"));
				
			} else {
				var hp = $charaInfo.data("hp");
				var skillAttpt = data[0];
				var attpt = $monsInfo.data("attpt");
				var defpt = $charaInfo.data("attpt");
				var damage = skillAttpt + attpt - defpt;
				
				if (damage < 0) {
					damage = 1;
				}
				
				var result = hp - (damage);
				console.log(damage);
				var resultPercent = (100 * result) / $("#charaHpBas").html() * 1;
				$charaHpSlide.css("width", resultPercent + "%");
				$charaHpSlideHandle.css("left", resultPercent + "%");
				

				if (result <= 0) {
					$charaInfo.data("hp", 0);
					$("#charaHpCnt").html(0);
					// 전투 끝 패배
					setBattleLog($monsInfo.data("monsnm"), $charaInfo.data("charanm"), damage);
					$("#charaImg").css("background", "black");
					$("#charaImg").css("opacity", "0.5");
					battleFinsh("LOSE");
				} else {
					if (26 < resultPercent && resultPercent < 51) {
						$("#charaHpSlideDiv").addClass("slider-success");
						$("#charaHpSlideDiv").addClass("slider-warning");
					} else if (resultPercent < 26) {
						$("#charaHpSlideDiv").removeClass("slider-success");
						$("#charaHpSlideDiv").removeClass("slider-warning");
						$("#charaHpSlideDiv").addClass("slider-danger");
					}
					
					$charaInfo.data("hp", result);
					$("#charaHpCnt").html(result);
					$("#skillListDiv").removeClass("loc_display_off");
					$("#skillListDiv").addClass("loc_display_on");
					$("#cmmnd-panel").find("button").prop("disabled", false);

					setBattleLog($monsInfo.data("monsnm"), $charaInfo.data("charanm"), damage);
				}
			}
			
				
		} else if (!cmmn.isEmpty(data[1]) || 0 != data[1] * 1) {
			// 회복
		} else if (!cmmn.isEmpty(data[2]) || 0 != data[2] * 1) {
			// 방어
		} else if (!cmmn.isEmpty(data[3]) || 0 != data[3] * 1) {
			// ap 소모
		}
	}
	
	function monsterAttckStart () {
		var data = g_monsterSkillList;
		var listLength = g_monsterSkillList.length;
		
		if (listLength > 1) {
			// 스킬 랜덤 호출
		} else if (listLength == 1) {
			var damage = data[0].skDamage;
			var heal = data[0].skHeal;
			var defense = data[0].skDefense;
			var ap = data[0].skAp;
			var data = [damage, heal, defense, ap];
		}
		
		blinkImg($("#charaImgSpan"));
		
		setTimeout(function() {
			effectSkill(data, "MONS", "USER")
			}, 500);// status, owner, trgt
	}
	
	function blinkImg ($this) {
		for (var i = 0; i < 3; i++) {
			$this.animate({opacity: "0.1"}, "fast");
			$this.animate({opacity: "1"}, "fast");
		}
	}
	
	function battleFinsh (clsf) {
		var params = {
				  clsf: clsf
				, addExp: $("#monsDtlInfo").data("moexp")
				, getItemCd: g_monsterItemList[0].itCode
		}
		
		var tranObj = {
				  url: "/battle/endBattle.do"
				, data: params
				, fn_callback: function (json) {
					aftEndBattle(json);
				}
		}
		tran.saction(tranObj);
	}
	
	function setBattleLog (owner, trgt, damage) {
		var $battleLogDiv = $("#battleLogDiv").children(0).eq(0).children(0).eq(0);
		var msg = owner + "의 공격으로 " + trgt + "에게 " + damage + "의 피해를 주었습니다.";
		var html = "";
		html += "		<h3>" + msg + "</h3>";
		
		if ($battleLogDiv.find("h3").length > 3) {
			$battleLogDiv.html("");
		}
		
		toggleDivVisible($("#battleLogDiv"), true, "N");
		toggleDivVisible($("#skillListDiv"), false, "N");
		
		$battleLogDiv.append(html);
		
	}
	
	function useItem (itCode) {
		var ivAmount = $("#ivAmount" + itCode).html() * 1;
		var itUseVal = $("#useVal" + itCode).val() * 1;
		var effectVal = $("#effectVal" + itCode).html().substring(1, $("#effectValIT0000000000004").html().length) * 1;
		var maxHp = $("#charaHpBas").html() * 1;
		var nowHp = $("#charaHpCnt").html() * 1;
		var aftHp = nowHp + (effectVal * itUseVal)
		if (aftHp > maxHp) {
			aftHp = maxHp
		}
		
		if (maxHp == nowHp) {
			popup.create("체력이 가득 차있습니다.");
			return;
		}
		
		if (ivAmount - itUseVal < 0) {
			popup.create("보유량을 초과 했습니다.");
			return;
		}
		
		var $charaHpSlide = $("#charaHpSlide");
		var $charaHpSlideHandle = $("#charaHpSlideHandle");
		var resultPercent = (100 * aftHp) / $("#charaHpBas").html() * 1;
		$charaHpSlide.css("width", resultPercent + "%");
		$charaHpSlideHandle.css("left", resultPercent + "%");
		
		$("#charaDtlInfo").data("hp", aftHp);
		$("#charaHpCnt").html(aftHp);
		$("#useVal" + itCode).val(1);
		$("#ivAmount" + itCode).html(ivAmount - itUseVal);
		
		if (ivAmount - itUseVal <= 0) {
			$("#momRow" + itCode).remove();
		}
		
		if (resultPercent > 50) {
			$("#charaHpSlideDiv").removeClass("slider-danger");
			$("#charaHpSlideDiv").removeClass("slider-warning");
			$("#charaHpSlideDiv").addClass("slider-success");
		} else if (26 < resultPercent && resultPercent < 51) {
			$("#charaHpSlideDiv").removeClass("slider-success");
			$("#charaHpSlideDiv").removeClass("slider-danger");
			$("#charaHpSlideDiv").addClass("slider-warning");
		} else if (resultPercent < 26) {
			$("#charaHpSlideDiv").removeClass("slider-success");
			$("#charaHpSlideDiv").removeClass("slider-warning");
			$("#charaHpSlideDiv").addClass("slider-danger");
		}
		
		var params = {
				  ivAmount: ivAmount - itUseVal
				, itCode: itCode
		}
		
		var tranObj = {
				  url: "/battle/useItem.do"
				, data: params
				, fn_callback: function (json) {
					popup.create(effectVal * itUseVal + " 만큼 체력이 회복되었습니다.");
				}
		}
		tran.saction(tranObj);
	}
	
	function toggleDivVisible ($this, flag, popupYn) {
		if (popupYn != "Y") {
			if (flag) { // 보이게
				$this.removeClass("loc_display_off");
				$this.addClass("loc_display_on");
			} else { // 가림
				$this.removeClass("loc_display_on");
				$this.addClass("loc_display_off");
			}
		} else { // 팝업
			if (flag) { // 보이게
				$this.removeClass("loc_display_off");
				$this.addClass("loc_popup_on");
			} else {
				$this.removeClass("loc_popup_on");
				$this.addClass("loc_display_off");
			}
		}
	}
	
	function aftEndBattle (json) {
		var data = "";
		var itemMap = "";
		var itemMsg = "";
		
		if (!cmmn.isEmpty(json.resultData)) {
			data = JSON.parse(json.resultData).map
			itemMap = data.itemMap;
		}
		
		if (!cmmn.isEmpty(itemMap)) {
			itemMsg = "<br>" + itemMap.itName + " 1개를 획득했습니다."
		}
		
		var msg = json.errorMsg + "";
		msg += itemMsg;
		
		popup.create(msg, {
			cnfmfunc: "cnfmEndBattlePopup"
		});
	}
	
	function cnfmEndBattlePopup () {
		$("#frm").attr("action", "/game/gameHome.do");
		$("#frm").attr("method", "post");
		$("#frm").submit();
	}
	
</script>
<div class="mainpanel">
	<div class="contentpanel">
		<!-- 적군 정보 row -->
		<div class="row">
			<div class="col-sm-12">
				<div class="panel">
					<div class="col-sm-7">
						<div id="monsInfo" class="panel">
						</div>
					</div>
					<div class="col-sm-5">
						<div id="monsImg" class="panel">
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 본인 정보 row -->
		<div class="row">
			<div class="col-sm-12">
				<div class="panel">
					<div class="col-sm-5">
						<div id="charaImg" class="panel">
						</div>
					</div>
					<div class="col-sm-7">
						<div id="charaInfo" class="panel">
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- command row -->
		<div class="row">
			<div class="col-sm-12">
				<div class="panel" style="margin-bottom: 0px;">
					<div class="panel-body" style="padding-bottom: 14px;">
						<div id="infoDiv" class="col-sm-7">
							<div id="skillListDiv" class="col-sm-12 loc_display_off">
							</div>
							<div id="battleLogDiv" class="col-sm-12 loc_display_on">
								<div class="panel">
									<div class="panel-body">
									</div>
								</div>
							</div>
						</div>
						<div id="cmmnd-panel" class="col-sm-5" style="border: 10px dashed darkgray;">
							<div class="panel">
								<div class="panel-body">
									<div class="row">
										<div class="col-sm-6">
											<div class="panel">
												<button id="skillListOpen" class="btn btn-default btn-quirk btn-quirk loc_btn_width"><h3>싸운다</h3></button>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="panel">
												<button id="invenListOpen" class="btn btn-default btn-quirk btn-quirk loc_btn_width"><h3>가방을 연다</h3></button>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="panel">
												<button id="runAway" class="btn btn-default btn-quirk btn-quirk loc_btn_width"><h3>도망친다</h3></button>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="panel">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<style type="text/css">
	.loc_btn_width {
		width: 100%;
		border: 5px #bdc3d1 solid;
	}
	
	.loc_display_on {
		display: block;
	}
	
	.loc_display_off {
		display: none;
	}
	
	.loc_popup_on {
		display: inline-flex;
	}

</style>

<section class="popup_wrap loc_display_off" id="invenListPopup">
	<div class="popup col-md-4">
		<div class="panel-warning">
			<div class="panel-heading">
				<h3 class="panel-title">가방</h3>
			</div>
		</div>
		<div class="panel-body">
			<div id="invenUseContent">
			</div>
			<hr class="invisible">
			<div class="popup_btn">
				<span>
					<a href="javascript:void(0)" id="invenListPopupCancBtn" class="btn btn-danger btn-quirk btn-stroke">닫기</a>
				</span>
			</div>
		</div>
	</div>
</section>
<!-- 
<script>
	$(document).ready(function() {

		'use strict';

		// Basic Slider
		$('#slider').slider({
			range : "min",
			max : 100,
			value : 50
		});

		// Basic Slider: Primary
		$('#slider-primary').slider({
			range : "min",
			max : 100,
			value : 43
		});

		// Basic Slider: Success
		$('#slider-success').slider({
			range : "min",
			max : 100,
			value : 100
		});

		// Basic Slider: Warning
		$('#slider-warning').slider({
			range : "min",
			max : 100,
			value : 37
		});

		// Basic Slider: Danger
		$('#slider-danger').slider({
			range : "min",
			max : 100,
			value : 45
		});

		// Basic Slider: Info
		$('#slider-info').slider({
			range : "min",
			max : 100,
			value : 55
		});

	});
</script> -->