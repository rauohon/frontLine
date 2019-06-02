<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	var g_invoice_cnt = 1;
	var g_input_oldValue = 0;
	var g_init
	
	$(function () {
		iqryShopItemList();
		
		$(document).on("click", ".chk_item", function () {
			randerInvoice($(this));
		});
		
		$(document).on({
			focus: function () {
				g_input_oldValue = $(this).val();
			},
			change: function () {
				var regexp = /[^0-9]/g;
				var val = $(this).val();
				var flag = $(this).data("flag");
				
				if (regexp.test(val)) {
					$(this).val(g_input_oldValue);
				}
				
				regexp = /[^1-9]/g;
				var a = val.length;
				if (val.length == 1 && regexp.test(val)) {
					$(this).val(g_input_oldValue);
				}
				console.log("'233'")
				if (flag != "shop") {
					if ((g_input_oldValue * 1) < (val * 1)) {
						popup.create("보유 수량을 초과했어요.");
						$(this).val(g_input_oldValue);
					}
				}
			}
		}, ".itAmount");
		
		$(document).on({
			focus: function () {
				g_input_oldValue = $(this).val();
			},
			change: function () {
				var regexp = /[^0-9]/g;
				var val = $(this).val();
				
				if (regexp.test(val)) {
					val = val.replace(regexp,"");
					$(this).val(val);
				}

				if (val.length == 1) {
					regexp = /[^1-9]/g;
					if (regexp.test(val)) {
						val = 1;
						$(this).val(1);
					}
				}
				
				var maxQnty = $(this).data("maxamount");
				
				if (val > maxQnty) {
					popup.create("보유 수량을 초과했어요.");
					$(this).val(g_input_oldValue);
					return false;
				}
				
				var basCost = $(this).parent().parent().children().eq(2).html() * 1;
				var totCost = val * basCost;
				var preFlag = $(this).parent().parent().children().eq(4).html().substring(0,5);
				
				var html = preFlag + totCost;
				
				$(this).parent().parent().children().eq(4).html(html);
				
				calcInvoceCostSum();
			}
		}, ".itemQnty");
		
		$("#btnApproval").on("click", function () {
			var chkGold = $("#checkGold").html() * 1;
			var myGold = $("#myGold").html() * 1;
			
			if ( (myGold + chkGold) < 0 ) {
				popup.create("소지금이 부족합니다.");
				return false;
			}
			
			sendShopInvoice();
			
		});
		
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
		var html = charaDtlInfo.mcGold;
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
// 		html += "				<label class=\"ckbox ckbox-primary\">";
// 		html += "					<input type=\"checkbox\"><span></span>";
// 		html += "				</label>";
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
			html += "			<td class=\"chk_item\">";
//	 		html += "				<label class=\"ckbox ckbox-primary\">";
			html += "					<input type=\"checkbox\">";
//			html += "					<span></span>";
//	 		html += "				</label>";
			html += "			</td>";
			if (flag == "shop") {
				html += "			<td data-subject=\"" + flag + "\" data-itemcd=\"" + itemList[i].itCode + "\">" + itemList[i].itName + "</td>";
			} else {
				html += "			<td data-subject=\"" + flag + "\" data-itemcd=\"" + itemList[i].ivItcode + "\">" + itemList[i].itName + "</td>";
			}
			if (cmmn.isEmpty(itemList[i].itPlusCnt)) {
				html += "			<td> x" + itemList[i].itMultiCnt + "</td>";
			} else {
				html += "			<td> +" + itemList[i].itPlusCnt + "</td>";
			}
			if (flag == "shop") {
				html += "			<td>" + itemList[i].itCost + "</td>";
				html += "			<td><input class=\"itAmount\" data-flag=\"shop\" type=\"text\" value=\"1\"></td>";
			} else {
				html += "			<td>" + itemList[i].itCost + "</td>";
				html += "			<td><input class=\"itAmount\" data-flag=\"inven\" type=\"text\" data-maxamount=\"" + itemList[i].ivAmount + "\" value=\"" + itemList[i].ivAmount + "\"></td>";
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
	
	function randerInvoice ($this) {
		
		var flag = $this.children().prop("checked");

		var $chkBoxSiblings = $this.siblings();
		var itemCd = $chkBoxSiblings.eq(0).data("itemcd");
		var shopInven = $chkBoxSiblings.eq(0).data("subject");

		var itemName = $chkBoxSiblings.eq(0).text();
		var itemAbli = $chkBoxSiblings.eq(1).text();
		var itemCost = $chkBoxSiblings.eq(2).text();
		var itemQnty = $chkBoxSiblings.eq(3).children(0).val();
		var maxQnty = $chkBoxSiblings.eq(3).children(0).data("maxamount");
		
		if (flag) {
			if ($("#" + shopInven + "-" + itemCd).length > 0) {
				return false;
			}
			
			$chkBoxSiblings.eq(3).children(0).prop("disabled",true);
			
			var html = "";
			if (shopInven == "shop") {
				html += "		<tr id=\"shop-" + itemCd + "\">";
			} else {
				html += "		<tr id=\"inven-" + itemCd + "\">";
			}
			html += "			<td class=\"invoice_cnt\">" + g_invoice_cnt + "</td>";
			html += "			<td>" + itemName + "</td>";
			html += "			<td>" + itemCost + "</td>";
			if (shopInven == "shop") {
				html += "			<td><input type=\"text\" class=\"itemQnty\" placeholder=\"\" value=\"" + itemQnty + "\"></td>";
				html += "			<td class=\"invoice_cost\">(구입) " + itemCost * itemQnty + "</td>";
			} else {
				html += "			<td><input type=\"text\" data-maxamount=\"" + maxQnty + "\" class=\"itemQnty\" placeholder=\"\" value=\"" + itemQnty + "\"></td>";
				html += "			<td class=\"invoice_cost\">(판매) " + itemCost * itemQnty + "</td>";
			}
			html += "		</tr>";
			
			$("#invoice").append(html);
			
			g_invoice_cnt += 1;
			
		} else {
			$("#" + shopInven + "-" + itemCd).remove();
			
			$chkBoxSiblings.eq(3).children(0).prop("disabled",false);

			g_invoice_cnt -= 1;
			
		}
		
		reCountIvoiceRow();
		calcInvoceCostSum();
		
	}
	
	function reCountIvoiceRow () {
		$(".invoice_cnt").each(function (index, item) {
			$(this).html(index + 1);
		});
	}
	
	function calcInvoceCostSum () {
		var buyCost = 0;
		var sellCost = 0;
		
		$(".invoice_cost").each(function (index, item) {
			var $html = $(this).html();
			var flag = $html.substring(0,5);
			var totCost = $html.substring(5,$(this).html().length) * 1;
			if (flag.indexOf("구입") > 0) {
				buyCost += totCost;
			} else {
				sellCost += totCost;
			}
		});
		
		if ($("#invoice").children().length == 0) {
			//$("#checkGold").html("결제 금액 : ");
		} else {
			$("#checkGold").html(( (buyCost * -1) + sellCost ));
		}
	}
	
	function sendShopInvoice () {
		var jsonArray = [];
		var jsonEl;
		
		$("#invoice").find("tr").each(function (index, item) {
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
		
		console.log(jsonArray);
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
					<div class="panel-body">
						<table class="table table-bordered nomargin table-hover table-striped-col">
							<colgroup>
								<col style="width:5%;">
								<col style="width:30%;">
								<col style="width:20%;">
								<col style="width:20%;">
								<col style="width:25%;">
							</colgroup>
							<thead>
								<tr>
									<th>No.</th>
									<th>아이템명</th>
									<th>단위가격</th>
									<th>수량</th>
									<th>전체가격</th>
								</tr>
							</thead>
							<tbody id="invoice">
							</tbody>
						</table>
						<div>
							<h3>결제 금액 : <span id="checkGold"></span></h3>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">구매/판매</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<h2>
								<i class="fa fa-money"></i>
								<span><span id="myGold"></span> 골드</span>
							</h2>
						</div>
						<div class="row" style="text-align: center;">
							<button id="btnApproval" class="btn btn-primary btn-quirk btn-stroke" style="min-width: 100px;">확인</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>