<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!--<link rel="shortcut icon" href="../images/favicon.png" type="image/png">-->

<title>Quirk Responsive Admin Templates</title>

<link rel="stylesheet" href="/resources/lib/fontawesome/css/font-awesome.css">
<link rel="stylesheet" href="/resources/lib/select2/select2.css">

<link rel="stylesheet" href="/resources/css/quirk.css">

<script src="/resources/lib/modernizr/modernizr.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="../lib/html5shiv/html5shiv.js"></script>
  <script src="../lib/respond/respond.src.js"></script>
  <![endif]-->
</head>

<body class="signwrapper">

	<div class="sign-overlay"></div>
	<div class="signpanel"></div>

	<div class="signup">
		<div class="row">
			<div class="col-sm-5">
				<div class="panel">
					<div class="panel-heading">
						<h1>Quirk</h1>
						<h4 class="panel-title">Create an Account!</h4>
					</div>
					<div class="panel-body">
						<button class="btn btn-primary btn-quirk btn-fb btn-block invisible">
							Sign Up Using Facebook
						</button>
						<div class="or">or</div>
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
					<h3 class="signtitle mb20">Two Good Reasons to Love Quirk</h3>
					<p>When it comes to websites or apps, one of the first
						impression you consider is the design. It needs to be high quality
						enough otherwise you will lose potential users due to bad design.</p>
					<p>Below are some of the reasons why you love Quirk.</p>

					<br>

					<h4 class="reason">1. Attractive</h4>
					<p>When your website or app is attractive to use, your users
						will not simply be using it, they’ll look forward to using it.
						This means that you should fashion the look and feel of your
						interface for your users.</p>

					<br>

					<h4 class="reason">2. Responsive</h4>
					<p>Responsive Web design is the approach that suggests that
						design and development should respond to the user’s behavior and
						environment based on screen size, platform and orientation. This
						would eliminate the need for a different design and development
						phase for each new gadget on the market.</p>

					<hr class="invisible">

					<div class="form-group">
						<a href="lgonPage.do"
							class="btn btn-default btn-quirk btn-stroke btn-stroke-thin btn-block btn-sign">Already
							a member? Sign In Now!</a>
					</div>
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

</body>
</html>
