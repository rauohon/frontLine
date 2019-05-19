<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(function () {
		$("#myCharacterInfo").on("click", function () {
			$("#frm").attr("action", "/game/myCharacterInfo.do");
			$("#frm").submit();
		});	
		
	})



</script>


<div class="mainpanel">
	<div class="contentpanel">
		<div class="row">
			<div class="col-sm-12">
				<div id="myCharacterInfo" class="panel" onClick="javascript:void(0)">
					<a>
						<div class="panel-heading">
							<h4 class="panel-title">캐릭터정보</h4>
						</div>
						<div class="panel-body">
							<h2>
								현재 <span class="text-primary">(레벨 : 10 | 경험치 :  90% (90 / 100)달성 )</span>
							</h2>
							내 캐릭터의 상태를 확인 합니다.
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">던전</h4>
					</div>
					<div class="panel-body">
						<h2>
							<span class="text-primary">몬스터와 전투를 위해 이동합니다.</span>
						</h2>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="panel panel-announcement">
					<div class="panel-heading">
						<h4 class="panel-title">퀘스트</h4>
					</div>
					<div class="panel-body">
						<h2>
							<span class="text-primary">준비중 입니다!</span>
						</h2>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="panel panel-announcement">
					<div class="panel-heading">
						<h4 class="panel-title">길드</h4>
					</div>
					<div class="panel-body">
						<h2>
							<span class="text-primary">준비중 입니다!</span>
						</h2>
					</div>
				</div>
			</div>
		</div>
		<div class="row panel-quick-page">
			<div class="col-sm-4 page-user">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">무기상점</h4>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="fimanager flaticon-004-axe"></i>
						</div>
					</div>
				</div>
            </div>
			<div class="col-sm-4 page-user">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">방어구 상점</h4>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="fimanager flaticon-017-shield"></i>
						</div>
					</div>
				</div>
            </div>
			<div class="col-sm-4 page-user">
				<div class="panel">
					<div class="panel-heading">
						<h4 class="panel-title">포션상점</h4>
					</div>
					<div class="panel-body">
						<div class="page-icon">
							<i class="fimanager flaticon-012-potion"></i>
						</div>
					</div>
				</div>
            </div>
            <div>Icons made by <a href="https://www.freepik.com/?__hstc=57440181.29888bd5c90d6ac0b629465382db4f31.1557640002327.1557640002327.1557640002327.1&__hssc=57440181.4.1557640002327&__hsfp=2594295958" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" 		    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
		</div>
	</div>
</div>