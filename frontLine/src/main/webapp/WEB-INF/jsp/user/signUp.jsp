<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<div class="sign-overlay"></div>
	<div class="signpanel"></div>

	<div class="signup">
		<div class="row">
			<div class="col-sm-5">
				<div class="panel">
					<div class="panel-heading">
						<h1>Front-Line</h1>
						<h4 class="panel-title" style="color: gray;">회원가입 해요</h4>
					</div>
					<div class="panel-body">
						<!-- <button class="btn btn-primary btn-quirk btn-fb btn-block invisible">
							Sign Up Using Facebook
						</button>
						<div class="or">or</div> -->
						<form id='valid' action="signUp.do" method="post">
							<div class="form-group mb15">
								<input type="text" name="mbId" class="form-control" placeholder="ID">
							</div>
							<div class="form-group mb15">
								<input type="password" name="mbPwd" class="form-control" placeholder="비밀번호" required="required">
							</div>
							<div class="error">${errorDetail}</div>
							<div class="form-group">
								<button class="btn btn-success btn-quirk btn-block">
									가입하기
								</button>
					<hr class="darken">
								<a href="lgonPage.do"
									class="btn btn-default btn-quirk btn-stroke btn-stroke-thin btn-block btn-sign">이미 회원이면 로그인 하러 가자!</a>
							</div>
						</form>
					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- col-sm-5 -->
			<div class="col-sm-7">
				<div class="sign-sidebar">
					<h3 class="signtitle mb20">Front-Line의 약관</h3>
					<p>
						Front-Line에서 지켜야하는 약속이 있습니다.<br>
						Front-Line에서 지켜야하는 약속이 있습니다.<br>
						Front-Line에서 지켜야하는 약속이 있습니다.<br> 
					</p>

					<br>

					<h4 class="reason">1. 개인정보 사용 동의</h4>
					<p>
						회원 가입하면 개인정보 사용에 동의한다는 내용이 있습니다.<br>
						회원 가입하면 개인정보 사용에 동의한다는 내용이 있습니다.<br>
						회원 가입하면 개인정보 사용에 동의한다는 내용이 있습니다.<br>
						회원 가입하면 개인정보 사용에 동의한다는 내용이 있습니다.<br>
					</p>

					<br>

					<h4 class="reason">2. 책임소재</h4>
					<p>
						문제가 발생하면 누군가 책임지는 내용이 있습니다.<br>
						문제가 발생하면 누군가 책임지는 내용이 있습니다.<br>
						문제가 발생하면 누군가 책임지는 내용이 있습니다.<br>
						문제가 발생하면 누군가 책임지는 내용이 있습니다.<br>
						문제가 발생하면 누군가 책임지는 내용이 있습니다.<br>
					</p>

				</div>
				<!-- sign-sidebar -->
			</div>
		</div>
	</div>
	<!-- signup -->



	<script src="/resources/lib/jquery/jquery.js"></script>
	<script src="/resources/lib/bootstrap/js/bootstrap.js"></script>
	<script src="/resources/lib/select2/select2.js"></script>
	<script src="/resources/lib/jquery-validate/jquery.validate.js"></script>
	<script>
	$(document).ready(function() {
		$('#valid').validate({
			errorLabelContainer: jQuery('#valid div.error')
		  });
	});
	$(function() {
	
	  // Select2 Box
	  $("select.form-control").select2({ minimumResultsForSearch: Infinity });
	
	});
	</script>