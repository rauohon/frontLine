<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(function () {
		
		// 캐릭터 정보 조회
		getCharacterDtlInfo();
		
		// 아이템 호버 이벤트
		$(document).on({
		    mouseenter: function () {
		    	$(this).css("background", "rgba(0,0,0,0.4)");
		    },
		    mouseleave: function () {
		    	$(this).css("background", "");
		    },
		    click: function () {
		    	var eqLoc = "eq" + $(this).data("iteqloc");
		    	var itCode = $(this).data("itcode");
		    	if ("eqEAT" != eqLoc) { //포션 등 먹는 종류 외 작동
			    	var offset = 0;
			    	var topStr = "eqNECK,eqHEAD,eqETC";
			    	var midStr1 = "eqWRIST_A,eqBODY,eqWRIST_B";
			    	var midStr2 = "eqHAND_A,eqLEG,eqHAND_B";
			    	var bottomStr = "eqARM_A,eqFOOT,eqARM_B";
			    	
			    	if (topStr.indexOf(eqLoc) > -1) {
			    		offset = 0;
			    	} else if (midStr1.indexOf(eqLoc) > -1) {
			    		offset = 100;
			    	} else if (midStr2.indexOf(eqLoc) > -1) {
			    		offset = 300;
			    	} else if (bottomStr.indexOf(eqLoc) > -1) {
			    		offset = 400;
			    	}
			    	
			    	$("#divEquip").animate({scrollTop : offset}, 400);
			    	
			    	
			    	// 아이템 착용 확인
			    	var data = itCode + "," + $(this).data("iteqloc");
			    	
			    	var obj = {
			    			  popid: "equipItem"
			    			, title: "알림"
			    			, btncanc: "취소"
			    			, data: data
			    			, cnfmfunc: "cnfmEquipItem"
			    	};
			    	console.log(obj);
			    	popup.create("아이템을 장착하시겠습니까?", obj);
		    	}
		    }
		}, ".local-item");
		
		$(document).on("click", ".panel-info-full", function () {
			var itCode = $(this).children(0).children(0).data("itcode");
			var msg = "장착 아이템을 해제하겠습니까?";
			var data = itCode;
			
			var obj = {
					  popid: "liftEquipment"
					, title: "알림"
					, btncanc: "취소"
					, data: data
					, cnfmfunc: "cnfmLiftEquipment"
			};
			
			$g_divEqItem = $(this);
			popup.create(msg, obj);
		});
		
		$(".local-shift").on("click", function () {
			$("#charaStatLoc").toggleClass("local-dpn");
			$("#charaAbilLoc").toggleClass("local-dpn");
		});
		
	})
	
	function getCharacterDtlInfo () {
		var params = {
				abc:"abcd"
		};
		
		var tranObj = {
				  url: "/game/getCharacterDtlInfo.do"
				, data: params
				, fn_callback: function (json) {
					if (json.errorCode > -1) {
						aftGetCharacterDtlInfo(json);
					} else {
						errGetCharacterDtlInfo(json);
					}
				}
		};
		
		tran.saction(tranObj);
		
	}
	
	function aftGetCharacterDtlInfo (json) {
		var data = JSON.parse(json.resultData).map;
		var dtlInfo = data.charaDtlInfo;
		var weaponInvenList = data.weaponInvenList;
		var armourInvenList = data.armourInvenList;
		var potionInvenList = data.potionInvenList;
		var equipInfo = data.charaEquipInfo;
		
		randerDtlInfo(dtlInfo); // 캐릭터 상태 셋팅
		randerAbilInfo(dtlInfo, equipInfo); // 캐릭터 능력치 셋팅
		randerInvenInfo(weaponInvenList, "weaponInven"); // 캐릭터 소지 무기 셋팅
		randerInvenInfo(armourInvenList, "armourInven"); // 캐릭터 소지 방어구 셋팅
		randerInvenInfo(potionInvenList, "potionInven"); // 캐릭터 소지 포션 셋팅
		randerEquipInfo(equipInfo); // 캐릭터 장비 아이템 셋팅
		
	}
	
	function randerDtlInfo (dtlInfo) { // 캐릭터 상태 셋팅
		
		if (cmmn.isEmpty(dtlInfo)) {
			return false;
		}
		
		var html = "";
		
		html += "<table class=\"table nomargin table-hover\">";
		html += "	<tbody>";
		html += "		<tr>";
		html += "			<th>이름</th>";
		html += "			<td>" + dtlInfo.mcLevel + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>직업</th>";
		html += "			<td>" + dtlInfo.jobName + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>레벨</th>";
		html += "			<td>" + dtlInfo.mcLevel + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>힘</th>";
		html += "			<td>" + dtlInfo.mcStr + "</td>";
		html += "		</tr>"
		html += "		<tr>"
		html += "			<th>민첩</th>";
		html += "			<td>" + dtlInfo.mcDex + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>지능</th>";
		html += "			<td>" + dtlInfo.mcInt + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>건강</th>";
		html += "			<td>" + dtlInfo.mcCon + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>HP</th>";
		html += "			<td>" + dtlInfo.mcNowhp + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>MP</th>";
		html += "			<td>" + dtlInfo.mcNowmp + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>소지금</th>";
		html += "			<td>" + dtlInfo.mcGold + "</td>";
		html += "		</tr>";
		html += "	</tbody>";
		html += "</table>";
		
		// 능력치 셋팅
		$("#charaStatLoc").html(html);
		
		// 경험치 셋팅
		var expPercents = (dtlInfo.mcNowexp / dtlInfo.reqExp) * 100;
		html = "경험치 :" + dtlInfo.mcNowexp + " (현재) / " + dtlInfo.reqExp + " (다음) / " + expPercents + "%";
		$("#charaExpLoc").html(html);

		// 이미지 셋팅
		var img = "<img name=\"male1\" src=\"" + dtlInfo.mcImglocation;
		img	+= dtlInfo.mcImgname + "\" class=\"media-object img-circle\" style=\"height: 100%; width: 100%;\">";
		$("#charaImgLoc").html(img);
		
	}
	
	function randerAbilInfo (dtlInfo, equipInfo) { // 캐릭터 능력치 셋팅
		var attckPt = Math.round((dtlInfo.mcStr + dtlInfo.mcDex/2 + dtlInfo.mcCon) * 2);
		var defencePt = (dtlInfo.mcStr + dtlInfo.mcCon) * 2;
		var actionPt = (Math.round(dtlInfo.mcStr/2) + dtlInfo.mcCon) * 3;
		var healthPt = (dtlInfo.mcStr*4 + dtlInfo.mcCon*6) * 2;
		
		var addAttackPt = 0;
		var addDefencePt = 0;
		var addActionPt = 0;
		var addHealthPt = 0;
		
		$("#charaAbilLoc").html("");

		for (var i = 0; i < equipInfo.length; i++) {
			var eqCode = equipInfo[i].cdCode;
			switch (eqCode) {
				case "AT" :
					if (cmmn.isEmpty(equipInfo[i].itPlusCnt)) {
						attckPt = attckPt + attckPt * equipInfo[i].itMultiCnt;
						addAttackPt += attckPt * equipInfo[i].itMultiCnt;
					} else {
						attckPt += equipInfo[i].itPlusCnt;
						addAttackPt += equipInfo[i].itPlusCnt
					}
					break;
				
				case "DF" :
					if (cmmn.isEmpty(equipInfo[i].itPlusCnt)) {
						defencePt = defencePt + defencePt * equipInfo[i].itMultiCnt;
						addDefencePt += attckPt * equipInfo[i].itMultiCnt;
					} else {
						defencePt += equipInfo[i].itPlusCnt;
						addDefencePt += equipInfo[i].itPlusCnt;
					}
					
					break;
				
				case "AC" :
					if (cmmn.isEmpty(equipInfo[i].itPlusCnt)) {
						actionPt = actionPt + actionPt * equipInfo[i].itMultiCnt;
						addActionPt = actionPt * equipInfo[i].itMultiCnt;
					} else {
						actionPt += equipInfo[i].itPlusCnt;
						addActionPt += equipInfo[i].itPlusCnt;
					}
					
					break;
				case "HP" :
					if (cmmn.isEmpty(equipInfo[i].itPlusCnt)) {
						healthPt = healthPt + healthPt * equipInfo[i].itMultiCnt;
						addHealthPt = healthPt * equipInfo[i].itMultiCnt;
					} else {
						healthPt += equipInfo[i].itPlusCnt;
						addHealthPt += equipInfo[i].itPlusCnt;
					}
					
					break;
			}
		}
		var html = "";
		html += "<table class=\"table nomargin table-hover\">";
		html += "	<tbody>";
		html += "		<tr>";
		html += "			<th>공격력</th>";
		html += "			<td id=\"attckPt\">" + attckPt + " (+" + addAttackPt + ")" + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>체력</th>";
		html += "			<td id=\"healthPt\">" + healthPt + " (+" + addHealthPt + ")" + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>방어력</th>";
		html += "			<td id=\"defencePt\">" + defencePt + " (+" + addDefencePt + ")" + "</td>";
		html += "		</tr>";
		html += "		<tr>";
		html += "			<th>기술력</th>";
		html += "			<td id=\"actionPt\">" + actionPt + " (+" + addActionPt + ")" + "</td>";
		html += "		</tr>"
		html += "	</tbody>";
		html += "</table>";
		
		var $html = $(html);
		var table = document.createElement("table");
		
		$("#charaAbilLoc").append($html);
	}
	
	function randerInvenInfo (list, loc) { // 캐릭터 소지 아이템 셋팅
		var html = "";
		if (cmmn.isEmpty(list) || list == null || list.length == 0) {
			return false;
		}
		
		for (var i = 0; i < list.length; i++) {
			var itTypeCd = list[i].itTypeCd;
			var plusContents = "";
			if (i % 6 == 0) {
				html += "<div class=\"row\">";
			}
			
			if ("AT" == itTypeCd) {
				plusContents = "추가 공격력 : ";
			} else if ("DF" == itTypeCd) {
				plusContents = "추가 방어력 : ";
			} else if ("PO" == itTypeCd) {
				plusContents = "회복량 : ";
			}
			
			if (cmmn.isEmpty(list[i].itPlusCnt)) {
				plusContents += "x " + list[i].itMultiCnt;
			} else {
				plusContents += "+ " + list[i].itPlusCnt;
			}

			html += "	<div class=\"col-sm-2 local-item\" style=\"border: 1px solid\" title=\"" + plusContents + "\" data-iteqloc=\"" + list[i].itEqLoc + "\" data-itcode=\"" + list[i].ivItcode + "\">";
			html += "		<div class=\"panel\">";
			html += "			<p>" + list[i].itName + "</p>";
			html += "			<p>" + list[i].ivAmount + "개</p>";
			html += "		</div>";
			html += "	</div>";
			
			if ((i % 6 == 0 && i != 0) || i == list.length - 1) {
				html += "</div>";
			}
		}
		$("#" + loc).html(html);
	}
	
	function randerEquipInfo (equipInfo) { // 캐릭터 장비 아이템 셋팅
		
		for (var item of equipInfo) {
			var eqLoc = item.eqLoc;
			var html = "";
			switch (eqLoc) {
			case "NECK" : //목걸이
				html = "목걸이 : ";
				break;
			
			case "HEAD" : //머리
				html = "머리 : ";
				break;
			
			case "ETC" : //기타
				html = "기타 : ";
				break;
			
			case "WRIST_A" : //팔찌A
				html = "팔찌  : ";
				break;
			
			case "BODY" : //몸통
				html = "몸통 : ";
				break;
			
			case "WRIST_B" : //팔찌B
				html = "팔찌 : ";
				break;
			
			case "HAND" : //장갑A&B
				html = "장갑 : ";
				break;
			
			case "LEG" : //바지
				html = "바지 : ";
				break;
			
			case "ARM_A" : //무기A
				html = "무기 : ";
				break;
			
			case "FOOT" : //신발
				html = "신발 : ";
				break;
			
			case "ARM_B" : //무기B
				html = "무기 : ";
				break;
			}
				html += item.itName + "<p data-itcode=\"" + item.eqItcode + "\"></p>";
			if ("HAND" != eqLoc) {
				$("#eq" + eqLoc).children(".panel").addClass("panel-info-full");
				$("#eq" + eqLoc + " .panel-heading").html(html);
			} else {
				//A & B 처리
				$("#eqHAND_A").children(".panel").addClass("panel-info-full");
				$("#eqHAND_B").children(".panel").addClass("panel-info-full");
				$("#eqHAND_A .panel-heading").html(html);
				$("#eqHAND_A .panel-heading").html(html);
			}
		}
		
	}

	function cnfmLiftEquipment (data) {
		
		var params = {
			eqItcode: data
		};
		
		var tranObj = {
				  url: "/game/liftEquipment.do"
				, data: params
				, fn_callback: function (json) {
					if (json.errorCode > -1) {
						effectiveEquip(json);

						$g_divEqItem.removeClass("panel-info-full");
						var pre = "<h6 class=\"panel-title\">";
						var aft = "</h6>"
						$g_divEqItem.children(".panel-heading").html(pre + $g_divEqItem.children(".panel-heading").html().substring(0,$g_divEqItem.children(0).html().indexOf(":") - 1) + aft);
						popup.create("해제되었습니다.");
					} else {
						var obj = {
								loccanc: "/game/myCharacterInfo.do"
						}
						popup.create("시스템 오류가 발생했습니다. 관리자에게 문의 바랍니다.", obj);
					}
				}
		};
		
		tran.saction(tranObj);
	}
	
	function cnfmEquipItem (data) {
		var itCode = data.split(",")[0];
		var eqLoc = data.split(",")[1];
		var params = {
			  itCode: itCode
			, eqLoc: eqLoc
		};
		
		var tranObj = {
				  url: "/game/equipItem.do"
				, data: params
				, fn_callback: function (json) {
					if (json.errorCode > -1) {
						var data = JSON.parse(json.resultData)
						if ("HAND" != eqLoc) {
							var html = "";
							switch (eqLoc) {
							case "NECK" : //목걸이
								html = "목걸이 : ";
								break;
							
							case "HEAD" : //머리
								html = "머리 : ";
								break;
							
							case "ETC" : //기타
								html = "기타 : ";
								break;
							
							case "WRIST_A" : //팔찌A
								html = "팔찌  : ";
								break;
							
							case "BODY" : //몸통
								html = "몸통 : ";
								break;
							
							case "WRIST_B" : //팔찌B
								html = "팔찌 : ";
								break;
							
							case "HAND" : //장갑A&B
								html = "장갑 : ";
								break;
							
							case "LEG" : //바지
								html = "바지 : ";
								break;
							
							case "ARM_A" : //무기A
								html = "무기 : ";
								break;
							
							case "FOOT" : //신발
								html = "신발 : ";
								break;
							
							case "ARM_B" : //무기B
								html = "무기 : ";
								break;
							}
							html += data.itName + "<p data-itcode=\"" + itCode + "\"></p>";
							$("#eq" + eqLoc).children(".panel").addClass("panel-info-full");
							$("#eq" + eqLoc + " .panel-heading").html(html);
						}
						effectiveEquip(json);
						popup.create("장착되었습니다.");
					} else {
						var obj = {
								loccanc: "/game/myCharacterInfo.do"
						}
						popup.create("시스템 오류가 발생했습니다. 관리자에게 문의 바랍니다.", obj);
					}
				}
		}
		tran.saction(tranObj);
	}
	
	function effectiveEquip(json) {
		var data = JSON.parse(json.resultData);
		var dtlInfo = data.charaDtlInfo;
		var equipInfo = data.charaEquipInfo;
		
		randerAbilInfo(dtlInfo, equipInfo)
	}

</script>


<div class="mainpanel">
	<div class="contentpanel">
		<div class="row">
			<!-- 캐릭터 정보창 -->
			<div class="col-sm-6" style="height: 87vh;">
				<div class="panel" style="height: 100%;">
					<div class="panel-heading">
						캐릭터 정보창
					</div>
					<div class="panel-body" style="height: 90%;">
						<!-- 사진 / 능력치 -->
						<div class="row" style="height: 50%;">
							<div class="col-sm-5" style="height: 100%;">
								<div id="charaImgLoc" class="panel-body" style="height: 100%;">
								</div>
							</div>
							<div class="col-sm-7" style="height: 100%;">
								<ul class="panel-options">
					            	<li class="local-shift"><a onclick="javastript:void(0)"><i class="fa fa-refresh" style="color: royalblue; font-size:27px;"></i></a></li>
				            	</ul>
								<div id="charaStatLoc" class="panel-body" style="height: 100%; padding-top: 8px; padding-bottom: 8px; overflow: auto;">
								</div>
								<div id="charaAbilLoc" class="panel-body local-dpn" style="height: 100%; padding-top: 8px; padding-bottom: 8px; overflow: auto;">
								</div>
							</div>
						</div>
						<!-- //사진 / 능력치 -->
						<!-- 경험치 정보 -->
						<div class="row" style="height: 7%;">
							<div class="col-sm-12" style="height: 100%;">
								<div id="charaExpLoc" class="panel-body" style="height: 100%; padding-top: 18px;">
								</div>
							</div>
						</div>
						<!-- //경험치 정보 -->
						<!-- 착용 아이템 정보 -->
						<div class="row" style="height: 43%;">
							<div class="col-sm-12" style="height: 100%;">
								<div class="panel-heading" style="height: 1vh;">
									착용 아이템
								</div>
								<div id="divEquip" class="panel-body" style="height: 29vh; overflow: auto;">
		<div class="row">
			<div id="eqNECK" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">목걸이</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-030-amulet"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqHEAD" class="col-sm-4">
				<div class="panel"><!-- panel-info-full 착용시 -->
					<div class="panel-heading">
						<h6 class="panel-title">머리</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-018-viking-helmet"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqETC" class="col-sm-4">
				<div class="panel panel-inverse-full"> <!-- panel-inverse-full 사용 불가시 -->
					<div class="panel-heading">
						<h6 class="panel-title">기타</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-006-gem"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="eqWRIST_A" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">팔찌</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-021-ring"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqBODY" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">몸통</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-019-heart"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqWRIST_B" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">팔찌</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-021-ring"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="eqHAND_A" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">장갑</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon mirtial flaticon-013-glove"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqLEG" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">바지</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon mirtial flaticon-050-boxing-shorts"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqHAND_B" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">장갑</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon mirtial flaticon-013-glove"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="eqARM_A" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">무기</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-004-axe"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqFOOT" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">신발</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon security flaticon-028-safety"></i>
						</div>
					</div>
				</div>
            </div>
			<div id="eqARM_B" class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h6 class="panel-title">무기</h6>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="local-icon fimanager flaticon-004-axe"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
								</div>
							</div>
						</div>
						<!-- //착용 아이템 정보 -->
					</div>
				</div>
			</div>
			<!-- //캐릭터 정보창 -->
			
			<!-- 캐릭터 소지아이템 창 -->
			<div class="col-sm-6" style="height: 87vh;">
				<div class="panel" style="height: 100%;">
					<div class="panel-heading">
						캐릭터 소지아이템 창
					</div>
					<div class="panel-body" style="height: 90%;">
						<!-- 무기 창 -->
						<div class="row" style="height: 33%;">
							<div class="panel-heading" style="height: 10%;">
								무기류
							</div>
							<div id="weaponInven" class="panel-body" style="height: 90%;">
							</div>
						</div>
						<!-- //무기 창 -->
						<!-- 방어구 -->
						<div class="row" style="height: 33%;">
							<div class="panel-heading" style="height: 10%;">
								방어구류
							</div>
							<div id="armourInven" class="panel-body" style="height: 90%;">
								머리 어깨 무릎 발 무릎 발
							</div>
						</div>
						<!-- //방어구 창 -->
						<!-- 포션 창 -->
						<div class="row" style="height: 33%;">
							<div class="panel-heading" style="height: 10%;">
								포션류
							</div>
							<div id="potionInven" class="panel-body" style="height: 90%;">
								머리 어깨 무릎 발 무릎 발
							</div>
						</div>
						<!-- //포션 창 -->
					</div>
				</div>				
			</div>
			<!-- //캐릭터 소지아이템 창 -->
		</div>
	</div>
</div>

<style type="text/css">
	.local-icon {
		font-size: 50px;
		text-align: center;
	}
	
	.local-dpn {
		display: none;
	}
</style>
<script type="text/javascript">
	var $g_divEqItem = null;
</script>