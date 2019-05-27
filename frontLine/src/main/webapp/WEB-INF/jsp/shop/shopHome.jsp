<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(function () {
		iqryShopItemList();
		
	})

	function iqryShopItemList () { // 상점 판매 리스트 조회
		var pageName = "${pageName}";
		var pageTitle = "";
		var params = {
			pageName: pageName
		};
		
		switch (pageName) {
			case "weaponShop" :
				pageTitle = "무기상점";
				break;
			
			case "armourShop" :
				pageTitle = "방어구상점";
				break;
			
			case "potionShop" :
				pageTitle = "포션상점";
				break;
		}
		
		$("#shopTitle").html(pageTitle);
		
		var tranObj = {
				url: "/shop/shopItemList.do"
				, data: params
				, fn_callback: function (json) {
					var data = JSON.parse(json.resultData).map;
					var charaDtlInfo = data.charaDtlInfo;
					var sellItemList = data.sellItemList;
					var myInvenList = data.myInvenList;
					
					
					randerCharaDtlInfo(charaDtlInfo);
					randerItemList(sellItemList, "shop");
					randerItemList(myInvenList, "inven");
				}
		}
		
		tran.saction(tranObj);
	}
	
	function randerCharaDtlInfo (charaDtlInfo) {
		var html = charaDtlInfo.mcGold + " 골드";
		$("#myGold").html(html);
	}
	
	function randerItemList (itemList, flag) {
		var html = "";
		if (flag == "shop") {
			$("#sellList").html("");
		} else {
			$("#invenList").html("");
		}

		html += "<table class=\"table table-bordered nomargin table-hover table-striped-col\">";
		html += "	<colgroup>";
		html += "		<col style=\"width:5%;\">";
		html += "		<col style=\"width:40%;\">";
		html += "		<col style=\"width:20%;\">";
		html += "		<col style=\"width:20%;\">";
		html += "		<col style=\"width:10%;\">";
		html += "	</colgroup>";
		html += "	<thead>";
		html += "		<tr>";
		html += "			<th tabindex=\"0\">";
		html += "				<label class=\"ckbox ckbox-primary\">";
		html += "					<input type=\"checkbox\"><span></span>";
		html += "				</label>";
		html += "			</th>";
		html += "			<th tabindex=\"0\">아이템명</th>";
		html += "			<th tabindex=\"0\">향상능력치</th>";
		if (flag == "shop") {
			html += "			<th tabindex=\"0\">구입가격</th>";
			html += "			<th tabindex=\"0\">구입수량</th>";
		} else {
			html += "			<th tabindex=\"0\">판매가격</th>";
			html += "			<th tabindex=\"0\">보유(판매)수량</th>";
		}
		html += "		</tr>";
		html += "	</thead>";
		html += "	<tbody>";
		for (var i = 0; i < itemList.length; i++) {
			html += "		<tr>";
			html += "			<td tabindex=\"0\">";
			html += "				<label class=\"ckbox ckbox-primary\">";
			html += "					<input type=\"checkbox\"><span></span>";
			html += "				</label>";
			html += "			</td>";
			html += "			<td>" + itemList[i].itName + "</td>";
			if (cmmn.isEmpty(itemList[i].itPlusCnt)) {
				html += "			<td> x" + itemList[i].itMultiCnt + "</td>";
			} else {
				html += "			<td> +" + itemList[i].itPlusCnt + "</td>";
			}
			if (flag == "shop") {
				html += "			<td>" + itemList[i].itCost + "</td>";
				html += "			<td><input type=\"text\" value=\"1\"></td>";
			} else {
				html += "			<td>" + itemList[i].itCost + "</td>";
				html += "			<td><input type=\"text\" value=\"" + itemList[i].ivAmount + "\"></td>";
			}
			html += "		</tr>";
		}
		html += "	</tbody>";
		html += "</table>";
		
		var $html = $(html);
		if (flag == "shop") {
			$("#sellList").append($html);
		} else {
			$("#invenList").append($html);
		}
	}

</script>


<div class="mainpanel">
	<div class="contentpanel">
		<div class="row">
			<div class="col-sm-12">
				<div class="panel">
					<div class="panel-heading">
						<h4 id="shopTitle" class="panel-title">ㅇㅇ 상점</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="panel" style="border:1px black solid;">
									<div class="panel-heading">
										<h4 id="shopTitle" class="panel-title">판매 리스트</h4>
									</div>
									<div id="sellList" class="panel-body">
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="panel" style="border:1px black solid;">
									<div class="panel-heading">
										<h4 id="shopTitle" class="panel-title">인벤 리스트</h4>
									</div>
									<div id="invenList" class="panel-body">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-8">
				<div class="panel">
					<div class="panel-heading">
						<h4 id="shopTitle" class="panel-title">invoice</h4>
					</div>
					<div id="invoice" class="panel-body">
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h4 id="shopTitle" class="panel-title">구매/판매</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<h2>
								<i class="fa fa-money"></i>
								<span id="myGold">0 골드</span>
							</h2>
						</div>
						<div class="row" style="text-align: center;">
							<button class="btn btn-primary btn-quirk btn-stroke" style="min-width: 100px;">구입</button>
							<button class="btn btn-danger btn-quirk btn-stroke" style="min-width: 100px;">판매</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>