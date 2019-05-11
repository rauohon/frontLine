<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(function () {
		setCharacterStatus();
		
		$(".col-sm-3").on("click", "label", function () {
			var $radio = $(this).children("img");
			setSelectedCharaTypeTxt($radio);
			setCharacterStatus();
		});
		
		$("#statReGene").on("click", function () {
			setCharacterStatus();
		})
		
		$("#saveBtn").on("click", function () {
			var errCdMsg = beforeCreateCharacter();
			if (errCdMsg[0] == "00") {
				createCharacter();
			} else {
				popup.create(errCdMsg[1]);
			}
		})
	})
	
	function setSelectedCharaTypeTxt ($radio) {
		var selectedName = $radio.attr("name");
		var selectedNmFirst = selectedName.substring(0, 1);
		var selectedNmLast = selectedName.substring(selectedName.length-1, selectedName.length) * 1;
		
		var result = "";
		if (selectedNmFirst == "m") {
			result += "남자 ";
			result += setOneToEndCase(selectedNmLast);
		} else if (selectedNmFirst == "f") {
			result += "여자 ";
			result += setOneToEndCase(selectedNmLast);
		}
		$("#selectedChara").html(result);
		return false;
	}
	
	function setOneToEndCase (jobCnt) {
		var result = "";
		switch (jobCnt) {
			case 1 :
				result = "A 타입";
				break;
			case 2 :
				result = "B 타입";
				break;
			case 3 :
				result = "C 타입";
				break;
			case 4 :
				result = "D 타입";
				break;
		}
		return result;
	}
	
	function setCharacterStatus () {
		var statusCmmnCd = '${statusCmmnCd}';
		var statusLength = JSON.parse(statusCmmnCd).length;
		var totStatus = 0;
		var cnt = 0;
		
		while (cnt < statusLength || totStatus < 22) {
			// 0. 4개 능력치에 맞춰 돌림
			for (var i = 0; i < statusLength; i++) {
				// 1. 능력치 계산
				var status = calcRandomStatus();
				// 2. 능력치 셋팅
				editHtmlCharacterStatus(i, status);
				
				totStatus += status;
				cnt++;
			}
			
			if (totStatus < 22) {
				totStatus = 0;
				cnt = 0;
			}
		}
		
		// 합계 셋팅
		$("#statusSum").html(totStatus);
	}
	
	function calcRandomStatus() {
		var status = Math.floor(Math.random() * 10) + 3;
		if (status > 10) status = status - 3;
		return status;
	}
	
	function editHtmlCharacterStatus (jobCnt, status) {
		
		switch (jobCnt) {
			case 0 : // 힘
				$("#strChara").html(status);
				break;
			case 1 : // 민첩
				$("#dexChara").html(status);
				break;
			case 2 : // 지능
				$("#intChara").html(status);
				break;
			case 3 : // 건강
				$("#conChara").html(status);
				break;
		}
	}
	
	function beforeCreateCharacter () {
		var errCdMsg = [];
		var charaNm = $("#charaNm").val();
		var charaSex = $("#selectedChara").html();
		
		var charaStat = {
				  strs: $("#strChara").html()
				, dexs: $("#dexChara").html()
				, ints: $("#intChara").html()
				, cons: $("#conChara").html()
		}
		
		errCdMsg[0] = "00";
		errCdMsg[1] = "능력치를 생성해주세요.";
		
		if (cmmn.isEmpty(charaNm)) {
			errCdMsg[0] = "01";
			errCdMsg[1] = "캐릭터 이름을 정해주세요.";
		}
		
		if (cmmn.isEmpty(charaSex)) {
			errCdMsg[0] = "02";
			errCdMsg[1] = "캐릭터 이름을 정해주세요.";
		}
		
		if (cmmn.isEmpty(charaSex)) {
			errCdMsg[0] = "03";
			errCdMsg[1] = "능력치를 생성해주세요.";
		}
		
		return errCdMsg;
	}
	
	function createCharacter () {
		var charaNm = $("#charaNm").val();
		var charaSex = $("#selectedChara").html();
		
		var charaStatObj = {
				  strs: $("#strChara").html()
				, dexs: $("#dexChara").html()
				, ints: $("#intChara").html()
				, cons: $("#conChara").html()
		}
		var charaStat = JSON.stringify(charaStatObj);
		
		var params = JSON.stringify({
				  charaNm: charaNm
				, charaSex: charaSex
				, charaStat: charaStat
		});
		
		var tranObj = {
				  url: "/game/createCharacter.do"
				, data: params
				, fn_callback: function (json) {
					console.log(json);
					if (json.errorCode > -1) {
						popup.create("캐릭터가 생성되었습니다.", {func:"aftClosePopup"});
					} else {
						popup.create("캐릭터 생성에 실패했습니다.");
					}
				}
		};
		
		tran.saction(tranObj);
	}
	
	function aftClosePopup () {
		$("#frm").attr("action", "/game/gameHome.do");
		$("#frm").attr("method", "post");
		console.log("asdf")
		$("#frm").submit();
	}
	
</script>


<div class="mainpanel">
	<div class="contentpanel">
		<div class="row">
			<div class="col-sm-12">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">캐릭터 이미지</h4>
					</div>
					<div class="panel-body">
						<div class="row male">
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>남자 A 타입</span>
									<img name="male1" src="/resources/images/characters/1.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>남자 B 타입</span>
									<img name="male2" src="/resources/images/characters/2.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>남자 C 타입</span>
									<img name="male3" src="/resources/images/characters/3.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>남자 D 타입</span>
									<img name="male4" src="/resources/images/characters/4.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
						</div>
						<div class="row female">
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>여자 A 타입</span>
									<img name="female1" src="/resources/images/characters/5.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>여자 B 타입</span>
									<img name="female2" src="/resources/images/characters/6.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>여자 C 타입</span>
									<img name="female3" src="/resources/images/characters/7.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
							<div class="col-sm-3 panel">
								<label class="rdiobox rdiobox-primary">
									<input type="radio" name="characters">
									<span>여자 D 타입</span>
									<img name="female4" src="/resources/images/characters/8.jpg" alt="남자 A 타입" class="media-object img-circle" style="width: 50%; height: 50%">
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">캐릭터 정보</h4>
					</div>
					<div class="panel-body">
						<div class="col-sm-3">
							<p>선택된 캐릭터 :</p>
							<p>힘 :</p>
							<p>민첩 :</p>
							<p>지능 :</p>
							<p>건강 :</p>
							<p>합 :</p>
						</div>
						<div class="col-sm-3">
							<p id="selectedChara">남자 A타입</p>
							<p id="strChara">1</p>
							<p id="dexChara">1</p>
							<p id="intChara">1</p>
							<p id="conChara">1</p>
							<p id="statusSum">1</p>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">캐릭터 정보 편집</h4>
					</div>
					<div class="panel-body">
						<table class="table table-striped nomargin">
							<tbody>
								<tr style="background-color: transparent;">
									<td style="padding-top: 3%;">
										이름 : 
									</td>
									<td>
										<input type="text" id="charaNm" name="charaNm" placeholder="캐릭터 명" class="form-control" maxlength="6">
									</td>
								</tr>
								<tr style="background-color: transparent;">
									<td style="padding-top: 3%;">
										능력치 다시 만들기 
									</td>
									<td>
										<button id="statReGene" name="statReGene" class="btn btn-success btn-stroke btn-icon btn-sm"><i class="fa fa-repeat"></i></button>
									</td>
								</tr>
								<tr style="background-color: transparent;">
									<td colspan="2">
										<button id="saveBtn" name="saveBtn" class="btn btn-primary btn-quirk btn-stroke btn-block">저장</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="frm" name="frm">
		<input type="hidden" id="saveCharaNm" name="saveCharaNm" />
		<input type="hidden" id="saveCharaSex" name="saveCharaSex" />
		<input type="hidden" id="saveCharaStat" name="saveCharaStat" />
	</form>
</div>